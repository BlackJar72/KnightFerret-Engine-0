package jaredbgreat.arcade.ui.input;

import jaredbgreat.arcade.entity.IInputController;

/**
 *
 * @author jared
 */
public class InputAgregator {
    private static InputAgregator in;
    private int commands;
    
    private final KeyInput KEYS;
    private final MouseInput MOUSE;
    private final IInputController inputController;
    
    
    public InputAgregator(int vectorSize, KeyInput keys, MouseInput mouse, 
            IInputController controller) {
        KEYS = keys;
        MOUSE = mouse;
        inputController = controller;
    }
    
    
    public static void init(int vectorSize, KeyInput keys, MouseInput mouse, 
            IInputController controller) {
        in = new InputAgregator(vectorSize, keys, mouse, controller);        
    }
    
    
    public static InputAgregator getInputAgregator() {
        return in;
    }
    
    
    public KeyInput getKeyListener() {
        return KEYS;
    }
    
    
    public MouseInput getMouseListener() {
        return MOUSE;
    }
    
    
    
    public void update() {
        commands  = 0;
        commands |= KEYS.getCommands();
        inputController.update(commands);
    }
    
    
    public void clear() {
        commands = 0;
        KEYS.clear();
    }
    
}
