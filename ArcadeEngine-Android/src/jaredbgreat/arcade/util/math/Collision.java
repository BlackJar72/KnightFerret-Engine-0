package jaredbgreat.arcade.util.math;

import android.graphics.Bitmap;

import jaredbgreat.arcade.entity.Entity;
import jaredbgreat.arcade.ui.graphics.Graphic;
import java.util.List;

/**
 *
 * @author Jared Blackburn
 */
public class Collision {
    
    
    /**
     * This will test for collision between all pairs of entities in the global 
     * entity list Entity.entities.  It will then call collide those entities 
     * that have collided.
     */
    public static void testCollision() {
        int n = Entity.listSize();          
        for(int i = 0; i < n; i++) {
            Entity a = Entity.get(i);
            if(!a.isCollideable()) {
                continue;
            }
            for(int j = i + 1; j < n; j++) {
                Entity b = Entity.get(j);
                if((!b.isCollideable())) {
                    continue;
                }
                if(hit(a, b)) {
                    a.collide(b);
                }
            }
        }
    }
    
    
    /**
     * This will test for collision between all pairs of entities in the List 
     * provided.  It will then call collide those entities 
     * that have collided.
     * 
     * @param entities A java.util.List of entities to test collision on.
     */
    public static void testCollision(List<Entity> entities) {
        int n = entities.size();          
        for(int i = 0; i < n; i++) {
            Entity a = entities.get(i);
            if(!a.isCollideable()) {
                continue;
            }
            for(int j = i + 1; j < n; j++) {
                Entity b = entities.get(j);
                if((!b.isCollideable())) {
                    continue;
                }
                if(hit(a, b)) {
                    a.collide(b);
                }
            }
        }
    }
    
    
    /**
     * This will determine if two entities have collided visually on screen by 
     * first performing an Axis Aligned Bounding Box (AABB) test on the entities 
     * images.  Any entities whose images overlap will then be tested to see if 
     * any visible pixels also overlap.
     * 
     * @param a First entity
     * @param b Second entity
     * @return 
     */
    public static boolean hit(Entity a, Entity b) {
        if(AABB(a, b)) {
            return imageOverlap(Graphic.registry.get(a.getGraphic()).getImage(), 
                    (int)a.getX(), (int)a.getY(),
                    Graphic.registry.get(b.getGraphic()).getImage(), 
                    (int)b.getX(), (int)b.getY());
        } else return false;
    }
    
    
    /**
     * And Axis Aligned Bounding Box (AABB) test, using two entities directly.  
     * This version will have images passed in directly.
     * 
     * @param a First entity
     * @param b Second entity
     * @return True of the images of the entities intersect.
     */
    private static boolean AABB(Entity a, Bitmap ga, Entity b,
            Bitmap gb) {
        double ax1 = a.getX();
        double ay1 = a.getY();
        double ax2 = ax1 + ga.getWidth();
        double ay2 = ay1 + ga.getHeight();
        double bx1 = b.getX();
        double by1 = b.getY();
        double bx2 = bx1 + gb.getWidth();
        double by2 = by1 + gb.getHeight();
        return (lineOverlap(ax1, ax2, bx1, bx2) && lineOverlap(ay1, ay2, by1, by2));
    }
    
    
    /**
     * And Axis Aligned Bounding Box (AABB) test, using two entities directly; 
     * this version will get the images from the entities.
     * 
     * @param a First entity
     * @param b Second entity
     * @return True of the images of the entities intersect.
     */
    public static boolean AABB(Entity a, Entity b) {        
        double ax1 = a.getX();
        double ay1 = a.getY();
        double ax2 = ax1 + Graphic.registry.get(a.getGraphic()).getImage().getWidth();
        double ay2 = ay1 + Graphic.registry.get(a.getGraphic()).getImage().getHeight();
        double bx1 = b.getX();
        double by1 = b.getY();
        double bx2 = bx1 + Graphic.registry.get(b.getGraphic()).getImage().getWidth();
        double by2 = by1 + Graphic.registry.get(b.getGraphic()).getImage().getHeight();
        return (lineOverlap(ax1, ax2, bx1, bx2) && lineOverlap(ay1, ay2, by1, by2));
    }
    
    
    /**
     * This will determine if two line segments intersect (overlap).  Used 
     * for AABB collision tests.
     * 
     * @param a11 Beginning of first line segment
     * @param a12 End of first line segment
     * @param a21 Beginning of second line segment
     * @param a22 End of second line segment
     * @return 
     */
    public static boolean lineOverlap(double a11, double a12, 
            double a21, double a22) {
        return !((a12 < a21) || (a22 < a11));
    }
    
    
    /**
     * This will determine if two line segments intersect (overlap).  Used 
     * for AABB collision tests.
     * 
     * @param a11 Beginning of first line segment
     * @param a12 End of first line segment
     * @param a21 Beginning of second line segment
     * @param a22 End of second line segment
     * @return 
     */
    public static boolean lineOverlap(int a11, int a12, 
            int a21, int a22) {
        return !((a12 < a21) || (a22 < a11));
    }
    
    
    /**
     * This will determine if two intersecting BufferedImages have overlapping 
     * images, that is, if both have a non-transparent pixel at the same global 
     * coordinate.  This is primarily to determine if two entities have visually 
     * collided.
     * 
     * It is important that the images overlap, as passing non-overlaping 
     * images / coordinate will result in an array out of bounds exception, 
     * crashing if the exceptions is not caught.  This is meant to be performed 
     * after a collision has been found using a axis aligned bounding box test 
     * on the images technical area, and should no be used otherwise.
     * 
     * @param a First image
     * @param ax Origin (upper left) of image a
     * @param ay Origin (upper left) of image a
     * @param b Second image
     * @param bx Origin (upper left) of image b
     * @param by Origin (upper left) of image b
     * @return 
     */
    public static boolean imageOverlap(Bitmap a, int ax, int ay,
            Bitmap b, int bx, int by) {
        int aw = a.getWidth();
        int ah = a.getHeight();
        int bw = b.getWidth();
        int bh = b.getHeight();
        int sx = Math.max(ax, bx);
        int sy = Math.max(ay, by);
        int ex = Math.min(ax + aw, bx + bw);
        int ey = Math.min(ay + ah, by + bh);
        int rx = ex - sx;
        int ry = ey - sy;
        if(ax < bx) {
            int offx = bx - ax;
            if(ay < by) {
                int offy = by - ay;
                for(int i = 0; i < rx; i++) {
                    for(int j = 0; j < ry; j++) {
                        if(pixelOverlap(a.getPixel(i + offx, j + offy),
                                b.getPixel(i, j))) {
                            return true;
                        }
                    }
                }
            } else {
                int offy = ay - by;
                for(int i = 0; i < rx; i++) {
                    for(int j = 0; j < ry; j++) {
                        if(pixelOverlap(a.getPixel(i + offx, j),
                                b.getPixel(i, j + offy))) {
                            return true;
                        }
                    }
                }
            }
        } else {
            int offx = ax - bx;
            if(ay < by) {
                int offy = by - ay;
                for(int i = 0; i < rx; i++) {
                    for(int j = 0; j < ry; j++) {
                        if(pixelOverlap(b.getPixel(i + offx, j),
                                a.getPixel(i, j + offy))) {
                            return true;
                        }
                    }
                }
            } else {
                int offy = ay - by;
                for(int i = 0; i < rx; i++) {
                    for(int j = 0; j < ry; j++) {
                        if(pixelOverlap(b.getPixel(i + offx, j + offy),
                                a.getPixel(i, j))) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    
    /**
     * This will determine if two pixels are both non-transparent, that is 
     * that both have an non-zero alpha component.
     * 
     * @param a first pixel
     * @param b second pixel
     * @return 
     */
    private static boolean pixelOverlap(int a, int b) {
        a &= 0xff000000;
        b &= 0xff000000;
        return (((a >> 24) * (b >> 24)) == 0);
    }
    
}
