package com.github.dskprt.bitgeon.main;

import com.github.dskprt.bitgeon.BitgeonGame;
import com.github.dskprt.bitgeon.config.GameConfiguration;

public class Main {

    public static void main(String[] args) {
        new BitgeonGame(new GameConfiguration("config.properties")).start();
    }
}
