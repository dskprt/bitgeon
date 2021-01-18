package com.github.dskprt.bitgeon.object.block.blocks;

import com.github.dskprt.bitgeon.level.Level;
import com.github.dskprt.bitgeon.object.block.BlockObject;

public class GrassBlock extends BlockObject {

    public GrassBlock(Level parent, float x, float y, float z) {
        super(parent, "grass", x, y, z, null, false, (byte)0);
    }
}
