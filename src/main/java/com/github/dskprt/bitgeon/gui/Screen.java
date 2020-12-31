package com.github.dskprt.bitgeon.gui;

import com.github.dskprt.bitgeon.input.Mouse;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Screen {

    protected final List<Component> components = new ArrayList<>();
    protected Component selectedItem;

    public void init() {
        if(components.size() > 0) selectedItem = components.get(0);
    }

    public void render(Graphics2D g2d) {
        components.forEach(c -> c.render(g2d));
    }

    public void update(double delta) {
        for(Component c : components) {
            if(c.isHovered() && Mouse.wasButtonClicked(1)) {
                selectedItem = c;
            }

            c.update();
        }
    }

    public Component getSelectedItem() {
        return selectedItem;
    }
}
