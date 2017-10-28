package jaredbgreat.arcade.event.mailbox;

/**
 * A dynamically sized, thread-safe, array-based queue.  This could have a 
 * variety of uses, but its primary purpose is to act as mailboxes in 
 * multi-threaded application -- especially when communication across threads 
 * is needed.
 * 
 * WARNING: Do not use until I learn why NetBeans warns about volatile arrays!
 * 
 * NOTE: Not implementing Oracles Queue interface -- its has way too many 
 * unneeded methods.  Maybe later...or not?  Though, it might be nice to add 
 * iterators.
 * 
 * @author Jared Blackburn
 * @param <T>
 */
public class DynamicRingQueue<T> {
    private volatile T[] data;
    private volatile int start, next;
    
    
    @SuppressWarnings({ "unchecked", "rawtypes" })     
    public DynamicRingQueue(int size) {
        data = (T[])(new Object[size]);
        next = start = 0;
    }
    
    
    @SuppressWarnings({ "unchecked", "rawtypes" }) 
    public DynamicRingQueue() {
        data = (T[])(new Object[16]);
        next = start = 0;
    }
    
    
    @SuppressWarnings({ "unchecked", "rawtypes" }) 
    private void grow() {
        T[] newData = (T[])(new Object[(data.length * 3) / 2]);
        if(start < next) {
            System.arraycopy(data, start, newData, 0, next - start);
        } else {
            System.arraycopy(data, start, newData, 0, data.length - start);
            System.arraycopy(data, 0, newData, data.length - start, next);
        }        
        start = 0;
        next = numElements();
        data = newData;
    }
    
    
    public synchronized int numElements() {
        int out = next - start;
        if(out > -1) {
            return out;
        } else {
            return data.length + out;
        }
    }
    
    
    public synchronized void push(T element) {
        data[next] = element;
        next = (++next) % data.length;
        if(next == start) grow();
    }
    
    
    public synchronized T peek() {
        return data[start];
    }
    
    
    public synchronized T pop() {
        if(isEmpty()) {
            return null;
        }
        T out = data[start];
        data[start] = null;
        start = (++start) % data.length;
        return out;
    }
    
    
    public synchronized T remove() throws IllegalStateException {
        if(isEmpty()) {
            throw new IllegalStateException();
        }
        T out = data[start];
        data[start] = null;
        start = (++start) % data.length;
        return out;
    }
    
    
    public synchronized boolean isEmpty() {
        return (start == next);
    }
    
    
    public int size() {
        return data.length;
    }
    

    public synchronized T element() throws IllegalStateException {
        if(isEmpty()) {
            throw new IllegalStateException();
        }
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
        data[start] = null;
        next = start = 0;
    }
    
}
