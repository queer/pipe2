package me.curlpipesh.rendermods;

import me.curlpipesh.pipe.plugin.BasicPlugin;
import me.curlpipesh.rendermods.modules.ModuleBrightness;
import me.curlpipesh.rendermods.modules.ModuleOverlay;
import me.curlpipesh.rendermods.modules.ModuleStorageESP;
import me.curlpipesh.rendermods.modules.ModuleTracers;

/**
 * @author audrey
 * @since 10/6/15.
 */
public class RenderMods extends BasicPlugin {
    public void onEnable() {
        registerModule(new ModuleStorageESP(this));
        registerModule(new ModuleTracers(this));
        registerModule(new ModuleOverlay(this));
        registerModule(new ModuleBrightness(this));
    }
}
