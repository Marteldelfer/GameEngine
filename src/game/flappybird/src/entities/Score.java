package game.flappybird.src.entities;

import engine.annotations.OnRender;
import engine.entities.Entity;
import engine.entities.Renderable;
import engine.state.EntityManager;
import engine.state.GameState;

import java.awt.*;

@OnRender(z = 1)
public class Score extends Entity implements Renderable {
    private static int value = 0;
    private static boolean incrementer = false;

    public Score(float x, float y, float width, float height, EntityManager entityManager, GameState gameState) {
        super(x, y, width, height, entityManager, gameState);
        value = 0;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.YELLOW);
        g.drawString(Integer.toString(value), (int) x, (int) y);
    }

    public static void increment() {
        if (incrementer) value++;
        incrementer = !incrementer;
    }
}
