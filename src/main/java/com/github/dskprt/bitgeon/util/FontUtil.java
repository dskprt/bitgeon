package com.github.dskprt.bitgeon.util;

import com.github.dskprt.bitgeon.BitgeonGame;

import java.awt.*;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;

public class FontUtil {

    public static Rectangle2D getStringBounds(Graphics2D g2d, String text, int w, int h) {
        final GlyphVector gv = g2d.getFont().createGlyphVector(g2d.getFontRenderContext(), text);
        final Rectangle2D stringBoundsForPosition = gv.getOutline().getBounds2D();
        final double xForShapeCreation = (w - stringBoundsForPosition.getWidth()) / 2d;
        final double yForShapeCreation = (h - stringBoundsForPosition.getHeight()) / 2d;

        final Shape textShape = gv.getOutline((float) xForShapeCreation, (float) yForShapeCreation + g2d.getFontMetrics(g2d.getFont()).getAscent());

        return textShape.getBounds2D();
    }

    public static Rectangle2D getStringBounds(String text, Font font) {
        final GlyphVector gv = font.createGlyphVector(BitgeonGame.INSTANCE.fontRenderContext, text);
        final Rectangle2D stringBoundsForPosition = gv.getOutline().getBounds2D();
        final double xForShapeCreation = (BitgeonGame.INSTANCE.canvas.getWidth() - stringBoundsForPosition.getWidth()) / 2d;
        final double yForShapeCreation = (BitgeonGame.INSTANCE.canvas.getHeight() - stringBoundsForPosition.getHeight()) / 2d;

        final Shape textShape = gv.getOutline((float) xForShapeCreation, (float) yForShapeCreation + BitgeonGame.INSTANCE.fontMetrics.getAscent());

        return textShape.getBounds2D();
    }

    public static Rectangle2D getStringBounds(String text) {
        final GlyphVector gv = BitgeonGame.INSTANCE.font.createGlyphVector(BitgeonGame.INSTANCE.fontRenderContext, text);
        final Rectangle2D stringBoundsForPosition = gv.getOutline().getBounds2D();
        final double xForShapeCreation = (BitgeonGame.INSTANCE.canvas.getWidth() - stringBoundsForPosition.getWidth()) / 2d;
        final double yForShapeCreation = (BitgeonGame.INSTANCE.canvas.getHeight() - stringBoundsForPosition.getHeight()) / 2d;

        final Shape textShape = gv.getOutline((float) xForShapeCreation, (float) yForShapeCreation + BitgeonGame.INSTANCE.fontMetrics.getAscent());

        return textShape.getBounds2D();
    }
}
