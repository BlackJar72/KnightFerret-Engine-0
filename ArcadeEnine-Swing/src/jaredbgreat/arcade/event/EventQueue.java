//Copyright (c) Jared Blackburn 2017
package jaredbgreat.arcade.event;

import java.util.ArrayList;
import java.util.List;

/**
 * A double buffered collection of Events.  Events may be added to the 
 * EventQueue and anytime and will be periodically delivered (typically at 
 * the beginning or end of a frame or tick/update).  This allows all events in
 * the cycle (frame/tick/update) to be accumulated regardless of there origin 
 * and executed in batches, in addition to allowing and easy one-to-many 
 * delivery.
 * 
 * If immediate, synchronous execution of the event is desired use the 
 * Synchronous Dispatcher (and relate interfaces) instead.
 * 
 * This should now be thread safe, so long as objects receiving messages on 
 * other threads use some sort mail box (such as a queue) rather than 
 * processing the events immediately -- the provided ring queue classes, 
 * StaticRingQueue and DynamicRingQueue should be useful in such situations, 
 * though a List or (for special purposes) priority queue could also be used.
 * 
 * @author Jared Blackburn
 * @param <T> Data type (usually an enum) carried by the events.
 */
public final class EventQueue<T> {
    // As event recievers may send more events in responce to recieving an
    // event (adding while interating) this must be double buffered to avoid 
    // concurrent modification of the underlying list sructure.
    // (Some would say code that recieve events should not send them -- while 
    // this is probably a good rule of thumb generally, I've found it has its 
    // uses.
    private volatile List<IEvent<T>> list, previous, swap;
    
    public EventQueue() {
        list = new ArrayList<>();
        previous = new ArrayList<>();
    }
    
    
    /**
     * Add the provided event to the queue.
     * 
     * @param msg 
     */
    public synchronized void add(Event<T> msg) {
        list.add(msg);
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
    public synchronized void add(T type, Object sender, 
            IEventReceiver<T>... recipients) {
        add(new Event<>(type, sender, recipients));
    }
    
    
    /**
     * Send all the queued events to their recipients.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" }) 
    public synchronized void deliver() {
        swap = previous;
        previous = list;
        list = swap;
        int number = previous.size();
        // Old school loop to avoid tossing iterators to the garbage collector
        for(int i = 0; i < number; i++) {
            IEvent msg = previous.get(i);
            IEventReceiver<T>[] recipients = msg.getRecipients();
            for(int j = 0; j < recipients.length; j++) {
                recipients[j].recieveMsg(msg);
            }
        }
        previous.clear();
    }
}
