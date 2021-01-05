package com.github.dskprt.bitgeon.tile.formats;

import com.github.dskprt.bitgeon.tile.ITileMapFormat;
import com.github.dskprt.bitgeon.tile.TileMap;
import com.github.dskprt.bitgeon.tile.block.BlockTile;
import com.github.dskprt.bitgeon.tile.block.Blocks;
import com.github.dskprt.bitgeon.tile.entity.Entities;
import com.github.dskprt.bitgeon.util.GZipUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.vecmath.Vector2f;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;

public class JMapFormat implements ITileMapFormat {

    public static final byte[] MAGIC = { 74, 77, 52, 80, 33 };

    @Override
    public TileMap parse(File file) throws Exception {
        byte[] buffer = Files.readAllBytes(file.toPath());

        if(!Arrays.equals(Arrays.copyOfRange(buffer, 0, MAGIC.length), MAGIC)) {
            throw new Exception("Bad magic.");
        }

        byte version = buffer[MAGIC.length];
        TileMap map = null;

        switch(version) {
            case 1:
                byte gzip = buffer[MAGIC.length + 1];
                buffer = Arrays.copyOfRange(buffer, MAGIC.length + 2, buffer.length);

                if(gzip == 1) {
                    buffer = GZipUtil.decompress(buffer);
                }

                JSONObject json = new JSONObject(new String(buffer, StandardCharsets.UTF_8));

                int width = json.getInt("w");
                int height = json.getInt("h");
                int spawnX = json.getInt("x");
                int spawnY = json.getInt("y");

                map = new TileMap(getBaseName(file.getName()), width, height, new Vector2f(spawnX, spawnY));

                JSONArray blocks = json.getJSONArray("b");

                for(int i = 0; i < blocks.length(); i++) {
                    JSONObject block = blocks.getJSONObject(i);

                    String id = block.getString("i");
                    int x = block.getInt("x");
                    int y = block.getInt("y");
                    byte data = (byte) block.getInt("d");

                    int index = (y * width) + x;

                    map.blocks.set(index, Blocks.createBlockFromId(map, id, new Vector2f(x, y), data));
                }

                JSONArray entities = json.getJSONArray("e");

                for(int i = 0; i < entities.length(); i++) {
                    JSONObject entity = entities.getJSONObject(i);

                    String id = entity.getString("i");
                    int x = entity.getInt("x");
                    int y = entity.getInt("y");
                    byte data = (byte) entity.getInt("d");

                    int index = (y * width) + x;

                    map.entities.set(index, Entities.createEntityFromId(map, id, new Vector2f(x, y), data));
                }
                break;
        }

        return map;
    }

    private static String getBaseName(String fileName) {
        int index = fileName.lastIndexOf('.');

        if (index == -1) {
            return fileName;
        } else {
            return fileName.substring(0, index);
        }
    }
}
