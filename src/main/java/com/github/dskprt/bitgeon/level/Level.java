package com.github.dskprt.bitgeon.level;

import com.github.dskprt.bitgeon.BitgeonGame;
import com.github.dskprt.bitgeon.level.formats.JMapFormat;
import com.github.dskprt.bitgeon.tile.Tile;
import com.github.dskprt.bitgeon.tile.block.Block;
import com.github.dskprt.bitgeon.tile.entity.Entity;
import com.github.dskprt.bitgeon.tile.entity.entities.PlayerEntity;

import javax.vecmath.Vector2f;
import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;

public class Level {

    private static final Map<String, Level> CACHE = new HashMap<>();
    public static final Map<String, LevelFormat> FORMATS = new HashMap<>();

    static {
        FORMATS.put(".jmap", new JMapFormat());
    }

    public final String name;

    public final int width;
    public final int height;

    public PlayerEntity player;
    public List<Block> blocks;
    public List<Entity> entities;

    public Level(String name, int width, int height, Vector2f spawnPosition) {
        this.name = name;

        this.width = width;
        this.height = height;

        this.blocks = new ArrayList<>(Collections.nCopies(width * height, null));
        this.entities = new ArrayList<>(Collections.nCopies(width * height, null));

        this.player = new PlayerEntity(this, spawnPosition);
    }

    public void render(Graphics2D g2d, float offsetX, float offsetY) {
        List<Block> blocks0 = new ArrayList<>(blocks);

        for(Block block : blocks0) {
            if(block != null) {
                double screenX = offsetX + (block.coordinates.x * Tile.TILE_WIDTH);
                double screenY = offsetY + (block.coordinates.y * Tile.TILE_HEIGHT);

                if(screenX + Tile.TILE_WIDTH < 0 || screenY + Tile.TILE_HEIGHT < 0
                        || screenX > BitgeonGame.WIDTH || screenY > BitgeonGame.HEIGHT) continue;

                block.render(g2d, offsetX, offsetY);
            }
        }

        List<Entity> entities0 = new ArrayList<>(entities);

        for(Entity entity : entities0) {
            if(entity != null) {
                double screenX = offsetX + (entity.coordinates.x * Tile.TILE_WIDTH);
                double screenY = offsetY + (entity.coordinates.y * Tile.TILE_HEIGHT);

                if(screenX + Tile.TILE_WIDTH < 0 || screenY + Tile.TILE_HEIGHT < 0
                        || screenX > BitgeonGame.WIDTH || screenY > BitgeonGame.HEIGHT) continue;

                entity.render(g2d, offsetX, offsetY);
            }
        }

        player.render(g2d, offsetX, offsetY);
    }

    public void update(double delta) {
        List<Block> blocks0 = new ArrayList<>(blocks);

        for(Block block : blocks0) {
            if(block != null) block.update(delta);
        }

        List<Entity> entities0 = new ArrayList<>(entities);

        for(Entity entity : entities0) {
            if(entity != null) entity.update(delta);
        }

        if(BitgeonGame.INSTANCE.getScreen() == null) {
            player.update(delta);
        }
    }

    public Block getBlockAt(int x, int y) {
        int index = (y * width) + x;

        if(index < 0 || index >= blocks.size()) return null;

        return blocks.get(index);
    }

    public Block[] getBlocksAround(int x, int y) {
        int[][] coords = { { x - 1, y - 1 }, { x, y - 1 }, { x + 1, y - 1 },
                           { x - 1, y },                   { x + 1, y },
                           { x - 1, y + 1 }, { x, y + 1 }, { x + 1, y + 1 } };

        return new Block[] { getBlockAt(coords[0][0], coords[0][1]), getBlockAt(coords[1][0], coords[1][1]), getBlockAt(coords[2][0], coords[2][1]),
                getBlockAt(coords[3][0], coords[3][1]), getBlockAt(coords[4][0], coords[4][1]), getBlockAt(coords[5][0], coords[5][1]),
                getBlockAt(coords[6][0], coords[6][1]), getBlockAt(coords[7][0], coords[7][1]) };
    }

    public Entity getEntityAt(int x, int y) {
        int index = (y * width) + x;

        if(index < 1 || index >= entities.size()) return null;

        return entities.get(index);
    }

    public Entity[] getEntitiesAround(int x, int y) {
        int[][] coords = { { x - 1, y - 1 }, { x, y - 1 }, { x + 1, y - 1 },
                { x - 1, y },                   { x + 1, y },
                { x - 1, y + 1 }, { x, y + 1 }, { x + 1, y + 1 } };

        return new Entity[] { getEntityAt(coords[0][0], coords[0][1]), getEntityAt(coords[1][0], coords[1][1]), getEntityAt(coords[2][0], coords[2][1]),
                getEntityAt(coords[3][0], coords[3][1]), getEntityAt(coords[4][0], coords[4][1]), getEntityAt(coords[5][0], coords[5][1]),
                getEntityAt(coords[6][0], coords[6][1]), getEntityAt(coords[7][0], coords[7][1]) };
    }

    public static Level load(File file) {
        String path = file.getAbsolutePath();

        if(CACHE.containsKey(path)) {
            return CACHE.get(path);
        }

        try {
            Level map = FORMATS.get(file.getName().substring(file.getName().lastIndexOf('.'))).parse(file);

            CACHE.put(path, map);

            return map;
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
