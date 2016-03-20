package lgbt.audrey.basicmods.modules;

import lgbt.audrey.pipe.Pipe;
import lgbt.audrey.pipe.bytecode.version.Version1_9_X;
import lgbt.audrey.pipe.config.ColorOption;
import lgbt.audrey.pipe.config.RangeOption;
import lgbt.audrey.pipe.event.Listener;
import lgbt.audrey.pipe.event.events.Render2D;
import lgbt.audrey.pipe.event.events.Render3D;
import lgbt.audrey.pipe.plugin.Plugin;
import lgbt.audrey.pipe.plugin.module.ToggleModule;
import lgbt.audrey.pipe.util.GLRenderer;
import lgbt.audrey.pipe.util.Keybind;
import lgbt.audrey.pipe.util.Vec2;
import lgbt.audrey.pipe.util.Vec3;
import lgbt.audrey.pipe.util.helpers.Helper;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * @author audrey
 * @since 10/6/15.
 */
public class ModuleStorageESP extends ToggleModule {
    private final Vec3 p = new Vec3(0, 0, 0);
    private final Vec3 v = new Vec3(0, 0, 0);
    private final Vec3 v2 = new Vec3(0, 0, 0);
    private final Vec3 offset = new Vec3(0, 1.62D, 0);
    private final Vec3 half = new Vec3(0.5D, 0.5D, 0.5D);

    private final ColorOption colorBox = new ColorOption("colorBox", 0x00FFFF);
    private final RangeOption<Integer> opacityTracers = new RangeOption<>("opacityTracers", 0x56, 0xFF, 0x00, 0x01);
    private final RangeOption<Integer> opacityBox = new RangeOption<>("opacityBox", 0x77, 0xFF, 0x00, 0x01);
    private final RangeOption<Float> thicknessTracers = new RangeOption<>("thicknessTracers", 2.2F, 5.0F, 1.0F, 0.1F);

    public ModuleStorageESP(final Plugin plugin) {
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
            public void event(final Render3D render3D) {
                if(isEnabled()) {
                    // Sneak bug fix
                    offset.y(Helper.isEntitySneaking(Helper.getPlayer()) ? 1.54D : 1.62D);
                    int count = 0;
                    GLRenderer.pre();
                    GL11.glDisable(GL11.GL_DEPTH_TEST);
                    Helper.disableLightmap();
                    final Vec3 prev = Helper.getEntityPrevVec(Helper.getPlayer());
                    final Vec3 cur = Helper.getEntityVec(Helper.getPlayer());
                    p.x(prev.x() + (cur.x() - prev.x()) * render3D.getPartialTickTime());
                    p.y(prev.y() + (cur.y() - prev.y()) * render3D.getPartialTickTime());
                    p.z(prev.z() + (cur.z() - prev.z()) * render3D.getPartialTickTime());
                    for(final Object o : Helper.getLoadedBlockEntities()) {
                        if(Helper.isBlockEntityChest(o)) {
                            v.set(Helper.getBlockEntityVec(o));
                            v2.set(v);
                            if(v != null && v2 != null) {
                                v.sub(p);
                                v2.add(Vec3.unit()).sub(p);
                                GLRenderer.drawBoxFromPoints(v, v2, colorBox.get() | opacityBox.get() << 24);
                                GLRenderer.drawLine(offset, v.add(half), colorBox.get() | opacityTracers.get() << 24, thicknessTracers.get());
                                ++count;
                            }
                        }
                    }
                    Helper.enableLightmap();
                    GL11.glEnable(GL11.GL_DEPTH_TEST);
                    GLRenderer.post();
                    setStatus(count > 0 ? "\247a" + count : "\247cNot rendering");
                }
            }
        });
    }

    /**
     * Converts a Minecraft world coordinate to a screen coordinate
     * <p>
     * The world coordinate is the absolute location of a 3d vector
     * in the Minecraft world relative to the world origin.
     * <p>
     * Note that the return value will be scaled to match the current
     * GUI resolution of Minecraft.
     *
     * @param x X coordinate in the Minecraft world
     * @param y Y coordinate in the Minecraft world
     * @param z Z coordinate in the Minecraft world
     *
     * @return Returns a {@link Vec2} representing a 2D location on the screen,
     * or null if the vector fails to be converted.
     */
    private Vec2 project(final float x, final float y, final float z) {
        /**
         * Buffer that will hold the screen coordinates
         */
        final FloatBuffer screen_coords = BufferUtils.createFloatBuffer(3);

        /**
         * Buffer that holds the transformation matrix of the view port
         */
        final IntBuffer viewport = BufferUtils.createIntBuffer(16);

        /**
         * Buffer that holds the transformation matrix of the model view
         */
        final FloatBuffer modelview = BufferUtils.createFloatBuffer(16);

        /**
         * Buffer that holds the transformation matrix of the projection
         */
        final FloatBuffer projection = BufferUtils.createFloatBuffer(16);

        /**
         * the return value of the gluProject call
         */
        final boolean ret;


        screen_coords.clear();
        modelview.clear();
        projection.clear();

        viewport.clear();

        GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelview);
        GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projection);
        GL11.glGetInteger(GL11.GL_VIEWPORT, viewport);

        ret = GLU.gluProject(x, y, z, modelview, projection, viewport, screen_coords);

        if(ret) {
            return new Vec2(screen_coords.get(0) / Helper.getScale(), Helper.getHeight() - screen_coords.get(1) / Helper.getScale());
        }

        return null;
    }

    public static float[] faceEntity(final Vec3 entity, final Vec3 p) {
        final double x = entity.x() - p.x();
        final double z = entity.z() - p.z();
        double y = 0;

        final double helper = Math.sqrt(x * x + z * z);

        float newYaw = (float) Math.toDegrees(-Math.atan(x / z));
        final float newPitch = (float) -Math.toDegrees(Math.atan(y / helper));

        if(z < 0 && x < 0) {
            newYaw = (float) (90D + Math.toDegrees(Math.atan(z / x)));
        } else if(z < 0 && x > 0) {
            newYaw = (float) (-90D + Math.toDegrees(Math.atan(z / x)));
        }

        return new float[] {newYaw, newPitch};
    }
}

