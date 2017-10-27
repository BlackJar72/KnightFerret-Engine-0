package jaredbgreat.arcade.ui.input;

/**
 *
 * @author jared
 */
public class PointerEvent {
    public static final int DOWN = 0;
    public static final int UP   = 1;
    public static final int DRAG = 2;
    
    public int x, y;
    public int type, index;
}
