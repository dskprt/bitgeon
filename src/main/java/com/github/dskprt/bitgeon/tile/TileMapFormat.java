package com.github.dskprt.bitgeon.tile;

import java.io.File;
import java.io.IOException;

public abstract class TileMapFormat {

    public String levelName;

    public int blocksLoaded = 0;
    public int entitiesLoaded = 0;
    public int blockCount = 0;
    public int entityCount = 0;

    public State state = State.OTHER;

    public abstract TileMap parse(File file) throws Exception;

    protected void reset() {
        levelName = null;

        blocksLoaded = 0;
        entitiesLoaded = 0;
        blockCount = 0;
        entityCount = 0;

        state = State.OTHER;
    }

    public enum State {

        LOADING_BLOCKS, LOADING_ENTITIES, FINISHED, OTHER
    }
}
