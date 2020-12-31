package com.github.dskprt.bitgeon.tile.entity.entities;

import com.github.dskprt.bitgeon.input.Keyboard;
import com.github.dskprt.bitgeon.tile.TileMap;
import com.github.dskprt.bitgeon.tile.entity.EntityTile;

import javax.vecmath.Vector2f;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class PlayerEntity extends EntityTile {

    public double movementSpeed = 0.01;

    public PlayerEntity(TileMap parent, Vector2f coordinates, byte data) throws IOException {
        super(parent, "player", coordinates, false, true, data);
    }

    @Override
    public void render(Graphics2D g2d, float offsetX, float offsetY) {
        super.render(g2d, offsetX, offsetY);

        g2d.setColor(Color.ORANGE);
        g2d.drawString("X: " + coordinates.x + "; Y: " + coordinates.y, 5, 50);
    }

    @Override
    public void update(double delta) {
        if(Keyboard.isKeyDown(KeyEvent.VK_W)) {
            if(coordinates.y - (movementSpeed * delta) > 0) coordinates.y -= movementSpeed * delta;
        }

        if(Keyboard.isKeyDown(KeyEvent.VK_S)) {
            if((coordinates.y * TILE_HEIGHT) + TILE_HEIGHT + (movementSpeed * delta) < (parent.height * TILE_HEIGHT)) coordinates.y += movementSpeed * delta;
        }

        if(Keyboard.isKeyDown(KeyEvent.VK_A)) {
            if(coordinates.x - (movementSpeed * delta) > 0) coordinates.x -= movementSpeed * delta;
        }

        if(Keyboard.isKeyDown(KeyEvent.VK_D)) {
            if((coordinates.x * TILE_WIDTH) + TILE_WIDTH + (movementSpeed * delta) < (parent.width * TILE_WIDTH)) coordinates.x += movementSpeed * delta;
        }
    }
}
