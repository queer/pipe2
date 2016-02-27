package me.curlpipesh.basicmods;

import me.curlpipesh.basicmods.modules.*;
import me.curlpipesh.pipe.Pipe;
import me.curlpipesh.pipe.command.Command;
import me.curlpipesh.pipe.plugin.BasicPlugin;
import me.curlpipesh.pipe.plugin.module.ToggleModule;

/**
 * @author audrey
 * @since 10/6/15.
 */
@SuppressWarnings("unused")
public class BasicMods extends BasicPlugin {
    public void onEnable() {
        registerModule(new ModuleStorageESP(this));
        registerModule(new ModuleTracers(this));
        registerModule(new ModuleOverlay(this));
        registerModule(new ModuleBrightness(this));
        registerModule(new ModuleAntiSoulSand(this));
        getProvidedModules().stream().filter(module -> module instanceof ToggleModule).forEach(module -> {
            //noinspection UnnecessarilyQualifiedInnerClassAccess
            Pipe.getInstance().getCommandManager()
                    .registerCommand(this, new Command.Builder()
                            .setName(module.getName().toLowerCase().replaceAll("\\s+", ""))
                            .setExecutor((command1, s, strings) -> {
                                ((ToggleModule) module).toggle();
                                return true;
                            }).build());
        });
    }
}
