package game.flappybird;

import engine.core.GameLoop;
import engine.graphics.Panel;
import engine.graphics.Window;
import engine.state.GameState;
import game.flappybird.src.states.MenuState;

public class Main {
    static void main() {
        Panel panel = new Panel();
        GameLoop gameloop = new GameLoop(60, 120, panel);
        Window window = new Window(panel);
        GameState gameState = new MenuState();
        gameloop.initState(gameState);
        gameloop.run();
    }
}
