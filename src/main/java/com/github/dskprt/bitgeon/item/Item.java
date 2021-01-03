package com.github.dskprt.bitgeon.item;

import com.github.dskprt.bitgeon.BitgeonGame;
import com.github.dskprt.bitgeon.tile.block.BlockTile;
import com.github.dskprt.bitgeon.tile.entity.EntityTile;
import com.github.dskprt.bitgeon.util.GameState;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class Item {

    public static final int TEXTURE_WIDTH = 20;
    public static final int TEXTURE_HEIGHT = 20;

    public EntityTile parent;
    public String id;
    public Type type;
    public byte data;

    public BufferedImage image;

    public Item(EntityTile parent, String id, Type type, byte data) {
        this.parent = parent;
        this.id = id;
        this.type = type;
        this.data = data;

        try {
            this.image = ImageIO.read(BlockTile.class.getResourceAsStream("/textures/items/" + id + ".png"));
        } catch(IOException e) {
            e.printStackTrace();
            BitgeonGame.INSTANCE.setState(GameState.STOPPED);
        }
    }

    public abstract void use();

    public enum Type {

        FOOD, WEAPON, POTION, OTHER
    }

    public abstract static class Food extends Item {

        public float healing;

        public Food(EntityTile parent, String id, float healing, byte data) {
            super(parent, id, Type.FOOD, data);
            this.healing = healing;
        }
    }

    public abstract static class Potion extends Item {

        public Potion(EntityTile parent, String id, byte data) {
            super(parent, id, Type.POTION, data);
        }
    }

    public abstract static class Weapon extends Item {

        public float damage;

        public Weapon(EntityTile parent, String id, float damage, byte data) {
            super(parent, id, Type.WEAPON, data);
            this.damage = damage;
        }
    }
}
