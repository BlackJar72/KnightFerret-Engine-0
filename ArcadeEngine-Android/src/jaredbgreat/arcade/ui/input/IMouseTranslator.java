package jaredbgreat.arcade.ui.input;

/**
 *
 * @author jared
 */
public interface IMouseTranslator {
    // This can produce commands, but will primarily be used to send 
    // events to the screen for use with clickzones.  It does not produce an     
    // array or list of coordinates, as the number of events may very and 
    // deciding which to use should be handled in some other way.
    public  int getCommands(PointerEvent e); 
    
    
    /**
     * This if for processing events into commands (meaning method calls) for 
     * other game systems.  It is separate in the Android version so that the 
     * commands bit flag int can be handled immediately (on the main UI thread) 
     * while calls to other game systems can be handled by the game loop on the 
     * games thread.
     * 
     * @param in 
     */
    public void process(PointerEvent in);   
}
