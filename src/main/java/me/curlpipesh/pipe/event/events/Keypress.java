package me.curlpipesh.pipe.event.events;

import lombok.Getter;

/**
 * Event that represents a keypress
 *
 * @author c
 * @since 5/2/15
 */
public class Keypress {
    /**
     * The key that was pressed. Uses the keycodes in
     * {@link org.lwjgl.input.Keyboard}.
     */
    @Getter
    private final int key;

    /**
     * Creates a new keypress event
     *
     * @param key The key that was pressed
     */
    public Keypress(int key) {
        this.key = key;
    }
}
