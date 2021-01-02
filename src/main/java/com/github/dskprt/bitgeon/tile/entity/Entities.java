package com.github.dskprt.bitgeon.tile.entity;

import com.github.dskprt.bitgeon.tile.TileMap;
import com.github.dskprt.bitgeon.tile.block.BlockTile;
import com.github.dskprt.bitgeon.tile.entity.entities.InteractionTestEntity;
import com.github.dskprt.bitgeon.tile.entity.entities.PlayerEntity;

import javax.vecmath.Vector2f;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class Entities {

    public static final Map<String, Class<?>> REGISTRY = new HashMap<>();

    static {
        REGISTRY.put("player", PlayerEntity.class);
        REGISTRY.put("interaction_test", InteractionTestEntity.class);
    }

    public static EntityTile createEntityFromId(TileMap parent, String id, Vector2f coordinates) {
        return createEntityFromId(parent, id, coordinates, (byte) 0);
    }

    public static EntityTile createEntityFromId(TileMap parent, String id, Vector2f coordinates, byte data) {
        try {
            return (EntityTile) REGISTRY.get(id).getDeclaredConstructor(TileMap.class, Vector2f.class).newInstance(parent, coordinates);
        } catch(NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();

            try {
                return (EntityTile) REGISTRY.get(id).getDeclaredConstructor(TileMap.class, Vector2f.class, byte.class).newInstance(parent, coordinates, data);
            } catch(NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
                ex.printStackTrace();
            }
        }

        return null;
    }
}
