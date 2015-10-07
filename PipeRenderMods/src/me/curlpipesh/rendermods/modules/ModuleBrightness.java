package me.curlpipesh.rendermods.modules;

import me.curlpipesh.pipe.event.events.Tick;
import me.curlpipesh.pipe.plugin.Plugin;
import me.curlpipesh.pipe.plugin.module.ToggleModule;
import me.curlpipesh.pipe.plugin.router.Route;
import me.curlpipesh.pipe.util.Keybind;
import me.curlpipesh.pipe.util.helpers.Helper;
import org.lwjgl.input.Keyboard;

/**
 * @author audrey
 * @since 10/7/15.
 */
public class ModuleBrightness extends ToggleModule {
    /**
     * A backup of the field <tt>lightBrightnessTable</tt> from Minecraft's
     * "WorldProvider" class.
     */
    private float[] lightBrightnessTableBackup = new float[16];

    public ModuleBrightness(Plugin plugin) {
        super(plugin, "Brightness", "Brightens up the world");
    }

    @Override
    public void init() {
        setKeybind(new Keybind(Keyboard.KEY_F));
        registerRoute(new Route<Tick>(this) {
            @Override
            public void route(Tick tick) {
                if(ModuleBrightness.this.isEnabled()) {
                    if(!Helper.isWorldNull()) {
                        for(int i = 0; i < 16; i++) {
                            Helper.getLightBrightnessTable()[i] = 1.0F;
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onEnable() {
        System.arraycopy(Helper.getLightBrightnessTable(), 0, lightBrightnessTableBackup, 0, lightBrightnessTableBackup.length);
    }

    @Override
    public void onDisable() {
        System.arraycopy(lightBrightnessTableBackup, 0, Helper.getLightBrightnessTable(), 0, 16);
    }
}
