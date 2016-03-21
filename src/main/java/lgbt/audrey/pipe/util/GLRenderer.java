package lgbt.audrey.pipe.util;

import lgbt.audrey.pipe.util.helpers.Helper;
import me.curlpipesh.gl.tessellation.Tessellator;
import me.curlpipesh.gl.tessellation.impl.VAOTessellator;
import org.lwjgl.opengl.GL11;

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
public final class GLRenderer {
    /**
     * The {@link Tessellator} to be used for rendering. By default, this is a
     * {@link VAOTessellator}.
     */
    private static final Tessellator tess = new VAOTessellator(16777216);

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
    /*public float[] worldToScreen(Object entity, float ySum){

        EntityClientPlayerMP me = mc.thePlayer;
        double meX = me.lastTickPosX + (me.posX - me.lastTickPosX) * partialTickTime;
        double meY = me.lastTickPosY + (me.posY - me.lastTickPosY) * partialTickTime;
        double meZ = me.lastTickPosZ + (me.posZ - me.lastTickPosZ) * partialTickTime;
        double pitch = ((me.rotationPitch + 90) * Math.PI) / 180;
        double yaw = ((me.rotationYaw + 90) * Math.PI) / 180;
        // direction the player is facing
        Vec3 lookDir = Vec3.createVectorHelper(Math.sin(pitch) * Math.cos(yaw), Math.cos(pitch), Math.sin(pitch) * Math.sin(yaw));
        if (mc.gameSettings.thirdPersonView == 2){
            // reversed 3rd-person view; flip the look direction
            lookDir.xCoord *= -1;
            lookDir.yCoord *= -1;
            lookDir.zCoord *= -1;
        }

        IntBuffer viewport = BufferUtils.createIntBuffer(16);
        GL11.glGetInteger(GL11.GL_VIEWPORT, viewport);

        ScaledResolution res = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        int width = res.getScaledWidth();
        int height = res.getScaledHeight();


        double entityX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTickTime;
        double entityY = (entity.lastTickPosY + ySum) + ((entity.posY + ySum) - (entity.lastTickPosY + ySum)) * partialTickTime;
        double entityZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTickTime;
        // direction to target entity
        Vec3 toEntity = Vec3.createVectorHelper(entityX - meX, entityY - meY, entityZ - meZ);
        float x = (float)toEntity.xCoord;
        float y = (float)toEntity.yCoord;
        float z = (float)toEntity.zCoord;
        double dist = toEntity.lengthVector();
        toEntity = toEntity.normalize();

        if (lookDir.dotProduct(toEntity) <= 0.02){
            // angle between vectors is greater than about 89 degrees, so
            // create a dummy target location that is 89 degrees away from look direction
            // along the arc between look direction and direction to target entity
            final double angle = 89.0 * pi / 180;
            final double sin = Math.sin(angle);
            final double cos = Math.cos(angle);
            Vec3 ortho = lookDir.crossProduct(toEntity); // vector orthogonal to look direction and direction to target entity
            double ox = ortho.xCoord;
            double oy = ortho.yCoord;
            double oz = ortho.zCoord;
            // build a rotation matrix to rotate around a vector (ortho) by an angle (89 degrees)
            // from http://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle
            double m00 = cos + ox*ox*(1-cos);
            double m01 = ox*oy*(1-cos) - oz*sin;
            double m02 = ox*oz*(1-cos) + oy*sin;
            double m10 = oy*ox*(1-cos) + oz*sin;
            double m11 = cos + oy*oy*(1-cos);
            double m12 = oy*oz*(1-cos) - ox*sin;
            double m20 = oz*ox*(1-cos) - oy*sin;
            double m21 = oz*oy*(1-cos) + ox*sin;
            double m22 = cos + oz*oz*(1-cos);
            // transform (multiply) look direction vector with rotation matrix and scale by distance to target entity;
            // this produces the coordinates for the dummy target
            x = (float)(dist * (m00*lookDir.xCoord + m01*lookDir.yCoord + m02*lookDir.zCoord));
            y = (float)(dist * (m10*lookDir.xCoord + m11*lookDir.yCoord + m12*lookDir.zCoord));
            z = (float)(dist * (m20*lookDir.xCoord + m21*lookDir.yCoord + m22*lookDir.zCoord));
        }

        FloatBuffer screenCoords = BufferUtils.createFloatBuffer(3);
        modelMatrix.rewind();
        projMatrix.rewind();
        // map target's object coordinates into window coordinates
        // using world render transform matrices stored by StoreMatrices()
        GLU.gluProject(x, y, z, ActiveRenderInfo.modelview, ActiveRenderInfo.projection, ActiveRenderInfo.viewport, screenCoords);
        float hudX = screenCoords.get(0) / res.getScaleFactor();
        float hudY = height - screenCoords.get(1) / res.getScaleFactor();
        //System.out.println(hudX + " : " + hudY);
        return new float[] { hudX, hudY };
    }*/
}
