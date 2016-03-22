package lgbt.audrey.basicmods.modules;

import lgbt.audrey.pipe.Pipe;
import lgbt.audrey.pipe.event.Listener;
import lgbt.audrey.pipe.event.events.Keypress;
import lgbt.audrey.pipe.event.events.Render2D;
import lgbt.audrey.pipe.plugin.Plugin;
import lgbt.audrey.pipe.plugin.module.BasicModule;
import lgbt.audrey.pipe.plugin.module.Module;
import lgbt.audrey.pipe.util.GLRenderer;
import lgbt.audrey.pipe.util.Keybind;
import lgbt.audrey.pipe.util.Vec2;
import lgbt.audrey.pipe.util.Vec3;
import lgbt.audrey.pipe.util.helpers.Helper;
import lgbt.audrey.pipe.util.helpers.KeypressHelper;
import lombok.NonNull;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
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
                final Collection<String> displayList = new ArrayList<>();
                // TODO: Build properly so that not appending a useless empty string. More verbose, but...
                displayList.add("MC " + Helper.getMinecraftVersion() + (Pipe.getInstance().isInDebugMode() ? " DEBUG" : ""));
                if(Pipe.getInstance().isInDebugMode()) {
                    final Vec3 playerVec = Helper.getEntityVec(Helper.getPlayer());
                    displayList.add("Position: " + (int) playerVec.x() + ", " + (int) playerVec.y() + ", " + (int) playerVec.z());

                    final Vec2 rot = Helper.getEntityRotation(Helper.getPlayer());
                    displayList.add("Rotation: " + (int) rot.x() + ", " + (int) rot.y());
                    final double r = 1;
                    final double x = r * Math.cos(Math.toRadians(rot.x())) * Math.sin(Math.toRadians(rot.y()));
                    final double y = r * Math.sin(Math.toRadians(rot.x())) * Math.cos(Math.toRadians(rot.y()));
                    final double z = r * Math.cos(Math.toRadians(rot.y()));
                    displayList.add("r, x, y, z: " + String.format("%.2f, %.2f, %.2f, %.2f", r, x, y, z));
                }

                final List<Plugin> plugins = Pipe.getInstance().getPluginManager().getPlugins();
                for(@NonNull final Plugin plugin : plugins) {
                    displayList.addAll(plugin.getProvidedModules().stream()
                            .filter(Module::isEnabled)
                            .filter(Module::isStatusShown)
                            // TODO: Build properly so that not appending a useless empty string. More verbose, but...
                            .map(module ->
                                    (Pipe.getInstance().isInDebugMode() ? module.getPlugin().getName().toLowerCase().replace(" ", "") + ':' : "")
                                            + (Pipe.getInstance().isInDebugMode() ? module.getName().toLowerCase().replace(" ", "") : module.getName())
                                            + ' ' + (!module.getStatus().isEmpty() ? '(' + module.getStatus() + "\247r)" : ""))
                            .collect(Collectors.toList()));
                }

                int width = 2;
                for(final String string : displayList) {
                    final int w = Helper.getStringWidth(string);
                    if(w > width) {
                        width = w;
                    }
                }
                width += 4;
                final int OFFSET = Helper.getFontHeight() + 2;
                int y = -OFFSET + 2;
                final int height = OFFSET * displayList.size();
                GLRenderer.drawRect(0, 0, width, height, 0x77000000);
                for(final String e : displayList) {
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
