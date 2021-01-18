package com.github.dskprt.bitgeon.object.entity.entities;

import com.github.dskprt.bitgeon.level.Level;
import com.github.dskprt.bitgeon.object.entity.EntityObject;

import java.awt.geom.Rectangle2D;

public class InteractionTestEntity extends EntityObject {

    public InteractionTestEntity(Level parent, float x, float y, float z) {
        super(parent, "interaction_test", x, y, z, 16, 16,
                new Rectangle2D.Float[] { new Rectangle2D.Float(0, 0, 16, 16) }, true, (byte) 0);
    }

    @Override
    public void interact() {
        System.out.println("[ENTITY] Boop! My coordinates are: [x=" + x + ",y=" + y + ",z=" + z + "]");
    }
}
