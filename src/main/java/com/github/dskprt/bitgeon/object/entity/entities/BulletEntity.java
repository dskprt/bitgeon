package com.github.dskprt.bitgeon.object.entity.entities;

import com.github.dskprt.bitgeon.BitgeonGame;
import com.github.dskprt.bitgeon.input.Mouse;
import com.github.dskprt.bitgeon.object.GameObject;
import com.github.dskprt.bitgeon.object.block.BlockObject;
import com.github.dskprt.bitgeon.object.entity.EntityObject;
import com.github.dskprt.bitgeon.level.Level;

import javax.vecmath.Vector2f;
import java.awt.geom.Rectangle2D;

public class BulletEntity extends EntityObject {

    private static final float moveSpeed = 0.3f;

    private boolean decreasingDamage = false;

    public float damage;
    private final float xVel, yVel;

    public BulletEntity(Level parent, float x, float y, float z, float damage, byte data) {
        this(parent, x, y, z, Mouse.getScaledPosition().x, Mouse.getScaledPosition().y, damage, data);
    }

    public BulletEntity(Level parent, float x, float y, float z, float destX, float destY, float damage, byte data) {
        //super(parent, "bullet", x, y, , new Rectangle2D.Float(8, 8, 4, 4), false, false, data);
        super(parent, "bullet", x, y, z, 4, 4,
                new Rectangle2D.Float[] { new Rectangle2D.Float(8, 8, 4, 4) }, false, (byte) 0);

        if(data == 1) {
            decreasingDamage = true;
        }

        this.damage = damage;

        float x1 = BitgeonGame.WIDTH / 2f - width / 2f;
        float y1 = BitgeonGame.HEIGHT / 2f - height / 2f;
        float tX = x1 - destX;
        float tY = y1 - destY;

        float mag = (float) -Math.hypot(tX, tY);

        tX /= mag;
        tY /= mag;
        tX *= moveSpeed;
        tY *= moveSpeed;

        xVel = tX;
        yVel = tY;
    }

    @Override
    public void update(double delta) {
        if(damage <= 0) {
            parent.entities.remove(this);
            return;
        }

        x += xVel;
        y += yVel;

        if(x < 0 || y < 0 || x > parent.width || y > parent.height) {
            parent.entities.remove(this);
            return;
        }

//        GameObject object = parent.getCollidingObject(this);
//
//        if(object != null) {
//            if(object.collisions != null) {
//                System.out.println("Hit block! [x=" + object.x + ",y=" + object.y + ",z=" + object.z + "]");
//                parent.entities.remove(this);
//
//                int index = ((int)object.y * parent.width) + (int).x;
//
//                parent.blocks.set(index, new GrassBlock(parent, new Vector2f(tile.coordinates)));
//            }
//        }
//
//        tile = parent.getEntityAt(Math.round(x), Math.round(y));
//
//        if(tile != null) {
//            if(tile.canCollide) {
//                System.out.println("Hit entity! " + tile.coordinates.toString());
//                parent.entities.remove(this);
//
//                ((Entity) tile).health -= damage;
//            }
//        }

        if(decreasingDamage) {
            damage -= 0.075f;
        }
    }
}
