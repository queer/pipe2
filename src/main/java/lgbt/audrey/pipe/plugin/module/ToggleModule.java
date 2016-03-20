package lgbt.audrey.pipe.plugin.module;

import lgbt.audrey.pipe.plugin.Plugin;
import lgbt.audrey.pipe.util.helpers.KeypressHelper;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lgbt.audrey.pipe.Pipe;
import lgbt.audrey.pipe.event.Listener;
import lgbt.audrey.pipe.event.events.Keypress;
import lgbt.audrey.pipe.plugin.Plugin;
import lgbt.audrey.pipe.util.Toggleable;
import lgbt.audrey.pipe.util.helpers.KeypressHelper;

/**
 * @author audrey
 * @since 10/6/15.
 */
public abstract class ToggleModule extends BasicModule implements Toggleable {
    @SuppressWarnings("FieldMayBeFinal")
    @Getter
    @Setter
    private boolean enabled;

    public ToggleModule(@NonNull final Plugin plugin, @NonNull final String name, @NonNull final String description) {
        super(plugin, name, description);
        Pipe.getInstance().getEventBus().register(getPlugin(), new Listener<Keypress>() {
            @Override
            public void event(@NonNull final Keypress event) {
                if(KeypressHelper.isKeyPlusModifiersDown(getKeybind(), event)) {
                    Pipe.getLogger().info(String.format("[%s] Toggled module %s.", plugin.getName(), name));
                    setEnabled(!isEnabled());
                    if(isEnabled()) {
                        onEnable();
                    } else {
                        onDisable();
                    }
                }
            }
        });
    }

    @Override
    public void onEnable() {}

    @Override
    public void onDisable() {}
}
