package jaredbgreat.arcade.ui.input;

import java.awt.event.MouseEvent;

/**
 *
 * @author jared
 */
public interface IMouseTranslator {
    public  int getCommands(MouseEvent e);
    public void getVector(MouseEvent e, double[] fvector);
    
}
