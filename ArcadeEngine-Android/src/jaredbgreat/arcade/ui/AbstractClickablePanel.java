package jaredbgreat.arcade.ui;

import android.view.SurfaceView;
import jaredbgreat.arcade.ui.input.clickzone.IActiveViewZone;
import jaredbgreat.arcade.ui.input.PointerEvent;
import java.util.ArrayList;

/**
 *
 * @author Jared Blackburn
 */
public abstract class AbstractClickablePanel extends AbstractGamePanel {
    private ArrayList<IActiveViewZone> widgets;
    

    public AbstractClickablePanel(SurfaceView screen) {
        super(screen);
    }
    
    
    public void addWidget(IActiveViewZone zone) {
        widgets.add(zone);
    }
    
    
    public void removeWidget(IActiveViewZone zone) {
        widgets.remove(zone);
    }
    
    
    public void checkWidgets(PointerEvent e) {
        for(int i = widgets.size() - 1; i > -1; i--) {
            widgets.get(i).activate(e);
        }
    }
    
}
