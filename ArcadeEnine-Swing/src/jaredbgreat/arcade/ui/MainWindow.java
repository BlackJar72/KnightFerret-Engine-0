//Copyright (c) Jared Blackburn 2017
package jaredbgreat.arcade.ui;

import jaredbgreat.arcade.ui.graphics.Graphic;
import jaredbgreat.arcade.ui.input.InputAgregator;
import jaredbgreat.arcade.util.Registry;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Jared Blackburn
 */
public class MainWindow extends JFrame {
    private static MainWindow window;

    public static MainWindow getMainWindow() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public final int xSize;
    public final int ySize;
    
    CardLayout layout;
    
    JPanel contentPane;
    StartPanel startPanel;
    IView gamePanel;
    IView currentPanel;
    Registry<IView> panels;
    
    
    
    @SuppressWarnings({ "unchecked"})
    private MainWindow(int width, int height) {
        super();
        xSize = width;
        ySize = height;
        setSize(xSize, ySize);
        setTitle("Ship Swarm Shoot-Up");
        setIconImages(Graphic.getIcons());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        layout = new CardLayout();
        contentPane = new JPanel(layout);
        setContentPane(contentPane);
        
        panels = new Registry<>();
        
        startPanel = new StartPanel();
        startPanel.setBackground(Color.BLACK);
        contentPane.add(startPanel, "start");
        panels.add("start", currentPanel);
        
        addKeyListener(InputAgregator.getInputAgregator().getKeyListener());        
        addMouseListener(InputAgregator.getInputAgregator().getMouseListener());
        
        currentPanel = startPanel; 
        layout.show(contentPane, "game");
        
        currentPanel.start();
        
        setLocationRelativeTo(null);
        requestFocus();
        setVisible(true);        
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
        contentPane.add((Component) panel, name);
        return panels.getID(name);
    }
    
    
    /**
     * This will set the named panel to be the panel for game play.
     * 
     * @param name 
     */
    public void setGamePanel(String name) {
        gamePanel = panels.getFromName(name);
    }
    
    
    /**
     * This will set the with the ID to be the panel for game play.
     * 
     * @param id 
     */
    public void setGamePanel(int id) {
        gamePanel = panels.get(id);
    }
    
    
    /**
     * Retrieve the singleton window instance.
     * 
     * @param width
     * @param height
     * @return 
     */
    // Maybe I should start looking for a non-singleton way to do this for 
    // future projects...?
    public static MainWindow getMainWindow(int width, int height) {
        if(window == null) {
            window = new MainWindow(width, height);
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
     * This will forward a mouse
     * 
     * @param e 
     */
    public void clickMouse(MouseEvent e) {
        if(currentPanel instanceof AbstractClickablePanel) {
            ((AbstractClickablePanel)currentPanel).activateWidgets(e);
        }
    }
    
    
    /**
     * Delete contents.
     * 
     * Probably not necessary but included for completeness.  In an OpnelGL 
     * this context such a cleanup would be more necessary.
     */
    public final void cleanup() {
        panels.clear();
        panels = null;
        currentPanel = null;
        startPanel = null;
        contentPane = null;
        layout = null;
    }
    
    
    /**
     * Sets the panel to the named panel, then starts the panels activity.
     * 
     * @param name 
     */
    public void setPanel(String name) {
        currentPanel = panels.getFromName(name);
        currentPanel.start();
        layout.show(contentPane, name);
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
        layout.show(contentPane, "game");     
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
        layout.show(contentPane, "start");
    }
}
