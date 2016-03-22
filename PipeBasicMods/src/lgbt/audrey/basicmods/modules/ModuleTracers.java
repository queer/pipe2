package lgbt.audrey.basicmods.modules;

import lgbt.audrey.pipe.Pipe;
import lgbt.audrey.pipe.config.ColorOption;
import lgbt.audrey.pipe.config.RangeOption;
import lgbt.audrey.pipe.event.Listener;
import lgbt.audrey.pipe.event.events.Render2D;
import lgbt.audrey.pipe.event.events.Render3D;
import lgbt.audrey.pipe.event.events.RenderFramebuffer;
import lgbt.audrey.pipe.plugin.Plugin;
import lgbt.audrey.pipe.plugin.module.ToggleModule;
import lgbt.audrey.pipe.util.GLRenderer;
import lgbt.audrey.pipe.util.Keybind;
import lgbt.audrey.pipe.util.Vec3;
import lgbt.audrey.pipe.util.helpers.Helper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

/**
 * @author audrey
 * @since 10/6/15.
 */
public class ModuleTracers extends ToggleModule {
    private final Vec3 half = new Vec3(0.5D, 0.5D, 0.5D);
    private final Vec3 p = new Vec3(0, 0, 0);
    private final Vec3 v = new Vec3(0, 0, 0);
    private final Vec3 v2 = new Vec3(0, 0, 0);

    private final ColorOption colorAnimal = new ColorOption("colorAnimal", 0x00FF00);
    private final ColorOption colorMonster = new ColorOption("colorMonster", 0xFF0000);
    private final ColorOption colorPlayer = new ColorOption("colorPlayer", 0xFF5555);
    private final ColorOption colorOther = new ColorOption("colorOther", 0x0000FF);
    private final RangeOption<Integer> opacityTracers = new RangeOption<>("opacityTracers", 0x55, 0xFF, 0x00, 0x01);
    private final RangeOption<Integer> opacityBox = new RangeOption<>("opacityBox", 0x22, 0xFF, 0x00, 0x01);
    private final RangeOption<Float> thicknessTracers = new RangeOption<>("thicknessTracers", 2.2F, 5.0F, 1.0F, 0.1F);

    public ModuleTracers(final Plugin plugin) {
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
            public void event(final Render3D render3D) {
                if(isEnabled()) {
                    // Sneak bug fix
                    //final double offset = Helper.isEntitySneaking(Helper.getPlayer()) ? 1.54D : 1.62D;
                    int count = 0;
                    final Vec3 prev = Helper.getEntityPrevVec(Helper.getPlayer());
                    final Vec3 cur = Helper.getEntityVec(Helper.getPlayer());
                    p.x(prev.x() + (cur.x() - prev.x()) * render3D.getPartialTickTime());
                    p.y(prev.y() + (cur.y() - prev.y()) * render3D.getPartialTickTime());
                    p.z(prev.z() + (cur.z() - prev.z()) * render3D.getPartialTickTime());
                    GLRenderer.pre();
                    GL11.glDisable(GL11.GL_DEPTH_TEST);
                    for(final Object o : Helper.getLoadedEntities()) {
                        if(!o.equals(Helper.getPlayer())) {
                            if(Helper.isEntityLiving(o) || Helper.isEntityPlayer(o)) {
                                final Vec3 e = Helper.getEntityVec(o);
                                if(e != null) {
                                    /*e.sub(p);
                                    GLRenderer.drawLine(e.x(), e.y(), e.z(),
                                            0, 0, 0,
                                            Helper.isEntityAnimal(o) ? colorAnimal.get() | opacityTracers.get() << 24 :
                                                    Helper.isEntityMonster(o) ? colorMonster.get() | opacityTracers.get() << 24 :
                                                            Helper.isEntityPlayer(o) ? colorPlayer.get() | opacityTracers.get() << 24 :
                                                                    colorOther.get() | opacityTracers.get() << 24, thicknessTracers.get());
                                    e.add(p);*/
                                    v.set(e);
                                    v2.set(e);
                                    v.sub(p).sub(half);
                                    v2.add(Vec3.unit()).sub(p).addY(1D);
                                    GLRenderer.drawBoxFromPoints(v, v2,
                                            Helper.isEntityAnimal(o) ? colorAnimal.get() | opacityBox.get() << 24 :
                                                    Helper.isEntityMonster(o) ? colorMonster.get() | opacityBox.get() << 24 :
                                                            Helper.isEntityPlayer(o) ? colorPlayer.get() | opacityBox.get() << 24 :
                                                                    colorOther.get() | opacityBox.get() << 24);
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
                    setStatus(count > 0 ? "\247a" + count : "\247cNot rendering");
                }
            }
        });
        Pipe.eventBus().register(getPlugin(), new Listener<RenderFramebuffer>() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void event(final RenderFramebuffer event) {
                if(isEnabled()) {
                    GL11.glPushMatrix();
                    GLRenderer.pre();
                    Helper.getLoadedEntities().stream().filter(o -> !o.equals(Helper.getPlayer()))
                            .filter(o -> Helper.isEntityLiving(o) || Helper.isEntityPlayer(o)).forEach(o -> {
                        final float[] coords = GLRenderer.worldToScreen(o, 1.62F, 0);
                        GLRenderer.drawLine(coords[0], coords[1], 0,
                                Helper.getWidth() / 4, Helper.getHeight() / 4, 0,
                                Helper.isEntityAnimal(o) ? colorAnimal.get() | opacityTracers.get() << 24 :
                                        Helper.isEntityMonster(o) ? colorMonster.get() | opacityTracers.get() << 24 :
                                                Helper.isEntityPlayer(o) ? colorPlayer.get() | opacityTracers.get() << 24 :
                                                        colorOther.get() | opacityTracers.get() << 24, thicknessTracers.get());
                    });
                    GLRenderer.post();
                    GL11.glPopMatrix();
                }
            }
        });
    }
}
