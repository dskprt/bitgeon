package com.github.dskprt.bitgeon.object.block.blocks;

import com.github.dskprt.bitgeon.level.Level;
import com.github.dskprt.bitgeon.object.block.BlockObject;

public class BricksBlock extends BlockObject {

    public BricksBlock(Level parent, float x, float y, float z) {
        super(parent, "bricks", x, y, z, false, (byte) 0);
    }
}
