package com.github.dskprt.bitgeon.tile.entity.entities;

import com.github.dskprt.bitgeon.tile.TileMap;
import com.github.dskprt.bitgeon.tile.entity.EntityTile;

import javax.vecmath.Vector2f;
import java.io.IOException;

public class InteractionTestEntity extends EntityTile {

    public InteractionTestEntity(TileMap parent, Vector2f coordinates) throws IOException {
        super(parent, "interaction_test", coordinates, true, true, (byte)0);
    }

    @Override
    public void interact() {
        System.out.println("[ENTITY] Boop! My coordinates are: " + coordinates.toString());
    }
}
