package com.github.dskprt.bitgeon.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

    private static final int KEY_COUNT = 256;

    private static boolean[] currentKeys = null;
    private static KeyState[] keys = null;

    public Keyboard() {
        currentKeys = new boolean[KEY_COUNT];
        keys = new KeyState[KEY_COUNT];

        for(int i = 0; i < KEY_COUNT; i++) {
            keys[i] = KeyState.RELEASED;
        }
    }

    public synchronized static void poll() {
        for(int i = 0; i < KEY_COUNT; i++) {
            if(currentKeys[i]) {
                if(keys[i] == KeyState.RELEASED) {
                    keys[i] = KeyState.CLICKED;
                } else {
                    keys[i] = KeyState.PRESSED;
                }
            } else {
                keys[i] = KeyState.RELEASED;
            }
        }
    }

    public static boolean isKeyDown(int keyCode) {
        return keys[keyCode] == KeyState.CLICKED ||
                keys[keyCode] == KeyState.PRESSED;
    }

    public static boolean wasKeyClicked(int keyCode) {
        return keys[keyCode] == KeyState.CLICKED;
    }

    @Override
    public synchronized void keyPressed( KeyEvent e ) {
        int keyCode = e.getKeyCode();

        if(keyCode >= 0 && keyCode < KEY_COUNT) {
            currentKeys[keyCode] = true;
        }
    }

    @Override
    public synchronized void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if(keyCode >= 0 && keyCode < KEY_COUNT) {
            currentKeys[keyCode] = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    public enum KeyState {
        RELEASED,
        PRESSED,
        CLICKED
    }
}
