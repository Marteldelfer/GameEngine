package engine.state;

import engine.core.GameLoop;
import engine.graphics.Panel;

import java.awt.*;

public abstract class GameState {

    protected Panel panel;
    protected EntityManager entityManager;
    protected GameLoop gameLoop;

    public GameState() {
    }

    public abstract void onEnter();
    public abstract void onExit();
    public abstract void update();
    public abstract void render(Graphics g);

    public void changeState(GameState gameState) {
        gameLoop.initState(gameState);
    }

    public Panel getPanel() {
        return panel;
    }
    public void setPanel(Panel panel) {
        this.panel = panel;
    }

    public void setGameLoop(GameLoop gameLoop) {
        this.gameLoop = gameLoop;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
