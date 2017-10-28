//Copyright (c) Jared Blackburn 2017
package jaredbgreat.arcade.event;


/**
 * An interface for classes receiving events through an event queue.
 * 
 * @author Jared Blackburn
 * @param <T>
 */
public interface IEventReceiver<T> {
    /**
     * This method is used to deliver events for processing.  Note that this 
     * will be called on the EventQueue's thread, which may not be that used 
     * primarily by the objects receiving the message.
     * 
     * As this method effectively pushes the event to the receiver, the 
     * receiver must maintain its own mailbox (usually some form of queue) in 
     * order to processes at a later time; this is particularly important in 
     * multi-threaded applications as the delivery may be on a different thread 
     * from that used by the receivers other methods.
     * 
     * In non-concurrent applications the use of a mailbox is less important 
     * and it is likely that events will simply be processes as they arrive.
     * 
     * @param e the event being sent.
     */
    public void recieveMsg(IEvent<T> e);    
}
