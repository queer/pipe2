package me.curlpipesh.pipe.event.events;

/**
 * Event for 2D renders
 *
 * @author c
 * @since 4/30/15
 */
public class Render2D {
    /**
     * Instance of the event so that a new one doesn't have to be created and
     * then thrown away every frame.
     */
    public static final Render2D instance = new Render2D();
}
