package com.github.dskprt.bitgeon.tile.block.blocks;

import com.github.dskprt.bitgeon.tile.TileMap;
import com.github.dskprt.bitgeon.tile.block.BlockTile;

import javax.vecmath.Vector2f;
import java.io.IOException;

public class InteractionTestBlock extends BlockTile {

    public InteractionTestBlock(TileMap parent, Vector2f coordinates) throws IOException {
        super(parent, "interaction_test", coordinates, true, true, (byte)0);
    }

    @Override
    public void interact() {
        System.out.println("[BLOCK] Beep! My coordinates are: " + coordinates.toString());
    }
}
