package me.curlpipesh.pipe.util;

/**
 * An object that has a state that can be toggled
 *
 * @author c
 * @since 5/14/15
 */
public interface Toggleable extends Enableable {
    /**
     * Toggles the state of this object
     */
    default void toggle() {
        setEnabled(!isEnabled());
        if(isEnabled()) {
            onEnable();
        } else {
            onDisable();
        }
    }

    /**
     * Things to be done when this object is enabled
     */
    void onEnable();

    /**
     * Things to be done when this object is disabled
     */
    void onDisable();
}
