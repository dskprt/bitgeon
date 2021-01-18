package com.github.dskprt.bitgeon.object;

import com.github.dskprt.bitgeon.level.Level;
import com.github.dskprt.bitgeon.util.CachedImageIO;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class GameObject {

    public Level parent;
    public String id;

    public float x, y, z;
    public int width, height;
    public boolean interactable;
    public byte data;

    public Rectangle2D.Float[] collisions;
    public BufferedImage image;

    public GameObject(Level parent, String id, float x, float y, float z, int width, int height,
                      Rectangle2D.Float[] collisions, boolean interactable, byte data) {
        this.parent = parent;
        this.id = id;

        this.x = x;
        this.y = y;
        this.z = z;

        this.width = width;
        this.height = height;

        this.interactable = interactable;
        this.data = data;

        this.collisions = collisions;
        this.image = CachedImageIO.read(GameObject.class.getResource("/textures/objects/" + id + ".png"));
    }

    public GameObject(Level parent, String id, float x, float y, float z, int width, int height,
                      Rectangle2D.Float[] collisions, BufferedImage image, boolean interactable, byte data) {
        this.parent = parent;
        this.id = id;

        this.x = x;
        this.y = y;
        this.z = z;

        this.width = width;
        this.height = height;

        this.interactable = interactable;
        this.data = data;

        this.collisions = collisions;
        this.image = image;
    }

    public void render(Graphics2D g2d) {
        AffineTransform transform = g2d.getTransform();

        g2d.translate(x, y);
        g2d.drawImage(image, 0, 0, null);
        g2d.setTransform(transform);
    }

    public void update(double delta) { }

    public void interact() { }
}
