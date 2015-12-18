package me.curlpipesh.pipe.gui.api.model.impl;

import me.curlpipesh.pipe.gui.api.model.base.Widget;
import me.curlpipesh.pipe.gui.api.util.Tuple;

/**
 * The simplest possible "implementation" of
 * {@link Widget}.
 *
 * @author c
 * @since 08.18.2014
 */
public class BasicWidget extends Widget {
    /**
     * Creates a new {@code Widget} with the given type, default text, and
     * tags.
     *
     * @param type The type of the {@code Widget}.
     * @param text The default text of the {@code Widget}.
     * @param tags The extra tags to add to the {@code Widget}.
     */
    @SafeVarargs
    public BasicWidget(String type, String text, Tuple<String, String>... tags) {
        super(type, text, tags);
    }

    @Override
    public void initialize() {
    }
}
