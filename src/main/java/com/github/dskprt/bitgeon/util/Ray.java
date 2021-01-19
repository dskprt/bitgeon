package com.github.dskprt.bitgeon.util;

import com.github.dskprt.bitgeon.BitgeonGame;
import com.github.dskprt.bitgeon.object.GameObject;

import javax.vecmath.Vector2f;

public class Ray {

    public Vector2f finish;
    private GameObject result;

    public Ray(Vector2f coordinates, float direction, float length, float step, Type type) {
        this(coordinates, direction, length, step, type, true);
    }

    public Ray(Vector2f coordinates, float direction, float length, float step, Type type, boolean ignoreEmpty) {
        final double cos = Math.cos(Math.toRadians(direction - 90));
        final double sin = Math.sin(Math.toRadians(direction - 90));

        this.finish = new Vector2f((float)(coordinates.x + (length * cos)),
                (float)(coordinates.y + (length * sin)));

        GameObject object = null;

        switch(type) {
            case BLOCK:
                object = BitgeonGame.INSTANCE.level.getBlockAt(Math.round(finish.x), Math.round(finish.y));
                break;
            case ENTITY:
                object = BitgeonGame.INSTANCE.level.getEntityAt(Math.round(finish.x), Math.round(finish.y));
                break;
        }

        if(object != null) {
            if(object.collisions != null) {
                result = object;
            } else if(!ignoreEmpty) {
                result = object;
            }
        }

        for(float i = 0; i < length; i += step) {
            Vector2f vec = new Vector2f((float)(coordinates.x + (i * cos)),
                    (float)(coordinates.y + (i * sin)));

            switch(type) {
                case BLOCK:
                    object = BitgeonGame.INSTANCE.level.getBlockAt(Math.round(vec.x), Math.round(vec.y));
                    break;
                case ENTITY:
                    object = BitgeonGame.INSTANCE.level.getEntityAt(Math.round(vec.x), Math.round(vec.y));
                    break;
            }

            if(object != null) {
                if(object.collisions != null) {
                    result = object;
                    break;
                }
            }
        }
    }

    public GameObject getResult() {
        return result;
    }

    public enum Type {

        BLOCK, ENTITY
    }
}
