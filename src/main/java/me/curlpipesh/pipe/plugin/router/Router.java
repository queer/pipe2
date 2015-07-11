package me.curlpipesh.pipe.plugin.router;

/**
 * A <tt>Router</tt> is meant to be used by a plugin for routing events to
 * modules. Rather than having to rely on the <tt>EventManager</tt> never
 * changing, modules instead register <tt>routes</tt> with their parent plugin.
 * The parent plugin then listens for events (using whatever event system
 * happens to be in use by the main mod), and then passes them to its modules
 * based on the routes that plugins have registered
 *
 * @author c
 * @since 7/10/15
 */
public interface Router {
    /**
     * Registers a new route for an event to pass through
     *
     * @param route The route to register
     */
    void register(Route route);

    /**
     * Unregisters the given route. This will prevent any events from being
     * routed across the specified route unless it is re-registered.
     *
     * @param route The route to unregister
     */
    void unregister(Route route);

    /**
     * Routes an event across the registered routes. This method is intended to
     * find only the valid paths for a given event, and then route the event
     * properly.
     *
     * @param event The event to route
     * @param <T> The type of the event
     * @return The event, as modified by any routes it passes through
     */
    <T> T route(T event);
}
