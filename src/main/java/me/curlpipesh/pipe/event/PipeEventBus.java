package me.curlpipesh.pipe.event;

import me.curlpipesh.pipe.Pipe;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The default {@link EventBus} that is used by the mod.
 *
 * @author c
 * @since 7/11/15
 */
public class PipeEventBus implements EventBus {
    private final List<Listener<?>> listeners = new CopyOnWriteArrayList<>();

    @Override
    public void register(Listener<?> listener) {
        if(listener == null) {
            Pipe.getLogger().warning("Was asked to register a null listener, ignoring!");
            return;
        }
        if(listeners.contains(listener)) {
            Pipe.getLogger().warning("Was asked to register a listener that is already registered, ignoring!");
        } else {
            if(!listeners.add(listener)) {
                Pipe.getLogger().warning("Error registering the specified listener!");
            }
        }
    }

    @Override
    public void unregister(Listener<?> listener) {
        if(listener == null) {
            Pipe.getLogger().warning("Was asked to register a null listener, ignoring!");
            return;
        }
        if(!listeners.contains(listener)) {
            Pipe.getLogger().warning("Was asked to register a listener that is not registered, ignoring!");
        } else {
            if(!listeners.remove(listener)) {
                Pipe.getLogger().warning("Error unregistering the specified listener!");
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T push(T event) {
        // The typecast being performed here is actually safe, due to the fact
        // that a listener is looking for an event of type T if its class is
        // equal to event.getClass(), because that's essentially T.class.
        listeners.stream().filter(l -> l.getType().equals(event.getClass())).forEach(l -> ((Listener<T>)l).event(event));
        return event;
    }

    @Override
    public void clear() {
        listeners.clear();
    }
}
