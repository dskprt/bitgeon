package com.github.dskprt.bitgeon.object.entity;

import com.github.dskprt.bitgeon.level.Level;
import com.github.dskprt.bitgeon.object.GameObject;
import com.github.dskprt.bitgeon.object.entity.inventory.Inventory;
import com.github.dskprt.bitgeon.util.CachedImageIO;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class EntityObject extends GameObject {

    private Inventory inventory;
    private float health;

    public EntityObject(Level parent, String id, float x, float y, float z, int width, int height,
                        Rectangle2D.Float[] collisions, boolean interactable, byte data) {
        this(parent, id, x, y, z, width, height, collisions, null, 20, interactable, data);
    }

    public EntityObject(Level parent, String id, float x, float y, float z, int width, int height,
                        Rectangle2D.Float[] collisions, Inventory inventory,
                        float health, boolean interactable, byte data) {
        super(parent, id, x, y, z, width, height, collisions,
                CachedImageIO.read(EntityObject.class.getResource("/textures/entities/" + id + ".png")), interactable, data);

        this.inventory = inventory == null ? Inventory.empty(this) : inventory;
        this.health = health;
    }

    @Override
    public void render(Graphics2D g2d) {
        AffineTransform transform = g2d.getTransform();

        g2d.translate(x, y);
        g2d.drawImage(image, 0, 0, null);
        g2d.drawString(String.valueOf(health), width / 2, -2);
        g2d.setTransform(transform);
    }

    @Override
    public void update(double delta) {
        if(health <= 0) {
            // remove this entity
        }
    }

    public Inventory getInventory() {
        return inventory;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public void damage(float damage) {
        this.health -= damage;
    }
}
