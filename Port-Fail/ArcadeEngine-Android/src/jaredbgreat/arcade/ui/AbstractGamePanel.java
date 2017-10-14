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
public abstract class AbstractGamePanel implements IView {
    private final SurfaceView surface;
    private final SurfaceHolder holder;
    
    public AbstractGamePanel(SurfaceView screen) {
        surface = screen;    
        holder = surface.getHolder();
    }
    
    
    /**
     * Draw the screen for the in-game view.
     */
    @Override
    public final void draw() {
        if(holder.getSurface().isValid()) {            
            // Put game / screen specific drawing in the drawGame() method
            drawGame();
        
        /* E.g., this in the drawGameFunction...
        Graphic.draw(board, 0, 0, 0);
        for(int i = Entity.listSize() - 1; i > -1; i--) {
            Entity.get(i).draw();
        }        
        Graphic.clearArea(0, 576, 480, 640);
        if(Game.game.isGameOver()) {
            Font.drawString("Game Over", 144, 276);
        }
        */

            // Double buffering -- draw to the screen
            Canvas canvas = holder.lockCanvas();        
            Rect area = canvas.getClipBounds();
            canvas.drawBitmap(Graphic.getGameScreen(), null, area, null);
            holder.unlockCanvasAndPost(canvas);
        }
    }
    
    
    public abstract void drawGame();
    
    
    /**
     * Switch to the in-game view.
     */
    @Override
    public void start() {}    
}
