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
public class ModuleTracers extends ToggleModule {
    private final Vec3 offset = new Vec3(0, 1.62D, 0);
    private final Vec3 half = new Vec3(0.5D, 0.5D, 0.5D);
    private final Vec3 p = new Vec3(0, 0, 0);
    private final Vec3 v = new Vec3(0, 0, 0), v2 = new Vec3(0, 0, 0);

    private ColorOption colorAnimal = new ColorOption("colorAnimal", 0x00FF00),
            colorMonster = new ColorOption("colorMonster", 0xFF0000),
            colorPlayer = new ColorOption("colorPlayer", 0xFF5555),
            colorOther = new ColorOption("colorOther", 0x0000FF);
    private RangeOption<Integer> opacityTracers = new RangeOption<>("opacityTracers", 0x33, 0xFF, 0x00, 0x01),
            opacityBox = new RangeOption<>("opacityBox", 0x56, 0xFF, 0x00, 0x01);
    private RangeOption<Float> thicknessTracers = new RangeOption<>("thicknessTracers", 2.2F, 5.0F, 1.0F, 0.1F);

    public ModuleTracers(Plugin plugin) {
        super(plugin, "Tracers", "Draws pretty lines from here to there");
    }
    
    @Override
    public void init() {
        setKeybind(new Keybind(Keyboard.KEY_R));

        addOption(colorAnimal);
        addOption(colorMonster);
        addOption(colorPlayer);
        addOption(colorOther);
        addOption(opacityTracers);
        addOption(opacityBox);
        addOption(thicknessTracers);

        Pipe.getInstance().getEventBus().register(getPlugin(), new Listener<Render3D>() {
            @Override
            @SuppressWarnings("ConstantConditions")
            public void event(Render3D render3D) {
                if(ModuleTracers.this.isEnabled()) {
                    // Sneak bug fix
                    offset.y(Helper.isEntitySneaking(Helper.getPlayer()) ? 1.54D : 1.62D);
                    int count = 0;
                    Vec3 prev = Helper.getEntityPrevVec(Helper.getPlayer());
                    Vec3 cur = Helper.getEntityVec(Helper.getPlayer());
                    p.x(prev.x() + ((cur.x() - prev.x()) * render3D.getPartialTickTime()));
                    p.y(prev.y() + ((cur.y() - prev.y()) * render3D.getPartialTickTime()));
                    p.z(prev.z() + ((cur.z() - prev.z()) * render3D.getPartialTickTime()));
                    GLRenderer.pre();
                    GL11.glDisable(GL11.GL_DEPTH_TEST);
                    for(Object o : Helper.getLoadedEntities()) {
                        if(!o.equals(Helper.getPlayer())) {
                            if(Helper.isEntityLiving(o) || Helper.isEntityPlayer(o)) {
                                Vec3 e = Helper.getEntityVec(o);
                                if(e != null) {
                                    e.sub(p);
                                    GLRenderer.drawLine(offset, e,
                                            Helper.isEntityAnimal(o) ? colorAnimal.get() | (opacityTracers.get() << 24) :
                                                    Helper.isEntityMonster(o) ? colorMonster.get() | (opacityTracers.get() << 24) :
                                                            Helper.isEntityPlayer(o) ? colorPlayer.get() | (opacityTracers.get() << 24) :
                                                                    colorOther.get() | (opacityTracers.get() << 24), thicknessTracers.get());
                                    e.add(p);
                                    v.set(e);
                                    v2.set(e);
                                    v.sub(p).sub(half);
                                    v2.add(Vec3.unit()).sub(p).addY(1D);
                                    GLRenderer.drawBoxFromPoints(v, v2,
                                            (Helper.isEntityAnimal(o) ? colorAnimal.get() | (opacityBox.get() << 24) :
                                                    Helper.isEntityMonster(o) ? colorMonster.get() | (opacityBox.get() << 24) :
                                                            Helper.isEntityPlayer(o) ? colorPlayer.get() | (opacityBox.get() << 24) :
                                                                    colorOther.get() | (opacityBox.get() << 24)));
                                    ++count;
                                }
                            }
                        }
                    }
                    p.x(0);
                    p.y(0);
                    p.z(0);
                    GL11.glEnable(GL11.GL_DEPTH_TEST);
                    GLRenderer.post();
                    setStatus(count > 0 ? "§a" + count : "§cNot rendering");
                }
            }
        });
    }
}
