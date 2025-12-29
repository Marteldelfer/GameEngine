package game;

import engine.annotations.*;
import engine.entities.Collidable;
import engine.entities.Entity;
import engine.entities.Renderable;
import engine.input.Keyboard;

import java.awt.*;
import java.awt.event.KeyEvent;

@EnableMovement
@MovementCollision(bounceSpeedX = 3, bounceSpeedY = 3, bounceThreshold = 5)
@VerticalMovement(speed = 10, acceleration = 0, deceleration = 0)
@HorizontalMovement(speed = 10, acceleration = .1f)
@Gravity()
public class Player extends Entity implements Renderable, Collidable {

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

        Keyboard.onPress(KeyEvent.VK_SPACE, () -> ySpeed -= 20);
    }

    @OnUpdate
    public void update() {}

    @Override
    @OnRender
    public void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect((int) x,(int) y,(int) width,(int) height);
    }

    @Override
    public void collide(Entity e) {
    }
}
