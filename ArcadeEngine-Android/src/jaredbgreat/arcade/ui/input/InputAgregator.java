package jaredbgreat.arcade.ui.input;

import android.content.Context;
import android.view.View;
import jaredbgreat.arcade.entity.IInputController;

/**
 *
 * @author Jared Blackburn
 */
public class InputAgregator {
    private static InputAgregator in;
    private int commands;
    private final float[] avector;
    private final IInputController inputController;
    
    private final KeyInput      KEYS;
    private final PointerInput  MOUSE; // Treating touches as virtual mice
    private final AcceloInput   ACCEL;
    private final AngleInput    ANGLES;
    
    
    public InputAgregator(Context context, View view, 
            IInputController controller) {
        ACCEL   = new AcceloInput(context);
        ANGLES  = new AngleInput(context);
        KEYS    = new KeyInput(view);
        MOUSE   = new PointerInput(view);
        avector = new float[6];
        inputController = controller;
    }
    
    
    public static void init(Context context, View view, 
            IInputController controller) {
        in = new InputAgregator(context, view, controller);        
    }
    
    
    public static InputAgregator getInputAgregator() {
        return in;
    }
    
    
    public KeyInput getKeyListener() {
        return KEYS;
    }
    
    
    public PointerInput getMouseListener() {
        return MOUSE;
    }
    
    
    
    public void update() {
        // Update buffered input systems
        KEYS.update();
        KEYS.processEvents();
        MOUSE.update();
        MOUSE.processEvents();
        // Apply and report input
        commands  = 0;
        commands |= KEYS.getCommands();
        inputController.update(commands, avector);
    }
    
    
    public void clear() {
        commands = 0;
        KEYS.clear();
    }
}
