package jaredbgreat.arcade.entity;

import jaredbgreat.arcade.util.math.Vec2d;

/**
 *
 * @author jared
 */
public interface IController {
    /**
     * Get the velocity of movement in the form of a Vec2d.
     * 
     * @return movement vector
     */
    public Vec2d getDirection();
    
    /**
     * True of the entity should "fire" (or use it most basic / common attack).
     * 
     * This could probably not be used, in favor of the more flexible 
     * codedAction() method, but for simple games this may be sufficient.
     * 
     * @return 
     */
    public boolean fire(); 
    
    /**
     * Return a game specific (and platform agnostic) integer code for possible 
     * actions.  The meaning of these codes is up to the entity receiving them 
     * to interpret, but in most cases will likely be coded as bit flags, 
     * allowing for multiple actions to be coded in a single number.
     * 
     * @return 
     */
    public int codedAction();
}
