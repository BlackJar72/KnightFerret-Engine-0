//Copyright (c) Jared Blackburn 2017
package jaredbgreat.arcade.ui;

import android.content.Context;
import android.view.SurfaceView;
import jaredbgreat.arcade.util.Registry;

/**
 *
 * @author Jared Blackburn
 */
public class GameView extends SurfaceView {
    private static GameView window;
    
    StartPanel startPanel;
    IView gamePanel;
    IView currentPanel;
    Registry<IView> panels;
    
    
    
    private GameView(Context context) {
        super(context);
        panels = new Registry<>();
        startPanel = new StartPanel(this);
        currentPanel = startPanel; 
        currentPanel.start();
    }
    
    
    /**
     * Retrieve the singleton window instance.
     * 
     * @param context
     * @return 
     */
    // Maybe I should start looking for a non-singleton way to do this for 
    // future projects...?
    public static GameView getMainWindow(Context context) {
        if(window == null) {
            window = new GameView(context);
        }
        return window;
    }
    
    
    /**
     * Draw the currently active panel.  This is called per frame to redraw 
     * the games graphics.
     */
    public void draw() {
        currentPanel.draw();
    }
    
    
    /**
     * Delete contents.
     * 
     * Probably not necessary but included for completeness.  In an OpnelGL 
     * context such a cleanup would be more necessary.
     */
    public final void cleanup() {
        currentPanel = null;
        gamePanel = null;
        startPanel = null;
    }
    
    
    /**
     * This will add an IView (the panel) to the registry of panels that 
     * may be rendered to the screen.
     * 
     * @param name
     * @param panel
     * @return 
     */
    public int addPanel(String name, IView panel) {
        panels.add(name, panel);
        // TODO: Anything to set it up for rendering?  IDK?!
        return panels.getID(name);
    }
    
    
    /**
     * Sets the panel to the named panel, then starts the panels activity.
     * 
     * @param name 
     */
    public void setPanel(String name) {
        currentPanel = panels.getFromName(name);
        currentPanel.start();
    }
    
    
    /**
     * Begin a new game.  This will switch the view to the game panel and 
     * initialize it.
     * 
     * Note: This is the old method; its setPanel(String name) is generally 
     * preferable.
     */
    @Deprecated
    public void startGame() {        
        gamePanel.start();
        currentPanel = gamePanel;
    }
    
    
    /**
     * Switch back to the start panel and initialize it.  This is called after 
     * a game is over in order to return to the start screen.
     * 
     * Note: This is the old method; its setPanel(String name) is generally 
     * preferable.
     */
    @Deprecated
    public void endGame() {
        startPanel.start();
        currentPanel = startPanel; 
    }
}
