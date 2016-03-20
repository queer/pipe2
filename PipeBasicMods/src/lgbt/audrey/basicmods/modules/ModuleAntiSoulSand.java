package lgbt.audrey.basicmods.modules;

import lombok.NonNull;
import lgbt.audrey.pipe.Pipe;
import lgbt.audrey.pipe.event.Listener;
import lgbt.audrey.pipe.event.events.SoulSandSpeed;
import lgbt.audrey.pipe.plugin.Plugin;
import lgbt.audrey.pipe.plugin.module.BasicModule;

/**
 * @author audrey
 * @since 12/21/15.
 */
public class ModuleAntiSoulSand extends BasicModule {
    public ModuleAntiSoulSand(@NonNull final Plugin plugin) {
        super(plugin, "Anti-SoulSand", "Cancels SoulSand slowdown");
    }

    @Override
    public void init() {
        Pipe.eventBus().register(getPlugin(), new Listener<SoulSandSpeed>() {
            @Override
            public void event(final SoulSandSpeed soulSandSpeed) {
                soulSandSpeed.setCancelled(true);
            }
        });
    }

    @Override
    public boolean isStatusShown() {
        return false;
    }
}
