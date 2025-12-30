package engine.state;

import engine.annotations.*;
import engine.entities.Collidable;
import engine.entities.Entity;
import engine.entities.Renderable;
import engine.helper.Collision;
import engine.graphics.Panel;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;

public class DefaultGameState extends GameState {
    public DefaultGameState(EntityManager entityManager, Panel panel) {
        super(panel, entityManager);
    }

    @Override
    public void onEnter() {
    }

    @Override
    public void onExit() {
    }

    @Override
    public void update() {
        System.out.println(entityManager.getEntities());
        clearInactiveEntities();
        for (Entity entity : entityManager.getEntities()) {
            for (Method m : entity.getClass().getDeclaredMethods()) {
                try {
                    if (m.isAnnotationPresent(OnUpdate.class)) applyUpdate(entity, m);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
            // detecting collisions
            if (!(entity instanceof Collidable)) continue;
            for (Entity b : entityManager.getEntities()) {
                if (entity == b || !(b instanceof Collidable)) continue;
                if (Collision.isColliding(entity, b)) applyCollision(entity, b);
            }
        }
    }

    private void clearInactiveEntities() {
        for (Entity e : entityManager.getEntities()) {
            if (!e.isActive()) entityManager.remove(e);
        }
        entityManager.sync();
    }

    private static void applyCollision(Entity entity, Entity b) {
        ((Collidable) entity).collide(b);
        ((Collidable) b).collide(entity);
        applyMovementCollision(entity, b);
    }

    private static void applyMovementCollision(Entity entity, Entity b) {
        if (!entity.getClass().isAnnotationPresent(MovementCollision.class)) return;
        if (!b.getClass().isAnnotationPresent(MovementCollision.class)) return;

        MovementCollision annotation = entity.getClass().getAnnotation(MovementCollision.class);
        // Horizontal collision
        entity.setY(entity.getY() - entity.getySpeed());
        if (Collision.isColliding(entity, b)) {
            if (entity.getxSpeed() > 0) {
                entity.setX(b.getX() - entity.getWidth());
                if (entity.getxSpeed() > annotation.bounceThreshold()) {
                    entity.setxSpeed(-annotation.bounceSpeedX());
                } else {
                    entity.setxSpeed(0);
                }
            } else if (entity.getxSpeed() < 0) {
                entity.setX(b.getX() + b.getWidth());
                if (entity.getxSpeed() < -annotation.bounceThreshold()) {
                    entity.setxSpeed(annotation.bounceSpeedX());
                } else {
                    entity.setxSpeed(0);
                }
            }
        }
        // Vertical collision
        entity.setY(entity.getY() + entity.getySpeed());
        if (Collision.isColliding(entity, b)) {
            if (entity.getySpeed() > 0) {
                entity.setY(b.getY() - entity.getHeight());
                if (entity.getySpeed() > annotation.bounceThreshold()) {
                    entity.setySpeed(-annotation.bounceSpeedY());
                } else {
                    entity.setySpeed(0);
                }
            } else if (entity.getySpeed() < 0) {
                entity.setY(b.getY() + b.getHeight());
                if (entity.getySpeed() < -annotation.bounceThreshold()) {
                    entity.setySpeed(annotation.bounceSpeedY());
                } else {
                    entity.setySpeed(0);
                }
            }

        }
    }

    private static void applyUpdate(Entity entity, Method m) throws IllegalAccessException, InvocationTargetException {
        m.invoke(entity);
        if (entity.getClass().isAnnotationPresent(HorizontalMovement.class)) applyHorizontalMovement(entity);
        if (entity.getClass().isAnnotationPresent(VerticalMovement.class)) applyVerticalMovement(entity);
        if (entity.getClass().isAnnotationPresent(Gravity.class)) applyGravity(entity);
        if (entity.getClass().isAnnotationPresent(EnableMovement.class)) applyMovement(entity);
    }

    private static void applyMovement(Entity entity) {
        entity.setY(entity.getY() + entity.getySpeed());
        entity.setX(entity.getX() + entity.getxSpeed());
    }

    private static void applyVerticalMovement(Entity entity) {
        VerticalMovement a = entity.getClass().getAnnotation(VerticalMovement.class);
        if (entity.isUp()) entity.setySpeed(Math.max(-a.speed(), entity.getySpeed() - a.acceleration()));
        if (entity.isDown()) entity.setySpeed(Math.min(a.speed(), entity.getySpeed() + a.acceleration()));

        if (!entity.isUp() && !entity.isDown()) {
            if (entity.getySpeed() > 0) entity.setySpeed(Math.max(0, entity.getySpeed() - a.deceleration()));
            if (entity.getySpeed() < 0) entity.setySpeed(Math.min(0, entity.getySpeed() + a.deceleration()));
        }
    }

    private static void applyHorizontalMovement(Entity entity) {
        HorizontalMovement a = entity.getClass().getAnnotation(HorizontalMovement.class);
        if (entity.isLeft()) entity.setxSpeed(Math.max(-a.speed(), entity.getxSpeed() - a.acceleration()));
        if (entity.isRight()) entity.setxSpeed(Math.min(a.speed(), entity.getxSpeed() + a.acceleration()));

        if (!entity.isRight() && !entity.isLeft()) {
            if (entity.getxSpeed() > 0) entity.setxSpeed(Math.max(0, entity.getxSpeed() - a.deceleration()));
            if (entity.getxSpeed() < 0) entity.setxSpeed(Math.min(0, entity.getxSpeed() + a.deceleration()));
        }
    }

    private static void applyGravity(Entity entity) {
        Gravity g = entity.getClass().getAnnotation(Gravity.class);
        entity.setxSpeed(entity.getxSpeed() + (float) (Math.cos(g.direction())) * g.acceleration());
        entity.setySpeed(entity.getySpeed() - (float) (Math.sin(g.direction()) * g.acceleration()));
    }

    @Override
    public void render(Graphics g) {
        entityManager.getEntities().stream()
                .filter(e -> e.getClass().isAnnotationPresent(OnRender.class) && e instanceof Renderable)
                .sorted(Comparator.comparingInt(e -> e.getClass().getAnnotation(OnRender.class).z()))
                .forEach(e -> ((Renderable) e).render(g));
    }
}
