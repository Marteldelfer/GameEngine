package engine.helper;

import engine.entities.Entity;

public class Collision {

    /**
     * Check if two entities are colliding.
     */
    public static boolean isColliding(Entity a, Entity b) {
        if (a.getX() + a.getWidth() <= b.getX()) return false; // a is to the left of b
        if (a.getX() >= b.getX() + b.getWidth()) return false; // a is to the right of b
        if (a.getY() + a.getHeight() <= b.getY()) return false; // a is bellow b
        if (a.getY() >= b.getY() + b.getHeight()) return false;// a is above b
        return true;
    }
}
