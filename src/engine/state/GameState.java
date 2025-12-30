package engine.state;

import engine.graphics.Panel;

import java.awt.*;

public abstract class GameState {

    protected Panel panel;
    protected EntityManager entityManager;

    public GameState(Panel panel, EntityManager entityManager) {
        this.panel = panel;
        this.entityManager = entityManager;
        panel.setGameState(this);
        onEnter();
    }

    public abstract void onEnter();
    public abstract void onExit();
    public abstract void update();
    public abstract void render(Graphics g);

    public Panel getPanel() {
        return panel;
    }
}
