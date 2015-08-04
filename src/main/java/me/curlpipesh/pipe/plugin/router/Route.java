package me.curlpipesh.pipe.plugin.router;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Describes a route that a {@link Router} can send an event across.
 *
 * @author c
 * @since 7/10/15
 */
public abstract class Route<T> {
    /**
     * The type of event that this route can handle.
     */
    private final Class<T> type;

    /**
     * Creates the new route.
     */
    @SuppressWarnings("unchecked")
    public Route() {
        Type type = getClass().getGenericSuperclass();
        if(type instanceof ParameterizedType) {
            this.type = (Class<T>) (((ParameterizedType)type).getActualTypeArguments()[0]);
        } else {
            throw new IllegalStateException("Unable to set type of route! There's not much you can do about " +
                    "this error.");
        }
    }

    /**
     * Actually handles the event routing, doing whatever needs to be done upon
     * receipt of the given event.
     *
     * @param event The event to handle
     */
    public abstract void route(T event);

    /**
     * Returns the type of this route
     *
     * @return The type of this route
     */
    @SuppressWarnings("unchecked")
    public final Class<T> getType() {
        return type;
    }
}
