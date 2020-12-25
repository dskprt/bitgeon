package com.github.dskprt.bitgeon.config;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class GameConfiguration {

    public int width;
    public int height;

    public GameConfiguration(String file) {
        Properties config = new Properties();

        try {
            config.load(new FileReader(file));
        } catch (IOException e) {
            try {
                Files.copy(GameConfiguration.class.getResourceAsStream("/default.properties"), Paths.get(file));
                config.load(new FileReader(file));
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }

        this.width = Integer.parseInt(config.getProperty("width"));
        this.height = Integer.parseInt(config.getProperty("height"));
    }
}
