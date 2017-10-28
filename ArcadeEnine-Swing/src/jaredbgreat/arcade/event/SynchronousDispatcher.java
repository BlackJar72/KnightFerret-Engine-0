package jaredbgreat.arcade.event;

/**
 * A multi-recipient message mediator that can take messages from any number of 
 * potential senders and deliver them to any number of potential "observers" 
 * (recipients).  This is done in an immediate, synchronous manor though direct 
 * method calls.  
 * 
 * The class has only one method, deliverEvent, which immediately calls the
 * each recipients executeEvent method -- there is no queuing.  For asynchronous 
 * events message use the Event and EventQueue classes instead.
 * 
 * Due to the generic nature of the class it must be instantiate with the 
 * correct event type (usually, though no necessarily,an enum), as a static 
 * method would not make since.  A single instance is often the best option, 
 * or one per supported type.  If multiple message types are desired it may 
 * be a good plan to store them in a HashMap or Registry.
 * 
 * As works through direct function calls, all action will be taken on the 
 * same thread as the caller.  It communication between thread is desired, 
 * and EventQueue should to push events into thread safe mailboxes (such as the 
 * provided ring queues) from which targets on the other thread may pull them.
 * 
 * @author Jared Blackburn
 * @param <T> The type (usually and enum) contained in the message.
 */
public class SynchronousDispatcher<T> {
    
    
    /**
     * Executes the event on all recipients by calling there executeEvent 
     * method.
     * 
     * @param event
     * @param recipients 
     */
    @SuppressWarnings({ "unchecked", "rawtypes" }) 
    public void deliverEvent(T event, ISynchronousReceiver<T>... recipients) {
        for(ISynchronousReceiver<T> recipient : recipients) {
            recipient.executeEvent(event);
        }
    }
}
