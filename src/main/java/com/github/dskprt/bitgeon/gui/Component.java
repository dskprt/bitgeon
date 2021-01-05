package com.github.dskprt.bitgeon.gui;

import com.github.dskprt.bitgeon.input.Mouse;

import java.awt.*;

public abstract class Component {

    protected Screen parent;
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    public Component(Screen parent, int x, int y, int width, int height) {
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract void render(Graphics2D g2d);
    public abstract void update();

    public boolean isFocused() {
        return parent.getSelectedItem() == this;
    }

    public boolean isHovered() {
        return (Mouse.getPosition().x >= x && Mouse.getPosition().x <= (x + width))
                && (Mouse.getPosition().y >= y && Mouse.getPosition().y <= (y + height));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
