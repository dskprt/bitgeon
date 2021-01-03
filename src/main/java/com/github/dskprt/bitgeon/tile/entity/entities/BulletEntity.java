package com.github.dskprt.bitgeon.tile.entity.entities;

import com.github.dskprt.bitgeon.BitgeonGame;
import com.github.dskprt.bitgeon.input.Mouse;
import com.github.dskprt.bitgeon.tile.Tile;
import com.github.dskprt.bitgeon.tile.TileMap;
import com.github.dskprt.bitgeon.tile.block.BlockTile;
import com.github.dskprt.bitgeon.tile.block.blocks.GrassBlock;
import com.github.dskprt.bitgeon.tile.entity.EntityTile;

import javax.vecmath.Vector2f;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

public class BulletEntity extends EntityTile {

    private static final float moveSpeed = 0.3f;

    public float damage;
    private final float xVel, yVel;

    public BulletEntity(TileMap parent, Vector2f coordinates, float damage, byte data) throws IOException {
        super(parent, "bullet", coordinates, new Rectangle2D.Float(8, 8, 4, 4), false, false, data);

        this.damage = damage;

        float x = BitgeonGame.WIDTH / 2f - TILE_WIDTH / 2f;
        float y = BitgeonGame.HEIGHT / 2f - TILE_HEIGHT / 2f;
        float tX = x - Mouse.getScaledPosition().x;
        float tY = y - Mouse.getScaledPosition().y;

        float mag = (float) -Math.hypot(tX, tY);

        tX /= mag;
        tY /= mag;
        tX *= moveSpeed;
        tY *= moveSpeed;

        xVel = tX;
        yVel = tY;
    }

    @Override
    public void update(double delta) {
        coordinates.x += xVel;
        coordinates.y += yVel;

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

                int index = ((int)tile.coordinates.y * parent.width) + (int)tile.coordinates.x;

                parent.entities.set(index, null);
            }
        }
    }
}
