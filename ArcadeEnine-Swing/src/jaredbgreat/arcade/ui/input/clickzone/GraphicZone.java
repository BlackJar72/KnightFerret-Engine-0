package jaredbgreat.arcade.ui.input.clickzone;

import jaredbgreat.arcade.ui.MainWindow;
import jaredbgreat.arcade.ui.input.clickzone.IViewZone;
import jaredbgreat.arcade.ui.graphics.Graphic;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 *
 * @author jared
 */
public class GraphicZone implements IViewZone {
    int x1, y1, x2, y2;
    private final BufferedImage image;
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
    

    public GraphicZone(int x, int y, BufferedImage graphic) {
        image = graphic;
        x1 = x;
        y1 = y;
        x2 = x + image.getWidth();
        y2 = y + image.getHeight();
    }
    

    @Override
    public boolean isActivated(MouseEvent e) {
        int x = (e.getX() * Graphic.width) 
                / MainWindow.getMainWindow().getWidth();
        int y = (e.getY() * Graphic.height) 
                / MainWindow.getMainWindow().getHeight();
        if(active && !((x < x1) || (x > x2) || (y < y1) || (y > y2))) {
            return (image.getRGB(x - x1, y - y1) & 0xff000000) != 0;
        }
        return false;
    }
    
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
}
