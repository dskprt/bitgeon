package com.github.dskprt.bitgeon.object.block.blocks;

import com.github.dskprt.bitgeon.level.Level;
import com.github.dskprt.bitgeon.object.block.BlockObject;

public class AirBlock extends BlockObject {

    public AirBlock(Level parent, float x, float y, float z) {
        super(parent, "air", x, y, z, null, false, (byte) 0);
    }
}
