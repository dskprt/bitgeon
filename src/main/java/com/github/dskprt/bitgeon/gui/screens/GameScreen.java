package com.github.dskprt.bitgeon.gui.screens;

import com.github.dskprt.bitgeon.BitgeonGame;
import com.github.dskprt.bitgeon.gui.Screen;
import com.github.dskprt.bitgeon.tile.Tile;
import com.github.dskprt.bitgeon.tile.TileMap;
import com.github.dskprt.bitgeon.tile.block.BlockTile;

import javax.vecmath.Vector2f;
import java.awt.*;
import java.io.IOException;

public class GameScreen extends Screen {

    TileMap tm;

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void render(Graphics2D g2d) {
        if(tm == null) {
            tm = new TileMap(5, 5, new Vector2f(0, 0));
            
            try {
                tm.blocks.add(new BlockTile(tm, "bricks", new Vector2f(0, 0), false, true, (byte)0));
                tm.blocks.add(new BlockTile(tm, "bricks", new Vector2f(1, 0), false, true, (byte)0));
                tm.blocks.add(new BlockTile(tm, "bricks", new Vector2f(2, 0), false, true, (byte)0));
                tm.blocks.add(new BlockTile(tm, "bricks", new Vector2f(3, 0), false, true, (byte)0));
                tm.blocks.add(new BlockTile(tm, "bricks", new Vector2f(4, 0), false, true, (byte)0));
                
                tm.blocks.add(new BlockTile(tm, "bricks", new Vector2f(0, 1), false, true, (byte)0));
                tm.blocks.add(new BlockTile(tm, "bricks", new Vector2f(1, 1), false, true, (byte)0));
                tm.blocks.add(new BlockTile(tm, "bricks", new Vector2f(2, 1), false, true, (byte)0));
                tm.blocks.add(new BlockTile(tm, "bricks", new Vector2f(3, 1), false, true, (byte)0));
                tm.blocks.add(new BlockTile(tm, "bricks", new Vector2f(4, 1), false, true, (byte)0));
                
                tm.blocks.add(new BlockTile(tm, "bricks", new Vector2f(0, 2), false, true, (byte)0));
                tm.blocks.add(new BlockTile(tm, "bricks", new Vector2f(1, 2), false, true, (byte)0));
                tm.blocks.add(new BlockTile(tm, "bricks", new Vector2f(2, 2), false, true, (byte)0));
                tm.blocks.add(new BlockTile(tm, "bricks", new Vector2f(3, 2), false, true, (byte)0));
                tm.blocks.add(new BlockTile(tm, "bricks", new Vector2f(4, 2), false, true, (byte)0));

                tm.blocks.add(new BlockTile(tm, "bricks", new Vector2f(0, 3), false, true, (byte)0));
                tm.blocks.add(new BlockTile(tm, "bricks", new Vector2f(1, 3), false, true, (byte)0));
                tm.blocks.add(new BlockTile(tm, "bricks", new Vector2f(2, 3), false, true, (byte)0));
                tm.blocks.add(new BlockTile(tm, "bricks", new Vector2f(3, 3), false, true, (byte)0));
                tm.blocks.add(new BlockTile(tm, "bricks", new Vector2f(4, 3), false, true, (byte)0));

                tm.blocks.add(new BlockTile(tm, "bricks", new Vector2f(0, 4), false, true, (byte)0));
                tm.blocks.add(new BlockTile(tm, "bricks", new Vector2f(1, 4), false, true, (byte)0));
                tm.blocks.add(new BlockTile(tm, "bricks", new Vector2f(2, 4), false, true, (byte)0));
                tm.blocks.add(new BlockTile(tm, "bricks", new Vector2f(3, 4), false, true, (byte)0));
                tm.blocks.add(new BlockTile(tm, "bricks", new Vector2f(4, 4), false, true, (byte)0));
            } catch(IOException e) {
                e.printStackTrace();
                return;
            }
        }

        if(tm != null) {
            tm.render(g2d, BitgeonGame.WIDTH / 2f - (tm.player.coordinates.x * Tile.TILE_WIDTH) - Tile.TILE_WIDTH / 2f,
                    BitgeonGame.HEIGHT / 2f - (tm.player.coordinates.y * Tile.TILE_HEIGHT) - Tile.TILE_HEIGHT / 2f);
        }

        super.render(g2d);
    }

    @Override
    public void update(double delta) {
        if(tm != null) {
            tm.update(delta);
        }

        super.update(delta);
    }
}
