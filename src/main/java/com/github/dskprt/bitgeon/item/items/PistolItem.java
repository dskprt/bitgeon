package com.github.dskprt.bitgeon.item.items;

import com.github.dskprt.bitgeon.item.Item;
import com.github.dskprt.bitgeon.object.entity.EntityObject;
import com.github.dskprt.bitgeon.object.entity.entities.BulletEntity;

public class PistolItem extends Item.Weapon {

    public PistolItem(EntityObject parent, byte data) {
        super(parent, "pistol", 2f, data);
    }

    @Override
    public void use() {
        parent.parent.entities.add(new BulletEntity(parent.parent, parent.x, parent.y, parent.z, damage, (byte) 0));
    }
}
