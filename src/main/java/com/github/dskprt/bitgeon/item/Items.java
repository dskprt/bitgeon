package com.github.dskprt.bitgeon.item;

import com.github.dskprt.bitgeon.tile.entity.EntityTile;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class Items {

    public static final Map<String, Class<?>> REGISTRY = new HashMap<>();

    static {

    }

    public static Item createItemFromId(EntityTile parent, String id) {
        return createItemFromId(parent, id, (byte) 0);
    }

    public static Item createItemFromId(EntityTile parent, String id, byte data) {
        try {
            return (Item) REGISTRY.get(id).getDeclaredConstructor(EntityTile.class).newInstance(parent);
        } catch(NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();

            try {
                return (Item) REGISTRY.get(id).getDeclaredConstructor(EntityTile.class, byte.class).newInstance(parent, data);
            } catch(NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
                ex.printStackTrace();
            }
        }

        return null;
    }
}
