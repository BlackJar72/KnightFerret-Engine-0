package jaredbgreat.arcade.ui.sound;

import jaredbgreat.arcade.util.GameLogger;
import jaredbgreat.arcade.util.Registry;
import java.util.List;
import java.util.Random;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Transmitter;

/**
 *
 * @author Jared Blackburn
 */
public class Music {
    private static final Registry<Music> registry = new Registry<>();
    private static final Listener listener = new Listener();
    private static final int END = 47;    
    private static boolean randomize = false;
    private static int which;
    private static Random rng;
    private static Sequencer sequencer;
    private static Synthesizer synth; 
    private Sequence[] seqs;
    private int id;
        
    private static class Listener implements MetaEventListener {    
        @Override
        public void meta(MetaMessage meta) {
            if(randomize && (meta.getType() == END) && (rng != null)) {
                registry.get(which).play(rng, randomize);
            }
        }
    }
    
    
    private Music(List<String> files) {
        int n = files.size();
        seqs = new Sequence[n];
        GameLogger.mainLogger.logInfo("Found " + n + " midid files to add");
        for(int i = 0; i < n; i++) {
            System.out.println("Loading midi #" + i + ", called " + files.get(i));
            try {
                seqs[i] = MidiSystem.getSequence(getClass()
                        .getResourceAsStream(files.get(i)));
            } catch (Exception ex) {
                GameLogger.mainLogger.logException(ex);
            }
        }
    }
    
    
    public static void addMusic(String name, List<String> files) {
        System.out.println("Adding midi music " + name);
        Music music = new Music(files);
        registry.logAdd(name, music);
        music.id = registry.getID(name);
    }
    
    
    public static void init() {
        try {
            sequencer = MidiSystem.getSequencer();
            if(sequencer == null) {
                GameLogger.mainLogger.logError("ERROR: Cannot get sequencer!");
                throw new MidiUnavailableException("Null sequencer: "
                        + "Cannot get sequencer!");
            }            
            sequencer.open();
            sequencer.addMetaEventListener(listener);
            if(!(sequencer instanceof Synthesizer)) {
                synth = MidiSystem.getSynthesizer();
                synth.open();
                Receiver ret = synth.getReceiver();
                Transmitter trans = synth.getTransmitter();                
            } else {
                synth = (Synthesizer)sequencer;
            }
        } catch (MidiUnavailableException ex) {
            GameLogger.mainLogger.logException(ex);
        }
    }
    
    
    public void play() {
        play(0);
    }
    
    
    public void play(int i) {
        randomize = false;
        try {
            sequencer.stop();
            sequencer.setSequence(seqs[i]);
            sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
            sequencer.start();
        } catch (InvalidMidiDataException ex) {
            GameLogger.mainLogger.logException(ex);
        }        
    }
    
    
    public void play(Random random) {
        play(random, false);
    }
    
    
    public void play(Random random, boolean shuffle) {
        randomize = shuffle;
        which = id;
        rng = random;
        play(random.nextInt(seqs.length));
    }
    
    
    public void playOnce() {
        playOnce(0);
    }
    
    
    public void playOnce(int i) {
        randomize = false;
        try {
            sequencer.stop();
            sequencer.setSequence(seqs[i]);
            sequencer.setLoopCount(0);
            sequencer.start();
        } catch (InvalidMidiDataException ex) {
            GameLogger.mainLogger.logException(ex);
        }        
    }
    
    
    public void playOnce(Random random) {
        randomize = false;
        which = id;
        rng = random;
        playOnce(random.nextInt(seqs.length));
    }
    
    
    public static void stop() {
        sequencer.stop();
    }
    
}
