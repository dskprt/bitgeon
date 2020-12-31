package com.github.dskprt.bitgeon.tile;

import com.github.dskprt.bitgeon.tile.block.BlockTile;
import com.github.dskprt.bitgeon.tile.entity.EntityTile;
import com.github.dskprt.bitgeon.tile.entity.entities.PlayerEntity;

import javax.vecmath.Vector2f;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TileMap {

    public final int width;
    public final int height;

    public PlayerEntity player;
    public List<BlockTile> blocks;
    public List<EntityTile> entities;

    public TileMap(int width, int height, Vector2f spawnPosition) {
        this.width = width;
        this.height = height;

        this.blocks = new ArrayList<>();
        this.entities = new ArrayList<>();

        try {
            this.player = new PlayerEntity(this, spawnPosition, (byte) 0);
            this.entities.add(this.player);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void render(Graphics2D g2d, float offsetX, float offsetY) {
        blocks.forEach(b -> b.render(g2d, offsetX, offsetY));
        entities.forEach(e -> e.render(g2d, offsetX, offsetY));
    }

    public void update(double delta) {
        blocks.forEach(b -> b.update(delta));
        entities.forEach(e -> e.update(delta));
    }
}
