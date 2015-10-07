package me.curlpipesh.pipe.plugin.module;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import me.curlpipesh.pipe.Pipe;
import me.curlpipesh.pipe.event.events.Keypress;
import me.curlpipesh.pipe.plugin.Plugin;
import me.curlpipesh.pipe.plugin.router.Route;
import me.curlpipesh.pipe.util.helpers.KeypressHelper;

/**
 * @author audrey
 * @since 10/6/15.
 */
public abstract class ToggleModule extends BasicModule {
    @Getter
    @Setter
    private boolean enabled = false;

    public ToggleModule(@NonNull Plugin plugin, @NonNull String name, @NonNull String description) {
        super(plugin, name, description);
        registerRoute(new Route<Keypress>(this) {
            @Override
            public void route(@NonNull Keypress event) {
                if(KeypressHelper.isKeyPlusModifiersDown(getKeybind(), event)) {
                    Pipe.getLogger().info(String.format("[%s] Toggled module %s.", plugin.getName(), name));
                    setEnabled(!isEnabled());
                }
            }
        });
    }
}
