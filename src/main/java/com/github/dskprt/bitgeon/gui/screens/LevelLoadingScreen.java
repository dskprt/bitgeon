package com.github.dskprt.bitgeon.gui.screens;

import com.github.dskprt.bitgeon.BitgeonGame;
import com.github.dskprt.bitgeon.gui.Screen;
import com.github.dskprt.bitgeon.gui.components.Label;
import com.github.dskprt.bitgeon.level.LevelFormat;
import com.github.dskprt.bitgeon.util.FontUtil;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class LevelLoadingScreen extends Screen {

    private static final String INFO = "[reading %s (%d/%d)]";

    private LevelFormat format;

    private Label infoLabel;

    public LevelLoadingScreen(LevelFormat format) {
        this.format = format;
    }

    @Override
    public void init() {
        String title = "Loading level \"" + format.levelName + "\"...";
        Rectangle2D bounds = FontUtil.getStringBounds(title);

        components.add(new Label(this, title, (int)(BitgeonGame.WIDTH / 2 - bounds.getWidth() / 2),
                (int)(BitgeonGame.HEIGHT / 2 - bounds.getHeight() / 2), Color.WHITE));

        String info = String.format(INFO, "block", 0, 0);
        bounds = FontUtil.getStringBounds(info);

        infoLabel = new Label(this, info, (int)(BitgeonGame.WIDTH / 2 - bounds.getWidth() / 2),
                (int)((BitgeonGame.HEIGHT / 2 - bounds.getHeight() / 2) + bounds.getHeight() + 2), Color.WHITE);

        components.add(infoLabel);
    }

    @Override
    public void update(double delta) {
        String info = "";

        if(format.state == LevelFormat.State.LOADING_BLOCKS) {
            info = String.format(INFO, "block", format.blocksLoaded + 1, format.blockCount);
        } else if(format.state == LevelFormat.State.LOADING_ENTITIES) {
            info = String.format(INFO, "entity", format.entitiesLoaded + 1, format.entityCount);
        } else if(format.state == LevelFormat.State.FINISHED) {
            info = "Finished!";
        }

        Rectangle2D bounds = FontUtil.getStringBounds(info);

        infoLabel.setText(info);
        infoLabel.setX((int)(BitgeonGame.WIDTH / 2 - bounds.getWidth() / 2));
    }
}
