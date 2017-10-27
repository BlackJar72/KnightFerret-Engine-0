//Copyright (c) Jared Blackburn 2017
package jaredbgreat.arcade.ui;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import jaredbgreat.arcade.ui.graphics.Graphic;

/**
 *
 * @author Jared Blackburn
 */
public class StartPanel implements IView {
    private final SurfaceView surface;
    private final int startScreen;
    private final SurfaceHolder holder;
    
    public StartPanel(SurfaceView screen) {
        surface = screen;
        startScreen = Graphic.registry.getID("title");
        holder = surface.getHolder();
    }

    @Override
    public void draw() {
        if(holder.getSurface().isValid()) {
            Graphic.draw(startScreen, 0, 0, 0);
            // Double buffering -- draw to the screen
            Canvas canvas = holder.lockCanvas();        
            Rect area = canvas.getClipBounds();
            canvas.drawBitmap(Graphic.getGameScreen(), null, area, null);
            holder.unlockCanvasAndPost(canvas);
        }
    }
    

    @Override
    public void start() {}
    
}
