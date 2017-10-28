package jaredbgreat.arcade.event;

import jaredbgreat.arcade.util.GameLogger;
import java.util.List;

/**
 * A mutable event class, for use in creating pooled events.
 *
 * @author Jared Blackburn
 * @param <T>
 */
public class MEvent<T> implements IEvent<T> {
    T type;
    Object sender;
    IEventReceiver<T>[] recipients;
    
    
    MEvent() {/*This is to limit initializationj*/}
    
    
    @SuppressWarnings({ "unchecked", "rawtypes" }) 
    MEvent init(T type, Object sender, IEventReceiver<T>... recipients) {
        this.type = type;
        this.sender = sender;
        this.recipients = recipients;
        return this;
    }
    
    
    @SuppressWarnings({ "unchecked", "rawtypes" }) 
    MEvent init(T type, Object sender, List<IEventReceiver<T>> recipients) {
        this.type = type;
        this.sender = sender;
        this.recipients = (IEventReceiver<T>[]) recipients.toArray();
        return this;
    }
    
    
    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" }) 
    public void send() {
        try {
            for(IEventReceiver recipient : recipients) {
                recipient.recieveMsg(this);
            }
        } catch (Throwable ex) {
            // Helpful, since stacktraces aren't typically useful for events, 
            // telling a call stack you already know but not who sent the event.
            GameLogger gl = GameLogger.mainLogger;
            gl.logError(ex.getLocalizedMessage());
            gl.logError("Caused by " + ex.getStackTrace()[0]);
            gl.logError("Caused by " + getClass().getCanonicalName());
            gl.logError("With data type " + type.getClass().getCanonicalName() 
                    + " and contents of " + type);
            gl.logError("And sent by " + sender + " of class " 
                    + sender.getClass().getCanonicalName());
            throw ex;
        }
    }
    
    
    ////////////////////////////////////////////////////////////////////////////
    /*                       GETTERS BELOW                                    */
    ////////////////////////////////////////////////////////////////////////////

    
    @Override
    public T getType() {
        return type;
    }

    @Override
    public Object getSender() {
        return sender;
    }

    @Override
    public IEventReceiver<T>[] getRecipients() {
        return recipients;
    }
    
}
