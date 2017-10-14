package jaredbgreat.arcade.ui.sound;

import jaredbgreat.arcade.util.GameLogger;
import jaredbgreat.arcade.util.Registry;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author jared
 */
public class Sound {
    public static final Registry<Sound> registry = new Registry<>();
    private final Clip[] sounds; 
    
    
    private Clip readClip(String filename) {
        Clip clip;
        AudioFormat format;
        DataLine.Info info;
        Object AudioInputSystem;
        try {
            AudioInputStream stream 
                    = AudioSystem.getAudioInputStream(getClass()
                            .getResource(filename));
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = AudioSystem.getClip();
            clip.open(stream);
            clip.close();
            return clip;
        } catch (LineUnavailableException | 
                UnsupportedAudioFileException | 
                IOException ex) {            
            GameLogger.mainLogger.logError("Adiofile " + filename 
                    + " failed to load!");
            GameLogger.mainLogger.logException(ex);
        }
        return null;
    }
    
    
    public Sound(List<String> files) {
        sounds = new Clip[files.size()];
        GameLogger.mainLogger.logInfo("Found " + sounds.length 
                + " audio clips to add");
        for(int i = 0; i < sounds.length; i++) {
            sounds[i] = readClip(files.get(i));
        }
    }
    
    
    public static void addSound(String name, List<String> files) {
        System.out.println("Adding sound clip " + name);
        Sound sound = new Sound(files);
        registry.logAdd(name, sound);
    }
    
    
    public void play() {
        sounds[0].start();
    }
    
    
    public void play(int i) {
        sounds[i].start();
    }
    
    
    public void play(Random rng) {
        sounds[rng.nextInt(sounds.length)].start();
    }
    
}
