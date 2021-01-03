package com.github.dskprt.bitgeon.tile.entity;

import com.github.dskprt.bitgeon.tile.Tile;
import com.github.dskprt.bitgeon.tile.TileMap;
import com.github.dskprt.bitgeon.tile.block.BlockTile;
import com.github.dskprt.bitgeon.tile.entity.inventory.Inventory;

import javax.imageio.ImageIO;
import javax.vecmath.Vector2f;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

public class EntityTile extends Tile {

    public Inventory inventory;

    public boolean canInteract;
    public byte data;

    public EntityTile(TileMap parent, String id, Vector2f coordinates, boolean canInteract, boolean canCollide, byte data) throws IOException {
        this(parent, id, coordinates, new Rectangle2D.Float(0, 0, TILE_WIDTH, TILE_HEIGHT), null, canInteract, canCollide, data);
    }

    public EntityTile(TileMap parent, String id, Vector2f coordinates, Rectangle2D.Float collisionBox, boolean canInteract, boolean canCollide, byte data) throws IOException {
        this(parent, id, coordinates, collisionBox, null, canInteract, canCollide, data);
    }

    public EntityTile(TileMap parent, String id, Vector2f coordinates, Inventory inventory, boolean canInteract, boolean canCollide, byte data) throws IOException {
        this(parent, id, coordinates, new Rectangle2D.Float(0, 0, TILE_WIDTH, TILE_HEIGHT), inventory, canInteract, canCollide, data);
    }

    public EntityTile(TileMap parent, String id, Vector2f coordinates, Rectangle2D.Float collisionBox, Inventory inventory, boolean canInteract, boolean canCollide, byte data) throws IOException {
        super(parent, id, ImageIO.read(BlockTile.class.getResourceAsStream("/textures/entities/" + id + ".png")), coordinates, collisionBox, canCollide);

        if(inventory == null) {
            this.inventory = Inventory.empty(this);
        } else {
            this.inventory = inventory;
        }

        this.canInteract = canInteract;
        this.data = data;
    }

    public void update(double delta) { }
    public void interact() { }
}
