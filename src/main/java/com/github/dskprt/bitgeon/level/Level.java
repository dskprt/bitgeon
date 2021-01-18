package com.github.dskprt.bitgeon.level;

import com.github.dskprt.bitgeon.BitgeonGame;
import com.github.dskprt.bitgeon.level.formats.JMapFormat;
import com.github.dskprt.bitgeon.object.GameObject;
import com.github.dskprt.bitgeon.object.block.BlockObject;
import com.github.dskprt.bitgeon.object.entity.EntityObject;
import com.github.dskprt.bitgeon.tile.Tile;
import com.github.dskprt.bitgeon.object.entity.entities.PlayerEntity;

import java.awt.*;
import java.awt.geom.AffineTransform;
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

    public List<BlockObject> blocks;
    public List<EntityObject> entities;
    public List<GameObject> objects;

    public Level(String name, int width, int height, float spawnX, float spawnY, float spawnZ) {
        this.name = name;

        this.width = width;
        this.height = height;

        this.blocks = new ArrayList<>();
        this.entities = new ArrayList<>();
        this.objects = new ArrayList<>();

        this.player = new PlayerEntity(this, spawnX, spawnY, spawnZ);
    }

    public void render(Graphics2D g2d, float offsetX, float offsetY) {
        AffineTransform transform = g2d.getTransform();
        g2d.translate(offsetX, offsetY);

        List<BlockObject> blocks0 = new ArrayList<>(blocks);

        for(BlockObject block : blocks0) {
            if(block != null) {
                double screenX = offsetX + block.x;
                double screenY = offsetY + block.y;

                if(screenX + Tile.TILE_WIDTH < 0 || screenY + Tile.TILE_HEIGHT < 0
                        || screenX > BitgeonGame.WIDTH || screenY > BitgeonGame.HEIGHT) continue;

                block.render(g2d);
            }
        }

        List<EntityObject> entities0 = new ArrayList<>(entities);

        for(EntityObject entity : entities0) {
            if(entity != null) {
                double screenX = offsetX + entity.x;
                double screenY = offsetY + entity.y;

                if(screenX + Tile.TILE_WIDTH < 0 || screenY + Tile.TILE_HEIGHT < 0
                        || screenX > BitgeonGame.WIDTH || screenY > BitgeonGame.HEIGHT) continue;

                entity.render(g2d);
            }
        }

        player.render(g2d);

        g2d.setTransform(transform);
    }

    public void update(double delta) {
        List<BlockObject> blocks0 = new ArrayList<>(blocks);

        for(BlockObject block : blocks0) {
            if(block != null) block.update(delta);
        }

        List<EntityObject> entities0 = new ArrayList<>(entities);

        for(EntityObject entity : entities0) {
            if(entity != null) entity.update(delta);
        }

        if(BitgeonGame.INSTANCE.getScreen() == null) {
            player.update(delta);
        }
    }

    public BlockObject getBlockAt(float x, float y) {
        for(BlockObject block : blocks) {
            if(block.x == x && block.y == y) {
                return block;
            }
        }

        return null;
    }

//    public BlockObject[] getBlocksAround(float x, float y) {
//        float[][] coords = { { x - 1, y - 1 }, { x, y - 1 }, { x + 1, y - 1 },
//                           { x - 1, y },                   { x + 1, y },
//                           { x - 1, y + 1 }, { x, y + 1 }, { x + 1, y + 1 } };
//
//        return new BlockObject[] { getBlockAt(coords[0][0], coords[0][1]), getBlockAt(coords[1][0], coords[1][1]), getBlockAt(coords[2][0], coords[2][1]),
//                getBlockAt(coords[3][0], coords[3][1]), getBlockAt(coords[4][0], coords[4][1]), getBlockAt(coords[5][0], coords[5][1]),
//                getBlockAt(coords[6][0], coords[6][1]), getBlockAt(coords[7][0], coords[7][1]) };
//    }

    public EntityObject getEntityAt(float x, float y) {
        for(EntityObject entity : entities) {
            if(entity.x == x && entity.y == y) {
                return entity;
            }
        }

        return null;
    }

    public EntityObject getCollidingObject(GameObject object) {
        // TODO
        return null;
    }

//    public EntityObject[] getEntitiesAround(float x, float y) {
//        float[][] coords = { { x - 1, y - 1 }, { x, y - 1 }, { x + 1, y - 1 },
//                { x - 1, y },                   { x + 1, y },
//                { x - 1, y + 1 }, { x, y + 1 }, { x + 1, y + 1 } };
//
//        return new EntityObject[] { getEntityAt(coords[0][0], coords[0][1]), getEntityAt(coords[1][0], coords[1][1]), getEntityAt(coords[2][0], coords[2][1]),
//                getEntityAt(coords[3][0], coords[3][1]), getEntityAt(coords[4][0], coords[4][1]), getEntityAt(coords[5][0], coords[5][1]),
//                getEntityAt(coords[6][0], coords[6][1]), getEntityAt(coords[7][0], coords[7][1]) };
//    }

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
