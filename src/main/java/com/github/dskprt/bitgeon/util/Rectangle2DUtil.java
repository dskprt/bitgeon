package com.github.dskprt.bitgeon.util;

import java.awt.geom.Rectangle2D;

public class Rectangle2DUtil {

    public static boolean intersects(Rectangle2D.Float r, Rectangle2D.Float r1) {
        float tw = r1.width;
        float th = r1.height;
        float rw = r.width;
        float rh = r.height;

        if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
            return false;
        }

        float tx = r1.x;
        float ty = r1.y;
        float rx = r.x;
        float ry = r.y;

        rw += rx;
        rh += ry;
        tw += tx;
        th += ty;

        return ((rw < rx || rw > tx) &&
                (rh < ry || rh > ty) &&
                (tw < tx || tw > rx) &&
                (th < ty || th > ry));
    }
}
