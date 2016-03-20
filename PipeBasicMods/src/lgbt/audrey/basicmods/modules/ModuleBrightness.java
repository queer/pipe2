package lgbt.audrey.basicmods.modules;

import lgbt.audrey.pipe.Pipe;
import lgbt.audrey.pipe.event.Listener;
import lgbt.audrey.pipe.event.events.Tick;
import lgbt.audrey.pipe.plugin.Plugin;
import lgbt.audrey.pipe.plugin.module.ToggleModule;
import lgbt.audrey.pipe.util.Keybind;
import lgbt.audrey.pipe.util.helpers.Helper;
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
        Pipe.getInstance().getEventBus().register(getPlugin(), new Listener<Tick>() {
            @Override
            public void event(Tick tick) {
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
