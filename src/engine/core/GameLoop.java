package engine.core;

import engine.graphics.Panel;
import engine.state.EntityManager;
import engine.state.GameState;

public class GameLoop implements Runnable {

    private final int FPS, UPS;
    private boolean running = true;
    private final Panel panel;
    private GameState gameState;
    private boolean changeStates;
    private GameState nextGameState;

    public GameLoop(int FPS, int UPS, Panel panel) {
        this.FPS = FPS;
        this.UPS = UPS;
        this.panel = panel;
    }
    public GameLoop(Panel panel) {
        this.FPS = 60;
        this.UPS = 60;
        this.panel = panel;
    }

    @Override
    public void run() {
        double timePerFrame = 1_000_000_000f / FPS;
        double timePerUpdate = 1_000_000_000f / UPS;

        int frames = 0, updates = 0;
        long lastCheck = System.currentTimeMillis();

        long prevTime = System.nanoTime();
        double deltaF = 0, deltaU = 0;

        while (running) {

            long curTime = System.nanoTime();
            deltaU += (curTime - prevTime) / timePerUpdate;
            deltaF += (curTime - prevTime) / timePerFrame;
            prevTime = curTime;

            while (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }
            while (deltaF >= 1) {
                render();
                frames++;
                deltaF--;
            }
            // debug
            if ((System.currentTimeMillis() - lastCheck) >= 1_000) {
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                updates = 0;
                frames = 0;
                lastCheck = System.currentTimeMillis();
            }
        }
    }

    private void update() {
        if (changeStates) updateState();
        gameState.update();
    }

    private void updateState() {
        if (this.gameState != null) {
            this.gameState.onExit();
            nextGameState.setEntityManager(this.gameState.getEntityManager());
        } else {
            nextGameState.setEntityManager(new EntityManager());
        }
        nextGameState.setPanel(panel);
        nextGameState.setGameLoop(this);
        panel.setGameState(nextGameState);
        this.gameState = nextGameState;
        gameState.onEnter();
        changeStates = false;
    }

    private void render() {
        panel.repaint();
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void initState(GameState gameState) {
        this.nextGameState = gameState;
        changeStates = true;
    }
}
