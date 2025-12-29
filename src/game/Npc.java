package game;

import engine.annotations.OnRender;
import engine.entities.Collidable;
import engine.entities.Entity;
import engine.entities.Renderable;

import java.awt.*;

public class Npc extends Entity implements Renderable, Collidable {

    public Npc(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    @Override
    @OnRender
    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect((int) x, (int) y, (int) width, (int) height);
    }

    @Override
    public void collide(Entity e) {
    }
}
