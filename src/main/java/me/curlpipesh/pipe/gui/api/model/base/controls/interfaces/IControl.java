package me.curlpipesh.pipe.gui.api.model.base.controls.interfaces;

import me.curlpipesh.pipe.gui.api.model.Area;
import me.curlpipesh.pipe.gui.api.model.base.Container;

/**
 * The 'skeleton' for "controls." Controls are, essentially, just the control
 * buttons on the titlebar of a
 * {@link Container}. A control must
 * contain a "tag table" (essentially a {@link java.util.Map} that uses
 * {@link String} for both the key and the value). With this tag
 * table, a control should store two things:
 * <ol>
 *     <li>Its type - can be one of "close," "pin," or "minimize."</li>
 *     <li>Its state - can be either "true" or "false."</li>
 * </ol>
 * The type of the control determines what it does; names should be
 * self-explanatory.
 * <p />
 * A control's state will, ideally, be stored as an
 * {@link Area}, since this is the default
 * for the rest of the components of the GUI, but this could be a
 * {@link java.awt.geom.Rectangle2D.Double}, since
 * {@link Area} is just a wrapper around that.
 *
 * @author c
 * @since 08.18.2014
 */
public interface IControl {
    /**
     * Returns the state of the control. Since the state should be internally
     * stored as a {@link String}, this method would end up having to
     * return something like
     * <code>
     *     Boolean.parseBoolean(getTagValue("state"));
     * </code>
     * or something similar.
     *
     * @return True if the control is active, false otherwise.
     */
    boolean getState();

    /**
     * Sets the state of the control. Since the state should be internally
     * stored as a {@link String}, and since the state can only be
     * {@code "true"} or {@code "false"} (not case-sensitive), the String being
     * passed in must be able to be parsed by
     * {@link Boolean#parseBoolean(String)}.
     *
     * @param state A {@link String} denoting the state of the
     *              control.
     */
    void setState(String state);

    /**
     * Sets the state of the control. Since the state should be internally
     * stored as a {@link String}, and since the state can only be
     * {@code "true"} or {@code "false"} (not case-sensitive), the value being
     * passed in will be parsed by {@link Boolean#toString(boolean)} in order
     * to convert it to a usable form.
     *
     * @param state A {@code boolean} denoting the state of the control.
     */
    void setState(boolean state);

    /**
     * Returns a representation of this {@code IControl} as a String. In
     * theory, this /could/ be used for serialization of things that controls
     * are attached to, but that should NOT be relied upon!
     *
     * @return A {@link String} that represents the {@code IControl}.
     */
    @Override
    String toString();

    /**
     * Returns the type of this control. The type is set once, ideally when the
     * control's {@code <init>} is run. Control type is determined by the
     * {@code "control-type"} tag, and nothing else.
     *
     * @return A {@link String} representing the control's type.
     */
    String getType();

    /**
     * Gets an {@link Area} that represents
     * the coordinate-space taken up by this {@code Control}.
     *
     * @return the {@link Area} that
     * represents the coordinate-space taken up by this {@code Control}.
     */
    Area getArea();

    /**
     * Sets the {@link Area} representing
     * this control's coordinate space.
     *
     * @param area The new {@code Area} that this control will use.
     */
    void setArea(Area area);
}
