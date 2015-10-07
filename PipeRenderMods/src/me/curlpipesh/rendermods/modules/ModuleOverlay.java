package me.curlpipesh.rendermods.modules;

import lombok.NonNull;
import me.curlpipesh.pipe.Pipe;
import me.curlpipesh.pipe.event.events.Render2D;
import me.curlpipesh.pipe.plugin.Plugin;
import me.curlpipesh.pipe.plugin.module.BasicModule;
import me.curlpipesh.pipe.plugin.module.Module;
import me.curlpipesh.pipe.plugin.router.Route;
import me.curlpipesh.pipe.util.GLRenderer;
import me.curlpipesh.pipe.util.helpers.Helper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author audrey
 * @since 10/6/15.
 */
public class ModuleOverlay extends BasicModule {
    public ModuleOverlay(Plugin plugin) {
        super(plugin, "Overlay", "Informational overlay");
    }

    @Override
    public void init() {
        registerRoute(new Route<Render2D>(this) {
            @Override
            public void route(Render2D render2D) {
                final List<String> enabledModules = new ArrayList<>();
                final List<Plugin> plugins = Pipe.getInstance().getPluginManager().getPlugins();
                for(@NonNull Plugin plugin : plugins) {
                    enabledModules.addAll(plugin.getProvidedModules().stream()
                            .filter(Module::isEnabled)
                            .filter(Module::isStatusShown)
                            .map(module -> module.getName() + " (" + module.getStatus() + "§r)")
                            .collect(Collectors.toList()));
                }
                int width = -1;
                for(String string : enabledModules) {
                    int w = Helper.getStringWidth(string);
                    if(w > width) {
                        width = w;
                    }
                }
                width += 4;
                final int OFFSET = Helper.getFontHeight() + 2;
                int y = -OFFSET + 2;
                int height = OFFSET * enabledModules.size();
                GLRenderer.drawRect(0, 0, width, height, 0x77000000);
                for(String e : enabledModules) {
                    Helper.drawString(e, 2, y += OFFSET, 0xFFFFFFFF, false);
                }
            }
        });
    }

    @Override
    public boolean isStatusShown() {
        return false;
    }
}
