package com.github.dskprt.bitgeon.tile;

import javax.vecmath.Vector2f;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Tile {

    public static final int TILE_WIDTH = 20;
    public static final int TILE_HEIGHT = 20;

    public TileMap parent;
    public String id;
    public BufferedImage image;
    public Vector2f coordinates;
    public Rectangle2D.Float collisionBox;
    public boolean canCollide;

    public Tile(TileMap parent, String id, BufferedImage image, Vector2f coordinates, boolean canCollide) {
        this(parent, id, image, coordinates, new Rectangle2D.Float(0, 0, TILE_WIDTH, TILE_HEIGHT), canCollide);
    }

    public Tile(TileMap parent, String id, BufferedImage image, Vector2f coordinates, Rectangle2D.Float collisionBox, boolean canCollide) {
        this.parent = parent;
        this.id = id;
        this.image = image;
        this.coordinates = coordinates;
        this.collisionBox = collisionBox;
        this.canCollide = canCollide;
    }

    public void render(Graphics2D g2d, float offsetX, float offsetY) {
        AffineTransform transform = g2d.getTransform();

        g2d.translate(offsetX + (coordinates.x * TILE_WIDTH), offsetY + (coordinates.y * TILE_HEIGHT));
        g2d.drawImage(image, 0, 0, null);
        g2d.setTransform(transform);
    }
}
