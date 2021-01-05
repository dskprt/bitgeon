package com.github.dskprt.bitgeon.tile.block.blocks;

import com.github.dskprt.bitgeon.BitgeonGame;
import com.github.dskprt.bitgeon.tile.TileMap;
import com.github.dskprt.bitgeon.tile.TileMaps;
import com.github.dskprt.bitgeon.tile.block.BlockTile;
import com.github.dskprt.bitgeon.tile.entity.entities.PlayerEntity;
import com.github.dskprt.bitgeon.util.GameState;

import javax.vecmath.Vector2f;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class DoorBlock extends BlockTile {

    private static Vector2f prevPos;

    public DoorBlock(TileMap parent, Vector2f coordinates, byte data) throws IOException {
        super(parent, "door", coordinates, true, true, data);
    }

    @Override
    public void interact() {
        TileMap level = null;

        if(data == 0) {
            try {
                level = TileMaps.loadMap(new File(DoorBlock.class.getResource("/levels/"
                        + BitgeonGame.INSTANCE.level.name.substring(0, BitgeonGame.INSTANCE.level.name.lastIndexOf('_')) + ".jmap").toURI()));
            } catch(URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            try {
                level = TileMaps.loadMap(new File(DoorBlock.class.getResource("/levels/"
                        + BitgeonGame.INSTANCE.level.name + "_" + data + ".jmap").toURI()));
            } catch(URISyntaxException e) {
                e.printStackTrace();
            }
        }

        if(level == null) {
            //TODO display an error screen?
            BitgeonGame.INSTANCE.setState(GameState.STOPPED);
            return;
        }

        if(prevPos != null) {
            level.player.coordinates = prevPos;
        }

        prevPos = BitgeonGame.INSTANCE.level.player.coordinates;
        BitgeonGame.INSTANCE.level = level;
    }
}
