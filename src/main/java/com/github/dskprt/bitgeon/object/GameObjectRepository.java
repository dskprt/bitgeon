package com.github.dskprt.bitgeon.object;

import com.github.dskprt.bitgeon.BitgeonGame;
import com.github.dskprt.bitgeon.level.Level;
import com.github.dskprt.bitgeon.object.block.BlockObject;
import com.github.dskprt.bitgeon.object.block.blocks.*;
import com.github.dskprt.bitgeon.object.entity.EntityObject;
import com.github.dskprt.bitgeon.object.entity.entities.BulletEntity;
import com.github.dskprt.bitgeon.object.entity.entities.InteractionTestEntity;
import com.github.dskprt.bitgeon.object.entity.entities.PlayerEntity;
import com.github.dskprt.bitgeon.util.GameState;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class GameObjectRepository {

    public static final Map<String, Class<? extends BlockObject>> BLOCKS = new HashMap<>();
    public static final Map<String, Class<? extends EntityObject>> ENTITIES = new HashMap<>();

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

    public static BlockObject createBlockFromId(Level parent, String id, float x, float y, float z, byte data) {
        try {
            return BLOCKS.get(id).getDeclaredConstructor(Level.class, float.class, float.class, float.class)
                    .newInstance(parent, x, y, z);
        } catch(NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            try {
                return BLOCKS.get(id).getDeclaredConstructor(Level.class, float.class, float.class, float.class, byte.class)
                        .newInstance(parent, x, y, z, data);
            } catch(NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
                ex.printStackTrace();
                BitgeonGame.INSTANCE.setState(GameState.STOPPED);
            }
        }

        return null;
    }

    public static EntityObject createEntityFromId(Level parent, String id, float x, float y, float z, byte data) {
        try {
            return ENTITIES.get(id).getDeclaredConstructor(Level.class, float.class, float.class, float.class)
                    .newInstance(parent, x, y, z);
        } catch(NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            try {
                return ENTITIES.get(id).getDeclaredConstructor(Level.class, float.class, float.class, float.class, byte.class)
                        .newInstance(parent, x, y, z, data);
            } catch(NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
                ex.printStackTrace();
            }
        }

        return null;
    }
}
