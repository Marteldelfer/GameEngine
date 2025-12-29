package engine.graphics;

import engine.input.Keyboard;
import engine.input.Mouse;

import javax.swing.*;
import java.awt.event.KeyListener;


public class Window extends JFrame {
    public Window(Panel panel) {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panel);
        setLocationRelativeTo(null);
        pack();
        setResizable(false);
        setVisible(true);
        addKeyListener(new Keyboard());
        addMouseListener(new Mouse());
        addMouseMotionListener(new Mouse());
        // addWindowFocusListener();
    }
}
