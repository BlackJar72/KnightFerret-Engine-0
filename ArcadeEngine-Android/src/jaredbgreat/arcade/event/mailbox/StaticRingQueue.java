package jaredbgreat.arcade.event.mailbox;

/**
 * A statically sized, thread-safe, array-based queue.  This could have a 
 * variety of uses, but its primary purpose is to act as mailboxes in 
 * multi-threaded application -- especially when communication across threads 
 * is needed.
 * 
 * NOTE: Not implementing Oracles Queue interface -- its has way too many 
 * unneeded methods.  Maybe later...or not?  Though, it might be nice to add 
 * iterators.
 * 
 * @author Jared Blackburn
 * @param <T>
 */
public class StaticRingQueue<T> {
    private final T[] data;
    private volatile int start, next;
    private volatile boolean full;
    
    
    @SuppressWarnings({ "unchecked", "rawtypes" }) 
    public StaticRingQueue(int size) {
        data = (T[])(new Object[size]);
        next = start = 0;
        full = false;
    }
    
    
    @SuppressWarnings({ "unchecked", "rawtypes" }) 
    public synchronized void push(T element) {
        if(full) {
            return;
        }
        data[next] = element;
        next = (++next) % data.length;
        full = next == start;
    }
    
    
    @SuppressWarnings({ "unchecked", "rawtypes" })     
    public synchronized boolean offer(T element) {
        if(full) {
            return false;
        }
        data[next] = element;
        next = (++next) % data.length;
        full = next == start;
        return true;
    }
    
    
    @SuppressWarnings({ "unchecked", "rawtypes" }) 
    public synchronized boolean add(T element) throws IllegalStateException {
        if(full) throw new IllegalStateException();
        data[next] = element;
        next = (++next) % data.length;
        full = next == start;
        return true;
    }
    
    
    public synchronized T peek() {
        return data[start];
    }
    
    
    public synchronized T pop() {
        if(isEmpty()) {
            return null;
        }
        T out = data[start];
        start = (++start) % data.length;
        full = false; // Must now be at least one away from full
        return out;
    }
    
    
    public synchronized T remove() throws IllegalStateException {
        if(isEmpty()) throw new IllegalStateException();
        T out = data[start];
        data[start] = null;
        start = (++start) % data.length;
        full = false; // Must now be at least one away from full
        return out;
    }
    
    
    public synchronized boolean isEmpty() {
        return (start == next) && !full;
    }
    
    
    public synchronized boolean isFull() {
        return full;
    }
    
    
    public int size() {
        return data.length;
    }
    
    
    public synchronized int numElements() {
        int out = next - start;
        if(out > -1) {
            return out;
        } else {
            return data.length + out;
        }
    }
    

    public synchronized T element() throws IllegalStateException {
        if(isEmpty()) throw new IllegalStateException();
        else return peek();
    }
    

    public synchronized boolean contains(Object o) {
        if(start < next) {
            for(int i = start; i < next; i++) {
                if(data[i].equals(o)) {
                    return true;
                }
            }
            return false;
        } else {
            for(int i = start; i < data.length; i++) {
                if(data[i].equals(o)) {
                    return true;
                }
            }
            for(int i = 0; i < next; i++) {
                if(data[i].equals(o)) {
                    return true;
                }
            }
            return false;
        }
    }
    

    public synchronized Object[] toArray() {
        int n = numElements();
        Object[] out = new Object[n];
        if(start < next) {
            System.arraycopy(data, start, out, 0, n);
        } else {
            System.arraycopy(data, start, out, 0, data.length - start);
            System.arraycopy(data, 0, out, data.length - start, next);
        }
        return out;
    }


    public synchronized void clear() {
        full = false;
        data[start] = null;
        next = start = 0;
    }
}
