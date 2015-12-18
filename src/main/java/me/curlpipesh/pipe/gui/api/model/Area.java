package me.curlpipesh.pipe.gui.api.model;

import java.awt.geom.Rectangle2D;

/**
 * Basic wrapper around {@link Double} to provide
 * both a shorter name and a few convenience methods.
 *
 * @author c
 * @since 08.16.2014
 */
public class Area extends Rectangle2D.Double {
    /**
     * Constructs a new {@link Area}.
     * @param x The X coordinate of the area.
     * @param y The Y coordinate of the area.
     * @param w The width of the area.
     * @param h The height of the area.
     */
    public Area(double x, double y, double w, double h) {
        super(x, y, w, h);
    }

    /**
     * Returns the difference between the two values. Used for converting two
     * X- or Y-coordinates into a distance.
     *
     * @param lower The smaller coordinate.
     * @param upper The larger coordinate.
     * @return The absolute value of the difference between the two
     *          coordinates, literally {@code |upper - lower|}
     */
    public static double diff(double lower, double upper) {
        return Math.abs(Math.abs(upper) - Math.abs(lower));
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + ", " + width + ", " + height + "]";
    }
}
