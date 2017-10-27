/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaredbgreat.arcade.ui.input;

/**
 * This is an interface from translating events from the keyboard.  Game and 
 * platform specific instance of this need to be supplied to KeyInput in 
 * order to supply and game and platform specific translation of events from 
 * the key board.
 * 
 * @author Jared Blackburn
 */
public interface IKeyTranslator {
    /**
     * This takes in a KeyEvent and translates it into a int.  Note that this 
     * interface will vary between platforms (Android and AWT keyboard events 
     * are not interchangeable) -- this is the PC (AWT) version.
     * 
     * @param in 
     * @return  but flags for actions
     */
    public int translate(KeyboardEvent in);
    
    
    /**
     * This if for processing events into commands (meaning method calls) for 
     * other game systems.  It is separate in the Android version so that the 
     * commands bit flag int can be handled immediately (on the main UI thread) 
     * while calls to other game systems can be handled by the game loop on the 
     * games thread.
     * 
     * @param in 
     */
    public void process(KeyboardEvent in);   
}
