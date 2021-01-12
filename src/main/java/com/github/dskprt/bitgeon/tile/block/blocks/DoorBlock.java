package com.github.dskprt.bitgeon.tile.block.blocks;

import com.github.dskprt.bitgeon.BitgeonGame;
import com.github.dskprt.bitgeon.level.Level;
import com.github.dskprt.bitgeon.tile.block.Block;
import com.github.dskprt.bitgeon.util.GameState;

import javax.vecmath.Vector2f;
import java.io.File;
import java.net.URISyntaxException;

public class DoorBlock extends Block {

    private static Vector2f prevPos;

    public DoorBlock(Level parent, Vector2f coordinates, byte data) {
        super(parent, "door", coordinates, true, true, data);
    }

    @Override
    public void interact() {
        if(data == 0) {
            new Thread(() -> {
                try {
                    loadLevel(new File(DoorBlock.class.getResource("/levels/"
                            + BitgeonGame.INSTANCE.level.name.substring(0, BitgeonGame.INSTANCE.level.name.lastIndexOf('_')) + ".jmap").toURI()));
                } catch(URISyntaxException e) {
                    e.printStackTrace();
                }
            }, "Level loading thread").start();
        } else {
            new Thread(() -> {
                try {
                    loadLevel(new File(DoorBlock.class.getResource("/levels/"
                            + BitgeonGame.INSTANCE.level.name + "_" + data + ".jmap").toURI()));
                } catch(URISyntaxException e) {
                    e.printStackTrace();
                }
            }, "Level loading thread").start();
        }
    }

    private void loadLevel(File file) {
        Level level = Level.load(file);

        if(level == null) {
            System.out.println("Unexpected error. (level == null)");
            BitgeonGame.INSTANCE.setState(GameState.STOPPED);
            return;
        }

        if(prevPos != null) {
            level.player.coordinates = prevPos;
        }

        prevPos = BitgeonGame.INSTANCE.level.player.coordinates;
        BitgeonGame.INSTANCE.level = level;
        BitgeonGame.INSTANCE.setScreen(null);
    }
}
