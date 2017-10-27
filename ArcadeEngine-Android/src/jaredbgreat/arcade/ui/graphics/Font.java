package jaredbgreat.arcade.ui.graphics;

import jaredbgreat.arcade.ui.graphics.Graphic;

/**
 *
 * @author Jared Blackburn
 */
public class Font {
    // Maps pure, 7 bit, ascii characters to TBOs of there image
    private static final int[] mapping = new int[128];
    private static int blank;
    
    
    public static int getID(char in) {
        return mapping[(byte)in & 127];
    }
    
    
    public static void init() {
        String name;
        blank = Graphic.registry.getID("blank");
        for(int i = 0; i < 128; i++) {
            mapping[i] = blank;
        }
        for(Graphic gr : Graphic.registry) {
            name = gr.getName();
            if(name.length() == 1) {
                char it = name.charAt(0);
                mapping[(byte)it & 127] = Graphic.registry.getID(name);
                if(Character.isDigit(it)) {
                    mapping[Character.digit(it, 10)] = Graphic.registry.getID(name);
                }
            }
        }
    }
    
    
    public static void drawChar(char it, int x, int y) {
        Graphic.draw(mapping[(byte)it & 127], 0, x, y);
    }
    
    
    public static void drawString(String it, int x, int y) {
        int size = it.length();
        char[] string = it.toUpperCase().toCharArray();
        for(int i = 0; i < size; i++) {
            drawChar(string[i], x + i * 24, y);
        }
    }
    
}
