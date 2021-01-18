package com.github.dskprt.bitgeon.object.block.blocks;

import com.github.dskprt.bitgeon.BitgeonGame;
import com.github.dskprt.bitgeon.level.Level;
import com.github.dskprt.bitgeon.object.block.BlockObject;
import com.github.dskprt.bitgeon.util.GameState;

import java.io.File;
import java.net.URISyntaxException;

public class DoorBlock extends BlockObject {

    private static float prevX = Float.MIN_VALUE;
    private static float prevY = Float.MIN_VALUE;

    public DoorBlock(Level parent, float x, float y, float z, byte data) {
        super(parent, "door", x, y, z, true, data);
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

        if(prevX != Float.MIN_VALUE && prevY != Float.MIN_VALUE) {
            level.player.x = prevX;
            level.player.y = prevY;
        }

        prevX = BitgeonGame.INSTANCE.level.player.x;
        prevY = BitgeonGame.INSTANCE.level.player.y;

        BitgeonGame.INSTANCE.level = level;
        BitgeonGame.INSTANCE.setScreen(null);
    }
}
