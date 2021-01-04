package com.github.dskprt.bitgeon.tile.entity.entities;

import com.github.dskprt.bitgeon.BitgeonGame;
import com.github.dskprt.bitgeon.input.Mouse;
import com.github.dskprt.bitgeon.tile.Tile;
import com.github.dskprt.bitgeon.tile.TileMap;
import com.github.dskprt.bitgeon.tile.block.BlockTile;
import com.github.dskprt.bitgeon.tile.block.blocks.GrassBlock;
import com.github.dskprt.bitgeon.tile.entity.EntityTile;

import javax.vecmath.Vector2f;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

public class BulletEntity extends EntityTile {

    private static final float moveSpeed = 0.3f;

    private boolean decreasingDamage = false;

    public float damage;
    private final float xVel, yVel;

    public BulletEntity(TileMap parent, Vector2f coordinates, float damage, byte data) throws IOException {
        this(parent, coordinates, new Vector2f(Mouse.getScaledPosition().x, Mouse.getScaledPosition().y), damage, data);
    }

    public BulletEntity(TileMap parent, Vector2f coordinates, Vector2f destination, float damage, byte data) throws IOException {
        super(parent, "bullet", coordinates, new Rectangle2D.Float(8, 8, 4, 4), false, false, data);

        if(data == 1) {
            decreasingDamage = true;
        }

        this.damage = damage;

        float x = BitgeonGame.WIDTH / 2f - TILE_WIDTH / 2f;
        float y = BitgeonGame.HEIGHT / 2f - TILE_HEIGHT / 2f;
        float tX = x - destination.x;
        float tY = y - destination.y;

        float mag = (float) -Math.hypot(tX, tY);

        tX /= mag;
        tY /= mag;
        tX *= moveSpeed;
        tY *= moveSpeed;

        xVel = tX;
        yVel = tY;
    }

    @Override
    public void render(Graphics2D g2d, float offsetX, float offsetY) {
        AffineTransform transform = g2d.getTransform();

        g2d.translate(offsetX + (coordinates.x * TILE_WIDTH), offsetY + (coordinates.y * TILE_HEIGHT));
        g2d.drawImage(image, 0, 0, null);
        g2d.setTransform(transform);
    }

    @Override
    public void update(double delta) {
        if(damage <= 0) {
            parent.entities.remove(this);
            return;
        }

        coordinates.x += xVel;
        coordinates.y += yVel;

        if(coordinates.x < 0 || coordinates.y < 0 || coordinates.x > parent.width || coordinates.y > parent.height) {
            parent.entities.remove(this);
            return;
        }

        Tile tile = parent.getBlockAt(Math.round(coordinates.x), Math.round(coordinates.y));

        if(tile != null) {
            if(tile.canCollide) {
                System.out.println("Hit block! " + tile.coordinates.toString());
                parent.entities.remove(this);

                int index = ((int)tile.coordinates.y * parent.width) + (int)tile.coordinates.x;

                try {
                    parent.blocks.set(index, new GrassBlock(parent, new Vector2f(tile.coordinates)));
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }

        tile = parent.getEntityAt(Math.round(coordinates.x), Math.round(coordinates.y));

        if(tile != null) {
            if(tile.canCollide) {
                System.out.println("Hit entity! " + tile.coordinates.toString());
                parent.entities.remove(this);

                ((EntityTile) tile).health -= damage;
            }
        }

        if(decreasingDamage) {
            damage -= 0.075f;
        }
    }
}
