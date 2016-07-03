package lgbt.audrey.basicmods.modules;

import lgbt.audrey.pipe.Pipe;
import lgbt.audrey.pipe.event.Listener;
import lgbt.audrey.pipe.event.events.Keypress;
import lgbt.audrey.pipe.event.events.Render2D;
import lgbt.audrey.pipe.plugin.Plugin;
import lgbt.audrey.pipe.plugin.module.BasicModule;
import lgbt.audrey.pipe.util.GLRenderer;
import lgbt.audrey.pipe.util.Keybind;
import lgbt.audrey.pipe.util.Vec2;
import lgbt.audrey.pipe.util.Vec3;
import lgbt.audrey.pipe.util.helpers.EntityHelper;
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
            // TODO: Render2D ptt
            @Override
            @SuppressWarnings("ConstantConditions")
            public void event(final Render2D render2D) {
                if(Helper.isIngameGuiInDebugMode() || !enabled) {
                    return;
                }
                final Collection<String> displayList = new ArrayList<>();
                displayList.add("MC " + Helper.getMinecraftVersion() + (Pipe.getInstance().isInDebugMode() ? " DEBUG" : ""));
                // See maven-jar-plugin <configuration> block in pom.xml
                displayList.add("Pipe v" + Pipe.getClientVersion());
                if(Pipe.getInstance().isInDebugMode()) {
                    final Vec3 playerVec = Helper.getEntityVec(Helper.getPlayer());
                    displayList.add("Position: " + (int) playerVec.x() + ", " + (int) playerVec.y() + ", " + (int) playerVec.z());

                    final Vec2 rot = Helper.getEntityRotation(Helper.getPlayer());
                    displayList.add("Rotation: " + (int) rot.x() + ", " + (int) rot.y());
                }

                final List<Plugin> plugins = Pipe.getInstance().getPluginManager().getPlugins();
                for(@NonNull final Plugin plugin : plugins) {
                    displayList.addAll(plugin.getProvidedModules().stream()
                            .filter(m -> !m.equals(ModuleOverlay.this)) // Never show this because it'll be obvious if it's borked
                            .filter(m -> m.isEnabled() || Pipe.getInstance().isInDebugMode())
                            .filter(m -> m.isStatusShown() || Pipe.getInstance().isInDebugMode())
                            .map(module ->
                                    // This is so ugly :(
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

                if(Pipe.getInstance().isInDebugMode()) {
                    for(final Object o : Helper.getLoadedEntities()) {
                        if(!Helper.getPlayer().equals(o) && Helper.isEntityLiving(o)) {
                            final float distanceFromMouse = EntityHelper.getDistanceFromMouse(o);
                            if(distanceFromMouse <= 90 && distanceFromMouse >= 0) {
                                final float[] coords = GLRenderer.worldToScreen(o, 0);
                                final Vec3 pos = Helper.getEntityVec(o).clone();
                                final Vec2 rot = Helper.getEntityRotation(o).clone();
                                final Collection<String> stuff = new ArrayList<>();
                                stuff.add("Class: " + o.getClass().getName());
                                stuff.add(String.format("X: %.2f", pos.x()));
                                stuff.add(String.format("Y: %.2f", pos.y()));
                                stuff.add(String.format("Z: %.2f", pos.z()));
                                stuff.add(String.format("Rot: %.2f, %.2f", rot.x(), rot.y()));
                                int w = -1;
                                for(final String s : stuff) {
                                    if(Helper.getStringWidth(s) > w) {
                                        w = Helper.getStringWidth(s);
                                    }
                                }
                                int yOffset = 0;
                                final float initialX = coords[0] - w / 2;
                                final float initialY = coords[1] - stuff.size() * Helper.getFontHeight() / 2;
                                GLRenderer.drawRect(initialX, initialY, w, Helper.getFontHeight() * stuff.size(), 0x77000000);
                                for(final String e : stuff) {
                                    Helper.drawString(e, initialX, initialY + yOffset, 0xFFFFFFFF, false);
                                    yOffset += Helper.getFontHeight();
                                }
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
