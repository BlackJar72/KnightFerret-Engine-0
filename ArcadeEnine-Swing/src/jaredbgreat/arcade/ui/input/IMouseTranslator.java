package jaredbgreat.arcade.ui.input;

import java.awt.event.MouseEvent;

/**
 *
 * @author jared
 */
public interface IMouseTranslator {
    // This can produce commands, but will primarily be used to send 
    // events to the screen for use with clickzones.  It does not produce an     
    // array or list of coordinates, as the number of events may very and 
    // deciding which to use should be handled in some other way.
    public  int getCommands(MouseEvent e); 
    
}
