package me.curlpipesh.pipe.gui.api.model.base.controls;

import lombok.Getter;
import lombok.Setter;
import me.curlpipesh.pipe.gui.api.controller.registry.TagRegistry;
import me.curlpipesh.pipe.gui.api.controller.registry.Taggable;
import me.curlpipesh.pipe.gui.api.model.Area;
import me.curlpipesh.pipe.gui.api.model.base.controls.interfaces.IControl;
import me.curlpipesh.pipe.gui.api.view.layout.ILayout;

/**
 * The basic implementation of
 * {@link IControl}. Whilst
 * this class in and of itself is not very complicated, it allows for the basic
 * specifications laid down by {@code IControl} to be fulfilled completely;
 * this is accomplished through extension of
 * {@link Taggable}, which
 * allows for easy tag management.
 *
 * @author c
 * @since 08.19.2014
 */
public class Control extends Taggable implements IControl {
    /**
     * The area that this control takes up. A {@code Control}'s
     * {@link Area} is determined by the
     * container itself, as
     * {@link ILayout}s are only
     * meant to be used to control sizes/positions of a container's child
     * widgets, not the controls. Note that this area must always be set prior
     * to the container it is attached to is used, else a
     * {@link NullPointerException} will be thrown (for obvious
     * reasons).
     *
     * TODO Make layouts control control locations (Sideways windows? :O)?
     * TODO This would require messing with how the window resizes, though.
     * TODO Should do?
     */
    @Getter
    @Setter
    private Area area;

    /**
     * Creates a new {@code Control} with the given type. The type is first
     * validated by
     * {@link TagRegistry},
     * so any invalid type(s) being passed in here will likely cause the
     * program to crash through a {@link IllegalArgumentException}
     * being thrown.
     *
     * @param type The type of the control. Can be either "close," "pin," or
     *             "minimize."
     */
    public Control(String type) {
        if(!TagRegistry.validateTagValue("control-type", type)) {
            throw new IllegalArgumentException(String.format("%s is an invalid control type!", type));
        }
        // Probably shouldn't be doing this in the constructor, but meh.
        addTag("control-type", type);
        setState(false);
        setArea(new Area(0, 0, 0, 0));
    }

    @Override
    public boolean getState() {
        return Boolean.parseBoolean(getTagValue("state"));
    }

    @Override
    public void setState(String state) {
        if(getTags().containsKey("state")) {
            editTag("state", state);
        } else {
            addTag("state", state);
        }
    }

    @Override
    public void setState(boolean state) {
        setState(Boolean.toString(state));
    }

    @Override
    public String toString() {
        return String.format("control[\"type: %s\", \"state: %s\"]", getTagValue("control-type"), getTagValue("state"));
    }

    @Override
    public String getType() {
        return getTagValue("control-type");
    }
}
