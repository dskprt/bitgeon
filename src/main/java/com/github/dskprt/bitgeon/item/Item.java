package com.github.dskprt.bitgeon.item;

import com.github.dskprt.bitgeon.BitgeonGame;
import com.github.dskprt.bitgeon.item.items.*;
import com.github.dskprt.bitgeon.object.entity.EntityObject;
import com.github.dskprt.bitgeon.util.GameState;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public abstract class Item {

    public static final int TEXTURE_WIDTH = 20;
    public static final int TEXTURE_HEIGHT = 20;

    public static final Map<String, Class<? extends Item>> REGISTRY = new HashMap<>();

    static {
        REGISTRY.put("pistol", PistolItem.class);
        REGISTRY.put("shotgun", ShotgunItem.class);
    }

    public EntityObject parent;
    public String id;
    public Type type;
    public byte data;

    public BufferedImage image;

    public Item(EntityObject parent, String id, Type type, byte data) {
        this.parent = parent;
        this.id = id;
        this.type = type;
        this.data = data;

        try {
            this.image = ImageIO.read(Item.class.getResourceAsStream("/textures/items/" + id + ".png"));
        } catch(IOException e) {
            e.printStackTrace();
            BitgeonGame.INSTANCE.setState(GameState.STOPPED);
        }
    }

    public abstract void use();

    public static Item createItemFromId(EntityObject parent, String id, byte data) {
        try {
            return REGISTRY.get(id).getDeclaredConstructor(EntityObject.class).newInstance(parent);
        } catch(NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();

            try {
                return REGISTRY.get(id).getDeclaredConstructor(EntityObject.class, byte.class).newInstance(parent, data);
            } catch(NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
                ex.printStackTrace();
            }
        }

        return null;
    }

    public enum Type {

        FOOD, WEAPON, POTION, OTHER
    }

    public abstract static class Food extends Item {

        public float healing;

        public Food(EntityObject parent, String id, float healing, byte data) {
            super(parent, id, Type.FOOD, data);
            this.healing = healing;
        }
    }

    public abstract static class Potion extends Item {

        public Potion(EntityObject parent, String id, byte data) {
            super(parent, id, Type.POTION, data);
        }
    }

    public abstract static class Weapon extends Item {

        public float damage;

        public Weapon(EntityObject parent, String id, float damage, byte data) {
            super(parent, id, Type.WEAPON, data);
            this.damage = damage;
        }
    }
}
