package me.curlpipesh.pipe.gui.api.view.render.theme;

import me.curlpipesh.pipe.gui.api.model.base.Container;
import me.curlpipesh.pipe.gui.api.model.base.Widget;
import me.curlpipesh.pipe.gui.api.model.base.interfaces.IContainer;
import me.curlpipesh.pipe.gui.api.model.base.interfaces.IWidget;
import me.curlpipesh.pipe.gui.api.view.render.Renderer;
import me.curlpipesh.pipe.gui.api.view.render.state.RenderException;

/**
 * A {@code Theme} is essentially a "container," if you will, of
 * {@link Renderer}s that define how
 * various {@link Widget}s are
 * rendered. A {@code Theme} maintains an internal list of
 * {@link Renderer}s that it uses to
 * display various components on the screen. Any implementation of this must
 * define a way to store {@code Renderer}s, as well as a way to dynamically add
 * and remove them, and the ability to render {@code Widget}s, namely rendering
 * a standalone {@code Widget} as well as rendering a
 * {@link Container} and its children.
 *
 * @author c
 * @since 08.16.2014
 */
public interface ITheme {
    /**
     * Renders a {@link Widget}. If
     * the {@code Widget} is a
     * {@link Container}, then
     * {@link #renderContainer(IContainer)}
     * should be called, rather than writing multiple implementations thereof.
     * Ideally, implementations of this class would store a
     * {@link java.util.Map} of type-tag keys and
     * {@link Renderer} values, thus
     * making it easy to find the correct renderer for a given
     * {@link Widget}.
     *
     * @param widget The {@link Widget}
     *               to be rendered.
     * @param <T> The specific type of
     *            {@link Widget}.
     * @throws RenderException If the
     *                         {@link Widget}
     *                         fails to render.
     */
    <T extends IWidget> void render(T widget) throws RenderException;

    /**
     * Renders a {@link Container}.
     * The ideal process to follow looks something like this:
     * <pre>
     *     * Draw the {@code Container} using the renderer registered for it
     *     * glPushAttrib(GL_ALL_ATTRIB_BITS); // Or whatever it's called
     *     * glPushMatrix();
     *     * glEnable(GL_SCISSOR);
     *     * glScissor(containerArea);
     *     * container.getChildren().stream().forEach(this::render);
     *     * glDisable(GL_SCISSOR);
     *     * glPopMatrix();
     *     * glPopAttrib();
     * </pre>
     * If the container has subcontainers in it,
     * {@link #render(IWidget)}
     * should just invoke this method on it, so that all nested containers and
     * their respective children get rendered recursively.
     *
     * @param container The
     *                  {@link Container}
     *                  to be rendered.
     * @param <T> The specific type of
     *            {@link Container}.
     * @throws RenderException If the
     *                         {@link Container}
     *                         fails to render.
     */
    <T extends IContainer> void renderContainer(T container) throws RenderException;

    /**
     * Registers a {@link Renderer}
     * to be used to render a component with a "type" tag that has the same
     * value as the {@code renderTypeTag} parameter to this method. Only one
     * {@code Renderer} should be allowed to be rendered per type tag, and,
     * ideally, the user would be made aware of this, either through an
     * exception being thrown, {@code System.out.println}, or something
     * similar.
     *
     * @param rendererTypeTag The type tag that the renderer is mapped to.
     * @param renderer The renderer to register.
     * @throws IllegalArgumentException If the renderer is already registered,
     *                                  or if the type tag already has a
     *                                  renderer registered for it.
     */
    void registerRenderer(String rendererTypeTag, Renderer<? extends IWidget> renderer) throws IllegalArgumentException;

    /**
     * Returns the name of this theme. The name <b>must</b> be immutable, and
     * must be set in the implementation of this class once - when the class is
     * instantiated. Reasons for this are:
     * <ol>
     *     <li>Config saving - If the name changes, one has to have an
     *     alternative way of referencing themes.</li>
     *     <li>Readability - It forces one to make it obvious which theme is
     *     which.</li>
     * </ol>
     * All that this method should return is a name; "Dark" for example. This
     * method is <b>not</b> for giving the user a description thereof; one has
     * to make a custom implementation of this class to provide such
     * functionality.
     *
     * @return The name of this theme.
     */
    String getName();
}
