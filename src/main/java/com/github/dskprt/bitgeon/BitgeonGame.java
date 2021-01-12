package com.github.dskprt.bitgeon;

import com.github.dskprt.bitgeon.config.GameConfiguration;
import com.github.dskprt.bitgeon.gui.Screen;
import com.github.dskprt.bitgeon.gui.screens.TitleScreen;
import com.github.dskprt.bitgeon.input.Keyboard;
import com.github.dskprt.bitgeon.input.Mouse;
import com.github.dskprt.bitgeon.tile.Tile;
import com.github.dskprt.bitgeon.level.Level;
import com.github.dskprt.bitgeon.util.GameState;
import com.github.dskprt.bitgeon.util.Timer;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferStrategy;
import java.awt.image.VolatileImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class BitgeonGame {

    public static BitgeonGame INSTANCE;

    public static final int WIDTH = 640;
    public static final int HEIGHT = 360;
    public static int SCALING = 0;

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

    public Level level;

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

        SCALING = config.width / WIDTH;

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

        VolatileImage img = gc.createCompatibleVolatileImage(WIDTH, HEIGHT);

        timer = new Timer();
        state = GameState.RUNNING;

        try {
            font = Font.createFont(Font.TRUETYPE_FONT, BitgeonGame.class.getResourceAsStream("/fonts/04B_03.ttf")).deriveFont(8f);
        } catch(FontFormatException | IOException e) {
            e.printStackTrace();
        }

        while(state != GameState.STOPPED) {
            update(timer.getDelta());

            try {
                if(img.validate(gc) == VolatileImage.IMAGE_INCOMPATIBLE) {
                    img = gc.createCompatibleVolatileImage(WIDTH, HEIGHT);
                }

                g2d = img.createGraphics();

                g2d.setColor(Color.BLACK);
                g2d.fillRect(0, 0, WIDTH, HEIGHT);

                g2d.setFont(font);
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
                g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

                fontMetrics = g2d.getFontMetrics();
                fontRenderContext = g2d.getFontRenderContext();

                if(screen == null && state != GameState.INGAME) setScreen(new TitleScreen());

                render(g2d);

                g = buffer.getDrawGraphics();

                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
                g.drawImage(img, 0, 0, config.width, config.height, null);

                if(!buffer.contentsLost()) buffer.show();

                Thread.yield();
            } finally {
                if(g != null) g.dispose();
                if(g2d != null) g2d.dispose();
            }
        }

        shutdown();
        System.exit(0);
    }

    private void update(double delta) {
        timer.tick();

        Keyboard.poll();
        Mouse.poll();

        if(level != null) {
            if(screen == null) {
                level.update(delta);
            } else if(!screen.doesScreenPauseGame()) {
                level.update(delta);
            }
        }

        if(screen != null) screen.update(delta);
    }

    private void render(Graphics2D g2d) {
        if(level != null) level.render(g2d, BitgeonGame.WIDTH / 2f - (level.player.coordinates.x * Tile.TILE_WIDTH) - Tile.TILE_WIDTH / 2f,
                BitgeonGame.HEIGHT / 2f - (level.player.coordinates.y * Tile.TILE_HEIGHT) - Tile.TILE_HEIGHT / 2f);

        if(screen != null) screen.render(g2d);

        g2d.setColor(Color.GREEN);
        g2d.drawString(String.format("FPS: %s", timer.getFps()), 5, 5 + 8);
        g2d.drawString(String.format("Mouse[x=%s,y=%s] Button0=%s, Button1=%s, Button2=%s",
                Mouse.getScaledPosition().x, Mouse.getScaledPosition().y, Mouse.isButtonDown(1), Mouse.isButtonDown(2), Mouse.isButtonDown(3)), 5, 5 + (8 * 2) + 2);
    }

    private void shutdown() {
        System.out.println("Shutting down.");
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public GameState getState() {
        return state;
    }

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        if(screen != null) screen.init();
        this.screen = screen;
    }

    public void loadLevel(String name) {
        new Thread(() -> {
            try {
                BitgeonGame.INSTANCE.level = Level.load(new File(TitleScreen.class.getResource("/levels/" + name).toURI()));
            } catch(URISyntaxException e) {
                e.printStackTrace();
                BitgeonGame.INSTANCE.setState(GameState.STOPPED);
            }

            BitgeonGame.INSTANCE.setScreen(null);
            BitgeonGame.INSTANCE.setState(GameState.INGAME);
        }, "Level loading thread").start();
    }
}
