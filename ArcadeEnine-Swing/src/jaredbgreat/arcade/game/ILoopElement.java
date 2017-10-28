package jaredbgreat.arcade.game;

/**
 * Am interface for portions of the game loop (for example, entity update, 
 * collision, or rendering).
 * 
 * @author Jared Blackburn 
 */
public interface ILoopElement {
    /**
     * Update this portion of the loop.
     */
    public void update();
}
