//Copyright (c) Jared Blackburn 2017
package jaredbgreat.arcade.ui.graphics;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import jaredbgreat.arcade.util.GameLogger;
import jaredbgreat.arcade.util.Registry;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    
    private static AssetManager assetMan;
    
    final Bitmap[]   frames;
    int              frame;   
    
    int id;
    String name;
    
    // FIXME: Remove hard-coded screen resolution; make configurable / initializable
    public static int width  = 480;
    public static int height = 640;
    
    private static Bitmap        gameI;
    private static Canvas        canvas; 
    private static Paint         paint;
    
    
    public Graphic(int size) {
        frames = new Bitmap[size];
        frame  = 0;
    } 
    
    
    public Graphic(List<String> files) {
        frames = new Bitmap[files.size()];
        GameLogger.mainLogger.logInfo("Found " + frames.length 
                + " images to add");
        for(frame = 0; frame < frames.length; frame++) {
            InputStream stream = null;
            try {
                stream = assetMan.open(files.get(frame));
                frames[frame] = BitmapFactory.decodeStream(stream);
            } catch (IOException ex) {
                GameLogger.mainLogger.logException(ex);
            } finally {
                try {
                    if(stream != null) {
                        stream.close();
                    }
                } catch (IOException ex) {
                    GameLogger.mainLogger.logException(ex);
                }
            }
        }
        frame = 0;        
    }
    
    
    public static void init(int x, int y, AssetManager man) {        
        width  = x;
        height = y;
        assetMan = man;
        gameI    = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        canvas   = new Canvas(gameI);
        paint    = new Paint();
        paint.setARGB(0xff, 0x00, 0x00, 0x00);
        paint.setStyle(Paint.Style.FILL);
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
        Bitmap f = graphic.getImage();
    }
    
    
    public static final void cleanup() { 
        for(Graphic graphic : registry) {
            for(int i = 0; i < graphic.frames.length; i++) {
                graphic.frames[i] = null;
            }
        }
        registry.clear();
    }
    
    
    public static Bitmap getGameScreen() {
        return gameI;
    }
    
    
    public static void clearScreen() {
        canvas.drawRGB(0, 0, 0);
    }
    
    
    public static void clearArea(int x1, int y1, int x2, int y2) {
        canvas.drawRect(x1, y1, x2 - x1, y2 - y1, paint);
    }
    
    
    public Bitmap getImage() {
        // For non-animated graphics, having only one image
        return frames[0];
    }
    
    
    public static Bitmap getImage(int id) {
        // For non-animated graphics, having only one image
        return registry.get(id).frames[0];
    }
    
    
    public Bitmap getImageForFrame(int idx) {
        // For non-animated graphics, having only one image
        // Mostly intended for the icons stored stored as frames of an image
        return frames[idx];
    }
    
    
    public Bitmap getNextImage() {
        frame++;
        if(frame >= frames.length) {
            frame = 0;
        }
        return frames[frame];
    }
    
    
    public static Bitmap getNextImage(int id) {
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
        canvas.drawBitmap(frames[0], x, y, null);
    }
    
    
    public void draw(int frame, float x, float y) {
        canvas.drawBitmap(frames[frame], x, y, null);
    }
    
    
    public static void draw(int ID, int frame, float x, float y) {
        canvas.drawBitmap(registry.get(ID).frames[frame], x, y, null);
    }
    
    
    public void drawReverse(float x, float y) {
        //TODO: How do I flip or rotate this?  The matrix veriosn???
    }
    
    
    public void drawReverse(int frame, float x, float y) {
        //TODO
    }
    
    
    public static void drawReverse(int ID, int frame, float x, float y) {
        //TODO
    }
    
    
    public int getID() {
        return id;
    }
    
    
    public String getName() {
        return name;
    }
    
    
    public static List getIcons() {
        // FIXME: Probably not needed, but if it has a use, it shouldn't be this.
        // Also, this is called in the PC version, but this must change 
        // elsewhere for Android.
        ArrayList<Bitmap> out = new ArrayList<>();
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
