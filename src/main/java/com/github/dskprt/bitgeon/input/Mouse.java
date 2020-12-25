package com.github.dskprt.bitgeon.input;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseListener, MouseMotionListener {

    private static final int BUTTON_COUNT = 3;

    private static final boolean[] state;
    private static final MouseButtonState[] buttons;

    private static Point position;

    static {
        position = new Point(0, 0);

        state = new boolean[BUTTON_COUNT];
        buttons = new MouseButtonState[BUTTON_COUNT];

        for(int i = 0; i < BUTTON_COUNT; ++i) {
            buttons[i] = MouseButtonState.RELEASED;
        }
    }

    public synchronized static void poll() {
        for(int i = 0; i < BUTTON_COUNT; i++) {
            if(state[i]) {
                if(buttons[i] == MouseButtonState.RELEASED) {
                    buttons[i] = MouseButtonState.CLICKED;
                } else {
                    buttons[i] = MouseButtonState.PRESSED;
                }
            } else {
                buttons[i] = MouseButtonState.RELEASED;
            }
        }
    }

    public static Point getPosition() {
        return position;
    }

    public static boolean isButtonDown(int button) {
        return buttons[button - 1] == MouseButtonState.CLICKED ||
                buttons[button - 1] == MouseButtonState.PRESSED;
    }

    public static boolean wasButtonClicked(int button) {
        return buttons[button - 1] == MouseButtonState.CLICKED;
    }

    @Override
    public synchronized void mousePressed(MouseEvent e) {
        state[e.getButton() - 1] = true;
    }

    @Override
    public synchronized void mouseReleased(MouseEvent e) {
        state[e.getButton() - 1] = false;
    }

    @Override
    public synchronized void mouseEntered(MouseEvent e) {
        mouseMoved(e);
    }

    @Override
    public synchronized void mouseExited(MouseEvent e) {
        mouseMoved(e);
    }

    @Override
    public synchronized void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }

    @Override
    public synchronized void mouseMoved(MouseEvent e) {
        position = e.getPoint();
    }

    @Override
    public void mouseClicked(MouseEvent e) { }

    public enum MouseButtonState {
        RELEASED,
        PRESSED,
        CLICKED
    }
}
