package me.curlpipesh.pipe.gui.api.model.impl;

import me.curlpipesh.pipe.gui.api.model.base.Container;
import me.curlpipesh.pipe.gui.api.util.Tuple;
import me.curlpipesh.pipe.gui.api.view.layout.impl.StandardLayout;

/**
 * The simplest possible "implementation" of
 * {@link Container}.
 *
 * @author c
 * @since 08.18.2014
 */
public class BasicContainer extends Container {
    /**
     * Creates a new {@code Container} with the given default text, and
     * tags. Also sets the x- and y-coordinates to (0, 0) for "convenience";
     * special positioning of windows will have to be done manually upon
     * creation.
     *
     * @param text The default text of the {@code Container}.
     * @param tags The extra tags to add to the {@code Container}.
     */
    @SafeVarargs
    public BasicContainer(String text, Tuple<String, String>... tags) {
        super(text, tags);
        setLayout(new StandardLayout());
    }
}
