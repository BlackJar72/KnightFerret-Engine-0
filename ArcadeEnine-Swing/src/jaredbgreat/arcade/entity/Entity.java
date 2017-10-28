
package jaredbgreat.arcade.entity;

import jaredbgreat.arcade.game.BaseGame;
import jaredbgreat.arcade.util.math.Vec2d;
import jaredbgreat.arcade.ui.graphics.Graphic;
import jaredbgreat.arcade.ui.graphics.IDrawable;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Jared Blackburn
 */
public abstract class Entity implements IDrawable {
    private static final ArrayList<Entity> entities = new ArrayList<>();
    protected IController controller;
    protected int graphic, frame;
    protected double locX, locY;
    
    
    @Override
    public void draw() {
        Graphic.draw(graphic, frame, (int)locX, (int)locY);
    }
    
    
    public void update(double delta) {
        Vec2d velocity = controller.getDirection();
        locX = (int)Math.min(448, Math.max(0, locX + (delta * velocity.getX())));
        locY = (int)Math.min(544, Math.max(0, locY + (delta * velocity.getY())));
    }
    
    
    public static void updateAll(double delta) {
        for(int i = entities.size() - 1; i > -1; i--) {
            entities.get(i).update(delta);
        }
    }
    
    
    public double getX() {
        return locX;
    }
    
    
    public double getY() {
        return locY;
    }
    
    
    public int getGraphic() {
        return graphic;
    }
    
    
    public void collide(Entity other) {}
    
    
    public boolean isCollideable() {
        return true;
    }
    
    
    public Vec2d getLoc() {
        return new Vec2d(locX, locY);
    }
    
    
    public void die() {
            BaseGame.game.kill(this);
    }
    
    
    /**
     * This is called by remove to ensure that any clean-up required 
     * gets done when the entity is removed from the global entities list.  This 
     * includes setting any sub-class members that might cause a circular 
     * reference to null.  If entities are managed by some other data structure 
     * this must be called manually whenever they reach a point where they could 
     * be garbage collected; otherwise the Entities.remeove() function will call 
     * this.
     */
    protected void cleanup() {}
    
    
    
    
    /*------------------------------------------------------------------------*/
    /*                        Entiy List Management                           */
    /*------------------------------------------------------------------------*/
    
    
    public static final void add(Entity e) {        
        entities.add(e);
    }
    
    
    public static final void addAll(Collection<Entity> e) {
        entities.addAll(e);
    }
    
    
    public static final void remove(Entity e) {
        if(entities.remove(e)) {
            e.cleanup();
        }
    }
    
    
    public static final boolean removeAll(Collection<Entity> e) {
        for(Entity entity : e) {
            entity.cleanup();
        }
        return entities.removeAll(e);
    }
    
    
    public static final void clearList() {
        for(Entity entity : entities) {
            entity.cleanup();
        }
        entities.clear();
    }
    
    
    public static final int listSize() {
        return entities.size();
    }
    
    
    public static final Entity get(int index) {
        return entities.get(index);
    }
    
}
