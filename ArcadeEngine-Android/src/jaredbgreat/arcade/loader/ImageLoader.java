//Copyright (c) Jared Blackburn 2017
//A game with play similar to arcade space shooter such as Space Ivaders and
//Galega, but on the ground with monsters in stead of alien ships
package jaredbgreat.arcade.loader;

import jaredbgreat.arcade.ui.graphics.Graphic;

/**
 * A class to load in image data.  This is largely derived from the Macy Mae 
 * source code, where it work well and will create graphics for a similar 
 * system.
 * 
 * @author Jared Blackburn
 */
public class ImageLoader extends AbstractLoader {
    private static final ImageLoader reader = new ImageLoader();
    private static final String LOC     = "/assets/pics/";
    private static final String INFO_LOC = LOC + "GraphicsData.txt"; 
    
    
    /**
     * One private instance exists to conveniently hold temporary 
     * information.  This should never be instantiated elsewhere nor
     * shared with other classes, but only used internally.
     */
    private ImageLoader(){
        super();
        loc = LOC;
        infoLoc = INFO_LOC;   
    }
    
    
    /**
     * The static entryway to this image loading system.  It should be 
     * called only once during initialization.  This then calls the private 
     * methods that have access to internal data storage.
     */
    public static void initGraphics() {
        reader.openInfo();
    }
    
    
    @Override
    protected void makeResource() {
        Graphic.addGraphic(name, list);
    }
    
    
    
    
}
