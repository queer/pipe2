package me.curlpipesh.pipe.gui.api.view.layout.arrangers;

import me.curlpipesh.pipe.gui.api.model.base.Container;
import me.curlpipesh.pipe.gui.api.model.base.Widget;
import me.curlpipesh.pipe.gui.api.model.base.interfaces.IContainer;
import me.curlpipesh.pipe.gui.api.model.base.interfaces.IWidget;
import me.curlpipesh.pipe.gui.api.view.layout.ILayout;

import java.util.List;

/**
 * A {@link FunctionalInterface} that is used to arrange the child
 * {@link Widget}s in a given
 * {@link Container}.
 *
 * @author c
 * @since 08.16.2014
 */
@FunctionalInterface
public interface Arranger {
    /**
     * Actually arranges the components.
     *
     * @param container The container whose children are being sorted
     * @param layout The current layout
     * @param components The children being sorted
     */
    void arrange(IContainer container, ILayout layout, List<IWidget> components);
}
