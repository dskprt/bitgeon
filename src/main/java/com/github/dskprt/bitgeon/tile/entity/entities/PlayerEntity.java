package com.github.dskprt.bitgeon.tile.entity.entities;

import com.github.dskprt.bitgeon.input.Keyboard;
import com.github.dskprt.bitgeon.tile.TileMap;
import com.github.dskprt.bitgeon.tile.block.BlockTile;
import com.github.dskprt.bitgeon.tile.entity.EntityTile;
import com.github.dskprt.bitgeon.util.Rectangle2DUtil;

import javax.vecmath.Vector2f;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

public class PlayerEntity extends EntityTile {

    public double movementSpeed = 0.01;

    public PlayerEntity(TileMap parent, Vector2f coordinates) throws IOException {
        super(parent, "player", coordinates, new Rectangle2D.Float(4, 2, 12, 16), false, true, (byte) 0);
    }

    @Override
    public void render(Graphics2D g2d, float offsetX, float offsetY) {
        super.render(g2d, offsetX, offsetY);

        g2d.setColor(Color.ORANGE);
        g2d.drawString("X: " + coordinates.x + "; Y: " + coordinates.y, 5, 50);
    }

    // i honestly have no idea how and why do those collisions even work
    @Override
    public void update(double delta) {
        double mv = Math.round((movementSpeed * delta) * 100.0) / 100.0;
        float var0 = (float)mv + 1f;

        if(Keyboard.isKeyDown(KeyEvent.VK_W)) {
            if(coordinates.y - mv > 0) {
                boolean flag = false;

                Rectangle2D.Float playerRect = new Rectangle2D.Float((coordinates.x * TILE_WIDTH) + collisionBox.x,
                        (coordinates.y * TILE_HEIGHT) + collisionBox.y - (float)mv * 5, collisionBox.width, collisionBox.height);

                for(BlockTile block : parent.getBlocksAround(Math.round(coordinates.x), Math.round(coordinates.y))) {
                    if(block == null) continue;
                    if(block.coordinates.y > coordinates.y) continue;

                    Rectangle2D.Float blockRect = new Rectangle2D.Float((block.coordinates.x * TILE_WIDTH) + block.collisionBox.x,
                            (block.coordinates.y * TILE_HEIGHT) + block.collisionBox.y, block.collisionBox.width, block.collisionBox.height + var0);

                    if(!block.canWalkThrough && Rectangle2DUtil.intersects(blockRect, playerRect)) {
                        flag = true;
                        break;
                    }
                }

                if(!flag) {
                    coordinates.y -= mv;
                }
            } else {
                coordinates.y = 0;
            }
        }

        if(Keyboard.isKeyDown(KeyEvent.VK_S)) {
            if((coordinates.y * TILE_HEIGHT) + TILE_HEIGHT + mv < (parent.height * TILE_HEIGHT)) {
                boolean flag = false;

                Rectangle2D.Float playerRect = new Rectangle2D.Float((coordinates.x * TILE_WIDTH) + collisionBox.x,
                        (coordinates.y * TILE_HEIGHT) + collisionBox.y + (float)mv * 5, collisionBox.width, collisionBox.height);

                for(BlockTile block : parent.getBlocksAround(Math.round(coordinates.x), Math.round(coordinates.y))) {
                    if(block == null) continue;
                    if(block.coordinates.y < coordinates.y) continue;

                    Rectangle2D.Float blockRect = new Rectangle2D.Float((block.coordinates.x * TILE_WIDTH) + block.collisionBox.x,
                            (block.coordinates.y * TILE_HEIGHT) + block.collisionBox.y - var0, block.collisionBox.width, block.collisionBox.height + var0);

                    if(!block.canWalkThrough && Rectangle2DUtil.intersects(blockRect, playerRect)) {
                        flag = true;
                        break;
                    }
                }

                if(!flag) {
                    coordinates.y += mv;
                }
            } else {
                coordinates.y = parent.height - 1;
            }
        }

        if(Keyboard.isKeyDown(KeyEvent.VK_A)) {
            if(coordinates.x - mv > 0) {
                boolean flag = false;

                Rectangle2D.Float playerRect = new Rectangle2D.Float((coordinates.x * TILE_WIDTH) + collisionBox.x - (float)mv * 5,
                        (coordinates.y * TILE_HEIGHT) + collisionBox.y, collisionBox.width, collisionBox.height);

                for(BlockTile block : parent.getBlocksAround(Math.round(coordinates.x), Math.round(coordinates.y))) {
                    if(block == null) continue;
                    if(block.coordinates.x > coordinates.x) continue;

                    Rectangle2D.Float blockRect = new Rectangle2D.Float((block.coordinates.x * TILE_WIDTH) + block.collisionBox.x,
                            (block.coordinates.y * TILE_HEIGHT) + block.collisionBox.y, block.collisionBox.width + var0, block.collisionBox.height);

                    if(!block.canWalkThrough && Rectangle2DUtil.intersects(blockRect, playerRect)) {
                        flag = true;
                        break;
                    }
                }

                if(!flag) {
                    coordinates.x -= mv;
                }
            } else {
                coordinates.x = 0;
            }
        }

        if(Keyboard.isKeyDown(KeyEvent.VK_D)) {
            if((coordinates.x * TILE_WIDTH) + TILE_WIDTH + mv < (parent.width * TILE_WIDTH)) {
                boolean flag = false;

                Rectangle2D.Float playerRect = new Rectangle2D.Float((coordinates.x * TILE_WIDTH) + collisionBox.x + (float)mv * 5,
                        (coordinates.y * TILE_HEIGHT) + collisionBox.y, collisionBox.width, collisionBox.height);

                for(BlockTile block : parent.getBlocksAround(Math.round(coordinates.x), Math.round(coordinates.y))) {
                    if(block == null) continue;
                    if(block.coordinates.x < coordinates.x) continue;

                    Rectangle2D.Float blockRect = new Rectangle2D.Float((block.coordinates.x * TILE_WIDTH) + block.collisionBox.x - var0,
                            (block.coordinates.y * TILE_HEIGHT) + block.collisionBox.y, block.collisionBox.width + var0, block.collisionBox.height);

                    if(!block.canWalkThrough && Rectangle2DUtil.intersects(blockRect, playerRect)) {
                        flag = true;
                        break;
                    }
                }

                if(!flag) {
                    coordinates.x += mv;
                }
            } else {
                coordinates.x = parent.width - 1;
            }
        }
    }
}
