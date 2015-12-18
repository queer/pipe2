package me.curlpipesh.pipe.gui.api.util;

/**
 * Small little class that has a utility method or two that I didn't find
 * elsewhere.
 */
public final class MathUtil {
    public static int wrapTo(int number, int lower, int upper) {
        if (number > upper) {
            return upper;
        } else if (number < lower) {
            return lower;
        } else {
            return number;
        }
    }
}
