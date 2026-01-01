package game.flappybird.src.entities;

import engine.annotations.*;
import engine.entities.Collidable;
import engine.entities.Entity;
import engine.entities.Renderable;
import engine.helper.Load;
import engine.input.Keyboard;
import engine.state.DefaultGameState;
import engine.state.EntityManager;
import engine.state.GameState;
import game.flappybird.src.states.MenuState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

@OnRender(z = 1)
@EnableMovement
@Gravity(acceleration = .1f)
public class Player extends Entity implements Renderable, Collidable {

    private final BufferedImage image;
    private float angle = 0;
    private final float spriteW, spriteH;

    public Player(
            float x, float y, float width, float height,
            EntityManager entityManager, GameState gameState,
            BufferedImage image
    ) {
        super(x, y, width, height, entityManager, gameState);
        this.image = image;
        Keyboard.onPress(KeyEvent.VK_SPACE, () -> ySpeed -= 7);
        spriteW = 80;
        spriteH = 59;
    }

    @OnUpdate
    public void update() {
        if (y < 0) {
            y = 0;
            ySpeed = 0;
        }
        if (y + height > 720) {
            y = 720 - height - 1;
            ySpeed = 0;
        }
        angle = ySpeed / 10;
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform old = g2d.getTransform();
        g2d.rotate(angle, x + spriteW / 2.0, y + spriteH / 2.0);
        g2d.drawImage(image, (int) (x - (spriteW - width) / 2), (int) (y - (spriteH - height) / 2), (int) spriteW, (int) spriteH, null);
        g2d.setTransform(old);
        g.setColor(Color.PINK);
        // hitbox
        g.drawRect((int) x, (int) y, (int) width, (int) height);
    }

    @Override
    public void collide(Entity e) {
        gameState.changeState(new MenuState());
    }
}
