package jaredbgreat.arcade.ui.input.clickzone;


import jaredbgreat.arcade.ui.graphics.Graphic;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

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
    

    public ActiveGraphicZone(int x, int y, Graphic graphic) {
        super(x, y, graphic);
    }
    

    public ActiveGraphicZone(int x, int y, BufferedImage graphic) {
        super(x, y, graphic);
    }
    
    
    public void setAction(IZoneAction act) {
        action = act;
    }
    
    
    @Override
    public void activate(MouseEvent e) {
        if(isActivated(e)) {
            action.activate(e);
        }
    }
    
}
