package jaredbgreat.arcade.loader;

import jaredbgreat.arcade.ui.sound.Music;

/**
 *
 * @author jared
 */
public class MusicLoader extends AbstractLoader {
    private static final MusicLoader reader = new MusicLoader();
    private static final String LOC     = "/assets/music/";
    private static final String INFO_LOC = LOC + "MusicData.txt"; 
    
    
    /**
     * One private instance exists to conveniently hold temporary 
     * information.  This should never be instantiated elsewhere nor
     * shared with other classes, but only used internally.
     */
    private MusicLoader(){
        super();
        loc = LOC;
        infoLoc = INFO_LOC;   
    }
    
    
    /**
     * The static entryway to this midi loading system.  It should be 
     * called only once during initialization.  This then calls the private 
     * methods that have access to internal data storage.
     */
    public static void initMusic() {
        reader.openInfo();
    }

    @Override
    protected void makeResource() {
        Music.addMusic(name, list);
    }
    
}
