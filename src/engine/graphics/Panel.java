package engine.graphics;

import engine.input.Keyboard;
import engine.input.Mouse;
import engine.state.GameState;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {
    private GameState gameState;

    public Panel() {
        Mouse mouse = new Mouse();
        setPreferredSize(new Dimension(1280, 720));
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        addKeyListener(new Keyboard());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        gameState.render(g);
        Toolkit.getDefaultToolkit().sync();
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
