package com.github.dskprt.bitgeon.config;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class GameConfiguration {

    public Properties properties;

    public int width;
    public int height;

    public GameConfiguration(String file) {
        properties = new Properties();

        try {
            properties.load(new FileReader(file));
        } catch (IOException e) {
            try {
                Files.copy(GameConfiguration.class.getResourceAsStream("/default.properties"), Paths.get(file));
                properties.load(new FileReader(file));
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }

        this.width = Integer.parseInt(properties.getProperty("width"));
        this.height = Integer.parseInt(properties.getProperty("height"));
    }
}
