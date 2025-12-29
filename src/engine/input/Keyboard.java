package engine.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Keyboard implements KeyListener {

    private static final HashMap<Integer, List<Runnable>> onPressEvents = new HashMap<>();
    private static final HashMap<Integer, List<Runnable>> onReleaseEvents = new HashMap<>();

    public static void onPress(int keyCode, Runnable procedure) {
        onPressEvents.computeIfAbsent(keyCode, _ -> new ArrayList<>()).add(procedure);
    }
    public static void onRelease(int keyCode, Runnable procedure) {
        onReleaseEvents.computeIfAbsent(keyCode, _ -> new ArrayList<>()).add(procedure);
    }
    public static void clearContext() {
        onPressEvents.clear();
        onReleaseEvents.clear();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (onPressEvents.containsKey(e.getKeyCode())) onPressEvents.get(e.getKeyCode())
                .forEach(Runnable::run);
    }
    @Override
    public void keyReleased(KeyEvent e) {
        if (onReleaseEvents.containsKey(e.getKeyCode())) onReleaseEvents.get(e.getKeyCode())
                .forEach(Runnable::run);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
