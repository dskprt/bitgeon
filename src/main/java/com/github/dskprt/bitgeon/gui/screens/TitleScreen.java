package com.github.dskprt.bitgeon.gui.screens;

import com.github.dskprt.bitgeon.BitgeonGame;
import com.github.dskprt.bitgeon.gui.Screen;
import com.github.dskprt.bitgeon.gui.components.Label;
import com.github.dskprt.bitgeon.input.Keyboard;
import com.github.dskprt.bitgeon.tile.TileMaps;
import com.github.dskprt.bitgeon.util.FontUtil;
import com.github.dskprt.bitgeon.util.GameState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.net.URISyntaxException;

public class TitleScreen extends Screen {

    private int selection = 0;
    private int maxSelection = 2;

    @Override
    public void init() {
        components.clear();

        Font titleFont = BitgeonGame.INSTANCE.font.deriveFont(32f);
        Rectangle2D titleRect = FontUtil.getStringBounds("bitgeon", titleFont);

        Font optionFont = BitgeonGame.INSTANCE.font.deriveFont(16f);

        components.add(new Label(this, "bitgeon",
                BitgeonGame.WIDTH / 2 - (int) titleRect.getWidth() / 2,
                100, Color.WHITE, titleFont));

        String op0 = selection == 0 ? "> Play <" : "Play";
        String op1 = selection == 1 ? "> Options <" : "Options";
        String op2 = selection == 2 ? "> Quit <" : "Quit";

        components.add(new Label(this, op0,
                BitgeonGame.WIDTH / 2 - (int) FontUtil.getStringBounds(op0, optionFont).getWidth() / 2,
                200, selection == 0 ? Color.GRAY : Color.WHITE, optionFont));
        components.add(new Label(this, op1,
                BitgeonGame.WIDTH / 2 - (int) FontUtil.getStringBounds(op1, optionFont).getWidth() / 2,
                225, selection == 1 ? Color.GRAY : Color.WHITE, optionFont));
        components.add(new Label(this, op2,
                BitgeonGame.WIDTH / 2 - (int) FontUtil.getStringBounds(op2, optionFont).getWidth() / 2,
                250, selection == 2 ? Color.GRAY : Color.WHITE, optionFont));

        super.init();
    }

    @Override
    public void update(double delta) {
        if(Keyboard.wasKeyClicked(KeyEvent.VK_UP)) {
            if(selection - 1 < 0) {
                selection = maxSelection;
            } else {
                selection--;
            }

            init();
        } else if(Keyboard.wasKeyClicked(KeyEvent.VK_DOWN)) {
            if(selection + 1 > maxSelection) {
                selection = 0;
            } else {
                selection++;
            }

            init();
        } else if(Keyboard.wasKeyClicked(KeyEvent.VK_ENTER)) {
            switch(selection) {
                case 0:
                    System.out.println("Play");
                    BitgeonGame.INSTANCE.setScreen(null);
                    BitgeonGame.INSTANCE.setState(GameState.INGAME);

                    try {
                        BitgeonGame.INSTANCE.level = TileMaps.loadMap(new File(TitleScreen.class.getResource("/levels/collisions.jmap").toURI()));
                    } catch(URISyntaxException e) {
                        e.printStackTrace();
                        BitgeonGame.INSTANCE.setState(GameState.STOPPED);
                    }
                    break;
                case 1:
                    System.out.println("Options");
                    break;
                case 2:
                    System.out.println("Quit");
                    BitgeonGame.INSTANCE.setState(GameState.STOPPED);
                    break;
            }
        }

        super.update(delta);
    }
}
