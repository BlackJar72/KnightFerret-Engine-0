//Copyright (c) Jared Blackburn 2017
package jaredbgreat.arcade.ui.graphics;

import jaredbgreat.arcade.util.GameLogger;
import jaredbgreat.arcade.util.Registry;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

/**
 *
 * @author jared
 */
public class Graphic { 
    /**
     * The main registry of all graphics used in the game
     */
    public static final Registry<Graphic> registry = new Registry<>();
    public static ArrayList<ByteBuffer> icons = new ArrayList<>();
    
    final BufferedImage[]   frames;
    int                     frame;   
    
    int id;
    String name;
    
    // FIXME: Remove hard-coded screen sizes; make configurable / initializable
    public static int width  = 480;
    public static int height = 640;
    
    private static Image      gameI;
    private static Graphics2D gameG; 
    
    
    public Graphic(int size) {
        frames = new BufferedImage[size];
        frame  = 0;
    } 
    
    
    public Graphic(List<String> files) {
        frames = new BufferedImage[files.size()];
        GameLogger.mainLogger.logInfo("Found " + frames.length 
                + " images to add");
        for(frame = 0; frame < frames.length; frame++) {
            frames[frame] = loadImage(files.get(frame));
        }
        frame = 0;        
    }
    
    
    public static void init(int x, int y) {
        width  = x;
        height = y;
        gameI = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        gameG = (Graphics2D) gameI.getGraphics();
        
        gameG.setColor(Color.BLACK);
        gameG.setFont(new Font("Serif", Font.BOLD, 24));
        gameG.fillRect(0, 0, width, height);
    }
    
    
    private BufferedImage loadImage(String address) {
        GameLogger.mainLogger.logInfo("Trying to load image " + address);
        try {
            BufferedInputStream stream 
                    = new BufferedInputStream(getClass()
                            .getResourceAsStream(address));
            ImageInputStream img;
            img = ImageIO.createImageInputStream(stream);
            if(img == null) {
                GameLogger.mainLogger.logError("ERROR! ImageInputStream was null!");
                stream.close();
                return null;
            } else {
                BufferedImage out = ImageIO.read(img);
                stream.close();
                return out;
            }
        } catch (IOException ex) {
            GameLogger.mainLogger.logException(ex);
            return null;
        }
    }
    
    
    public static void addGraphic(String name, ArrayList<String> files) {
        System.out.println("Adding graphic " + name);
        Graphic graphic = new Graphic(files);
        registry.logAdd(name, graphic);
    }
    
    
    /**
     * Returns the number of images in the graphic.  This is the same as 
     * the number of animations frames it has.
     * 
     * @return Number of frames in the graphic
     */
    public int size() {
        return frames.length;
    }
    
    
    /**
     * Add a graphic to the registry.  This will create a new graphic named name 
     * containing images listed in files.
     * 
     * @param name
     * @param files 
     */
    public static void addGraphic(String name, List<String> files) {
        GameLogger.mainLogger.logInfo("Adding graphic " + name);
        Graphic graphic = new Graphic(files);
        graphic.name = name;
        registry.logAdd(name, graphic);
        BufferedImage f = graphic.getImage();
    }
    
    
    public static final void cleanup() { 
        for(Graphic graphic : registry) {
            for(int i = 0; i < graphic.frames.length; i++) {
                graphic.frames[i] = null;
            }
        }
        registry.clear();
    }
    
    
    public static Image getGameScreen() {
        return gameI;
    }
    
    
    public static void clearScreen() {
        gameG.clearRect(0, 0, width, height);
    }
    
    
    public static void clearArea(int x1, int y1, int x2, int y2) {
        gameG.clearRect(x1, y1, x2 - x1, y2 - y1);
    }
    
    
    public BufferedImage getImage() {
        // For non-animated graphics, having only one image
        return frames[0];
    }
    
    
    public static BufferedImage getImage(int id) {
        // For non-animated graphics, having only one image
        return registry.get(id).frames[0];
    }
    
    
    public BufferedImage getImageForFrame(int idx) {
        // For non-animated graphics, having only one image
        // Mostly intended for the icons stored stored as frames of an image
        return frames[idx];
    }
    
    
    public BufferedImage getNextImage() {
        frame++;
        if(frame >= frames.length) {
            frame = 0;
        }
        return frames[frame];
    }
    
    
    public static BufferedImage getNextImage(int id) {
        Graphic graphic = registry.get(id);
        graphic.frame++;
        if(graphic.frame >= graphic.frames.length) {
            graphic.frame = 0;
        }
        return graphic.frames[graphic.frame];
    }
    
    
    public void setFrame(int frame) {
        frame = Math.abs(frame) % frames.length;
    }
    
    
    public void nextFrame(int frame) {
        frame = (++frame) % frames.length;
    }
    
    
    public void reset() {
        frame = 0;
    }
    
    
    public void draw(float x, float y) {
        gameG.drawImage(frames[frame], 
                    (int)(x), 
                    (int)(y), 
                    null);
    }
    
    
    public void draw(int frame, float x, float y) {
        gameG.drawImage(frames[frame], 
                    (int)(x), 
                    (int)(y), 
                    null);
    }
    
    
    public static void draw(int ID, int frame, float x, float y) {
        gameG.drawImage(registry.get(ID).frames[frame], (int)x, (int)y, null);
    }
    
    
    public void drawReverse(float x, float y) {
        BufferedImage toDraw = frames[frame];
        int w = toDraw.getWidth();
        int h = toDraw.getHeight();
        gameG.drawImage(toDraw, 
                    (int)(x + w), 
                    (int)y, 
                    -w, 
                     h,
                     null);
    }
    
    
    public void drawReverse(int frame, float x, float y) {
        BufferedImage toDraw = frames[frame];
        int w = toDraw.getWidth();
        int h = toDraw.getHeight();
        gameG.drawImage(toDraw, 
                    (int)(x + w), 
                    (int)y, 
                    -w, 
                     h,
                     null);
    }
    
    
    public static void drawReverse(int ID, int frame, float x, float y) {
        BufferedImage toDraw = registry.get(ID).frames[frame];
        int w = toDraw.getWidth();
        int h = toDraw.getHeight();
        gameG.drawImage(toDraw, 
                    (int)(x + w), 
                    (int)y, 
                    -w, 
                     h,
                     null);
    }
    
    
    public int getID() {
        return id;
    }
    
    
    public String getName() {
        return name;
    }
    
    
    public static List getIcons() {
        ArrayList<BufferedImage> out = new ArrayList<>();
        out.addAll(Arrays.asList(registry.getFromName("icon").frames));
        return out;        
    }
    
    
    public static int getBufferWidth() {
        return width;
    }
    
    
    public static int getBufferHeight() {
        return height;
    }
    
}
