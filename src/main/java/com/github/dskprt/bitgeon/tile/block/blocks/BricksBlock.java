package com.github.dskprt.bitgeon.tile.block.blocks;

import com.github.dskprt.bitgeon.level.Level;
import com.github.dskprt.bitgeon.tile.block.Block;

import javax.vecmath.Vector2f;

public class BricksBlock extends Block {

    public BricksBlock(Level parent, Vector2f coordinates) {
        super(parent, "bricks", coordinates, false, true, (byte) 0);
    }
}
