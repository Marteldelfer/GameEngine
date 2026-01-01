package game.flappybird.src.entities;

import engine.annotations.OnUpdate;
import engine.entities.Entity;
import engine.state.EntityManager;
import engine.state.GameState;

public class Spawner extends Entity {

    private long timeToSpawn = System.nanoTime() + randomTime();

    public Spawner(float x, float y, float width, float height, EntityManager entityManager, GameState gameState) {
        super(x, y, width, height, entityManager, gameState);
    }
    @OnUpdate
    public void update() {
        if (System.nanoTime() >= timeToSpawn) {
            spawn();
            timeToSpawn = System.nanoTime() + randomTime();
        }
    }

    private void spawn() {
        float center = (float) (140 + Math.random() * 620);
        entityManager.addEntity(new Pipe(1300, 0, 100, center - 130, entityManager, gameState));
        entityManager.addEntity((new Pipe(1300, center + 130, 100, 1000, entityManager, gameState)));
    }

    private long randomTime() {
        return (long) ((Math.random() * 3 + 3) * 1_000_000_000);
    }
}
