package com.github.dskprt.bitgeon.tile;

import com.github.dskprt.bitgeon.tile.formats.JMapFormat;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TileMaps {

    public static final Map<String, ITileMapFormat> FORMATS = new HashMap<>();

    static {
        FORMATS.put(".jmap", new JMapFormat());
    }

    public static TileMap loadMap(File file) {
        try {
            return FORMATS.get(file.getName().substring(file.getName().lastIndexOf('.'))).parse(file);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
