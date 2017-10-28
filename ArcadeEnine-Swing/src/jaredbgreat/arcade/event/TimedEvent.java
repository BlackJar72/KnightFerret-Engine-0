//Copyright (c) Jared Blackburn 2017
package jaredbgreat.arcade.event;

import jaredbgreat.arcade.util.GameLogger;
import java.util.List;

/**
 * An event class.  Type \<T\> should almost always be an enum.  This is for 
 * delayed response events -- the event can be stored in a local list acting 
 * as a kind of mail box and executed when the game time is greater of equal 
 * to the postDate time.
 * 
 * @author Jared Blackburn
 * @param <T> The data type (usually an enum) carried by the event.
 */
public final class TimedEvent<T> implements IEvent<T> {
    final T type;
    final float postDate;
    final Object sender;
    final IEventReceiver<T>[] recipients;
    
    
    @SuppressWarnings("unchecked")
    public TimedEvent(T type, float postDate, 
            Object sender, IEventReceiver<T>... recipients) {
        this.type = type;
        this.postDate = postDate;
        this.sender = sender;
        this.recipients = recipients;
    }
    
    
    @SuppressWarnings("unchecked")
    public TimedEvent(T type, float postDate, 
            Object sender, List<IEventReceiver<T>> recipients) {
        this.type = type;
        this.postDate = postDate;
        this.sender = sender;
        this.recipients = (IEventReceiver<T>[]) recipients.toArray();    
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
    
    public float getTime() {
        return postDate;
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
