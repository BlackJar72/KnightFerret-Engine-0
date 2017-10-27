package jaredbgreat.arcade.game;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import jaredbgreat.arcade.loader.AudioLoader;
import jaredbgreat.arcade.loader.ImageLoader;
import jaredbgreat.arcade.ui.graphics.Font;
import jaredbgreat.arcade.ui.GameView;

/**
 *
 * @author Jared Blackburn 
 */
public abstract class GameActivity extends Activity {
    protected Window window;
    protected WakeLock wakelock;
    protected GameView view;
    protected boolean wasPaused;
    protected BaseGame game; // Set to a subclass of game by a subclass of this
    protected int screenX, screenY;
    protected boolean portrait;
    
    /*
     * REMEMBER! Use the manifest to set orientation.
     */
    
    
    @Override
    public void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        findScreenSize();
        wasPaused = false;
        window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        PowerManager powerman = (PowerManager) 
                this.getSystemService(Context.POWER_SERVICE);
        ImageLoader.initGraphics();
        AudioLoader.initAudio();
        Font.init();
        view = GameView.getMainWindow(this);
        setContentView(view);
        wakelock = powerman.newWakeLock(PowerManager.FULL_WAKE_LOCK, "GLGame");        
    }
    
    
    @Override
    public void onResume() {
        super.onResume();
        wakelock.acquire();
        // Normally, do not unpause game automatically
        game.onResume();
    }
    
    
    @Override
    public void onPause() {
        super.onPause();
        game.onPause(); // Must be first.
        wasPaused = game.isPaused();
        game.setPause(true);
        wakelock.release();
    }
    
    
    // TODO / FIXME: Make this fit better variant setting (landscape / portrait).
    public void findScreenSize() {
        portrait = getResources().getConfiguration().orientation 
                == Configuration.ORIENTATION_PORTRAIT;
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        screenX = metrics.widthPixels;
        screenY = metrics.heightPixels;
    }
    
    
    public int getScreenX() {
        return screenX;
    }
    
    
    public int getScreenY() {
        return screenY;
    }
    
}
