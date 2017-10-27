//Copyright (c) Jared Blackburn 2017
package jaredbgreat.arcade.game;

import jaredbgreat.arcade.entity.Entity;
import jaredbgreat.arcade.ui.GameView;
import jaredbgreat.arcade.ui.input.InputAgregator;
import jaredbgreat.arcade.util.GameLogger;
import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author Jared Blackburn
 */
public abstract class BaseGame implements Runnable {
    public volatile static BaseGame game;
    public volatile GameView view;
    private Thread thread;
    public volatile Timer timer;
    public final Random rng;
    public InputAgregator in;
    
    private volatile boolean running = true,   paused   = false, 
                             inGame  = false,  gameOver = false,
                             inDemo  = false;
    
    private static final int baseFps = 60;
    private static final float expectedTime  = 1f / baseFps;
    private static final long  expectedSleep = 1000 / baseFps;
    private double lastTime, thisTime, passedTime;
    private double delta;
    private double gameOverTime;
    private int   inputBlocking;
    
    private boolean tmpPause = false;
    private float tmpPauseTime = 0f;
    
    private final ArrayList<Entity> spawns;
    private final ArrayList<Entity> kills;
    
    private ILoopElement update;
    private ILoopElement render;
    private ILoopElement physical;
    private ILoopElement input;
    
    
    private BaseGame() {
        timer  = new Timer();
        spawns = new ArrayList<>();
        kills  = new ArrayList<>(); 
        rng = new Random(System.nanoTime());
        init();
    }
    
    
    /**
     * Initialize variable for a new game.
     */
    private void init() {
        
    }
    
    //FIXME: How to handle main windown in new launching syste?
    //(Perhaps pass it in for PC, and pass in an activity or canvas on Android?)
    
    public void start() {
        game.timer.reset();
        inputBlocking = 0;
        game.loop(/*window*/);
    }
    

    public void loop(/*MainWindow window*/) {
        while(running) {
            updateDelta();
            render.update(); //window.draw();
            input.update();  // KeyInput.in.update();
            //Toast.update();
            if(tmpPause && (inGame || inDemo)) {
                doTmpPause();
            } else {
                if((inGame || inDemo || gameOver) && !paused) {                    
                    physical.update(); //Collision.testCollision();
                    update.update();   //Entity.updateAll(delta);
                                       //EntityPlayer.player.respawn();
                    doKills();
                    doSpawns();
                }
            }
            if(gameOver) {
                gameOver();
            }
            gameSleep();
        }
    }
    
    
    private void updateDelta() {
        lastTime   = thisTime;
        thisTime   = timer.getTime();
        passedTime = (thisTime - lastTime);
        delta      =  passedTime / expectedTime;
//        System.out.print("Elapsed = " + passedTime * 1000 + " ms ->  ");
//        System.out.println("FPS = " + BASE_FPS / delta);
//        System.out.println("delta = " + delta);
        
    }
    
    
    private void gameSleep() {
        //System.out.println((long)((timer.getTime() - thisTime) * 1000));
        try {
            long time =  Math.max(expectedSleep 
                        - (long)((timer.getTime() - thisTime) * 1000), 
                    10);
            
            //System.out.println("Thread.sleep(" + time +")");
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            GameLogger.mainLogger.logException(ex);
        }
    }
    
    
    public void spawn(Entity entity) {
        spawns.add(entity);
    }
    
    
    public void kill(Entity entity) {
        kills.add(entity);
    }
    
    
    private void doSpawns() {
        Entity.addAll(spawns);
        spawns.clear();
    }
    
    
    private void doKills() {
        Entity.removeAll(kills);
        kills.clear();        
    }
    
    
    public Random getRNG() {
        return rng;
    }
    
    
    public void startGame() {
        if(inGame) {
            return;
        }
        Entity.clearList();
        inGame = true;
        inDemo = false;
        paused = false;
        gameOver = false;
        view.startGame();
    }
    
    
    void setPause(boolean pause) {
        paused = pause;
        if(paused) {
            timer.pause();
        } else {
            timer.resume();
        }
    }
    
    
    public void togglePause() {        
        paused = !paused;
        if(paused) {
            timer.pause();
        } else {
            timer.resume();
        }
    }
    
    
    private void doTmpPause() {
        tmpPauseTime -= passedTime;
        if(tmpPauseTime <= 0f) {
            tmpPauseTime = 0f;
            tmpPause = false;
        }
    }
    
    
    public void endGame() {
        inGame   = false;
        inDemo   = false;
        paused   = false;
        gameOver = true;
        inputBlocking = baseFps;
        gameOverTime = thisTime + 30f;    
    }
    
    
    private void gameOver() {
        if(inputBlocking > 0) {
            inputBlocking--;
        }
        if(gameOverTime <= thisTime) {
            gameOverTime = -1;
            gameOver = false;
            inputBlocking = 0;
            view.endGame();
        }
    }
    
    
    public boolean isInGame() {
        return inGame;
    }
    
    
    public boolean blockInput() {
        return inputBlocking > 0;
    }
    

    public boolean isGameOver() {
        return gameOver;
    }
    
    
    public boolean isPaused() {
        return paused;
    }
    
    
    public double getTime() {
        return timer.getTime();
    }
    
    
    /**
     * This is to be called whenever the application is paused.  It 
     * has absolutely nothing to do with pausing the game while the app 
     * is running.
     */
    public void onPause() {
        while(true) {
            try {
                thread.join();
                return;
            } catch (InterruptedException ex) {/*do nothing, try again*/}
        }
    }
    
    
    /**
     * This is to be called whenever the application is paused.
     */
    public void onResume() {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        loop();
    }
    
}
