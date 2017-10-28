package jaredbgreat.arcade.event;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A list of IEventRecievers that receive common messages.  This simplifies 
 * sending events to potentially large numbers of recipients, and is especially
 * useful of if the recipients need to be added and removed (as in the case 
 * of entities the may be spawned into and/or removed from a game).
 * 
 * Any events sent to the list will be forwarded to all on the list.
 * 
 * Note that while the list can contain other SubscriberLists this is not a 
 * good practice, as accidently adding a list to itself will lead to infinite 
 * recursion resulting a crash (stack overflow).
 * 
 * @author Jared Blackburn
 * @param <T>
 */
public class EventSubscriberList<T> extends ArrayList<IEventReceiver<T>> 
implements IEventReceiver<T> {
    /**
     * A convenient map for registering and finding event subscriber lists 
     * using human readable strings.  This may not always be the best way 
     * to store and access subscription lists, but its here if you need it.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" }) 
    public static final HashMap<String, EventSubscriberList> lists = 
            new HashMap<>();
    
    @Override
    public boolean add(IEventReceiver<T> recipient) {
        // Should this really use contains?  Or have a set to check?
        if(!contains(recipient)) {
            return super.add(recipient);
        }
        else return false;
    }
    
    
    /**
     * This will deliver the event to all IEventRecievers in the list.  
     * Specifically it will call the recieveMsg method of each receiver 
     * in the list.
     *
     * @param e
     */
    @Override
    public void recieveMsg(IEvent<T> e) {
        for(IEventReceiver<T> recipient : this) {
            recipient.recieveMsg(e);
        }
    }
    
    
}
