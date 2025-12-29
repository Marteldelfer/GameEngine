package engine.core;

import engine.state.GameState;

public class GameLoop implements Runnable {

    private final int FPS, UPS;
    private boolean running = true;
    private GameState gameState;

    public GameLoop(int FPS, int UPS) {
        this.FPS = FPS;
        this.UPS = UPS;
    }
    public GameLoop() {
        this.FPS = 60;
        this.UPS = 60;
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
        gameState.update();
    }
    private void render() {
        gameState.getPanel().repaint();
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
