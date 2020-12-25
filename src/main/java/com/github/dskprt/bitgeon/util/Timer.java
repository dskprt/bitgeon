package com.github.dskprt.bitgeon.util;

public class Timer {

    private long lastFrame;
    private int frames;
    private int fps;
    private long lastFPS;

    public Timer() {
        getDelta();
        lastFPS = getTime();
    }

    public void tick() {
        if (getTime() - lastFPS > 1000) {
            fps = frames;
            frames = 0;
            lastFPS += 1000;
        }

        frames++;
    }

    public int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;

        return delta;
    }

    public long getTime() {
        return System.nanoTime() / 1000000;
    }

    public int getFps() {
        return fps;
    }
}
