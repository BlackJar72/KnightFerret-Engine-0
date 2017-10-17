package jaredbgreat.arcade.ui;

import jaredbgreat.arcade.ui.input.clickzone.IActiveViewZone;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 *
 * @author Jared Blackburn
 */
public abstract class AbstractClickablePanel extends AbstractGamePanel {
    private ArrayList<IActiveViewZone> widgets;
    
    
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
