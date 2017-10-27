package jaredbgreat.arcade.ui.input;

import android.view.MotionEvent;
import static android.view.MotionEvent.*;
import android.view.View;
import android.view.View.OnTouchListener;
import jaredbgreat.arcade.util.memory.ObjectPool;
import java.util.ArrayList;
import static jaredbgreat.arcade.ui.input.MouseEvent.*;
import java.util.List;

/**
 *
 * @author Jared Blackburn
 */
public class PointerInput implements OnTouchListener {
    private final View screen;
    private final float scaleX, scaleY;
    private volatile int commands;
    private IMouseTranslator pressed;
    private IMouseTranslator released;
    private IMouseTranslator moved;
    private final ObjectPool.ObjectFactory<MouseEvent> factory;
    private final ObjectPool<MouseEvent> pool;
    private final ArrayList<MouseEvent> upBuffer, upEvents;
    private final ArrayList<MouseEvent> downBuffer, downEvents;
    private final ArrayList<MouseEvent> moveBuffer, moveEvents;;
    
    
    public PointerInput(View view) {
        scaleX = 1.0f;
        scaleY = 1.0f;
        factory  = new ObjectPool.ObjectFactory<MouseEvent>() {
            @Override
            public MouseEvent create() {
                return new MouseEvent();
            }
        };
        pool = new ObjectPool<>(factory, 512);
        upBuffer = new ArrayList<>();        
        upEvents = new ArrayList<>();
        downBuffer = new ArrayList<>();        
        downEvents = new ArrayList<>();
        moveBuffer = new ArrayList<>();        
        moveEvents = new ArrayList<>();
        screen = view;
        screen.setOnTouchListener(this);
    }
    
    
    
    

    @Override
    public synchronized boolean onTouch(View view, MotionEvent e) {
        int action = e.getAction();
        int pointerIndex = (action & ACTION_POINTER_ID_MASK) 
                >> ACTION_POINTER_ID_SHIFT;
        int pcount = e.getPointerCount();
        action &= ACTION_MASK;
        MouseEvent event;
        for(int i = 0; i < pcount; i++) {
            event = pool.getObject();
            event.index = pointerIndex;
            event.x = (int)(e.getX(i) * scaleX);
            event.y = (int)(e.getX(i) * scaleY);
            switch(action) {
                case ACTION_DOWN:
                case ACTION_POINTER_DOWN:
                    event.type = DOWN;
                    downBuffer.add(event);
                    break;
                case ACTION_UP:
                case ACTION_POINTER_UP:
                case ACTION_CANCEL:
                    event.type = UP;
                    upBuffer.add(event);
                    break;
                case ACTION_MOVE:
                    event.type = DRAG;                    
                    moveBuffer.add(event);
                    break;
            }            
        }
        return true;
    }
    
    
    public synchronized void update() {
        swap(upBuffer, upEvents);
        swap(downBuffer, downEvents);
        swap(moveBuffer, moveEvents);
    }
    
    
    private void swap(ArrayList<MouseEvent> buffer, 
            ArrayList<MouseEvent> events) {
        for(int i = events.size() - 1; i > -1; i--) {
            pool.free(events.get(i));
        }
        events.clear();
        events.addAll(buffer);
        buffer.clear();
    }
    
    
    public void processEvents() {
        int num = downEvents.size();
        if(pressed != null) {
            for(int i = 0; i < num; i++) {
                commands |= pressed.getCommands(downEvents.get(i));
                pressed.process(downEvents.get(i));
            }
        }
        if(released != null) {
            num = upEvents.size();
            for(int i = 0; i < num; i++) {
                commands &= ~released.getCommands(upEvents.get(i));
                released.process(upEvents.get(i));
            }
        }
        if(moved != null) {
            num = upEvents.size();
            for(int i = 0; i < num; i++) {
                moved.process(moveEvents.get(i));
            }
        }
    }
    
    
    public void setTranslators(IMouseTranslator press, 
            IMouseTranslator release, IMouseTranslator move) {
        pressed = press;
        released = release;
        moved = move;
    }
    
    
    public List<MouseEvent> getPressed() {
        return downEvents;
    }
    
    
    public List<MouseEvent> getReleased() {
        return upEvents;
    }
    
    
    public List<MouseEvent> getDragged() {
        return moveEvents;
    }
    
    
    public int getCommands() {
        return commands;
    }

}