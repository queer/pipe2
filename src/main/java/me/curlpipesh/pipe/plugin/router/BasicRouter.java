package me.curlpipesh.pipe.plugin.router;

import me.curlpipesh.pipe.Pipe;
import me.curlpipesh.pipe.event.IgnoreEnable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author c
 * @since 7/11/15
 */
public class BasicRouter implements Router {
    private List<Route<?>> routes = new CopyOnWriteArrayList<>();

    @Override
    public void register(Route route) {
        if(route == null) {
            Pipe.getLogger().warning("Asked to register a null route, ignoring!");
            return;
        }
        if(!routes.contains(route)) {
            if(!routes.add(route)) {
                Pipe.getLogger().warning("Failed to register the given route!");
            }
        } else {
            Pipe.getLogger().warning("Asked to register a route we already handle, ignoring!");
        }
    }

    @Override
    public void unregister(Route route) {
        if(route == null) {
            Pipe.getLogger().warning("Asked to unregister a null route, ignoring!");
            return;
        }
        if(routes.contains(route)) {
            if(!routes.remove(route)) {
                Pipe.getLogger().warning("Failed to unregister the given route!");
            }
        } else {
            Pipe.getLogger().warning("Asked to unregister a route we already handle, ignoring!");
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T route(T event) {
        routes.stream().filter(r -> r.getType().equals(event.getClass()))
                .filter(r -> r.getModule().isEnabled() || event.getClass().isAnnotationPresent(IgnoreEnable.class))
                .forEach(r -> ((Route<T>) r).route(event));
        return event;
    }
}
