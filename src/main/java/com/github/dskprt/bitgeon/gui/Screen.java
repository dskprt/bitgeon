package com.github.dskprt.bitgeon.gui;

import com.github.dskprt.bitgeon.BitgeonGame;
import com.github.dskprt.bitgeon.input.Mouse;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Screen {

    private static final Color INGAME_BACKGROUND = new Color(0, 0, 0, 128);

    protected final List<Component> components = new ArrayList<>();
    protected Component selectedItem;

    public void init() {
        if(components.size() > 0) selectedItem = components.get(0);
    }

    public void render(Graphics2D g2d) {
        if(BitgeonGame.INSTANCE.level != null) {
            g2d.setColor(INGAME_BACKGROUND);
            g2d.fillRect(0, 0, BitgeonGame.WIDTH, BitgeonGame.HEIGHT);
        }

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

    public boolean doesScreenPauseGame() {
        return true;
    }
}
