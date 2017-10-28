package jaredbgreat.arcade.ui.input.clickzone;


import java.awt.event.MouseEvent;

/**
 *
 * @author Jared Blackburn
 */
public class ActiveGraphicZone extends GraphicZone implements IActiveViewZone {
    private IZoneAction action;
    

    public ActiveGraphicZone(int x, int y, int graphic) {
        super(x, y, graphic);
    }
    

    public ActiveGraphicZone(int x, int y, String graphic) {
        super(x, y, graphic);
    }
    

    public ActiveGraphicZone(int x, int y, int graphic, IZoneAction action) {
        super(x, y, graphic);
        this.action = action;
    }
    

    public ActiveGraphicZone(int x, int y, String graphic, IZoneAction action) {
        super(x, y, graphic);
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
