import engine.core.GameLoop;
import engine.graphics.Panel;
import engine.graphics.Window;
import engine.state.DefaultGameState;
import engine.state.EntityManager;
import engine.state.GameState;

void main() {
    GameLoop gameloop = new GameLoop(60, 100);
    Panel panel = new Panel();
    Window window = new Window(panel);
    GameState gameState = new DefaultGameState(new EntityManager(), panel);
    gameloop.setGameState(gameState);
    gameloop.run();
}
