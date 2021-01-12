package com.github.dskprt.bitgeon.level;

import java.io.File;

public abstract class LevelFormat {

    public String levelName;

    public int blocksLoaded = 0;
    public int entitiesLoaded = 0;
    public int blockCount = 0;
    public int entityCount = 0;

    public State state = State.OTHER;

    public abstract Level parse(File file) throws Exception;

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
