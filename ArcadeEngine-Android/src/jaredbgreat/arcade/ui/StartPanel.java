//Copyright (c) Jared Blackburn 2017
package jaredbgreat.arcade.ui;

import android.view.SurfaceView;
import jaredbgreat.arcade.ui.graphics.Graphic;

/**
 *
 * @author Jared Blackburn
 */
public class StartPanel extends AbstractClickablePanel {
    private final int startScreen;
    
    public StartPanel(SurfaceView screen) {
        super(screen);
        startScreen = Graphic.registry.getID("title");
    }
    

    @Override
    public void start() {}

    @Override
    public void drawGame() {
        Graphic.draw(startScreen, 0, 0, 0);
        drawWidgets();
    }
    
}
