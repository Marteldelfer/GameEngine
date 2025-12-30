package game;

import engine.annotations.OnRender;
import engine.entities.Entity;
import engine.entities.Renderable;
import engine.state.EntityManager;

import java.awt.*;

@OnRender
public class Background extends Entity implements Renderable {
    public Background(float x, float y, float width, float height, EntityManager entityManager) {
        super(x, y, width, height, entityManager);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect((int) x, (int) y, (int) width, (int) height);
    }
}
