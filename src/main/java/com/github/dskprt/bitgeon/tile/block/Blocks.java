package com.github.dskprt.bitgeon.tile.block;

import com.github.dskprt.bitgeon.BitgeonGame;
import com.github.dskprt.bitgeon.tile.TileMap;
import com.github.dskprt.bitgeon.tile.block.blocks.*;
import com.github.dskprt.bitgeon.util.GameState;

import javax.vecmath.Vector2f;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class Blocks {

    public static final Map<String, Class<?>> REGISTRY = new HashMap<>();

    static {
        REGISTRY.put("bricks", BrickBlock.class);
        REGISTRY.put("air", AirBlock.class);
        REGISTRY.put("grass", GrassBlock.class);
        REGISTRY.put("interaction_test", InteractionTestBlock.class);
        REGISTRY.put("door", DoorBlock.class);
    }

    public static BlockTile createBlockFromId(TileMap parent, String id, Vector2f coordinates) {
        return createBlockFromId(parent, id, coordinates, (byte) 0);
    }

    public static BlockTile createBlockFromId(TileMap parent, String id, Vector2f coordinates, byte data) {
        try {
            return (BlockTile) REGISTRY.get(id).getDeclaredConstructor(TileMap.class, Vector2f.class).newInstance(parent, coordinates);
        } catch(NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            //e.printStackTrace();

            try {
                return (BlockTile) REGISTRY.get(id).getDeclaredConstructor(TileMap.class, Vector2f.class, byte.class).newInstance(parent, coordinates, data);
            } catch(NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
                ex.printStackTrace();
                BitgeonGame.INSTANCE.setState(GameState.STOPPED);
            }
        }

        return null;
    }
}
