package lgbt.audrey.pipe.event.events;

/**
 * Event for an in-game tick. One tick is ~1/20th of a second.
 *
 * @author c
 * @since 5/1/15
 */
public class Tick {
    /**
     * Instance of the event so that a new one doesn't have to be created and
     * then thrown away every frame.
     */
    public static final Tick instance = new Tick();
}
