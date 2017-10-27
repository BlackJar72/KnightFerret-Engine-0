package jaredbgreat.arcade.ui.sound;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import jaredbgreat.arcade.util.GameLogger;
import jaredbgreat.arcade.util.Registry;
import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 *
 * @author jared
 */
public class Sound {
    public static final Registry<Sound> registry = new Registry<>();
    private static AssetManager assetMan;
    private static SoundPool player;
    private int[] sounds;
    
    public static void initSound(AssetManager man) {
        assetMan = man;
        player = new SoundPool(24, AudioManager.STREAM_MUSIC, 0);
    }
    
    
    public Sound(List<String> files) {
        sounds = new int[files.size()];
        GameLogger.mainLogger.logInfo("Found " + sounds.length 
                + " audio clips to add");
        for(int i = 0; i < sounds.length; i++) {
            try {
                AssetFileDescriptor desc = assetMan.openFd(files.get(i));
                sounds[i] = player.load(desc, 1);
            } catch (IOException ex) {
                GameLogger.mainLogger.logException(ex);
            }
        }
    }
    
    
    public static void addSound(String name, List<String> files) {
        System.out.println("Adding sound clip " + name);
        Sound sound = new Sound(files);
        registry.logAdd(name, sound);
    }
    
    
    public void play() {
        player.play(sounds[0], 1.0f, 1.0f, 0, 0, 1f);
    }
    
    
    public void play(int i) {        
        player.play(sounds[i], 1.0f, 1.0f, 0, 0, 1f);
    }
    
    
    public void play(int i, float left, float right, 
            int priority, int times, float speed) {
        player.play(sounds[i], 1.0f, 1.0f, 0, 0, 1f);
    }
    
    
    public void play(Random rng) {
        player.play(sounds[rng.nextInt(sounds.length)], 1.0f, 1.0f, 0, 0, 1f);
    }
    
}
