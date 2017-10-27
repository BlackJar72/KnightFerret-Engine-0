package jaredbgreat.arcade.event;

import jaredbgreat.arcade.util.memory.ObjectPool;
import jaredbgreat.arcade.util.memory.ObjectPool.ObjectFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * An event queue using pooled event to avoid garbage collection (especially 
 * on mobile devices, but available for all versions of the library).  It is 
 * important to consider a two things when using this: First, the events are, 
 * be necessity, mutable and of type MEvent; Second, all all events must be 
 * fully processed during the same iteration of the game loop (or whatever 
 * loop controls delivery) since they will be recycled on the next iteration, 
 * and can have their data overwritten.
 * 
 * @author Jared Blackburn
 * @param <T>
 */
public class PooledEventQueue<T> {
    // As event recievers may send more events in responce to recieving an
    // event (adding while interating) this must be double buffered to avoid 
    // concurrent modification of the underlying list sructure.
    // (Some would say code that recieve events should not send them -- while 
    // this is probably a good rule of thumb generally, I've found it has its 
    // uses.
    private volatile List<MEvent<T>> list, previous, swap;
    private final ObjectFactory<MEvent<T>> factory;
    private final ObjectPool<MEvent<T>> pool;
    
    
    public PooledEventQueue() {
        list = new ArrayList<>();
        previous = new ArrayList<>();
        factory = new ObjectFactory<MEvent<T>>() {
            @Override
            public MEvent<T> create() {
                return new MEvent<>();
            }
        };
        pool = new ObjectPool<>(factory, 64);
    }
    
    
    public PooledEventQueue(int poolSize) {
        list = new ArrayList<>();
        previous = new ArrayList<>();
        factory = new ObjectFactory<MEvent<T>>() {
            @Override
            public MEvent<T> create() {
                return new MEvent<>();
            }
        };
        pool = new ObjectPool<>(factory, poolSize);
    }
    
    
    /**
     * This will create a new event using the provided parameters and 
     * add it to the queue.  Note that it is generally preferable to 
     * create the event in the calling method and provide it, using 
     * add(Event\<T\> msg).
     * 
     * @param type
     * @param sender
     * @param recipients 
     */
    @SuppressWarnings({ "unchecked", "rawtypes" }) 
    public void add(T type, Object sender, IEventReceiver<T>... recipients) {
        list.add(pool.getObject().init(type, sender, recipients));
    }
    
    
    /**
     * This will create a new event using the provided parameters and 
     * add it to the queue.  Note that it is generally preferable to 
     * create the event in the calling method and provide it, using 
     * add(Event\<T\> msg).
     * 
     * @param type
     * @param sender
     * @param recipients 
     */
    @SuppressWarnings({ "unchecked", "rawtypes" }) 
    public void add(T type, Object sender, List<IEventReceiver<T>> recipients) {
        list.add(pool.getObject().init(type, sender, recipients));
    }
    
    
    /**
     * Send all the queued events to their recipients.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" }) 
    public void deliver() {
        // Old school loop to avoid tossing iterators to the garbage collector
        int number = previous.size();
        for(int i = 0; i < number; i++) {
            pool.free(previous.get(i));
        }
        previous.clear();
        swap = previous;
        previous = list;
        list = swap;
        number = previous.size();
        // Old school loop to avoid tossing iterators to the garbage collector
        for(int i = 0; i < number; i++) {
            MEvent msg = previous.get(i);
            IEventReceiver<T>[] recipients = msg.getRecipients();
            for(int j = 0; j < recipients.length; j++) {
                recipients[j].recieveMsg(msg);
            }
        }
    }
    
}
