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
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineEvent.Type;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author jared
 */
public class Sound implements LineListener {
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
    
    
    private Sound(List<String> files) {
        sounds = new Clip[files.size()];
        GameLogger.mainLogger.logInfo("Found " + sounds.length 
                + " audio clips to add");
        for(int i = 0; i < sounds.length; i++) {
            System.out.println("Loading sound #" + i + ", called " + files.get(i));
            sounds[i] = readClip(files.get(i));
            sounds[i].addLineListener(this);
        }
    }
    
    
    public static void addSound(String name, List<String> files) {
        System.out.println("Adding sound clip " + name);
        Sound sound = new Sound(files);
        registry.logAdd(name, sound);
    }
    
    
    public void play() {
        if(sounds[0].isActive()) {
            sounds[0].stop();
            sounds[0].setFramePosition(0);
            sounds[0].start();
        } else {
            sounds[0].start();
        }
    }
    
    
    public void play(int i) {
        if(sounds[i].isActive()) {
            sounds[i].stop();
            sounds[i].setFramePosition(0);
            sounds[i].start();
        } else {
            sounds[i].start();
        }
    }
    
    
    public void play(Random rng) {
        play(rng.nextInt(sounds.length));
    }

    @Override
    public void update(LineEvent event) {
        if(event.getType() == Type.STOP) {
            Clip clip = ((Clip)(event.getLine()));
            clip.setFramePosition(0);
        }
    }
    
}
