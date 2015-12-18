package me.curlpipesh.pipe.util;

/**
 * Something that can be enabled and disabled
 *
 * @author c
 * @since 5/14/15
 */
public interface Enableable {
    /**
     * Whether or not the object is currently enabled
     *
     * @return True if the object is enabled, false otherwise
     */
    boolean isEnabled();

    /**
     * Sets the enabled state of the object
     *
     * @param e The new state of enablement
     */
    void setEnabled(boolean e);
}
