package game.flappybird.src.entities;

import engine.annotations.OnRender;
import engine.entities.Entity;
import engine.entities.Renderable;
import engine.helper.Load;
import engine.state.EntityManager;
import engine.state.GameState;

import java.awt.*;
import java.awt.image.BufferedImage;

@OnRender(z = -1)
public class Background extends Entity implements Renderable {

    private final BufferedImage image;

    public Background(float x, float y, float width, float height, EntityManager entityManager, GameState gameState) {
        super(x, y, width, height, entityManager, gameState);
        image = Load.loadImage("/flappybird/background.png");
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(image, (int) x, (int) y, (int) width, (int) height, null);
    }
}
