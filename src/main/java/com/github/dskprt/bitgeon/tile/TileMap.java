package com.github.dskprt.bitgeon.tile;

import com.github.dskprt.bitgeon.tile.block.BlockTile;
import com.github.dskprt.bitgeon.tile.entity.EntityTile;
import com.github.dskprt.bitgeon.tile.entity.entities.PlayerEntity;

import javax.vecmath.Vector2f;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

        this.blocks = new ArrayList<>(Collections.nCopies(width * height, null));
        this.entities = new ArrayList<>(Collections.nCopies(width * height, null));

        try {
            this.player = new PlayerEntity(this, spawnPosition);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void render(Graphics2D g2d, float offsetX, float offsetY) {
        blocks.forEach(b -> {
            if(b != null) b.render(g2d, offsetX, offsetY);
        });

        entities.forEach(e -> {
            if(e != null) e.render(g2d, offsetX, offsetY);
        });

        player.render(g2d, offsetX, offsetY);
    }

    public void update(double delta) {
        blocks.forEach(b -> {
            if(b != null) b.update(delta);
        });

        entities.forEach(e -> {
            if(e != null) e.update(delta);
        });

        player.update(delta);
    }

    public BlockTile getBlockAt(int x, int y) {
        int index = (y * width) + x;

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

    public EntityTile getEntityAt(int x, int y) {
        int index = (y * width) + x;

        if(index < 1 || index >= entities.size()) return null;

        return entities.get(index);
    }

    public EntityTile[] getEntitiesAround(int x, int y) {
        int[][] coords = { { x - 1, y - 1 }, { x, y - 1 }, { x + 1, y - 1 },
                { x - 1, y },                   { x + 1, y },
                { x - 1, y + 1 }, { x, y + 1 }, { x + 1, y + 1 } };

        return new EntityTile[] { getEntityAt(coords[0][0], coords[0][1]), getEntityAt(coords[1][0], coords[1][1]), getEntityAt(coords[2][0], coords[2][1]),
                getEntityAt(coords[3][0], coords[3][1]), getEntityAt(coords[4][0], coords[4][1]), getEntityAt(coords[5][0], coords[5][1]),
                getEntityAt(coords[6][0], coords[6][1]), getEntityAt(coords[7][0], coords[7][1]) };
    }
}
