//Copyright (c) Jared Blackburn 2017
package jaredbgreat.arcade.game;

/**
 *
 * @author Jared Blackburn
 */
public final class Timer {
    private volatile long base;
    private volatile long current;
    private volatile long previous;
    private volatile long elapsed;
    private volatile long pstart;
    private volatile long ptime;
    private volatile long lost;
    private volatile double out;
    private volatile boolean paused;
    
    
    public Timer() {
        reset();
    }
    
    
    /**
     * Reset the current, previous, and base times to System.nanoTime(), 
     * resets elapse and lost times to 0.
     */
    public void reset() {
        current = previous = base = System.nanoTime();
        elapsed = 0;
        lost    = 0;
    }
    
    
    /**
     * Pauses the timer.
     */
    public void pause() {
        pstart = System.nanoTime();
        paused = true;
    }
    
    
    /**
     * Unpauses the timer.
     */
    public void resume() {
        lost += (System.nanoTime() - pstart);
        paused = false;
    }
    
    
    /**
     * Ticks the timer forwards.
     * 
     * This should no long be used, as getTime now updates the time itself.
     */
    @Deprecated
    public void tick() {
        if(paused) {
            return;
        }
        previous = current;
        current = System.nanoTime();
        elapsed = current - base - lost;
    }
    
    
    /**
     * This will return the elapsed time in seconds, as of the last tick of the
     * timer.  In other word, the number of seconds (usually must less than one) 
     * between the last tick and the tick before.
     * 
     * @return 
     */
    public double getTime() {
       if(!paused) {           
            previous = current;
            current = System.nanoTime();
            elapsed = current - base - lost;
       }
       if(elapsed < 1000) {
           elapsed = 1000;
       }
       out = (((double)(elapsed)) / 1000000000.0d);
       return out;
    }
    
}
