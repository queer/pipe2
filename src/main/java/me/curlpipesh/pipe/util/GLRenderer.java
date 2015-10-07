package me.curlpipesh.pipe.util;

import me.curlpipesh.gl.tessellation.Tessellator;
import me.curlpipesh.gl.tessellation.impl.VAOTessellator;
import me.curlpipesh.pipe.util.helpers.Helper;
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
public class GLRenderer {
    /**
     * The {@link Tessellator} to be used for rendering. By default, this is a
     * {@link VAOTessellator}.
     */
    private static final Tessellator tess = new VAOTessellator(16777216);

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
    public static void drawRect(double x, double y, double w, double h, int c) {
        pre();
        tess.startDrawing(GL_QUADS).color(c)
                .addVertex(x, y, 0)
                .addVertex(x, y + h, 0)
                .addVertex(x + w, y + h, 0)
                .addVertex(x + w, y, 0)
                .bindAndDraw();
        post();
    }

    /**
     * Draws a rectangle with a vertical gradient, using <tt>c1</tt> at the top
     * and <tt>c2</tt> at the bottom.
     *
     * @param x X coordinate of the rectangle
     * @param y Y coordinate of the rectangle
     * @param w Width of the rectangle
     * @param h Height of the rectangle
     * @param c1 Upper gradient color of the rectangle, in 0xAARRGGBB format
     * @param c2 Lower gradient color of the rectangle, in 0xAARRGGBB format
     */
    public static void drawGradientRect(double x, double y, double w, double h, int c1, int c2) {
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
     * @param x X coordinate of the rectangle
     * @param y Y coordinate of the rectangle
     * @param w Width of the rectangle
     * @param h Height of the rectangle
     * @param c1 Upper gradient color of the rectangle, in 0xAARRGGBB format
     * @param c2 Lower gradient color of the rectangle, in 0xAARRGGBB format
     */
    public static void drawSideGradientRect(double x, double y, double w, double h, int c1, int c2) {
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
     * @param a The starting vector
     * @param b The ending vector
     * @param color The color of the line, in 0xAARRGGBB format
     * @param size The width of the line
     */
    public static void drawLine(Vec3 a, Vec3 b, int color, float size) {
        drawLine(a.x(), a.y(), a.z(), b.x(), b.y(), b.z(), color, size);
    }

    /**
     * Renders a 3-dimensional line on the screen
     *
     * @param x Starting x-coordinate
     * @param y Starting y-coordinate
     * @param z Starting z-coordinate
     * @param xx Ending x-coordinate
     * @param yy Ending y-coordinate
     * @param zz Ending z-coordinate
     * @param c Color of the ine, in 0xAARRGGBB format
     * @param size The width of the line
     */
    public static void drawLine(double x, double y, double z, double xx, double yy, double zz, int c, float size) {
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
     * @param min Minimum vector of all three axes
     * @param max Maximum vector of all three axes
     * @param color Color of the box, in 0xAARRGGBB format
     */
    public static void drawBoxFromPoints(Vec3 min, Vec3 max, int color) {
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
     * @param s The String to render
     * @param x The x position to render at
     * @param y The y position to render at
     * @param c The main color of the String
     * @param c2 The "emboss" color of the String
     * @param eOffset The amount to offset the "emboss" effect by.
     */
    public static void drawEmbossedString(String s, float x, float y, int c, int c2, float eOffset) {
        Helper.drawString(s, x, y + eOffset, c2, false);
        Helper.drawString(s, x, y, c, false);
    }
}
