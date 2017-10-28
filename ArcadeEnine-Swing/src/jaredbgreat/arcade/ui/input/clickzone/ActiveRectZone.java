package jaredbgreat.arcade.ui.input.clickzone;

import java.awt.event.MouseEvent;

/**
 *
 * @author Jared Blackburn
 */
public class ActiveRectZone extends RectZone implements IActiveViewZone {
    private IZoneAction action;
    

    public ActiveRectZone(int x, int y, int w, int h) {
        super(x, y, w, h);
    }
    

    public ActiveRectZone(int x, int y, int w, int h, IZoneAction action) {
        super(x, y, w, h);
        this.action = action;
    }
    
    
    public void setAction(IZoneAction act) {
        action = act;
    }
    
    
    @Override
    public void activate(MouseEvent e) {
        // Should I test for the null case?  Or leave it alone to fail fast?
        if(isActivated(e) && (action != null)) {
            action.activate(e);
        }
    }
    
}
