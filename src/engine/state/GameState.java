package engine.state;

import engine.graphics.Panel;

import java.awt.*;

public abstract class GameState {

    protected Panel panel;

    public GameState(Panel panel) {
        this.panel = panel;
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
