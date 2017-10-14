package jaredbgreat.arcade.event;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A list of ISynchronoustRecievers that receive common messages.  This 
 * simplifies sending events to potentially large numbers of recipients, and 
 * is especially useful of if the recipients need to be added and removed (as 
 * in the case of entities the may be spawned into and/or removed from a game).
 * 
 * Any events sent to the list will be forwarded to all on the list.
 * 
 * Note that while the list can contain other SynchornousSubscriberLists this 
 * is not a good practice, as accidently adding a list to itself will lead to 
 * infinite recursion resulting a crash (stack overflow).
 * 
 * @author Jared Blackburn
 * @param <T>
 */
public class SynchronousSubscriberList<T> 
extends ArrayList<ISynchronousReceiver<T>> implements  ISynchronousReceiver<T> {
    /**
     * A convenient map for registering and finding synchronous subscriber 
     * lists using human readable strings.  This may not always be the best way 
     * to store and access subscription lists, but its here if you need it.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" }) 
    public static final HashMap<String, SynchronousSubscriberList> lists = 
            new HashMap<>();
    
    @Override
    public boolean add(ISynchronousReceiver<T> recipient) {
        // Should this really use contains?  Or have a set to check?
        if(!contains(recipient)) {
            return super.add(recipient);
        }
        else return false;
    }
    
    
    /**
     * This will deliver the event to all ISynchronousReceivers in the list.
     * Specifically, this will call the executeEvent method on each receiver 
     * in the list.
     * 
     * @param type
     */
    @Override
    public void executeEvent(T type) {
        for(ISynchronousReceiver<T> recipient : this) {
            recipient.executeEvent(type);
        }
    }
    
}
