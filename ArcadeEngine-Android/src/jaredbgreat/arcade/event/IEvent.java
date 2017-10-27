package jaredbgreat.arcade.event;

/**
 *
 * @author Jared Blackburn
 * @param <T>
 */
public interface IEvent<T> {
    void send();
    public T getType();
    public Object getSender();
    public IEventReceiver<T>[] getRecipients();
    
}
