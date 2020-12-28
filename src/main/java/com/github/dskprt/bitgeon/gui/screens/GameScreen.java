package com.github.dskprt.bitgeon.gui.screens;

import com.github.dskprt.bitgeon.block.Block;
import com.github.dskprt.bitgeon.block.blocks.BrickBlock;
import com.github.dskprt.bitgeon.gui.Screen;
import com.github.dskprt.bitgeon.input.Keyboard;
import com.github.dskprt.bitgeon.level.Level;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class GameScreen extends Screen {

    public Level level;
    public int x;
    public int y;

    @Override
    public void init() {
        List<Block> blocks = new ArrayList<>();
        blocks.add(new BrickBlock(0, 0));
        blocks.add(new BrickBlock(1, 0));
        blocks.add(new BrickBlock(2, 0));
        blocks.add(new BrickBlock(0, 1));
        blocks.add(new BrickBlock(1, 1));
        blocks.add(new BrickBlock(2, 1));
        blocks.add(new BrickBlock(0, 2));
        blocks.add(new BrickBlock(1, 2));
        blocks.add(new BrickBlock(2, 2));

        level = new Level(3, 3, blocks);

        super.init();
    }

    @Override
    public void render(Graphics2D g2d) {
        level.render(g2d, x, y);

        super.render(g2d);
    }

    @Override
    public void update(int delta) {
        if(Keyboard.isKeyDown(KeyEvent.VK_W)) {
            y--;
        }

        if(Keyboard.isKeyDown(KeyEvent.VK_S)) {
            y++;
        }

        if(Keyboard.isKeyDown(KeyEvent.VK_A)) {
            x--;
        }

        if(Keyboard.isKeyDown(KeyEvent.VK_D)) {
            x++;
        }

        super.update(delta);
    }
}
