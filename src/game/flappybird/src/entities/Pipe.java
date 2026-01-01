package game.flappybird.src.entities;

import engine.annotations.OnRender;
import engine.annotations.OnUpdate;
import engine.entities.Collidable;
import engine.entities.Entity;
import engine.entities.Renderable;
import engine.state.EntityManager;
import engine.state.GameState;

import java.awt.*;

@OnRender(z = 1)
public class Pipe extends Entity implements Renderable, Collidable {
    private boolean pointAwarded = false;

    public Pipe(float x, float y, float width, float height, EntityManager entityManager, GameState gameState) {
        super(x, y, width, height, entityManager, gameState);
    }

    @OnUpdate
    public void update() {
        x -= 3;
        if (x + width < 0) active = false;
        if (x < 25 && !pointAwarded) {
            Score.increment();
            pointAwarded = true;
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(0, 230, 0));
        g.fillRect((int) x, (int) y, (int) width, (int) height);
    }

    @Override
    public void collide(Entity e) {
        e.setActive(false);
    }
}