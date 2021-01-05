package com.github.dskprt.bitgeon.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

public class CachedImageIO {

    private final static HashMap<String, BufferedImage> CACHE = new HashMap<>();

    public static BufferedImage read(URL input) {
        String path = input.getHost() + input.getPath();

        if(CACHE.containsKey(path)) {
            return CACHE.get(path);
        }

        try {
            BufferedImage image = ImageIO.read(input);

            CACHE.put(path, image);

            return image;
        } catch(IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
