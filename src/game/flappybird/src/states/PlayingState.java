package game.flappybird.src.states;

import engine.state.DefaultGameState;
import game.flappybird.src.entities.Background;
import game.flappybird.src.entities.Player;
import game.flappybird.src.entities.Score;
import game.flappybird.src.entities.Spawner;

public class PlayingState extends DefaultGameState {
    public PlayingState() {
        super();
    }

    @Override
    public void onEnter() {
        super.onEnter();
        entityManager.addEntity(new Score(1200, 30, 60, 60, entityManager, this));
        entityManager.addEntity(new Background(0, 0, 1280, 720, entityManager, this));
        entityManager.addEntity(new Spawner(0, 0, 0, 0, entityManager, this));
        entityManager.sync();
    }
}
