package jaredbgreat.arcade.ui.input;

import jaredbgreat.arcade.ui.input.clickzone.IViewZone;
import android.graphics.Bitmap;
import jaredbgreat.arcade.ui.graphics.Graphic;

/**
 *
 * @author jared
 */
public class GraphicZone implements IViewZone {
    int x1, y1, x2, y2;
    private final Bitmap image;
    private boolean active;

    public GraphicZone(int x, int y, int graphic) {
        image = Graphic.registry.get(graphic).getImage();
        x1 = x;
        y1 = y;
        x2 = x + image.getWidth();
        y2 = y + image.getHeight();
        active = true;
    }
    

    public GraphicZone(int x, int y, String graphic) {
        image = Graphic.registry.getFromName(graphic).getImage();
        x1 = x;
        y1 = y;
        x2 = x + image.getWidth();
        y2 = y + image.getHeight();
    }
    

    public GraphicZone(int x, int y, Graphic graphic) {
        image = graphic.getImage();
        x1 = x;
        y1 = y;
        x2 = x + image.getWidth();
        y2 = y + image.getHeight();
    }
    

    public GraphicZone(int x, int y, Bitmap graphic) {
        image = graphic;
        x1 = x;
        y1 = y;
        x2 = x + image.getWidth();
        y2 = y + image.getHeight();
    }
    

    @Override
    public boolean isActivated(MouseEvent e) {
        if(active && !((e.x < x1) || (e.x > x2) || (e.y < y1) || (e.y > y2))) {
            return (image.getPixel(e.x - x1, e.y - y1) & 0xff000000) != 0;
        }
        return false;
    }
    
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
}
