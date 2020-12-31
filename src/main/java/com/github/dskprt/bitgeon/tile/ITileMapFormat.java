package com.github.dskprt.bitgeon.tile;

import java.io.File;
import java.io.IOException;

public interface ITileMapFormat {

    TileMap parse(File file) throws Exception;
}
