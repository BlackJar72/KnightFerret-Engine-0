package jaredbgreat.arcade.event;

import jaredbgreat.arcade.game.BaseGame;
import java.util.ArrayList;
import java.util.List;

/**
 * This will store events of type TimedEvent, and deliver events once their 
 * post date time has been surpassed.  More specifically, 
 *
 * @author Jared Blackburn
 * @param <T>
 */
public class TimedEventQueue<T> {
    private volatile List<TimedEvent<T>> list, delivered;
    
    
    public TimedEventQueue() {
        list = new ArrayList<>();
        delivered = new ArrayList<>();
    }
    
    
    /**
     * Add the provided event to the queue.
     * 
     * @param msg 
     */
    public synchronized void add(TimedEvent<T> msg) {
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
    public synchronized void add(T type, float postData, Object sender, 
            IEventReceiver<T>... recipients) {
        add(new TimedEvent<>(type, postData, sender, recipients));
    }
    
    
    /**
     * Send queued events to their recipients if they have reach the delivery 
     * time.  Technically, this will deliver any with a delivery time less than 
     * that of the games central clock.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" }) 
    public synchronized void deliver() {
        int number = list.size();
        // Old school loop to avoid tossing iterators to the garbage collector
        for(int i = 0; i < number; i++) {
            TimedEvent msg = list.get(i);
            IEventReceiver<T>[] recipients = msg.getRecipients();
            if(BaseGame.game.getTime() < msg.getTime()) {
                for(int j = 0; j < recipients.length; j++) {
                    recipients[j].recieveMsg(msg);
                }
                delivered.add(msg);
            }
        }
        number = delivered.size();
        for(int i = number - 1; i > -1; i--) {
            list.remove(delivered.get(i));
        }
        delivered.clear();
    }
    
}
