package engine.state;

import engine.annotations.*;
import engine.entities.Collidable;
import engine.entities.Entity;
import engine.helper.Collision;
import game.Npc;
import game.Player;
import engine.graphics.Panel;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DefaultGameState extends GameState {
    protected final EntityManager entityManager;

    public DefaultGameState(EntityManager entityManager, Panel panel) {
        this.entityManager = entityManager;
        super(panel);
    }

    @Override
    public void onEnter() {
        entityManager.addEntity(new Player(200, 200, 400, 400));
        entityManager.addEntity(new Npc(200, 600, 200, 100));
        entityManager.sync();
    }

    @Override
    public void onExit() {
    }

    @Override
    public void update() {
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

    private static void applyCollision(Entity entity, Entity b) {
        ((Collidable) entity).collide(b);
        applyMovementCollision(entity, b);
    }

    private static void applyMovementCollision(Entity entity, Entity b) {
        if (entity.getClass().isAnnotationPresent(MovementCollision.class)) {
            MovementCollision annotation = entity.getClass().getAnnotation(MovementCollision.class);
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
        for (Entity entity : entityManager.getEntities()) {
            for (Method m : entity.getClass().getDeclaredMethods()) {
                try {
                    if (m.isAnnotationPresent(OnRender.class)) m.invoke(entity, g);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        }
    }
}
