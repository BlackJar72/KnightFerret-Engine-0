//Copyright (c) Jared Blackburn 2017
//An arcade space shooter
package jaredbgreat.arcade.ui.input;


import android.view.View;
import android.view.View.OnKeyListener;
import jaredbgreat.arcade.game.BaseGame;
import jaredbgreat.arcade.util.memory.ObjectPool;
import jaredbgreat.arcade.util.memory.ObjectPool.ObjectFactory;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jared Blackburn
 */
public class KeyInput implements OnKeyListener {
    private final View screen;
    private IKeyTranslator pressed, released;
    private volatile int commands;
    private final ObjectFactory<KeyboardEvent> factory;
    private final ObjectPool<KeyboardEvent> pool;
    private final ArrayList<KeyboardEvent> upBuffer, upEvents;
    private final ArrayList<KeyboardEvent> downBuffer, downEvents;
    
    
    public KeyInput(View view) {
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
        screen.setOnKeyListener(this);
    }
    
    
    
    public void requestFocus() {        
        screen.setFocusableInTouchMode(true);
        screen.requestFocus();
    }
    

    @Override
    public boolean onKey(View view, int code, android.view.KeyEvent e) {
        int action = e.getAction();
        if(!BaseGame.game.blockInput() 
                && (action != android.view.KeyEvent.ACTION_MULTIPLE)) 
            synchronized (this) {
                KeyboardEvent event = pool.getObject();
                event.kchar = e.getUnicodeChar();
                event.code = code;
                if(action == android.view.KeyEvent.ACTION_DOWN) {
                    event.type = KeyboardEvent.DOWN;                    
                    commands = commands | pressed.translate(event);
                    downBuffer.add(event);
                }
                if(action == android.view.KeyEvent.ACTION_UP) {
                    event.type = KeyboardEvent.UP;                    
                    commands &= ~released.translate(event);
                    upBuffer.add(event);
                }
        }
        return false;
    }
    
    
    public synchronized void update() {
        swap(upBuffer, upEvents);
        swap(downBuffer, downEvents);
    }
    
    
    public void clear() {
        commands = 0;
    }
    
    
    public int getCommands() {
        return commands;
    }
    
    
    public void processEvents() {
        int num = downEvents.size();
        if(pressed != null) {
            for(int i = 0; i < num; i++) {
                pressed.process(downEvents.get(i));
            }
        }
        if(released != null) {
            num = upEvents.size();
            for(int i = 0; i < num; i++) {
                released.process(upEvents.get(i));
            }
        }
    }
    
    
    public void setTranslators(IKeyTranslator press, 
            IKeyTranslator release) {
        pressed = press;
        released = release;        
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
    
    
    
    
}
