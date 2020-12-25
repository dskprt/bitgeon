package com.github.dskprt.bitgeon.gui.components;

import com.github.dskprt.bitgeon.gui.Component;
import com.github.dskprt.bitgeon.gui.Screen;

import java.awt.*;

public class Label extends Component {

    private String text;
    private Color color;
    private Font font;

    public Label(Screen parent, String text, int x, int y, Color color) {
        super(parent, x, y, 0, 0);

        this.text = text;
        this.color = color;
    }

    public Label(Screen parent, String text, int x, int y, Color color, Font font) {
        super(parent, x, y, 0, 0);

        this.text = text;
        this.color = color;
        this.font = font;
    }

    @Override
    public void render(Graphics2D g2d) {
        if(font != null) {
            Font old = g2d.getFont();

            g2d.setFont(font);
            g2d.setColor(color);
            g2d.drawString(text, x, y);

            g2d.setFont(old);
        } else {
            g2d.setColor(color);
            g2d.drawString(text, x, y);
        }
    }

    @Override
    public void update() { }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
