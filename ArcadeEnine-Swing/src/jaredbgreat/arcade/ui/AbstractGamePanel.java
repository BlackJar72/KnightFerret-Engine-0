//Copyright (c) Jared Blackburn 2017
package jaredbgreat.arcade.ui;


import jaredbgreat.arcade.ui.graphics.Graphic;
import jaredbgreat.arcade.util.GameLogger;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JPanel;

/**
 *
 * @author Jared Blackburn
 */
public abstract class AbstractGamePanel extends JPanel implements IView {
    private final Toolkit tk = Toolkit.getDefaultToolkit();
    
    
    /**
     * Draw the screen for the in-game view.
     */
    @Override
    public final void draw() {
        // Put game / screen specific drawing in the drawGame() method
        drawGame();
        // Double buffering -- draw to the screen
        Graphics g;
        try {
            g = this.getGraphics();
            if(g != null) {
                drawImage(g);
                tk.sync();
                g.dispose();
            }
        } catch (Exception ex) {
            GameLogger.mainLogger.logException(ex);
        }
    }
    
    
    public abstract void drawGame();
    
    
    /**
     * Switch to the in-game view.
     */
    @Override
    public void start() {}
    
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawImage(g);        
    }
    
    
    /**
     * Actually render the drawn images to the screen.  This is for the purpose 
     * of double buffering the graphics draws by drawing the updated Graphics 
     * object all at once.
     * 
     * @param g 
     */
    private void drawImage(Graphics g) {
        Image img = Graphic.getGameScreen();
        if(img != null) {
            g.drawImage(img, 0, 0, //WIDTH, WIDTH, 
                    this.getWidth(), 
                    this.getHeight(),
                    null);
        }
        Graphic.clearScreen();
    }
    
}
