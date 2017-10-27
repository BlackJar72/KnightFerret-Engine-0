package jaredbgreat.arcade.ui.input.clickzone;

import jaredbgreat.arcade.ui.input.MouseEvent;
import android.graphics.Bitmap;
import jaredbgreat.arcade.ui.graphics.Graphic;
import jaredbgreat.arcade.ui.input.GraphicZone;

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
    

    public ActiveGraphicZone(int x, int y, Bitmap graphic) {
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
