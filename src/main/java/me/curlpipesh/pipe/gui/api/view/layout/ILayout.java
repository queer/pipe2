package me.curlpipesh.pipe.gui.api.view.layout;

import me.curlpipesh.pipe.gui.api.model.base.Container;
import me.curlpipesh.pipe.gui.api.model.base.Widget;
import me.curlpipesh.pipe.gui.api.model.base.interfaces.IContainer;
import me.curlpipesh.pipe.gui.api.model.base.interfaces.IWidget;
import me.curlpipesh.pipe.gui.api.view.layout.arrangers.Arranger;
import me.curlpipesh.pipe.gui.api.view.layout.resizers.Resizer;

import java.util.List;

/**
 * The base of all component layouts. Implementations of this class are
 * expected to use
 * {@link Arranger}s and
 * {@link Resizer}s to
 * manipulate the locations and sizes of all the components in a given
 * {@link Container}.
 *
 * @author c
 * @since 08.16.2014
 */
public interface ILayout {
    /**
     * Adjusts the location of the given
     * {@link IWidget}s.
     *
     * @param container The container whose children are being sorted.
     * @param children The
     *                 {@link IWidget}s
     *                 to be arranged.
     */
    void layout(IContainer container, List<IWidget> children);

    /**
     * Adjusts the sizes of the given
     * {@link IWidget}s.
     *
     * @param container The container whose children are being sorted.
     * @param children The
     *                 {@link IWidget}s
     *                 to be resized.
     */
    void resize(IContainer container, List<IWidget> children);

    /**
     * Returns the amount of space that belongs between each
     * {@link Widget}
     *
     * @return An amount of space.
     */
    double getPadding();

    /**
     * Generic initialization method. However this is overridden by a subclass
     * is how it's used.
     */
    void initialize();
}
