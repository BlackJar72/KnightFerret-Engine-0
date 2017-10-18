//Copyright (c) Jared Blackburn 2017
package jaredbgreat.arcade.ui;

import jaredbgreat.arcade.ui.graphics.Graphic;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

/**
 *
 * @author Jared Blackburn
 */
public class StartPanel extends AbstractClickablePanel {
    private final Toolkit tk = Toolkit.getDefaultToolkit();
    private final int startScreen;
    
    public StartPanel() {
        super();
        startScreen = Graphic.registry.getID("title");
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

    @Override
    public void drawGame() {
        Graphic.draw(startScreen, 0, 0, 0);
        drawWidgets();
    }
    
}
