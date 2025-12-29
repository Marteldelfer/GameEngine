package engine.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class Mouse implements MouseListener, MouseMotionListener {

    private static final HashMap<Integer, List<Function<MouseEvent, Void>>> onPressEvents = new HashMap<>();
    private static final HashMap<Integer, List<Function<MouseEvent, Void>>> onReleaseEvents = new HashMap<>();
    private static final List<Function<MouseEvent, Void>> onMovementEvents = new ArrayList<>();


    public static void addPressEvent(int buttonCode, Function<MouseEvent, Void> procedure) {
        onPressEvents.computeIfAbsent(buttonCode, _ -> new ArrayList<>()).add(procedure);
    }
    public static void addReleaseEvent(int buttonCode, Function<MouseEvent, Void> procedure) {
        onReleaseEvents.computeIfAbsent(buttonCode, _ -> new ArrayList<>()).add(procedure);
    }
    public static void addMovementEvent(int buttonCode, Function<MouseEvent, Void> procedure) {
        onMovementEvents.add(procedure);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (onPressEvents.containsKey(e.getButton())) {
            onPressEvents.get(e.getButton()).forEach(p -> p.apply(e));
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        if (onReleaseEvents.containsKey(e.getButton())) {
            onReleaseEvents.get(e.getButton()).forEach(p -> p.apply(e));
        }
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        onMovementEvents.forEach(p -> p.apply(e));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    @Override
    public void mouseExited(MouseEvent e) {
    }
    @Override
    public void mouseDragged(MouseEvent e) {
    }

}
