package game.flappybird.src.entities;

import engine.annotations.OnRender;
import engine.entities.Entity;
import engine.entities.Renderable;
import engine.helper.Load;
import engine.input.Keyboard;
import engine.state.EntityManager;
import engine.state.GameState;
import game.flappybird.src.states.PlayingState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

@OnRender
public class Menu extends Entity implements Renderable {

    private final BufferedImage bird, davaJonas;
    private Player player = Player.BIRD;

    private enum Player {
        BIRD, DAVAJONAS;
    }

    public Menu(float x, float y, float width, float height, EntityManager entityManager, GameState gameState) {
        super(x, y, width, height, entityManager, gameState);
        bird = Load.loadImage("/flappybird/bird.png");
        davaJonas = Load.loadImage("/flappybird/davaJonas.jpeg");;

        Keyboard.onPress(KeyEvent.VK_RIGHT, this::toRight);
        Keyboard.onPress(KeyEvent.VK_LEFT, this::toLeft);
        Keyboard.onPress(KeyEvent.VK_ENTER, () -> gameState.changeState(new PlayingState()));
    }

    private void toRight() {
        player = switch (player) {
            case BIRD -> Player.DAVAJONAS;
            case DAVAJONAS -> Player.BIRD;
        };
    }
    private void toLeft() {
        player = switch (player) {
            case BIRD -> Player.DAVAJONAS;
            case DAVAJONAS -> Player.BIRD;
        };
    }
    public BufferedImage getImage() {
        return switch (player) {
            case BIRD -> bird;
            case DAVAJONAS -> davaJonas;
        };
    }


    @Override
    public void render(Graphics g) {
        float centerX = x + width / 2;
        float centerY = y + height / 2;
        float imgW = 80f, imgH = 59f;

        g.drawImage(getImage(), (int) (centerX - imgW / 2), (int) (centerY - imgH / 2), (int) imgW, (int) imgH, null);
    }
}
