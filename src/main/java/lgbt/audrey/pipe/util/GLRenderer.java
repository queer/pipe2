package lgbt.audrey.pipe.util;

import lgbt.audrey.pipe.util.helpers.Helper;
import me.curlpipesh.gl.tessellation.Tessellator;
import me.curlpipesh.gl.tessellation.impl.VAOTessellator;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_MULTISAMPLE;
import static org.lwjgl.opengl.GL13.GL_SAMPLE_ALPHA_TO_COVERAGE;

/**
 * Used for all rendering with OpenGL, save for a few calls into Minecraft's
 * methods.
 *
 * @author c
 * @since 4/30/15
 */
@SuppressWarnings("unused")
public final class GLRenderer {
    /**
     * The {@link Tessellator} to be used for rendering. By default, this is a
     * {@link VAOTessellator}.
     */
    private static final Tessellator tess = new VAOTessellator(16777216);

    private static final FloatBuffer modelview = BufferUtils.createFloatBuffer(16);
    private static final FloatBuffer projection = BufferUtils.createFloatBuffer(16);
    private static final IntBuffer viewport = BufferUtils.createIntBuffer(16);

    private GLRenderer() {
    }

    /**
     * Does OpenGL cap enables/disables in preparation for rendering. If one is
     * going to be doing a large number of render calls, it is suggested that
     * render calls are created that do not call <tt>pre()</tt> and
     * {@link #post()} at the beginning and end of the method (respectively),
     * so as to not have an impact on render performance.
     */
    public static void pre() {
        glPushAttrib(GL_ALL_ATTRIB_BITS);
        glPushMatrix();
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glDisable(GL_TEXTURE_2D);
        glShadeModel(GL_SMOOTH);
        glDisable(GL_ALPHA_TEST);
        glEnable(GL_MULTISAMPLE);
        glEnable(GL_LINE_SMOOTH);
        glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
        glEnable(GL_POINT_SMOOTH);
        glHint(GL_POINT_SMOOTH_HINT, GL_NICEST);
        glEnable(GL_POLYGON_SMOOTH);
        glHint(GL_POLYGON_SMOOTH_HINT, GL_NICEST);
        glEnable(GL_SAMPLE_ALPHA_TO_COVERAGE);
        glDisable(GL_LIGHTING);
    }

    /**
     * Undoes all the OpenGL cap enables/disables done by {@link #pre()} If one
     * is going to be doing a large number of render calls, it is suggested
     * that render calls are created that do not call {@link #pre()} and
     * <tt>post()</tt> at the beginning and end of the method (respectively),
     * so as to not have an impact on render performance.
     */
    public static void post() {
        glEnable(GL_LIGHTING);
        glDisable(GL_SAMPLE_ALPHA_TO_COVERAGE);
        glDisable(GL_POLYGON_SMOOTH);
        glDisable(GL_POINT_SMOOTH);
        glDisable(GL_LINE_SMOOTH);
        glDisable(GL_MULTISAMPLE);
        glEnable(GL_ALPHA_TEST);
        glShadeModel(GL_FLAT);
        glEnable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);
        glPopMatrix();
        glPopAttrib();
    }

    /**
     * Renders a rectangle on the screen
     *
     * @param x X coordinate of the rectangle
     * @param y Y coordinate of the rectangle
     * @param w Width of the rectangle
     * @param h Height of the rectangle
     * @param c Color of the rectangle, in 0xAARRGGBB format
     */
    public static void drawRect(final double x, final double y, final double w, final double h, final int c) {
        glPushAttrib(GL_ALL_ATTRIB_BITS);
        glPushMatrix();
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glDisable(GL_TEXTURE_2D);
        glShadeModel(GL_SMOOTH);
        glDisable(GL_ALPHA_TEST);
        tess.startDrawing(GL_QUADS).color(c)
                .addVertex(x, y, 0)
                .addVertex(x, y + h, 0)
                .addVertex(x + w, y + h, 0)
                .addVertex(x + w, y, 0)
                .bindAndDraw();
        glEnable(GL_ALPHA_TEST);
        glShadeModel(GL_FLAT);
        glEnable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);
        glPopMatrix();
        glPopAttrib();
    }

    /**
     * Draws a rectangle with a vertical gradient, using <tt>c1</tt> at the top
     * and <tt>c2</tt> at the bottom.
     *
     * @param x  X coordinate of the rectangle
     * @param y  Y coordinate of the rectangle
     * @param w  Width of the rectangle
     * @param h  Height of the rectangle
     * @param c1 Upper gradient color of the rectangle, in 0xAARRGGBB format
     * @param c2 Lower gradient color of the rectangle, in 0xAARRGGBB format
     */
    public static void drawGradientRect(final double x, final double y, final double w, final double h, final int c1, final int c2) {
        pre();
        tess.startDrawing(GL_QUADS).color(c1)
                .addVertex(x, y, 0)
                .addVertex(x + w, y, 0)
                .color(c2)
                .addVertex(x + w, y + h, 0)
                .addVertex(x, y + h, 0)
                .bindAndDraw();
        post();
    }

    /**
     * Draws a rectangle with a vertical gradient, using <tt>c1</tt> at the top
     * and <tt>c2</tt> at the bottom.
     *
     * @param x  X coordinate of the rectangle
     * @param y  Y coordinate of the rectangle
     * @param w  Width of the rectangle
     * @param h  Height of the rectangle
     * @param c1 Upper gradient color of the rectangle, in 0xAARRGGBB format
     * @param c2 Lower gradient color of the rectangle, in 0xAARRGGBB format
     */
    public static void drawSideGradientRect(final double x, final double y, final double w, final double h, final int c1, final int c2) {
        pre();
        tess.startDrawing(GL_QUADS).color(c1)
                .addVertex(x, y, 0)
                .addVertex(x, y + h, 0)
                .color(c2)
                .addVertex(x + w, y + h, 0)
                .addVertex(x + w, y, 0)
                .bindAndDraw();
        post();
    }

    /**
     * Renders a 3-dimensional line on the screen.
     *
     * @param a     The starting vector
     * @param b     The ending vector
     * @param color The color of the line, in 0xAARRGGBB format
     * @param size  The width of the line
     */
    public static void drawLine(final Vec3 a, final Vec3 b, final int color, final float size) {
        drawLine(a.x(), a.y(), a.z(), b.x(), b.y(), b.z(), color, size);
    }

    /**
     * Renders a 3-dimensional line on the screen
     *
     * @param x    Starting x-coordinate
     * @param y    Starting y-coordinate
     * @param z    Starting z-coordinate
     * @param xx   Ending x-coordinate
     * @param yy   Ending y-coordinate
     * @param zz   Ending z-coordinate
     * @param c    Color of the ine, in 0xAARRGGBB format
     * @param size The width of the line
     */
    public static void drawLine(final double x, final double y, final double z, final double xx, final double yy, final double zz, final int c, final float size) {
        glLineWidth(size);
        tess.startDrawing(GL_LINES).color(c)
                .addVertex(x, y, z)
                .addVertex(xx, yy, zz)
                .bindAndDraw();
        glLineWidth(1.0F);
    }

    /**
     * Draws a box from the supplied vectors
     *
     * @param min   Minimum vector of all three axes
     * @param max   Maximum vector of all three axes
     * @param color Color of the box, in 0xAARRGGBB format
     */
    public static void drawBoxFromPoints(final Vec3 min, final Vec3 max, final int color) {
        glDisable(GL_CULL_FACE);
        tess
                .startDrawing(GL_QUADS)
                .color(color)
                .addVertex((float) min.x(), (float) min.y(), (float) min.z())
                .addVertex((float) max.x(), (float) min.y(), (float) min.z())
                .addVertex((float) max.x(), (float) max.y(), (float) min.z())
                .addVertex((float) min.x(), (float) max.y(), (float) min.z())
                .bindAndDraw()

                .startDrawing(GL_QUADS)
                .color(color)
                .addVertex((float) min.x(), (float) min.y(), (float) min.z())
                .addVertex((float) min.x(), (float) min.y(), (float) max.z())
                .addVertex((float) min.x(), (float) max.y(), (float) max.z())
                .addVertex((float) min.x(), (float) max.y(), (float) min.z())
                .bindAndDraw()

                .startDrawing(GL_QUADS)
                .color(color)
                .addVertex((float) min.x(), (float) max.y(), (float) min.z())
                .addVertex((float) max.x(), (float) max.y(), (float) min.z())
                .addVertex((float) max.x(), (float) max.y(), (float) max.z())
                .addVertex((float) min.x(), (float) max.y(), (float) max.z())
                .bindAndDraw()

                .startDrawing(GL_QUADS)
                .color(color)
                .addVertex((float) max.x(), (float) min.y(), (float) min.z())
                .addVertex((float) max.x(), (float) max.y(), (float) min.z())
                .addVertex((float) max.x(), (float) max.y(), (float) max.z())
                .addVertex((float) max.x(), (float) min.y(), (float) max.z())
                .bindAndDraw()

                .startDrawing(GL_QUADS)
                .color(color)
                .addVertex((float) min.x(), (float) min.y(), (float) max.z())
                .addVertex((float) max.x(), (float) min.y(), (float) max.z())
                .addVertex((float) max.x(), (float) max.y(), (float) max.z())
                .addVertex((float) min.x(), (float) max.y(), (float) max.z())
                .bindAndDraw()

                .startDrawing(GL_QUADS)
                .color(color)
                .addVertex((float) min.x(), (float) min.y(), (float) min.z())
                .addVertex((float) max.x(), (float) min.y(), (float) min.z())
                .addVertex((float) max.x(), (float) min.y(), (float) max.z())
                .addVertex((float) min.x(), (float) min.y(), (float) max.z())
                .bindAndDraw();
        glEnable(GL_CULL_FACE);
    }

    /**
     * Applies a scaled {@link GL11#glScissor(int, int, int, int)} operation.
     *
     * @param x Starting x-coordinate of the scissor
     * @param y Starting y-coordinate of the scissor
     * @param w Width of the scissor
     * @param h Height of the scissor
     */
    public static void scissor(final double x, final double y, final double w, final double h) {
        final int factor = Helper.getScale();
        final int height = Helper.getHeight() / factor;

        final double x2 = x + w;
        final double y2 = y + h;

        glScissor((int) (x * factor), (int) ((height - y2) * factor),
                (int) ((x2 - x) * factor), (int) ((y2 - y) * factor));
    }

    /**
     * Draws a String with an "embossing" effect, using the provided offset and colors.
     *
     * @param s       The String to render
     * @param x       The x position to render at
     * @param y       The y position to render at
     * @param c       The main color of the String
     * @param c2      The "emboss" color of the String
     * @param eOffset The amount to offset the "emboss" effect by.
     */
    public static void drawEmbossedString(final String s, final float x, final float y, final int c, final int c2,
                                          final float eOffset) {
        Helper.drawString(s, x, y + eOffset, c2, false);
        Helper.drawString(s, x, y, c, false);
    }

    /*
     * Modified Copypasta from: https://github.com/lowell/SaferHUD/blob/master/src/main/java/com/zyin/zyinhud/helper/HUDEntityTrackerHelper.java
     */
    @SuppressWarnings("ConstantConditions")
    public static float[] worldToScreen(final Object entity, final float ySum, final float partialTickTime) {
        final Vec3 lastPlayerPos = Helper.getEntityPrevVec(Helper.getPlayer()).clone();
        final Vec3 playerPos = Helper.getEntityVec(Helper.getPlayer()).clone();
        final Vec2 pRot = Helper.getEntityRotation(Helper.getPlayer()).clone();
        final Vec3 lastEntityPos = Helper.getEntityPrevVec(entity).clone();
        final Vec3 entityPos = Helper.getEntityVec(entity).clone();
        final Vec2 eRot = Helper.getEntityRotation(entity).clone();

        final double meX = lastPlayerPos.x() + (playerPos.x() - lastPlayerPos.x()) * partialTickTime;
        final double meY = lastPlayerPos.y() + (playerPos.y() - lastPlayerPos.y()) * partialTickTime;
        final double meZ = lastPlayerPos.z() + (playerPos.z() - lastPlayerPos.z()) * partialTickTime;
        final double pitch = (pRot.x() + 90) * Math.PI / 180;
        final double yaw = (pRot.y() + 90) * Math.PI / 180;
        // direction the player is facing
        final Vec3 lookDir = new Vec3(Math.sin(pitch) * Math.cos(yaw), Math.cos(pitch), Math.sin(pitch) * Math.sin(yaw));
        //Vec3.createVectorHelper(Math.sin(pitch) * Math.cos(yaw), Math.cos(pitch), Math.sin(pitch) * Math.sin(yaw));
        /*if (mc.gameSettings.thirdPersonView == 2){
            // reversed 3rd-person view; flip the look direction
            lookDir.x() *= -1;
            lookDir.y() *= -1;
            lookDir.z() *= -1;
        }*/

        glGetFloat(GL_MODELVIEW_MATRIX, modelview);
        glGetFloat(GL_PROJECTION_MATRIX, projection);
        glGetInteger(GL_VIEWPORT, viewport);

        final int width = Helper.getWidth();
        final int height = Helper.getHeight();


        final double entityX = lastEntityPos.x() + (entityPos.x() - lastEntityPos.x()) * partialTickTime;
        final double entityY = lastEntityPos.y() + ySum + (entityPos.y() + ySum - (lastEntityPos.y() + ySum)) * partialTickTime;
        final double entityZ = lastEntityPos.z() + (entityPos.z() - lastEntityPos.z()) * partialTickTime;
        // direction to target entity
        //Vec3 toEntity = Vec3.createVectorHelper(entityX - meX, entityY - meY, entityZ - meZ);
        Vec3 toEntity = new Vec3(entityX - meX, entityY - meY, entityZ - meZ);
        float x = (float) toEntity.x();
        float y = (float) toEntity.y();
        float z = (float) toEntity.z();
        final double dist = toEntity.length();
        toEntity = toEntity.normalize();

        if(lookDir.dot(toEntity) <= 0.02) {
            // angle between vectors is greater than about 89 degrees, so
            // create a dummy target location that is 89 degrees away from look direction
            // along the arc between look direction and direction to target entity
            final double angle = 89.0 * Math.PI / 180;
            final double sin = Math.sin(angle);
            final double cos = Math.cos(angle);
            final Vec3 ortho = lookDir.cross(toEntity); // vector orthogonal to look direction and direction to target entity
            final double ox = ortho.x();
            final double oy = ortho.y();
            final double oz = ortho.z();
            // build a rotation matrix to rotate around a vector (ortho) by an angle (89 degrees)
            // from http://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle
            final double m00 = cos + ox * ox * (1 - cos);
            final double m01 = ox * oy * (1 - cos) - oz * sin;
            final double m02 = ox * oz * (1 - cos) + oy * sin;
            final double m10 = oy * ox * (1 - cos) + oz * sin;
            final double m11 = cos + oy * oy * (1 - cos);
            final double m12 = oy * oz * (1 - cos) - ox * sin;
            final double m20 = oz * ox * (1 - cos) - oy * sin;
            final double m21 = oz * oy * (1 - cos) + ox * sin;
            final double m22 = cos + oz * oz * (1 - cos);
            // transform (multiply) look direction vector with rotation matrix and scale by distance to target entity;
            // this produces the coordinates for the dummy target
            x = (float) (dist * (m00 * lookDir.x() + m01 * lookDir.y() + m02 * lookDir.z()));
            y = (float) (dist * (m10 * lookDir.x() + m11 * lookDir.y() + m12 * lookDir.z()));
            z = (float) (dist * (m20 * lookDir.x() + m21 * lookDir.y() + m22 * lookDir.z()));
        }

        final FloatBuffer screenCoords = BufferUtils.createFloatBuffer(3);
        //modelMatrix.rewind();
        //projMatrix.rewind();
        // map target's object coordinates into window coordinates
        // using world render transform matrices stored by StoreMatrices()
        GLU.gluProject(x, y, z, modelview, projection, viewport, screenCoords);
        final float hudX = screenCoords.get(0) / Helper.getScale();//res.getScaleFactor();
        final float hudY = height - screenCoords.get(1) / Helper.getScale();//res.getScaleFactor();
        //System.out.println(hudX + " : " + hudY);
        return new float[] {hudX, hudY};
    }
}
