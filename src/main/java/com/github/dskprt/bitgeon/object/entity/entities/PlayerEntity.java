package com.github.dskprt.bitgeon.object.entity.entities;

import com.github.dskprt.bitgeon.BitgeonGame;
import com.github.dskprt.bitgeon.input.Keyboard;
import com.github.dskprt.bitgeon.input.Mouse;
import com.github.dskprt.bitgeon.item.Item;
import com.github.dskprt.bitgeon.item.items.PistolItem;
import com.github.dskprt.bitgeon.item.items.ShotgunItem;
import com.github.dskprt.bitgeon.level.Level;
import com.github.dskprt.bitgeon.object.entity.EntityObject;
import com.github.dskprt.bitgeon.tile.block.Block;
import com.github.dskprt.bitgeon.tile.entity.Entity;
import com.github.dskprt.bitgeon.object.entity.inventory.Inventory;
import com.github.dskprt.bitgeon.util.Facing;
import com.github.dskprt.bitgeon.util.FontUtil;
import com.github.dskprt.bitgeon.util.Rectangle2DUtil;
import com.github.dskprt.bitgeon.util.Ray;

import javax.vecmath.Vector2f;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class PlayerEntity extends EntityObject {

    private static final Color SELECTION_COLOR = Color.RED;

    public double movementSpeed = 0.01;
    public Facing facing = Facing.NORTH;

    public double rotation = 0;

    private Ray blockSelectionRay;
    private Ray entitySelectionRay;

    public PlayerEntity(Level parent, float x, float y, float z) {
        super(parent, "player", x, y, z, 16, 16,
                new Rectangle2D.Float[] { new Rectangle2D.Float(0, 0, 16, 16) }, false, (byte) 0);

        getInventory().items.put(Inventory.Slot.MAIN, new PistolItem(this, (byte) 0));
        getInventory().items.put(Inventory.Slot.SECONDARY, new ShotgunItem(this, (byte) 0));
    }

    @Override
    public void render(Graphics2D g2d) {
        //super.render(g2d, offsetX, offsetY);

        g2d.setColor(Color.ORANGE);
        g2d.drawString("X: " + x + "; Y: " + y, 5, 50);
        g2d.drawString("Facing: " + facing.toString(), 5, 60);
        g2d.drawString("Rotation: " + rotation, 5, 70);

        AffineTransform transform = g2d.getTransform();

        g2d.translate(x, y);

        g2d.rotate(Math.toRadians(rotation), width / 2f, height / 2f);
        g2d.drawImage(image, 0, 0, null);
        g2d.setTransform(transform);

        if(blockSelectionRay != null) {
            if(blockSelectionRay.getResult() != null) {
                transform = g2d.getTransform();

                g2d.translate(blockSelectionRay.getResult().coordinates.x,
                        blockSelectionRay.getResult().coordinates.y);

                g2d.setColor(SELECTION_COLOR);
                g2d.drawRect(0, 0, width, height);
                g2d.setTransform(transform);
            }
        }

        if(entitySelectionRay != null) {
            if(entitySelectionRay.getResult() != null) {
                transform = g2d.getTransform();

                g2d.translate(entitySelectionRay.getResult().coordinates.x,
                        entitySelectionRay.getResult().coordinates.y);

                g2d.setColor(SELECTION_COLOR);
                g2d.drawRect(0, 0, width, height);
                g2d.setTransform(transform);
            }
        }

        int x = 0;
        int y = BitgeonGame.HEIGHT - (Item.TEXTURE_HEIGHT + 2);

        if(getInventory().getSelectedItem() != null) {
            g2d.setColor(Color.DARK_GRAY);
            g2d.fillRect(x, y, Item.TEXTURE_WIDTH + 2, Item.TEXTURE_HEIGHT + 2);
            g2d.drawImage(getInventory().getSelectedItem().image, x + 1, y + 1, null);
        }

        g2d.setColor(Color.RED);

        String s = "Health: " + Math.round(getHealth() * 100.0) / 100.0;
        Rectangle2D bounds = FontUtil.getStringBounds(s);

        g2d.drawString(s, BitgeonGame.WIDTH - (int)bounds.getWidth() - 2, (int) bounds.getHeight() + 2);
    }

    @Override
    public void update(double delta) {
        double mv = Math.round((movementSpeed * delta) * 100.0) / 100.0;
        float var0 = (float)mv + 1f;

        int centerX = BitgeonGame.WIDTH / 2 - width / 2;
        int centerY = BitgeonGame.HEIGHT / 2 - height / 2;

        rotation = Math.toDegrees(Math.atan2(centerY - Mouse.getScaledPosition().y, centerX - Mouse.getScaledPosition().x)) - 90;

        if (rotation > 180) {
            rotation -= 360;
        } else if (rotation < -180) {
            rotation += 360;
        }

        if(rotation > -45 && rotation <= 45) {
            facing = Facing.NORTH;
        } else if(rotation > 45 && rotation <= 135) {
            facing = Facing.EAST;
        } else if((rotation > 135 && rotation <= 180) || (rotation <= -135 && rotation > -180)) {
            facing = Facing.SOUTH;
        } else if(rotation > -135 && rotation <= -45) {
            facing = Facing.WEST;
        }

        blockSelectionRay = new Ray(new Vector2f(x, y), (float)rotation, 1f, 0.5f, Ray.Type.BLOCK);
        entitySelectionRay = new Ray(new Vector2f(x, y), (float)rotation, 1f, 0.5f, Ray.Type.ENTITY);

        // TODO rewrite the collision system for the new object system
//        if(Keyboard.isKeyDown(KeyEvent.VK_W)) {
//            if(coordinates.y - mv > 0) {
//                boolean flag = false;
//
//                Rectangle2D.Float playerRect = new Rectangle2D.Float((coordinates.x * TILE_WIDTH) + collisionBox.x,
//                        (coordinates.y * TILE_HEIGHT) + collisionBox.y - (float)mv * 5, collisionBox.width, collisionBox.height);
//
//                for(Block block : parent.getBlocksAround(Math.round(coordinates.x), Math.round(coordinates.y))) {
//                    if(block == null) continue;
//                    if(block.coordinates.y > coordinates.y) continue;
//
//                    Rectangle2D.Float blockRect = new Rectangle2D.Float((block.coordinates.x * TILE_WIDTH) + block.collisionBox.x,
//                            (block.coordinates.y * TILE_HEIGHT) + block.collisionBox.y, block.collisionBox.width, block.collisionBox.height + var0);
//
//                    if(block.canCollide && Rectangle2DUtil.intersects(blockRect, playerRect)) {
//                        flag = true;
//                        break;
//                    }
//                }
//
//                for(Entity entity : parent.getEntitiesAround(Math.round(coordinates.x), Math.round(coordinates.y))) {
//                    if(entity == null) continue;
//                    if(entity.coordinates.y > coordinates.y) continue;
//
//                    Rectangle2D.Float entityRect = new Rectangle2D.Float((entity.coordinates.x * TILE_WIDTH) + entity.collisionBox.x - var0,
//                            (entity.coordinates.y * TILE_HEIGHT) + entity.collisionBox.y, entity.collisionBox.width + var0, entity.collisionBox.height);
//
//                    if(entity.canCollide && Rectangle2DUtil.intersects(entityRect, playerRect)) {
//                        flag = true;
//                        break;
//                    }
//                }
//
//                if(!flag) {
//                    coordinates.y -= mv;
//                }
//            } else {
//                coordinates.y = 0;
//            }
//        }
//
//        if(Keyboard.isKeyDown(KeyEvent.VK_S)) {
//            if((coordinates.y * TILE_HEIGHT) + TILE_HEIGHT + mv < (parent.height * TILE_HEIGHT)) {
//                boolean flag = false;
//
//                Rectangle2D.Float playerRect = new Rectangle2D.Float((coordinates.x * TILE_WIDTH) + collisionBox.x,
//                        (coordinates.y * TILE_HEIGHT) + collisionBox.y + (float)mv * 5, collisionBox.width, collisionBox.height);
//
//                for(Block block : parent.getBlocksAround(Math.round(coordinates.x), Math.round(coordinates.y))) {
//                    if(block == null) continue;
//                    if(block.coordinates.y < coordinates.y) continue;
//
//                    Rectangle2D.Float blockRect = new Rectangle2D.Float((block.coordinates.x * TILE_WIDTH) + block.collisionBox.x,
//                            (block.coordinates.y * TILE_HEIGHT) + block.collisionBox.y - var0, block.collisionBox.width, block.collisionBox.height + var0);
//
//                    if(block.canCollide && Rectangle2DUtil.intersects(blockRect, playerRect)) {
//                        flag = true;
//                        break;
//                    }
//                }
//
//                for(Entity entity : parent.getEntitiesAround(Math.round(coordinates.x), Math.round(coordinates.y))) {
//                    if(entity == null) continue;
//                    if(entity.coordinates.y < coordinates.y) continue;
//
//                    Rectangle2D.Float entityRect = new Rectangle2D.Float((entity.coordinates.x * TILE_WIDTH) + entity.collisionBox.x - var0,
//                            (entity.coordinates.y * TILE_HEIGHT) + entity.collisionBox.y, entity.collisionBox.width + var0, entity.collisionBox.height);
//
//                    if(entity.canCollide && Rectangle2DUtil.intersects(entityRect, playerRect)) {
//                        flag = true;
//                        break;
//                    }
//                }
//
//                if(!flag) {
//                    coordinates.y += mv;
//                }
//            } else {
//                coordinates.y = parent.height - 1;
//            }
//        }
//
//        if(Keyboard.isKeyDown(KeyEvent.VK_A)) {
//            if(coordinates.x - mv > 0) {
//                boolean flag = false;
//
//                Rectangle2D.Float playerRect = new Rectangle2D.Float((coordinates.x * TILE_WIDTH) + collisionBox.x - (float)mv * 5,
//                        (coordinates.y * TILE_HEIGHT) + collisionBox.y, collisionBox.width, collisionBox.height);
//
//                for(Block block : parent.getBlocksAround(Math.round(coordinates.x), Math.round(coordinates.y))) {
//                    if(block == null) continue;
//                    if(block.coordinates.x > coordinates.x) continue;
//
//                    Rectangle2D.Float blockRect = new Rectangle2D.Float((block.coordinates.x * TILE_WIDTH) + block.collisionBox.x,
//                            (block.coordinates.y * TILE_HEIGHT) + block.collisionBox.y, block.collisionBox.width + var0, block.collisionBox.height);
//
//                    if(block.canCollide && Rectangle2DUtil.intersects(blockRect, playerRect)) {
//                        flag = true;
//                        break;
//                    }
//                }
//
//                for(Entity entity : parent.getEntitiesAround(Math.round(coordinates.x), Math.round(coordinates.y))) {
//                    if(entity == null) continue;
//                    if(entity.coordinates.x > coordinates.x) continue;
//
//                    Rectangle2D.Float entityRect = new Rectangle2D.Float((entity.coordinates.x * TILE_WIDTH) + entity.collisionBox.x - var0,
//                            (entity.coordinates.y * TILE_HEIGHT) + entity.collisionBox.y, entity.collisionBox.width + var0, entity.collisionBox.height);
//
//                    if(entity.canCollide && Rectangle2DUtil.intersects(entityRect, playerRect)) {
//                        flag = true;
//                        break;
//                    }
//                }
//
//                if(!flag) {
//                    coordinates.x -= mv;
//                }
//            } else {
//                coordinates.x = 0;
//            }
//        }
//
//        if(Keyboard.isKeyDown(KeyEvent.VK_D)) {
//            if((coordinates.x * TILE_WIDTH) + TILE_WIDTH + mv < (parent.width * TILE_WIDTH)) {
//                boolean flag = false;
//
//                Rectangle2D.Float playerRect = new Rectangle2D.Float((coordinates.x * TILE_WIDTH) + collisionBox.x + (float)mv * 5,
//                        (coordinates.y * TILE_HEIGHT) + collisionBox.y, collisionBox.width, collisionBox.height);
//
//                for(Block block : parent.getBlocksAround(Math.round(coordinates.x), Math.round(coordinates.y))) {
//                    if(block == null) continue;
//                    if(block.coordinates.x < coordinates.x) continue;
//
//                    Rectangle2D.Float blockRect = new Rectangle2D.Float((block.coordinates.x * TILE_WIDTH) + block.collisionBox.x - var0,
//                            (block.coordinates.y * TILE_HEIGHT) + block.collisionBox.y, block.collisionBox.width + var0, block.collisionBox.height);
//
//                    if(block.canCollide && Rectangle2DUtil.intersects(blockRect, playerRect)) {
//                        flag = true;
//                        break;
//                    }
//                }
//
//                for(Entity entity : parent.getEntitiesAround(Math.round(coordinates.x), Math.round(coordinates.y))) {
//                    if(entity == null) continue;
//                    if(entity.coordinates.x < coordinates.x) continue;
//
//                    Rectangle2D.Float entityRect = new Rectangle2D.Float((entity.coordinates.x * TILE_WIDTH) + entity.collisionBox.x - var0,
//                            (entity.coordinates.y * TILE_HEIGHT) + entity.collisionBox.y, entity.collisionBox.width + var0, entity.collisionBox.height);
//
//                    if(entity.canCollide && Rectangle2DUtil.intersects(entityRect, playerRect)) {
//                        flag = true;
//                        break;
//                    }
//                }
//
//                if(!flag) {
//                    coordinates.x += mv;
//                }
//            } else {
//                coordinates.x = parent.width - 1;
//            }
//        }

        if(Keyboard.wasKeyClicked(KeyEvent.VK_E)) {
            if(entitySelectionRay != null) {
                if(entitySelectionRay.getResult() != null) {
                    Entity entity = (Entity) entitySelectionRay.getResult();

                    if(entity.canInteract) {
                        entity.interact();
                        return;
                    }
                }
            }

            if(blockSelectionRay != null) {
                if(blockSelectionRay.getResult() != null) {
                    Block block = (Block) blockSelectionRay.getResult();

                    if(block.canInteract) {
                        block.interact();
                    }
                }
            }
        }

        if(Keyboard.wasKeyClicked(KeyEvent.VK_1)) {
            getInventory().selectedSlot = Inventory.Slot.MAIN;
        }

        if(Keyboard.wasKeyClicked(KeyEvent.VK_2)) {
            getInventory().selectedSlot = Inventory.Slot.SECONDARY;
        }

        if(Mouse.wasButtonClicked(1)) {
            getInventory().getSelectedItem().use();
        }

        if(getHealth() <= 0) {
            // TODO display death screen
        }
    }
}
