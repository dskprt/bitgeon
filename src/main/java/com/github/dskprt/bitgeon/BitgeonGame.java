package com.github.dskprt.bitgeon;

import com.github.dskprt.bitgeon.config.GameConfiguration;
import com.github.dskprt.bitgeon.input.Keyboard;
import com.github.dskprt.bitgeon.input.Mouse;
import com.github.dskprt.bitgeon.util.GameState;
import com.github.dskprt.bitgeon.util.Timer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.nio.file.Files;
import java.util.Random;

public class BitgeonGame {

    public final GameConfiguration config;

    private final JFrame frame;
    private final Canvas canvas;

    private Timer timer;

    private Graphics g = null;
    private Graphics2D g2d = null;

    public GameState state;

    int x = 50;
    int y = 50;

    public BitgeonGame(GameConfiguration config) {
        this.config = config;

        frame = new JFrame("bitgeon");
        frame.setIgnoreRepaint(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        canvas = new Canvas();
        canvas.setIgnoreRepaint(true);
        canvas.setSize(config.width, config.height);

        frame.add(canvas);
        frame.pack();

        canvas.requestFocus();

        canvas.addKeyListener(new Keyboard());

        Mouse m = new Mouse();

        canvas.addMouseListener(m);
        canvas.addMouseMotionListener(m);
    }

    public void start() {
        frame.setVisible(true);

        canvas.createBufferStrategy(2);
        BufferStrategy buffer = canvas.getBufferStrategy();

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();

        VolatileImage img = gc.createCompatibleVolatileImage(config.width, config.height);

        timer = new Timer();

        state = GameState.RUNNING;

        while(state != GameState.STOPPED) {
            update(timer.getDelta());
            render(img, gc, buffer);
        }
    }

    private void update(int delta) {
        timer.tick();

        Keyboard.poll();
        Mouse.poll();

        if(Keyboard.isKeyDown(KeyEvent.VK_W)) {
            y -= delta;
        }

        if(Keyboard.isKeyDown(KeyEvent.VK_S)) {
            y += delta;
        }

        if(Keyboard.isKeyDown(KeyEvent.VK_A)) {
            x -= delta;
        }

        if(Keyboard.isKeyDown(KeyEvent.VK_D)) {
            x += delta;
        }
    }

    private void render(VolatileImage img, GraphicsConfiguration gc, BufferStrategy buffer) {
        try {
            if(img.validate(gc) == VolatileImage.IMAGE_INCOMPATIBLE) {
                img = gc.createCompatibleVolatileImage(config.width, config.height);
            }

            g2d = img.createGraphics();
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, config.width, config.height);

            g2d.setFont(new Font("Consolas", Font.PLAIN, 12));
            g2d.setColor(Color.GREEN);
            g2d.drawString(String.format("FPS: %s", timer.getFps()), 5, 5 + 12);
            g2d.drawString(String.format("Mouse[x=%s,y=%s] Button0=%s, Button1=%s, Button2=%s",
                    Mouse.getPosition().x, Mouse.getPosition().y, Mouse.isButtonDown(1), Mouse.isButtonDown(2), Mouse.isButtonDown(3)), 5, 5 + (12 * 2) + 3);

            g2d.setColor(Color.BLUE);
            g2d.drawRect(x, y, 50, 50);

            g = buffer.getDrawGraphics();
            g.drawImage(img, 0, 0, null);

            if(!buffer.contentsLost()) buffer.show();

            Thread.yield();
        } finally {
            if(g != null) g.dispose();
            if(g2d != null) g2d.dispose();
        }
    }
}
