package com.github.dskprt.bitgeon.gui.components;

import com.github.dskprt.bitgeon.BitgeonGame;
import com.github.dskprt.bitgeon.gui.Component;
import com.github.dskprt.bitgeon.gui.Screen;
import com.github.dskprt.bitgeon.util.FontUtil;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//TODO change cursor position with arrow keys
public class TextBox extends Component {

    private String text;
    private String hint;

    public TextBox(Screen parent, String defaultText, String hint, int x, int y, int width) {
        super(parent, x, y, width, -1);

        text = defaultText;
        this.hint = hint;

        BitgeonGame.INSTANCE.canvas.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                if(!isFocused()) return;

                if(e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
                    if(text.length() > 0) text = text.substring(0, text.length() - 1);
                } else {
                    text += e.getKeyChar();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) { }

            @Override
            public void keyReleased(KeyEvent e) { }
        });
    }

    @Override
    public void render(Graphics2D g2d) {
        if(height == -1) height = (int) (FontUtil.getStringBounds(g2d, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmopqrstuvwxyz0123456789",
                        BitgeonGame.WIDTH, BitgeonGame.HEIGHT).getHeight() + 6);

        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(x, y, width, height);

        if(isFocused()) {
            g2d.setColor(Color.DARK_GRAY);
            g2d.setStroke(new BasicStroke(2f));
            g2d.drawRect(x, y, width, height);
        }

        if(text.equals("")) {
            g2d.setColor(Color.GRAY);
            g2d.drawString(hint, x + 3, y + height - 3);
        }

        String display = clipString(g2d, text + (isFocused() ? "_" : ""));

        g2d.setColor(Color.BLACK);
        g2d.drawString(display, x + 3, y + height - 3);
    }

    private String clipString(Graphics2D g2d, String s) {
        while(g2d.getFontMetrics().stringWidth(s) > width - 4) {
            s = s.substring(1);
        }

        return s;
    }

    @Override
    public void update() { }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
