package me.curlpipesh.pipe.event.events;

/**
 * Event for 3D renders
 *
 * @author c
 * @since 5/21/15
 */
public class Render3D {
    /**
     * Instance of the event so that a new one doesn't have to be created and
     * then thrown away every frame.
     */
    public static final Render3D instance = new Render3D();
}
