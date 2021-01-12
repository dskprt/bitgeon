package com.github.dskprt.bitgeon.tile.block;

import com.github.dskprt.bitgeon.tile.Tile;
import com.github.dskprt.bitgeon.level.Level;
import com.github.dskprt.bitgeon.util.CachedImageIO;

import javax.vecmath.Vector2f;
import java.awt.geom.Rectangle2D;

public class Block extends Tile {

    public boolean canInteract;
    public byte data;

    public Block(Level parent, String id, Vector2f coordinates, boolean canInteract, boolean canCollide, byte data) {
        this(parent, id, coordinates, new Rectangle2D.Float(0, 0, TILE_WIDTH, TILE_HEIGHT), canInteract, canCollide, data);
    }

    public Block(Level parent, String id, Vector2f coordinates, Rectangle2D.Float collisionBox, boolean canInteract, boolean canCollide, byte data) {
        super(parent, id, CachedImageIO.read(Block.class.getResource("/textures/blocks/" + id + ".png")), coordinates, collisionBox, canCollide);

        this.canInteract = canInteract;
        this.data = data;
    }

    public void update(double delta) { }
    public void interact() { }
}
