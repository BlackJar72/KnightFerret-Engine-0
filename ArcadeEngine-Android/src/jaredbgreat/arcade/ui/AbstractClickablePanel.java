package jaredbgreat.arcade.ui;

import android.view.SurfaceView;
import jaredbgreat.arcade.ui.graphics.IDrawable;
import jaredbgreat.arcade.ui.input.clickzone.IActiveViewZone;
import jaredbgreat.arcade.ui.input.MouseEvent;
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
    
    
    protected void drawWidgets() {
        int n = widgets.size();
        for(int i = 0; i < n; i++) {
            IActiveViewZone widget = widgets.get(i);
            if(widget instanceof IDrawable) {
                ((IDrawable)widget).draw();
            }            
        }
    }
    
    
    public void addWidget(IActiveViewZone zone) {
        widgets.add(zone);
    }
    
    
    public void removeWidget(IActiveViewZone zone) {
        widgets.remove(zone);
    }
    
    
    public void activateWidgets(MouseEvent e) {
        for(int i = widgets.size() - 1; i > -1; i--) {
            widgets.get(i).activate(e);
        }
    }
    
}
