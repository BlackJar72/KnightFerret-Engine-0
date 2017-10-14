package jaredbgreat.arcade.ui.input;

import jaredbgreat.arcade.ui.MainWindow;
import java.util.List;

/**
 *
 * @author Jared Blackburn
 */
public class InputAgregator {
    private final KeyInput      keyboard;
    private final PointerInput  pointer;
    
    
    public InputAgregator(MainWindow view) {
        keyboard = new KeyInput(view);
        pointer  = new PointerInput(view);
    }
    
    
    public void update() {
        keyboard.update();
    }
    
    
    public List<KeyboardEvent> getPressedKeys() {
        return keyboard.getPressed();
    }
    
    
    public List<KeyboardEvent> getReleasedKeys() {
        return keyboard.getReleased();
    }
    
    
    public KeyInput getKeyInput() {
        return keyboard;
    }
    
    
    /*------------------------------------------------------------------------*/
    /*                     KEYBOARD INPUT FUNCTIONS                           */
    /*------------------------------------------------------------------------*/
    
    
    public List<PointerEvent> getPointerDown() {
        return pointer.getPressed();
    }
    
    
    public List<PointerEvent> getPointerUp() {
        return pointer.getReleased();
    }
    
    
    public List<PointerEvent> getPointerDrag() {
        return pointer.getDragged();
    }
    
    
    public PointerInput getPointerInput() {
        return pointer;
    }

}
