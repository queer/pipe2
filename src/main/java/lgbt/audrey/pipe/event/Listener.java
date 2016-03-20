package lgbt.audrey.pipe.event;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Listener class. When a <tt>Listener</tt> is registered, it will have its
 * {@link #event(Object)} method invoked when an event of the corresponding
 * type is pushed through the "system."
 *
 * @param <T> The type of event to listen for.
 *
 * @author c
 * @since 7/11/15
 */
public abstract class Listener<T> {
    /**
     * A Class&lt;T&gt; representing the type of event to listen for.
     */
    private Class<T> type;

    /**
     * Default constructor. Sets up type.
     */
    @SuppressWarnings("unchecked")
    public Listener() {
        Type type = getClass().getGenericSuperclass();
        if(type instanceof ParameterizedType) {
            this.type = (Class<T>) (((ParameterizedType)type).getActualTypeArguments()[0]);
        }
    }

    /**
     * Invoked when an event of the corresponding type is pushed through the
     * "system."
     *
     * @param event The event to be "acted" on.
     */
    public abstract void event(T event);

    /**
     * The type of the event. Publicly accessible.
     *
     * @return The type of the event.
     */
    public final Class<T> getType() {
        return type;
    }
}
