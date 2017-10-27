package jaredbgreat.arcade.ui.input;

import android.view.MotionEvent;
import static android.view.MotionEvent.*;
import android.view.View;
import android.view.View.OnTouchListener;
import jaredbgreat.arcade.util.memory.ObjectPool;
import java.util.ArrayList;
import static jaredbgreat.arcade.ui.input.PointerEvent.*;
import java.util.List;

/**
 *
 * @author Jared Blackburn
 */
public class PointerInput implements OnTouchListener {
    private final View screen;
    private final float scaleX, scaleY;
    private final ObjectPool.ObjectFactory<PointerEvent> factory;
    private final ObjectPool<PointerEvent> pool;
    private final ArrayList<PointerEvent> upBuffer, upEvents;
    private final ArrayList<PointerEvent> downBuffer, downEvents;
    private final ArrayList<PointerEvent> moveBuffer, moveEvents;;
    
    
    public PointerInput(View view) {
        scaleX = 1.0f;
        scaleY = 1.0f;
        factory  = new ObjectPool.ObjectFactory<PointerEvent>() {
            @Override
            public PointerEvent create() {
                return new PointerEvent();
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
        PointerEvent event;
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
    
    
    private void swap(ArrayList<PointerEvent> buffer, 
            ArrayList<PointerEvent> events) {
        for(int i = events.size() - 1; i > -1; i--) {
            pool.free(events.get(i));
        }
        events.clear();
        events.addAll(buffer);
        buffer.clear();
    }
    
    
    public List<PointerEvent> getPressed() {
        return downEvents;
    }
    
    
    public List<PointerEvent> getReleased() {
        return upEvents;
    }
    
    
    public List<PointerEvent> getDragged() {
        return moveEvents;
    }

}