package com.github.dskprt.bitgeon.item.items;

import com.github.dskprt.bitgeon.item.Item;
import com.github.dskprt.bitgeon.tile.entity.EntityTile;
import com.github.dskprt.bitgeon.tile.entity.entities.BulletEntity;

import javax.vecmath.Vector2f;
import java.io.IOException;

public class PistolItem extends Item.Weapon {

    public PistolItem(EntityTile parent, byte data) {
        super(parent, "pistol", 2f, data);
    }

    @Override
    public void use() {
        try {
            parent.parent.entities.add(new BulletEntity(parent.parent, new Vector2f(parent.coordinates), damage, (byte)0));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
