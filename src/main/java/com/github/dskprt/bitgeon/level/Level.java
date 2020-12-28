package com.github.dskprt.bitgeon.level;

import com.github.dskprt.bitgeon.block.Block;

import java.awt.*;
import java.util.List;

public class Level {

    public int width;
    public int height;
    public List<Block> blocks;

    public Level(int width, int height, List<Block> blocks) {
        this.width = width;
        this.height = height;
        this.blocks = blocks;
    }

    public void render(Graphics2D g2d, int offsetX, int offsetY) {
        blocks.forEach(b -> b.render(g2d, offsetX, offsetY));
    }
}
