package com.github.dskprt.bitgeon.gui.components;

import com.github.dskprt.bitgeon.gui.Component;
import com.github.dskprt.bitgeon.gui.Screen;
import com.github.dskprt.bitgeon.input.Mouse;

import java.awt.*;
import java.util.function.Consumer;

public class Button extends Component {

    private String text;
    private Consumer<Button> consumer;
    private Color background;
    private Color foreground;

    public Button(Screen parent, String text, int x, int y, int width, int height, Consumer<Button> consumer,
                  Color background, Color foreground) {
        super(parent, x, y, width, height);

        this.text = text;
        this.consumer = consumer;
        this.background = background;
        this.foreground = foreground;
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.setColor(isHovered() ? background.darker() : background);
        g2d.fillRect(x, y, width, height);
        g2d.setColor(foreground);
        g2d.drawString(text, x + (width / 2) - (g2d.getFontMetrics().stringWidth(text) / 2),
                y + (height / 2) - (g2d.getFontMetrics().getHeight() / 2));
    }

    @Override
    public void update() {
        if(isHovered() && Mouse.isButtonDown(1) && Mouse.wasButtonClicked(1)) {
            consumer.accept(this);
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Color getBackground() {
        return background;
    }

    public void setBackground(Color background) {
        this.background = background;
    }

    public Color getForeground() {
        return foreground;
    }

    public void setForeground(Color foreground) {
        this.foreground = foreground;
    }
}
