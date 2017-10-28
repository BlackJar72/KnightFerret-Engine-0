//Copyright (c) Jared Blackburn 2017
package jaredbgreat.arcade.event;

/**
 * An interface for classes sending synchronous messages of the given type.
 * 
 * @author Jared Blackubrn
 * @param <T>
 */
public interface ISynchronousSender<T> {
    /**
     * This *MUST* call deliverEvent on a an appropriate SynchronousDispacher 
     * (using the same generic type).  That is the purpose and contract of 
     * this method.
     * 
     * While having it do something else is possible, calling the deliverEvent 
     * method on a SynchronousDispatch is the entire purpose.  If some other
     * behavior is desired it should not be here.
     * 
     * @param message
     * @param recipients 
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })             
    public void sendEvent(T message, ISynchronousReceiver<T>... recipients);
    
}
