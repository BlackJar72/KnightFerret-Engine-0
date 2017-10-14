package jaredbgreat.arcade.ui.input;

import jaredbgreat.arcade.ui.MainWindow;
import jaredbgreat.arcade.ui.graphics.Graphic;
import jaredbgreat.arcade.util.memory.ObjectPool;
import java.util.ArrayList;
import static jaredbgreat.arcade.ui.input.PointerEvent.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

/**
 *
 * @author Jared Blackburn
 */
public class PointerInput implements MouseListener {
    private final MainWindow screen;
    private final ObjectPool.ObjectFactory<PointerEvent> factory;
    private final ObjectPool<PointerEvent> pool;
    private final ArrayList<PointerEvent> upBuffer, upEvents;
    private final ArrayList<PointerEvent> downBuffer, downEvents;
    private final ArrayList<PointerEvent> moveBuffer, moveEvents;;
    
    
    public PointerInput(MainWindow view) {
        float scaleX = Graphic.getBufferWidth() / view.getWidth();
        float scaleY = Graphic.getBufferHeight() / view.getHeight();
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
        screen.addMouseListener(this);
    }
    

    @Override
    public synchronized void mousePressed(MouseEvent e) {       
        float scaleX = Graphic.getBufferWidth() / screen.getWidth();
        float scaleY = Graphic.getBufferHeight() / screen.getHeight();
        PointerEvent event;
        event = pool.getObject();
        event.index = 0;
        event.x = (int)(e.getX() * scaleX);
        event.y = (int)(e.getX() * scaleY);
        event.type = DOWN;
        downBuffer.add(event);
    }
    

    @Override
    public void mouseReleased(MouseEvent e) {               
        float scaleX = Graphic.getBufferWidth() / screen.getWidth();
        float scaleY = Graphic.getBufferHeight() / screen.getHeight();
        PointerEvent event;
        event = pool.getObject();
        event.index = 0;
        event.x = (int)(e.getX() * scaleX);
        event.y = (int)(e.getX() * scaleY);
        event.type = UP;
        downBuffer.add(event);
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
    

    /*------------------------------------------------------------------------//
    //                           UNUSED METHODS                               //
    //------------------------------------------------------------------------*/
        
    @Override
    public void mouseEntered(MouseEvent e) {/*do nothing*/}
    @Override
    public void mouseExited(MouseEvent e) {/*do nothing*/}
    @Override
    public void mouseClicked(MouseEvent e) {/*Do nothing*/}

}