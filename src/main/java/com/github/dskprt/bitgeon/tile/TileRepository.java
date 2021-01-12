package com.github.dskprt.bitgeon.tile;

import com.github.dskprt.bitgeon.BitgeonGame;
import com.github.dskprt.bitgeon.level.Level;
import com.github.dskprt.bitgeon.tile.block.Block;
import com.github.dskprt.bitgeon.tile.block.blocks.*;
import com.github.dskprt.bitgeon.tile.entity.Entity;
import com.github.dskprt.bitgeon.tile.entity.entities.*;
import com.github.dskprt.bitgeon.util.GameState;

import javax.vecmath.Vector2f;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class TileRepository {

    public static final Map<String, Class<? extends Block>> BLOCKS = new HashMap<>();
    public static final Map<String, Class<? extends Entity>> ENTITIES = new HashMap<>();

    static {
        BLOCKS.put("air", AirBlock.class);
        BLOCKS.put("bricks", BricksBlock.class);
        BLOCKS.put("door", DoorBlock.class);
        BLOCKS.put("grass", GrassBlock.class);
        BLOCKS.put("interaction_test", InteractionTestBlock.class);

        ENTITIES.put("bullet", BulletEntity.class);
        ENTITIES.put("interaction_test", InteractionTestEntity.class);
        ENTITIES.put("player", PlayerEntity.class);
    }

    public static Block createBlockFromId(Level parent, String id, Vector2f coordinates, byte data) {
        try {
            return BLOCKS.get(id).getDeclaredConstructor(Level.class, Vector2f.class).newInstance(parent, coordinates);
        } catch(NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            try {
                return BLOCKS.get(id).getDeclaredConstructor(Level.class, Vector2f.class, byte.class).newInstance(parent, coordinates, data);
            } catch(NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
                ex.printStackTrace();
                BitgeonGame.INSTANCE.setState(GameState.STOPPED);
            }
        }

        return null;
    }

    public static Entity createEntityFromId(Level parent, String id, Vector2f coordinates, byte data) {
        try {
            return ENTITIES.get(id).getDeclaredConstructor(Level.class, Vector2f.class).newInstance(parent, coordinates);
        } catch(NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            try {
                return ENTITIES.get(id).getDeclaredConstructor(Level.class, Vector2f.class, byte.class).newInstance(parent, coordinates, data);
            } catch(NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
                ex.printStackTrace();
            }
        }

        return null;
    }
}
