package jaredbgreat.arcade.loader;

import jaredbgreat.arcade.ui.sound.Sound;

/**
 *
 * @author jared
 */
public class AudioLoader extends AbstractLoader {
    private static final AudioLoader reader = new AudioLoader();
    private static final String LOC     = "/assets/audio/";
    private static final String INFO_LOC = LOC + "AudioData.txt"; 
    
    
    /**
     * One private instance exists to conveniently hold temporary 
     * information.  This should never be instantiated elsewhere nor
     * shared with other classes, but only used internally.
     */
    private AudioLoader(){
        super();
        loc = LOC;
        infoLoc = INFO_LOC;   
    }
    
    
    /**
     * The static entryway to this sound loading system.  It should be 
     * called only once during initialization.  This then calls the private 
     * methods that have access to internal data storage.
     */
    public static void initAudio() {
        reader.openInfo();
    }
    

    @Override
    protected void makeResource() {
        Sound.addSound(name, list);
    }
    
}
