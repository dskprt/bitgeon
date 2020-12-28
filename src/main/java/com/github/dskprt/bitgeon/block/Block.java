package com.github.dskprt.bitgeon.block;

import com.github.dskprt.bitgeon.BitgeonGame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.IOException;

public abstract class Block {

    public static final int TEXTURE_WIDTH = 20;
    public static final int TEXTURE_HEIGHT = 20;

    private final GraphicsConfiguration gc;
    private BufferedImage image;

    public int numericId;
    public String id;
    public String displayName;

    public int x;
    public int y;

    public boolean canInteract;
    public boolean canWalkThrough;
    public byte data;

    public Block(int numericId, String id, String displayName, int x, int y, boolean canInteract, boolean canWalkThrough, byte data) {
        this.gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        //this.image = gc.createCompatibleVolatileImage(TEXTURE_WIDTH, TEXTURE_HEIGHT);
        try {
            this.image = ImageIO.read(Block.class.getResourceAsStream("/textures/blocks/" + id + ".png"));
            this.image.setAccelerationPriority(1f);
        } catch(IOException e) {
            e.printStackTrace();
        }

        this.numericId = numericId;
        this.id = id;
        this.displayName = displayName;

        this.x = x;
        this.y = y;

        this.canInteract = canInteract;
        this.canWalkThrough = canWalkThrough;
        this.data = data;
    }

    public Block(int numericId, String id, String displayName, int x, int y, byte data) {
        this(numericId, id, displayName, x, y, false, true, data);
    }

    public Block(int numericId, String id, String displayName, int x, int y) {
        this(numericId, id, displayName, x, y, false, true, (byte) 0);
    }

    public void render(Graphics2D g2d, int offsetX, int offsetY) {
//        if(image.validate(gc) == VolatileImage.IMAGE_INCOMPATIBLE) {
//            ImageIO.
//            image = gc.createCompatibleVolatileImage(TEXTURE_WIDTH, TEXTURE_HEIGHT, Transparency.TRANSLUCENT);
//            image.getGraphics().
//        }
//        image.
        if(offsetX + x > BitgeonGame.WIDTH || offsetX + (x * TEXTURE_WIDTH) < -TEXTURE_WIDTH
                || offsetY + y > BitgeonGame.HEIGHT || offsetY + (y * TEXTURE_HEIGHT) < -TEXTURE_HEIGHT) return;
        g2d.drawImage(image, offsetX + (x * TEXTURE_WIDTH), offsetY + (y * TEXTURE_HEIGHT), null);
    }

    public void interact() { }
}
