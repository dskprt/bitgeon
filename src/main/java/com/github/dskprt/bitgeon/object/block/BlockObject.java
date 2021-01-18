package com.github.dskprt.bitgeon.object.block;

import com.github.dskprt.bitgeon.level.Level;
import com.github.dskprt.bitgeon.object.GameObject;
import com.github.dskprt.bitgeon.util.CachedImageIO;

import java.awt.geom.Rectangle2D;

public class BlockObject extends GameObject {

    public static final int WIDTH = 16;
    public static final int HEIGHT = 16;

    public BlockObject(Level parent, String id, float x, float y, float z, boolean interactable, byte data) {
        this(parent, id, x, y, z, new Rectangle2D.Float[] { new Rectangle2D.Float(0, 0, WIDTH, HEIGHT) }, interactable, data);
    }

    public BlockObject(Level parent, String id, float x, float y, float z, Rectangle2D.Float[] collisions, boolean interactable, byte data) {
        super(parent, id, x, y, z, WIDTH, HEIGHT, collisions,
                CachedImageIO.read(BlockObject.class.getResource("/textures/blocks/" + id + ".png")), interactable, data);
    }
}
