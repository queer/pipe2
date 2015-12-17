package me.curlpipesh.basicmods.modules;

import me.curlpipesh.pipe.Pipe;
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
    public ModuleStorageESP(Plugin plugin) {
        super(plugin, "Storage ESP", "Draws pretty boxes around storage things");
    }

    @Override
    public void init() {
        setKeybind(new Keybind(Keyboard.KEY_C));
        Pipe.getInstance().getEventBus().register(getPlugin(), new Listener<Render3D>() {
            @Override
            @SuppressWarnings("ConstantConditions")
            public void event(Render3D render3D) {
                if(ModuleStorageESP.this.isEnabled()) {
                    int count = 0;
                    GLRenderer.pre();
                    GL11.glDisable(GL11.GL_DEPTH_TEST);
                    Helper.disableLightmap();
                    //Vec3 p = Helper.getEntityVec(Helper.getPlayer());
                    Vec3 prev = Helper.getEntityPrevVec(Helper.getPlayer());
                    Vec3 cur = Helper.getEntityVec(Helper.getPlayer());
                    Vec3 p = new Vec3(prev.x() + ((cur.x() - prev.x()) * render3D.getPartialTickTime()),
                            prev.y() + ((cur.y() - prev.y()) * render3D.getPartialTickTime()),
                            prev.z() + ((cur.z() - prev.z()) * render3D.getPartialTickTime())
                    );
                    for(Object o : Helper.getLoadedBlockEntities()) {
                        if(Helper.isBlockEntityChest(o)) {
                            Vec3 v = Helper.getBlockEntityVec(o);
                            Vec3 v2 = Helper.getBlockEntityVec(o);
                            if(v != null && v2 != null) {
                                v.sub(p);
                                v2.add(Vec3.unit()).sub(p);
                                GLRenderer.drawBoxFromPoints(v, v2, 0x7700FFFF);
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
