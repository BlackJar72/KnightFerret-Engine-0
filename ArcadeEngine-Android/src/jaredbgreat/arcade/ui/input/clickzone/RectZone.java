package jaredbgreat.arcade.ui.input.clickzone;

import jaredbgreat.arcade.ui.input.MouseEvent;

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
        return active && !((e.x < x1) || (e.x > x2) || (e.y < y1) || (e.y > y2));
    }
    
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
}
