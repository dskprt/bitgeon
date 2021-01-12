package com.github.dskprt.bitgeon.item.items;

import com.github.dskprt.bitgeon.item.Item;
import com.github.dskprt.bitgeon.tile.entity.Entity;
import com.github.dskprt.bitgeon.tile.entity.entities.BulletEntity;

import javax.vecmath.Vector2f;

public class PistolItem extends Item.Weapon {

    public PistolItem(Entity parent, byte data) {
        super(parent, "pistol", 2f, data);
    }

    @Override
    public void use() {
        parent.parent.entities.add(new BulletEntity(parent.parent, new Vector2f(parent.coordinates), damage, (byte)0));
    }
}
