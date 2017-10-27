//Copyright (c) Jared Blackburn 2017
package jaredbgreat.arcade.game;

import jaredbgreat.arcade.entity.Entity;
import jaredbgreat.arcade.ui.MainWindow;
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
    protected volatile MainWindow window;
    private Thread thread;
    protected volatile Timer timer;
    protected final Random rng;
    protected InputAgregator in;
    
    protected volatile boolean running = true,   paused   = false, 
                             inGame  = false,  gameOver = false,
                             inDemo  = false;
    
    // FIXME: This should not be a hard-coded value!  (Especially for mobile)
    public static final int BASE_FPS = 60;
    protected static final float expectedTime  = 1f / BASE_FPS;
    protected static final long  expectedSleep = 1000 / BASE_FPS;
    
    protected double lastTime, thisTime, passedTime;
    protected double delta;
    protected double gameOverTime;
    protected int   inputBlocking;
    
    protected boolean tmpPause = false;
    protected float tmpPauseTime = 0f;
    
    protected final ArrayList<Entity> spawns;
    protected final ArrayList<Entity> kills;
    
    protected ILoopElement update;
    protected ILoopElement render;
    protected ILoopElement physical;
    protected ILoopElement spawning;
    protected ILoopElement input;
    
    
    public BaseGame() {
        timer  = new Timer();
        spawns = new ArrayList<>();
        kills  = new ArrayList<>(); 
        rng = new Random(System.nanoTime());
        init();
    }
    
    
    /**
     * Initialize variable for a new game.
     */
    protected void init() {
        
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
    
    
    protected void doSpawns() {
        Entity.addAll(spawns);
        spawns.clear();
    }
    
    
    protected void doKills() {
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
        window.startGame();
    }
    
    
    protected void setPause(boolean pause) {
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
    
    
    protected void doTmpPause() {
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
        inputBlocking = BASE_FPS;
        gameOverTime = thisTime + 30f;    
    }
    
    
    public void gameOver() {
        if(inputBlocking > 0) {
            inputBlocking--;
        }
        if(gameOverTime <= thisTime) {
            gameOverTime = -1;
            gameOver = false;
            inputBlocking = 0;
            window.endGame();
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
