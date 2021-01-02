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
            this.player = new PlayerEntity(this, spawnPosition);
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

    public BlockTile getBlockAt(int x, int y) {
        int index = (x * width) + y;

        if(index < 0 || index >= blocks.size()) return null;

        return blocks.get(index);
    }

    public BlockTile[] getBlocksAround(int x, int y) {
        int[][] coords = { { x - 1, y - 1 }, { x, y - 1 }, { x + 1, y - 1 },
                           { x - 1, y },                   { x + 1, y },
                           { x - 1, y + 1 }, { x, y + 1 }, { x + 1, y + 1 } };

        return new BlockTile[] { getBlockAt(coords[0][0], coords[0][1]), getBlockAt(coords[1][0], coords[1][1]), getBlockAt(coords[2][0], coords[2][1]),
                getBlockAt(coords[3][0], coords[3][1]), getBlockAt(coords[4][0], coords[4][1]), getBlockAt(coords[5][0], coords[5][1]),
                getBlockAt(coords[6][0], coords[6][1]), getBlockAt(coords[7][0], coords[7][1]) };
    }
}
