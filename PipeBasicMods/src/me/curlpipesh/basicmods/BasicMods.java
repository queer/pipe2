package me.curlpipesh.basicmods;

import me.curlpipesh.basicmods.modules.*;
import me.curlpipesh.pipe.plugin.BasicPlugin;

/**
 * @author audrey
 * @since 10/6/15.
 */
public class BasicMods extends BasicPlugin {
    public void onEnable() {
        registerModule(new ModuleStorageESP(this));
        registerModule(new ModuleTracers(this));
        registerModule(new ModuleOverlay(this));
        registerModule(new ModuleBrightness(this));
    }
}
