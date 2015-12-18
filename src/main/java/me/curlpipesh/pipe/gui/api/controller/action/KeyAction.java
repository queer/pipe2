package me.curlpipesh.pipe.gui.api.controller.action;

import me.curlpipesh.pipe.gui.api.model.base.Widget;
import me.curlpipesh.pipe.gui.api.model.base.interfaces.IWidget;
import me.curlpipesh.pipe.gui.api.util.IAction;

/**
 * An action that is called every time a key is pressed on the
 * {@link Widget} that it is
 * attached to.
 *
 * @param <T> The specific type of component.
 *
 * @author c
 * @since 08.16.2014
 */
public interface KeyAction<T extends IWidget> extends IAction<T> {
    /**
     * This method is run every tick, and the component that it is attached to
     * is passed in as an argument.
     *
     * @param component The component to be ticked.
     */
    void key(T component, int keyCode, char keyChar);
}
