package com.github.dskprt.bitgeon.tile.entity;

import com.github.dskprt.bitgeon.tile.Tile;
import com.github.dskprt.bitgeon.tile.TileMap;
import com.github.dskprt.bitgeon.tile.block.BlockTile;
import com.github.dskprt.bitgeon.tile.entity.inventory.Inventory;
import com.github.dskprt.bitgeon.util.FontUtil;

import javax.imageio.ImageIO;
import javax.vecmath.Vector2f;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

public class EntityTile extends Tile {

    public Inventory inventory;

    public float health;
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
        this(parent, id, coordinates, collisionBox, inventory, 10f, canInteract, canCollide, data);
    }

    public EntityTile(TileMap parent, String id, Vector2f coordinates, Rectangle2D.Float collisionBox, Inventory inventory, float health, boolean canInteract, boolean canCollide, byte data) throws IOException {
        super(parent, id, ImageIO.read(BlockTile.class.getResourceAsStream("/textures/entities/" + id + ".png")), coordinates, collisionBox, canCollide);

        if(inventory == null) {
            this.inventory = Inventory.empty(this);
        } else {
            this.inventory = inventory;
        }

        this.health = health;
        this.canInteract = canInteract;
        this.data = data;
    }

    @Override
    public void render(Graphics2D g2d, float offsetX, float offsetY) {
        AffineTransform transform = g2d.getTransform();

        Rectangle2D bounds = FontUtil.getStringBounds(String.valueOf(health)).getBounds();
        g2d.translate(offsetX + (coordinates.x * TILE_WIDTH) + (TILE_WIDTH / 2f - bounds.getWidth() / 2), offsetY + (coordinates.y * TILE_HEIGHT) - bounds.getHeight() + 5);
        g2d.setColor(Color.RED);
        g2d.drawString(String.valueOf(health), 0, 0);
        g2d.setTransform(transform);

        super.render(g2d, offsetX, offsetY);
    }

    public void update(double delta) {
        if(health <= 0) {
            remove();
        }
    }

    public void interact() { }

    public void remove() {
        parent.entities.set(((int)coordinates.y * parent.width) + (int)coordinates.x, null);
    }
}
