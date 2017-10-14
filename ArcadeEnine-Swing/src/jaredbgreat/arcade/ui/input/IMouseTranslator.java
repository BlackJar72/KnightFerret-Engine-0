package jaredbgreat.arcade.ui.input;

import java.awt.event.MouseEvent;

/**
 *
 * @author jared
 */
public interface IMouseTranslator {
    public void getCommands(MouseEvent e, int out);
    public void getVector(MouseEvent e, double[] fvector);
    
}
