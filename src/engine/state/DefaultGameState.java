package engine.state;

import engine.annotations.Gravity;
import engine.annotations.MovementCollision;
import engine.annotations.OnRender;
import engine.annotations.OnUpdate;
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
        entityManager.addEntity(new Npc(600, 600, 100, 100));
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
                    if (m.isAnnotationPresent(Gravity.class)) applyGravity(entity, m);
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
            entity.setY(entity.getY() - entity.getySpeed());
            if (Collision.isColliding(entity, b)) {
                if (entity.getxSpeed() > 0) entity.setX(b.getX() - entity.getWidth());
                if (entity.getxSpeed() < 0) entity.setX(b.getX() + b.getWidth());
            }
            entity.setY(entity.getY() + entity.getySpeed());
            if (Collision.isColliding(entity, b)) {
                if (entity.getySpeed() > 0) entity.setY(b.getY() - entity.getHeight());
                if (entity.getySpeed() < 0) entity.setY(b.getY() + b.getHeight());
            }
        }
    }

    private static void applyUpdate(Entity entity, Method m) throws IllegalAccessException, InvocationTargetException {
        m.invoke(entity);
    }

    private static void applyGravity(Entity entity, Method m) {
        Gravity g = m.getAnnotation(Gravity.class);
        entity.setxSpeed(entity.getxSpeed() + (float) (Math.cos(g.direction())) * g.acceleration());
        entity.setySpeed(entity.getySpeed() - (float) (Math.sin(g.direction())) * g.acceleration());
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
