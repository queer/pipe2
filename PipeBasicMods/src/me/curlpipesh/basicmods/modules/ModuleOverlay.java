package me.curlpipesh.basicmods.modules;

import lombok.NonNull;
import me.curlpipesh.pipe.Pipe;
import me.curlpipesh.pipe.event.Listener;
import me.curlpipesh.pipe.event.events.Keypress;
import me.curlpipesh.pipe.event.events.Render2D;
import me.curlpipesh.pipe.event.events.RenderFramebuffer;
import me.curlpipesh.pipe.plugin.Plugin;
import me.curlpipesh.pipe.plugin.module.BasicModule;
import me.curlpipesh.pipe.plugin.module.Module;
import me.curlpipesh.pipe.util.GLRenderer;
import me.curlpipesh.pipe.util.Keybind;
import me.curlpipesh.pipe.util.Vec3;
import me.curlpipesh.pipe.util.helpers.Helper;
import me.curlpipesh.pipe.util.helpers.KeypressHelper;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author audrey
 * @since 10/6/15.
 */
public class ModuleOverlay extends BasicModule {
    private boolean enabled = true;

    public ModuleOverlay(final Plugin plugin) {
        super(plugin, "Overlay", "Informational overlay");
    }

    @Override
    public void init() {
        setKeybind(new Keybind(Keyboard.KEY_O).withModifier(Keyboard.KEY_LCONTROL));
        Pipe.eventBus().register(getPlugin(), new Listener<Keypress>() {
            @Override
            public void event(final Keypress keypress) {
                if(KeypressHelper.isKeyPlusModifiersDown(getKeybind(), keypress)) {
                    enabled = !enabled;
                }
            }
        });
        Pipe.eventBus().register(getPlugin(), new Listener<Render2D>() {
            @Override
            @SuppressWarnings("ConstantConditions")
            public void event(final Render2D render2D) {
                if(Helper.isIngameGuiInDebugMode() || !enabled) {
                    return;
                }
                final String statusLine = "MC " + Helper.getMinecraftVersion();
                final Collection<String> enabledModules = new ArrayList<>();
                final List<Plugin> plugins = Pipe.getInstance().getPluginManager().getPlugins();
                for(@NonNull final Plugin plugin : plugins) {
                    enabledModules.addAll(plugin.getProvidedModules().stream()
                            .filter(Module::isEnabled)
                            .filter(Module::isStatusShown)
                            .map(module -> module.getName() + " (" + module.getStatus() + "\247r)")
                            .collect(Collectors.toList()));
                }
                int width = Helper.getStringWidth(statusLine);
                for(final String string : enabledModules) {
                    final int w = Helper.getStringWidth(string);
                    if(w > width) {
                        width = w;
                    }
                }
                width += 4;
                final int OFFSET = Helper.getFontHeight() + 2;
                int y = 2;
                final int height = OFFSET * (enabledModules.size() + 1);
                GLRenderer.drawRect(0, 0, width, height, 0x77000000);
                Helper.drawString(statusLine, 2, 2, 0xFFFFFFFF, false);
                for(final String e : enabledModules) {
                    Helper.drawString(e, 2, y += OFFSET, 0xFFFFFFFF, false);
                }
            }
        });
        Pipe.eventBus().register(getPlugin(), new Listener<RenderFramebuffer>() {
            @SuppressWarnings({"Convert2streamapi", "ConstantConditions"})
            @Override
            public void event(final RenderFramebuffer event) {
                if(!Helper.isWorldNull()) {
                    for(final Object o : Helper.getLoadedEntities()) {
                        if(!o.equals(Helper.getPlayer())) {
                            final Vec3 vec = Helper.getEntityVec(o);
                            final float[] screenCoords = GLRenderer.getScreenPos(vec.x(), vec.y(), vec.z());
                            if(screenCoords != null) {
                                GLRenderer.drawRect(screenCoords[0], screenCoords[1], 50, 50, 0xFFFFFFFF);
                                System.out.println(Arrays.toString(screenCoords));
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public boolean isStatusShown() {
        return false;
    }
}
