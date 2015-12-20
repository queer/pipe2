package me.curlpipesh.pipe.event;

import lombok.NonNull;
import me.curlpipesh.pipe.plugin.Plugin;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The default {@link EventBus} that is used by the mod.
 *
 * @author c
 * @since 7/11/15
 */
public class PipeEventBus implements EventBus {
    private final Map<Plugin, List<Listener<?>>> listeners = new ConcurrentHashMap<>();
    private final List<Listener<?>> directListeners = new CopyOnWriteArrayList<>();

    @Override
    public void register(Plugin plugin, @NonNull Listener<?> listener) {
        if(!listeners.containsKey(plugin)) {
            listeners.put(plugin, new CopyOnWriteArrayList<>());
        }
        listeners.get(plugin).add(listener);
    }

    @Override
    public void unregister(@NonNull Plugin plugin, @NonNull Listener<?> listener) {
        if(!listeners.containsKey(plugin)) {
            return;
        }
        listeners.get(plugin).remove(listener);
    }

    @Override
    public void unregister(Plugin plugin) {
        listeners.remove(plugin);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T push(@NonNull T event) {
        // The typecast being performed here is actually safe, due to the fact
        // that a listener is looking for an event of type T if its class is
        // equal to event.getClass(), because that's essentially T.class.
        // listeners.stream().filter(l -> l.getType().equals(event.getClass())).forEach(l -> ((Listener<T>) l).event(event));
        listeners.entrySet().stream()
                .forEach(l -> l.getValue().stream().filter(i -> i.getType().equals(event.getClass()))
                        .forEach(i -> ((Listener<T>) i).event(event)));
        directListeners.stream().filter(l -> l.getType().equals(event.getClass())).forEach(l -> ((Listener<T>) l).event(event));
        return event;
    }

    /**
     * Intended for internal use only.
     * @param listener
     */
    @Deprecated
    public void addDirectListener(Listener<?> listener) {
        directListeners.add(listener);
    }

    @Override
    public void clear() {
        listeners.clear();
        directListeners.clear();
    }
}
