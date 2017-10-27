package jaredbgreat.arcade.ui.input;

import android.content.Context;
import android.view.View;
import java.util.List;

/**
 *
 * @author Jared Blackburn
 */
public class InputAgregator {
    private static InputAgregator in;
    private int commands;
    
    private final KeyInput      keyboard;
    private final PointerInput  pointer;
    private final AcceloInput   accel;
    private final AngleInput    angles;
    
    
    public InputAgregator(Context context, View view) {
        accel  = new AcceloInput(context);
        angles = new AngleInput(context);
        keyboard = new KeyInput(view);
        pointer  = new PointerInput(view);
    }
    
    
    public void update() {
        keyboard.update();
    }
    
    
    /*------------------------------------------------------------------------*/
    /*                 ACCELOROMETER INPUT FUNCTIONS                          */
    /*------------------------------------------------------------------------*/
    
    
    public float getAx() {
        return accel.getAx();
    }
    
    
    public float getAy() {
        return accel.getAy();
    }
    
    
    public float getAz() {
        return accel.getAx();
    }
    
    
    public float[] getAcceleration() {
        return accel.getAccel();
    }
    
    
    public AcceloInput getAcceloInput() {
        return accel;
    }
    
    
    /*------------------------------------------------------------------------*/
    /*                   ORIENTATION INPUT FUNCTIONS                          */
    /*------------------------------------------------------------------------*/
    
    
    public float getyaw() {
        return angles.getYaw();
    }
    
    
    public float getPitch() {
        return angles.getPitch();
    }
    
    
    public float getRoll() {
        return angles.getRoll();
    }
    
    
    public float[] getAngles() {
        return angles.getAngles();
    }
    
    
    public AngleInput getAngleInput() {
        return angles;
    }
    
    
    /*------------------------------------------------------------------------*/
    /*                     KEYBOARD INPUT FUNCTIONS                           */
    /*------------------------------------------------------------------------*/
    
    
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
