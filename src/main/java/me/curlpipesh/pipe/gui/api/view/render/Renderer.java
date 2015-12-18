package me.curlpipesh.pipe.gui.api.view.render;

import me.curlpipesh.pipe.gui.api.controller.registry.TagRegistry;
import me.curlpipesh.pipe.gui.api.model.base.Widget;
import me.curlpipesh.pipe.gui.api.model.base.interfaces.IWidget;
import me.curlpipesh.pipe.gui.api.view.render.state.RenderException;

/**
 * A {@code Renderer} defines how a specific widget is rendered. In themes,
 * {@code Renderer}s are mapped to a String that defines what type of widget
 * that they control the rendering process for; the String that a renderer is
 * mapped to <b>must</b> be a valid type, as defined by
 * {@link TagRegistry}. If
 * the value that this {@code Renderer} is mapped to is invalid, an
 * {@link IllegalArgumentException} will be thrown.
 *
 * @param <T> The type of widget being rendered.
 * @author c
 * @since 08.17.2014
 */
public interface Renderer<T extends IWidget> {
    /**
     * Tries to render the given
     * {@link Widget}, throwing an
     * {@link RenderException}
     * if it fails to render for some reason. Ideally, this would provide as
     * much information about the render process as possible, but this may not
     * always be possible, so it relies on the exception being thrown with a
     * useful error message.
     *
     * @param widget The
     *               {@link Widget} to
     *               be rendered.
     * @throws RenderException If the rendering fails.
     */
    void render(T widget) throws RenderException;
}
