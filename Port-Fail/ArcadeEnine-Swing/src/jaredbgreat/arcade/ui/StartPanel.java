//Copyright (c) Jared Blackburn 2017
//A game with play similar to arcade space shooter such as Space Ivaders and
//Galega, but on the ground with monsters in stead of alien ships
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
public class StartPanel extends JPanel implements IView {
    private final Toolkit tk = Toolkit.getDefaultToolkit();
    private final int startScreen;
    
    public StartPanel() {
        startScreen = Graphic.registry.getID("title");
    }

    @Override
    public void draw() {
        // Draw to the hidden graphics buffer
        Graphic.draw(startScreen, 0, 0, 0);
        
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
    

    @Override
    public void start() {}
    
    
    
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
