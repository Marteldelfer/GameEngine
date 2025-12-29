package game;

import engine.annotations.MovementCollision;
import engine.annotations.OnRender;
import engine.annotations.OnUpdate;
import engine.entities.Collidable;
import engine.entities.Entity;
import engine.entities.Renderable;
import engine.helper.Collision;
import engine.input.Keyboard;

import java.awt.*;
import java.awt.event.KeyEvent;

@MovementCollision
public class Player extends Entity implements Renderable, Collidable {
    boolean left, right, up, down;

    public Player(float x, float y, float width, float height) {
        super(x, y, width, height);
        Keyboard.onPress(KeyEvent.VK_A, () -> left = true);
        Keyboard.onPress(KeyEvent.VK_D, () -> right = true);
        Keyboard.onPress(KeyEvent.VK_W, () -> up = true);
        Keyboard.onPress(KeyEvent.VK_S, () -> down = true);

        Keyboard.onRelease(KeyEvent.VK_A, () -> left = false);
        Keyboard.onRelease(KeyEvent.VK_D, () -> right = false);
        Keyboard.onRelease(KeyEvent.VK_W, () -> up = false);
        Keyboard.onRelease(KeyEvent.VK_S, () -> down = false);

        Keyboard.onPress(KeyEvent.VK_SPACE, () -> ySpeed -= 10);
    }

    @OnUpdate
    //@Gravity(acceleration = .1f)
    public void update() {
        this.move();
    }

    @Override
    @OnRender
    public void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect((int) x,(int) y,(int) width,(int) height);
    }

    @Override
    public void collide(Entity e) {
    }

    private void move() {
        xSpeed = 0;
        ySpeed = 0;
        if (left) xSpeed -= 5;
        if (right) xSpeed += 5;
        if (down) ySpeed += 5;
        if (up) ySpeed -= 5;

        x += xSpeed;
        y += ySpeed;
    }
}
