package com.github.dskprt.bitgeon.tile.block.blocks;

import com.github.dskprt.bitgeon.level.Level;
import com.github.dskprt.bitgeon.tile.block.Block;

import javax.vecmath.Vector2f;

public class InteractionTestBlock extends Block {

    public InteractionTestBlock(Level parent, Vector2f coordinates) {
        super(parent, "interaction_test", coordinates, true, true, (byte)0);
    }

    @Override
    public void interact() {
        System.out.println("[BLOCK] Beep! My coordinates are: " + coordinates.toString());
    }
}
