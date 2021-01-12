package com.github.dskprt.bitgeon.tile.block.blocks;

import com.github.dskprt.bitgeon.level.Level;
import com.github.dskprt.bitgeon.tile.block.Block;

import javax.vecmath.Vector2f;

public class GrassBlock extends Block {

    public GrassBlock(Level parent, Vector2f coordinates) {
        super(parent, "grass", coordinates, false, false, (byte)0);
    }
}
