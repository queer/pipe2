package me.curlpipesh.basicmods.modules;

import lombok.NonNull;
import me.curlpipesh.pipe.Pipe;
import me.curlpipesh.pipe.event.Listener;
import me.curlpipesh.pipe.event.events.SoulSandSpeed;
import me.curlpipesh.pipe.plugin.Plugin;
import me.curlpipesh.pipe.plugin.module.BasicModule;

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
