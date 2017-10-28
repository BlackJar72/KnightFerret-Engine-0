package jaredbgreat.arcade.ui.input.clickzone;

import jaredbgreat.arcade.ui.MainWindow;
import jaredbgreat.arcade.ui.graphics.Graphic;
import java.awt.event.MouseEvent;

/**
 *
 * @author jared
 */
public class RectZone implements IViewZone {
    private final int x1, y1, x2, y2;
    private boolean active;
    
    
    public RectZone(int x, int y, int w, int h) {
        x1 = x;
        x2 = x + w;
        y1 = y;
        y2 = y + h;
        active = true;
    }
    

    @Override
    public boolean isActivated(MouseEvent e) {
        int x = (e.getX() * Graphic.width) 
                / MainWindow.getMainWindow().getWidth();
        int y = (e.getY() * Graphic.height) 
                / MainWindow.getMainWindow().getHeight();
        return active && !((x < x1) || (x > x2) || (y < y1) || (y > y2));
    }
    
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
}
