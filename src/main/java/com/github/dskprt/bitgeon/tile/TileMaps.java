package com.github.dskprt.bitgeon.tile;

import com.github.dskprt.bitgeon.tile.formats.JMapFormat;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class TileMaps {

    private static final Map<String, TileMap> CACHE = new HashMap<>();
    public static final Map<String, TileMapFormat> FORMATS = new HashMap<>();

    static {
        FORMATS.put(".jmap", new JMapFormat());
    }

    public static TileMap loadMap(File file) {
        String path = file.getAbsolutePath();

        if(CACHE.containsKey(path)) {
            return CACHE.get(path);
        }

        try {
            TileMap map = FORMATS.get(file.getName().substring(file.getName().lastIndexOf('.'))).parse(file);

            CACHE.put(path, map);

            return map;
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
