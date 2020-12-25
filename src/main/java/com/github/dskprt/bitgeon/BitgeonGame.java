package com.github.dskprt.bitgeon;

import com.github.dskprt.bitgeon.config.GameConfiguration;
import com.github.dskprt.bitgeon.gui.Screen;
import com.github.dskprt.bitgeon.gui.screens.TitleScreen;
import com.github.dskprt.bitgeon.input.Keyboard;
import com.github.dskprt.bitgeon.input.Mouse;
import com.github.dskprt.bitgeon.util.GameState;
import com.github.dskprt.bitgeon.util.Timer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.Random;

public class BitgeonGame {

    public static BitgeonGame INSTANCE;

    public final GameConfiguration config;

    public final JFrame frame;
    public final Canvas canvas;

    private Timer timer;

    private Graphics g = null;
    private Graphics2D g2d = null;
    public Font font;

    private GameState state;
    private Screen screen;

    public FontRenderContext fontRenderContext;
    public FontMetrics fontMetrics;

    public BitgeonGame(GameConfiguration config) {
        INSTANCE = this;

        this.config = config;

        frame = new JFrame("bitgeon");
        frame.setIgnoreRepaint(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

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

        try {
            font = Font.createFont(Font.TRUETYPE_FONT, BitgeonGame.class.getResourceAsStream("/fonts/DeterminationMonoWebRegular.ttf")).deriveFont(16f);
        } catch(FontFormatException | IOException e) {
            e.printStackTrace();
        }

        while(state != GameState.STOPPED) {
            update(timer.getDelta());

            try {
                if(img.validate(gc) == VolatileImage.IMAGE_INCOMPATIBLE) {
                    img = gc.createCompatibleVolatileImage(config.width, config.height);
                }

                g2d = img.createGraphics();

                g2d.setColor(Color.BLACK);
                g2d.fillRect(0, 0, config.width, config.height);

                g2d.setFont(font);
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
                g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);

                fontMetrics = g2d.getFontMetrics();
                fontRenderContext = g2d.getFontRenderContext();

                if(screen == null) setScreen(new TitleScreen());

                render(g2d);

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

    private void update(int delta) {
        timer.tick();

        if(screen != null) screen.update(delta);

        Keyboard.poll();
        Mouse.poll();
    }

    private void render(Graphics2D g2d) {
        g2d.setColor(Color.GREEN);
        g2d.drawString(String.format("FPS: %s", timer.getFps()), 5, 5 + 12);
        g2d.drawString(String.format("Mouse[x=%s,y=%s] Button0=%s, Button1=%s, Button2=%s",
                Mouse.getPosition().x, Mouse.getPosition().y, Mouse.isButtonDown(1), Mouse.isButtonDown(2), Mouse.isButtonDown(3)), 5, 5 + (12 * 2) + 3);

        if(screen != null) screen.render(g2d);
    }

    public GameState getState() {
        return state;
    }

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        screen.init();
        this.screen = screen;
    }
}
