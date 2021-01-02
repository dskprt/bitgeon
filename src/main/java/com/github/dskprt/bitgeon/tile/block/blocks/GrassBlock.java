package com.github.dskprt.bitgeon.tile.block.blocks;

import com.github.dskprt.bitgeon.tile.TileMap;
import com.github.dskprt.bitgeon.tile.block.BlockTile;

import javax.vecmath.Vector2f;
import java.io.IOException;

public class GrassBlock extends BlockTile {

    public GrassBlock(TileMap parent, Vector2f coordinates) throws IOException {
        super(parent, "grass", coordinates, false, true, (byte)0);
    }
}
