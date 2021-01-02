package com.github.dskprt.bitgeon.tile.block;

import com.github.dskprt.bitgeon.tile.Tile;
import com.github.dskprt.bitgeon.tile.TileMap;

import javax.imageio.ImageIO;
import javax.vecmath.Vector2f;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

public class BlockTile extends Tile {

    public boolean canInteract;
    public boolean canWalkThrough;
    public byte data;

    public BlockTile(TileMap parent, String id, Vector2f coordinates, boolean canInteract, boolean canWalkThrough, byte data) throws IOException {
        this(parent, id, coordinates, new Rectangle2D.Float(0, 0, TILE_WIDTH, TILE_HEIGHT), canInteract, canWalkThrough, data);
    }

    public BlockTile(TileMap parent, String id, Vector2f coordinates, Rectangle2D.Float collisionBox, boolean canInteract, boolean canWalkThrough, byte data) throws IOException {
        super(parent, id, ImageIO.read(BlockTile.class.getResourceAsStream("/textures/blocks/" + id + ".png")), coordinates, collisionBox);

        this.canInteract = canInteract;
        this.canWalkThrough = canWalkThrough;
        this.data = data;
    }

    public void update(double delta) { }
    public void interact() { }
}
