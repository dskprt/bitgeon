package com.github.dskprt.bitgeon.tile.entity.entities;

import com.github.dskprt.bitgeon.level.Level;
import com.github.dskprt.bitgeon.tile.entity.Entity;

import javax.vecmath.Vector2f;

public class InteractionTestEntity extends Entity {

    public InteractionTestEntity(Level parent, Vector2f coordinates) {
        super(parent, "interaction_test", coordinates, true, true, (byte)0);
    }

    @Override
    public void interact() {
        System.out.println("[ENTITY] Boop! My coordinates are: " + coordinates.toString());
    }
}
