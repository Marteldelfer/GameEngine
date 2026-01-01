package game.flappybird.src.states;

import engine.entities.Entity;
import engine.state.DefaultGameState;
import game.flappybird.src.entities.Background;
import game.flappybird.src.entities.Menu;
import game.flappybird.src.entities.Player;

import java.awt.image.BufferedImage;

public class MenuState extends DefaultGameState {
    @Override
    public void onEnter() {
        super.onEnter();
        entityManager.addEntity(new Background(0,0,1280,720, entityManager, this));
        entityManager.addEntity(new Menu(0, 0, 1280, 720, entityManager, this));
        entityManager.sync();
    }

    @Override
    public void onExit() {
        Entity menu = entityManager.getEntities().stream()
                .filter(e -> e instanceof Menu).findFirst().orElseThrow();
        BufferedImage img = ((Menu) menu).getImage();
        super.onExit();
        entityManager.addEntity(new Player(75, 75, 40, 29, entityManager, this, img));
    }
}
