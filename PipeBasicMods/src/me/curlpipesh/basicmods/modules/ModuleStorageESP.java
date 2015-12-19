package me.curlpipesh.basicmods.modules;

import me.curlpipesh.pipe.Pipe;
import me.curlpipesh.pipe.config.ColorOption;
import me.curlpipesh.pipe.config.RangeOption;
import me.curlpipesh.pipe.event.Listener;
import me.curlpipesh.pipe.event.events.Render3D;
import me.curlpipesh.pipe.plugin.Plugin;
import me.curlpipesh.pipe.plugin.module.ToggleModule;
import me.curlpipesh.pipe.util.GLRenderer;
import me.curlpipesh.pipe.util.Keybind;
import me.curlpipesh.pipe.util.Vec3;
import me.curlpipesh.pipe.util.helpers.Helper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

/**
 * @author audrey
 * @since 10/6/15.
 */
public class ModuleStorageESP extends ToggleModule {
    private final Vec3 p = new Vec3(0, 0, 0);
    private final Vec3 v = new Vec3(0, 0, 0), v2 = new Vec3(0, 0, 0);
    private final Vec3 offset = new Vec3(0, 1.62D, 0);
    private final Vec3 half = new Vec3(0.5D, 0.5D, 0.5D);

    private ColorOption colorBox = new ColorOption("colorBox", 0x00FFFF);
    private RangeOption<Integer> opacityTracers = new RangeOption<>("opacityTracers", 0x56, 0xFF, 0x00, 0x01),
            opacityBox = new RangeOption<>("opacityBox", 0x77, 0xFF, 0x00, 0x01);
    private RangeOption<Float> thicknessTracers = new RangeOption<>("thicknessTracers", 2.2F, 5.0F, 1.0F, 0.1F);

    public ModuleStorageESP(Plugin plugin) {
        super(plugin, "Storage ESP", "Draws pretty boxes around storage things");
    }

    @Override
    public void init() {
        setKeybind(new Keybind(Keyboard.KEY_C));

        addOption(colorBox);
        addOption(opacityTracers);
        addOption(opacityBox);
        addOption(thicknessTracers);

        Pipe.getInstance().getEventBus().register(getPlugin(), new Listener<Render3D>() {
            @Override
            @SuppressWarnings("ConstantConditions")
            public void event(Render3D render3D) {
                if(ModuleStorageESP.this.isEnabled()) {
                    // Sneak bug fix
                    offset.y(Helper.isEntitySneaking(Helper.getPlayer()) ? 1.54D : 1.62D);
                    int count = 0;
                    GLRenderer.pre();
                    GL11.glDisable(GL11.GL_DEPTH_TEST);
                    Helper.disableLightmap();
                    Vec3 prev = Helper.getEntityPrevVec(Helper.getPlayer());
                    Vec3 cur = Helper.getEntityVec(Helper.getPlayer());
                    p.x(prev.x() + ((cur.x() - prev.x()) * render3D.getPartialTickTime()));
                    p.y(prev.y() + ((cur.y() - prev.y()) * render3D.getPartialTickTime()));
                    p.z(prev.z() + ((cur.z() - prev.z()) * render3D.getPartialTickTime()));
                    for(Object o : Helper.getLoadedBlockEntities()) {
                        if(Helper.isBlockEntityChest(o)) {
                            v.set(Helper.getBlockEntityVec(o));
                            v2.set(v);
                            if(v != null && v2 != null) {
                                v.sub(p);
                                v2.add(Vec3.unit()).sub(p);
                                GLRenderer.drawBoxFromPoints(v, v2, colorBox.get() | (opacityBox.get() << 24));
                                GLRenderer.drawLine(offset, v.add(half), colorBox.get() | (opacityTracers.get() << 24), thicknessTracers.get());
                                ++count;
                            }
                        }
                    }
                    Helper.enableLightmap();
                    GL11.glEnable(GL11.GL_DEPTH_TEST);
                    GLRenderer.post();
                    setStatus(count > 0 ? "§a" + count : "§cNot rendering");
                }
            }
        });
    }
}
