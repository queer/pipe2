package me.curlpipesh.pipe.event;

import me.curlpipesh.pipe.plugin.Plugin;

/**
 * An <tt>EventBus</tt> is responsible for exactly what it sounds like:
 * "events" - really any {@link Object} - are {@link #push(Object)}ed across
 * the bus, sent to the various {@link Listener}s that have been
 * {@link #register(Plugin, Listener)}ed with the given <tt>EventBus</tt> instance.
 *
 * @author c
 * @since 7/11/15
 */
public interface EventBus {
    /**
     * Registers the specified listener with this <tt>EventBus</tt>. If the
     * supplied listener is already registered, it will be ignored.
     *
     * @param plugin The plugin the listener is registered to. May not be null
     * @param listener The listener to register. May not be null.
     */
    void register(Plugin plugin, Listener<?> listener);

    /**
     * Unregisters the specified listener from this <tt>EventBus</tt>. If the
     * supplied listener is not registered, this method will do nothing.
     *
     * @param plugin The plugin the listener is registered to. May not be null
     * @param listener The listener to unregister. May not be null.
     */
    void unregister(Plugin plugin, Listener<?> listener);

    /**
     * Unregisters all event listeners for the given plugin.
     *
     *
     * @param plugin The plugin to unregister
     */
    void unregister(Plugin plugin);

    /**
     * Pushes an event across the bus. When this method is invoked, the
     * <tt>EventBus</tt> must push the event out to every valid listener that
     * has been registered. Implementations of this method may optionally exit
     * the invocation forcibly when an <tt>Exception</tt> is thrown, or may
     * attempt to resolve the problem or warn the user, and then continue
     * execution. This method must be thread-safe so that invocations from
     * multi-threaded contexts do not fail.
     *
     * @param event The event to push across the bus. May not be null.
     * @param <T> The type of the event.
     * @return The event that was passed in, after any potential modification
     *         by any {@link Listener}s that have received the event.
     */
    <T> T push(T event);

    /**
     * Clears the list of {@link Listener}s that have been registered with this
     * <tt>EventBus</tt> instance.
     */
    void clear();
}
