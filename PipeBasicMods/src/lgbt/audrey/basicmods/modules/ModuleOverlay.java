package lgbt.audrey.basicmods.modules;

import lombok.NonNull;
import lgbt.audrey.pipe.Pipe;
import lgbt.audrey.pipe.event.Listener;
import lgbt.audrey.pipe.event.events.Keypress;
import lgbt.audrey.pipe.event.events.Render2D;
import lgbt.audrey.pipe.event.events.RenderFramebuffer;
import lgbt.audrey.pipe.plugin.Plugin;
import lgbt.audrey.pipe.plugin.module.BasicModule;
import lgbt.audrey.pipe.plugin.module.Module;
import lgbt.audrey.pipe.util.GLRenderer;
import lgbt.audrey.pipe.util.Keybind;
import lgbt.audrey.pipe.util.Vec2;
import lgbt.audrey.pipe.util.Vec3;
import lgbt.audrey.pipe.util.helpers.Helper;
import lgbt.audrey.pipe.util.helpers.KeypressHelper;
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
                if (KeypressHelper.isKeyPlusModifiersDown(getKeybind(), keypress)) {
                    enabled = !enabled;
                }
            }
        });
        Pipe.eventBus().register(getPlugin(), new Listener<Render2D>() {
            @Override
            @SuppressWarnings("ConstantConditions")
            public void event(final Render2D render2D) {
                if (Helper.isIngameGuiInDebugMode() || !enabled) {
                    return;
                }
                final Collection<String> displayList = new ArrayList<>();
                // TODO: Build properly so that not appending a useless empty string. More verbose, but...
                displayList.add("MC " + Helper.getMinecraftVersion() + (Pipe.getInstance().isInDebugMode() ? " DEBUG" : ""));
                if (Pipe.getInstance().isInDebugMode()) {
                    final Vec3 playerVec = Helper.getEntityVec(Helper.getPlayer());
                    displayList.add("Position: " + (int) playerVec.x() + ", " + (int) playerVec.y() + ", " + (int) playerVec.z());

                    // TODO: This is wrong!
                    final Vec2 rotationVec = Helper.getEntityRotation(Helper.getPlayer());
                    displayList.add("Rotation: " + (int) rotationVec.x() + ", " + (int) rotationVec.y());
                }

                final List<Plugin> plugins = Pipe.getInstance().getPluginManager().getPlugins();
                for (@NonNull final Plugin plugin : plugins) {
                    displayList.addAll(plugin.getProvidedModules().stream()
                            .filter(Module::isEnabled)
                            .filter(Module::isStatusShown)
                            // TODO: Build properly so that not appending a useless empty string. More verbose, but...
                            .map(module -> module.getName() + (!module.getStatus().isEmpty() ? " (" + module.getStatus() + "\247r)" : ""))
                            .collect(Collectors.toList()));
                }

                int width = 2;
                for (final String string : displayList) {
                    final int w = Helper.getStringWidth(string);
                    if (w > width) {
                        width = w;
                    }
                }
                width += 4;
                final int OFFSET = Helper.getFontHeight() + 2;
                int y = -OFFSET + 2;
                final int height = OFFSET * displayList.size();
                GLRenderer.drawRect(0, 0, width, height, 0x77000000);
                for (final String e : displayList) {
                    Helper.drawString(e, 2, y += OFFSET, 0xFFFFFFFF, false);
                }
            }
        });
        Pipe.eventBus().register(getPlugin(), new Listener<RenderFramebuffer>() {
            @SuppressWarnings({"Convert2streamapi", "ConstantConditions"})
            @Override
            public void event(final RenderFramebuffer event) {
                if (!Helper.isWorldNull()) {
                    for (final Object o : Helper.getLoadedEntities()) {
                        if (!o.equals(Helper.getPlayer())) {
                            final Vec3 vec = Helper.getEntityVec(o);
                            final float[] screenCoords = GLRenderer.getScreenPos(vec.x(), vec.y(), vec.z());
                            if (screenCoords != null) {
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
