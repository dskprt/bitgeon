package com.github.dskprt.bitgeon.object.block.blocks;

import com.github.dskprt.bitgeon.level.Level;
import com.github.dskprt.bitgeon.object.block.BlockObject;

public class InteractionTestBlock extends BlockObject {

    public InteractionTestBlock(Level parent, float x, float y, float z) {
        super(parent, "interaction_test", x, y, z, true, (byte)0);
    }

    @Override
    public void interact() {
        System.out.println("[BLOCK] Beep! My coordinates are: [x=" + x + ",y=" + y + ",z=" + z + "]");
    }
}
