//Copyright (c) Jared Blackburn 2017
//An arcade space shooter
package jaredbgreat.arcade.ui.input;


import jaredbgreat.arcade.game.BaseGame;
import jaredbgreat.arcade.ui.MainWindow;
import jaredbgreat.arcade.util.memory.ObjectPool;
import jaredbgreat.arcade.util.memory.ObjectPool.ObjectFactory;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jared Blackburn
 */
public class KeyInput implements KeyListener {
    private final MainWindow screen;
    private final ObjectFactory<KeyboardEvent> factory;
    private final ObjectPool<KeyboardEvent> pool;
    private final ArrayList<KeyboardEvent> upBuffer, upEvents;
    private final ArrayList<KeyboardEvent> downBuffer, downEvents;
    
    
    public KeyInput(MainWindow view) {
        factory  = new ObjectFactory<KeyboardEvent>() {
            @Override
            public KeyboardEvent create() {
                return new KeyboardEvent();
            }
        };
        pool = new ObjectPool<>(factory, 128);
        upBuffer = new ArrayList<>();        
        upEvents = new ArrayList<>();
        downBuffer = new ArrayList<>();        
        downEvents = new ArrayList<>();
        screen = view;
        screen.addKeyListener(this);
    }
    
    
    
    public void requestFocus() {  
        screen.requestFocus();
    }
    

    @Override
    public synchronized void keyPressed(KeyEvent e) {
        if(!BaseGame.game.blockInput()) {            
            KeyboardEvent event = pool.getObject();
            event.kchar = e.getKeyChar();
            event.code = e.getKeyCode();
            event.type = KeyboardEvent.DOWN;                    
            downBuffer.add(event);
        }
    }
    

    @Override
    public synchronized void keyReleased(KeyEvent e) {
        if(!BaseGame.game.blockInput()) {            
            KeyboardEvent event = pool.getObject();
            event.kchar = e.getKeyChar();
            event.code = e.getKeyCode();
            event.type = KeyboardEvent.UP;                    
            downBuffer.add(event);
        }
    }
    
    
    public synchronized void update() {
        swap(upBuffer, upEvents);
        swap(downBuffer, downEvents);
    }
    
    
    public List<KeyboardEvent> getPressed() {
        return downEvents;
    }
    
    
    public List<KeyboardEvent> getReleased() {
        return upEvents;
    }
    
    
    private void swap(ArrayList<KeyboardEvent> buffer, 
            ArrayList<KeyboardEvent> events) {
        for(int i = events.size() - 1; i > -1; i--) {
            pool.free(events.get(i));
        }
        events.clear();
        events.addAll(buffer);
        buffer.clear();
    }
    
    

    @Override
    public void keyTyped(KeyEvent e) {/*Do nothing*/}
    
    
    
    
}
