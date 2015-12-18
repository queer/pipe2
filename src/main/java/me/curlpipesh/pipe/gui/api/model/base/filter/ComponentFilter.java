package me.curlpipesh.pipe.gui.api.model.base.filter;

import me.curlpipesh.pipe.gui.api.model.base.Container;
import me.curlpipesh.pipe.gui.api.model.base.Widget;
import me.curlpipesh.pipe.gui.api.model.base.interfaces.IWidget;

/**
 * A filter used to determine whether a given
 * {@link Widget} is allowed to be
 * added to a specific
 * {@link Container}
 */
@FunctionalInterface
public interface ComponentFilter {
    /**
     * Returns whether the given
     * {@link Widget} can be added
     * to the {@link Container} that
     * {@code this} is a filter for.
     *
     * @param widget The widget to test.
     * @return True if the widget can be added, false otherwise.
     */
    boolean filter(IWidget widget);
}
