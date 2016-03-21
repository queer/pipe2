package lgbt.audrey.pipe.event;

import lgbt.audrey.pipe.plugin.Plugin;
import lgbt.audrey.pipe.util.Cancellable;
import lombok.NonNull;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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

    @Override
    public void register(@NonNull final Plugin plugin, @NonNull final Listener<?> listener) {
        if(!listeners.containsKey(plugin)) {
            listeners.put(plugin, new CopyOnWriteArrayList<>());
        }
        listeners.get(plugin).add(listener);
    }

    @Override
    public void unregister(@NonNull final Plugin plugin, @NonNull final Listener<?> listener) {
        if(!listeners.containsKey(plugin)) {
            return;
        }
        listeners.get(plugin).remove(listener);
    }

    @Override
    public void unregister(final Plugin plugin) {
        listeners.remove(plugin);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T push(@NonNull final T event) {
        // The typecast being performed here is actually safe, due to the fact
        // that a listener is looking for an event of type T if its class is
        // equal to event.getClass(), because that's essentially T.class.
        // listeners.stream().filter(l -> l.getType().equals(event.getClass())).forEach(l -> ((Listener<T>) l).event(event));
        outerLoop: for(final Entry<Plugin, List<Listener<?>>> pluginListEntry : listeners.entrySet()) {
            for(final Listener<?> listener : pluginListEntry.getValue()) {
                if(listener.getType().equals(event.getClass())) {
                    ((Listener<T>) listener).event(event);
                    if(event instanceof Cancellable) {
                        if(((Cancellable) event).isCancelled()) {
                            break outerLoop;
                        }
                    }
                }
            }
        }
        return event;
    }

    @Override
    public void clear() {
        listeners.clear();
    }
}
