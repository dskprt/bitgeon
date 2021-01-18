package com.github.dskprt.bitgeon.item.items;

import com.github.dskprt.bitgeon.BitgeonGame;
import com.github.dskprt.bitgeon.input.Mouse;
import com.github.dskprt.bitgeon.item.Item;
import com.github.dskprt.bitgeon.object.entity.EntityObject;
import com.github.dskprt.bitgeon.object.entity.entities.BulletEntity;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class ShotgunItem extends Item.Weapon {

    private final static float spread = 7.5f;

    public ShotgunItem(EntityObject parent, byte data) {
        super(parent, "shotgun", 3f, data);
    }

    @Override
    public void use() {
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(spread), BitgeonGame.WIDTH / 2d, BitgeonGame.HEIGHT / 2d);

        Shape shape = transform.createTransformedShape(new Rectangle(Mouse.getScaledPosition()));

        BulletEntity bullet1 = new BulletEntity(parent.parent, parent.x, parent.y, parent.z,
                (float)shape.getBounds2D().getX(), (float)shape.getBounds2D().getY(), damage, (byte) 1);

        BulletEntity bullet2 = new BulletEntity(parent.parent, parent.x, parent.y, parent.z, damage, (byte) 1);

        transform = new AffineTransform();
        transform.rotate(Math.toRadians(-spread), BitgeonGame.WIDTH / 2d, BitgeonGame.HEIGHT / 2d);

        shape = transform.createTransformedShape(new Rectangle(Mouse.getScaledPosition()));

        BulletEntity bullet3 = new BulletEntity(parent.parent, parent.x, parent.y, parent.z,
                (float)shape.getBounds2D().getX(), (float)shape.getBounds2D().getY(), damage, (byte) 1);

        parent.parent.entities.add(bullet1);
        parent.parent.entities.add(bullet2);
        parent.parent.entities.add(bullet3);
    }
}
