//Copyright (c) Jared Blackburn 2017
package jaredbgreat.arcade.event;

import jaredbgreat.arcade.util.GameLogger;
import java.util.List;

/**
 * An event class.  Type \<T\> should almost always be an enum, though other 
 * types are possible.  Note the while Integer could used to directly map ints, 
 * this would not be good in terms or human readability and code cleanliness.
 *
 * Alternately this could contain object of a type that encapsulates both an
 * enum and a data payload that some responses might use.  Or, it could also 
 * be use to passes action directly with \<T\> being a command (as in the 
 * command pattern, with either flyweight or data-carrying commands).  Then, 
 * since this is Java, commands whose identity is meant to be immutable could
 * be stored in a enum anyway -- so, do you want to re-map anything??
 * 
 * @author Jared Blackburn
 * @param <T> The data type (usually an enum) carried by the event.
 */
public final class Event<T> implements IEvent<T> {
    final T type;
    final Object sender;
    final IEventReceiver<T>[] recipients;
    
    
    @SuppressWarnings({ "unchecked", "rawtypes" }) 
    Event(T type, Object sender, IEventReceiver<T>... recipients) {
        this.type = type;
        this.sender = sender;
        this.recipients = recipients;
    }
    
    
    @SuppressWarnings({ "unchecked", "rawtypes" }) 
    Event(T type, Object sender, List<IEventReceiver<T>> recipients) {
        this.type = type;
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

    @Override
    public Object getSender() {
        return sender;
    }

    @Override
    public IEventReceiver<T>[] getRecipients() {
        return recipients;
    }
}
