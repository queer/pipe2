package me.curlpipesh.pipe.gui.api.view.render.state;

import me.curlpipesh.pipe.gui.api.model.base.Widget;
import me.curlpipesh.pipe.gui.api.view.render.Renderer;

/**
 * A {@code RenderException} is thrown by a
 * {@link Renderer} that encounters
 * some sort of problem while rendering a
 * {@link Widget}.
 *
 * @author c
 * @since 08.17.2014
 */
public class RenderException extends Exception {
    /**
     * Instantiates a {@link RenderException} with a generic error message.
     */
    public RenderException() {
        super("An unknown error was encountered!");
    }

    /**
     * Instantiates a {@link RenderException} with the given error message.
     *
     * @param string The error message.
     */
    public RenderException(String string) {
        super(string);
    }
}
