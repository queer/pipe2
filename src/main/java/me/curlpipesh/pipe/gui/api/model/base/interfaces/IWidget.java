package me.curlpipesh.pipe.gui.api.model.base.interfaces;

import me.curlpipesh.pipe.gui.api.controller.action.*;
import me.curlpipesh.pipe.gui.api.controller.registry.ITaggable;
import me.curlpipesh.pipe.gui.api.controller.registry.TagRegistry;
import me.curlpipesh.pipe.gui.api.model.Area;
import me.curlpipesh.pipe.gui.api.model.base.Container;
import me.curlpipesh.pipe.gui.api.model.base.Widget;
import me.curlpipesh.pipe.gui.api.util.IAction;

/**
 * The base widget class for the GUI. All widgets - including containers
 * and windows(!) - ultimately are just subclasses of this class. This class
 * contains everything needed to create a basic widget - such as a label or
 * a button - that can be created simply through defining various actions that
 * are invoked on the widget.
 *
 * @author c
 * @since 08.16.2014
 */
public interface IWidget extends ITaggable {
    /**
     * Runs all the
     * {@link TickAction}s that
     * have been registered with this {@link Widget}. Unless this widget
     * is itself a {@link Container} (And a top-level container at that!),
     * this method should <b>never</b> be invoked directly.
     */
    void tick();

    /**
     * Runs all the
     * {@link MouseClickAction}s
     * that have been registered with this {@link Widget}. Unless this
     * widget is itself a {@link Container} (And a top-level container at
     * that!), this method should <b>never</b> be invoked directly.
     *
     * @param button The mouse button that was pressed
     */
    void click(int button);

    /**
     * Runs all the
     * {@link MouseDragAction}s
     * that have been registered with this {@link Widget}. Unless this
     * widget is itself a {@link Container} (And a top-level container at
     * that!), this method should <b>never</b> be invoked directly.
     *
     * @param button The mouse button that was pressed
     */
    void drag(int button);

    /**
     * Runs all the
     * {@link MouseReleaseAction}s
     * that have been registered with this {@link Widget}. Unless this
     * widget is itself a {@link Container} (And a top-level container at
     * that!), this method should <b>never</b> be invoked directly.
     *
     * @param button The mouse button that was pressed
     */
    void release(int button);

    /**
     * Runs all the
     * {@link KeyAction}s that
     * have been registered with this {@link Widget}. Unless this widget
     * is itself a {@link Container} (And a top-level container at that!), this
     * method should <b>never</b> be invoked directly.
     *
     * @param keyCode The integer code that maps to the key that was pressed
     * @param keyChar The character that maps to the key that was pressed
     */
    void key(int keyCode, char keyChar);

    /**
     * Adds an action to the given widget, first checking whether the action
     * needs to be added by ensuring that it is not already in the list of
     * actions.
     *
     * @param action The action to be added. Not allowed to be null.
     */
    void addAction(IAction action);

    /**
     * Returns the current text of this Widget
     *
     * @return The String stored in the {@link java.util.concurrent.atomic.AtomicReference}.
     */
    String getText();

    /**
     * Sets the current text of this Widget
     *
     * @param text The String to be stored in the {@link java.util.concurrent.atomic.AtomicReference}.
     */
    void setText(String text);

    /**
     * Returns whether the mouse cursor is over this {@code Widget}.
     *
     * @return True if the mouse cursor is over this {@code Widget}, false
     *         otherwise.
     */
    boolean isMouseOver();

    /**
     * Returns whether the widget is visible. If a widget is visible, it is
     * allowed to be rendered, allowed to have its actions invoked, and so on.
     *
     * @return True if the widget is visible, false otherwise.
     */
    boolean isVisible();

    /**
     * Sets the visibility state of the widget.
     *
     * @param visible The new visibility state.
     */
    void setVisible(boolean visible);

    /**
     * Returns whether or not the widget is focused. If a widget is focused, it
     * is able to be acted upon by various actions.
     *
     * @return True of the widget is focused, false otherwise.
     */
    boolean isFocused();

    /**
     * Sets whether or not this widget is focused.
     *
     * @param focused The new state of focus for the widget.
     */
    void setFocused(boolean focused);

    /**
     * Returns an {@link Area} representing
     * the coordinate-space taken up by this {@code IWidget}.
     *
     * @return {@link Area}
     */
    Area getArea();

    /**
     * Sets the area of this {@code IWidget} to the given new
     * {@link Area}.
     *
     * @param area The new {@link Area} of
     *             this {@code IWidget}.
     */
    void setArea(Area area);

    /**
     * Returns the container that parents this {@code IWidget}. A widget's
     * parent may be null if and <i>only</i> if the following conditions are
     * met:
     * <ul>
     *     <li>The widget is a container</li>
     *     <li>The widget is not nested inside of a container</li>
     * </ul>
     *
     * @return The parent container of this widget, or null if applicable.
     */
    IContainer getParent();

    /**
     * Sets the parent of this {@code IWidget}. The new parent may be null if
     * and <i>only</i> if the following conditions are met:
     * <ul>
     *     <li>The widget is a container</li>
     *     <li>The widget is not nested inside of a container</li>
     * </ul>
     *
     * @param parent The new parent container for this widget, or null if
     *               applicable.
     */
    void setParent(IContainer parent);

    /**
     * Returns the state of this widget. State is different from focus - while
     * focus is used for determining status for actions, state is (more likely)
     * used for rendering. The state of a widget is initialized to false, and
     * changed only when necessary. Ideally, the state of a widget would not be
     * able to be changed without the widget having focus, as to prevent weird
     * things from happening. A widget's state may be stored in whatever manner
     * that one wishes, although using the "state" tag (as defined in
     * {@link TagRegistry})
     * would probably be for the best, as this is how
     * {@link Widget} (as well as its
     * subclasses) stores its state.
     *
     * @return The current state of the object.
     */
    boolean isState();

    /**
     * Changes the state of this widget. As mentioned in {@link #isState()},
     * this is ideally done only while the widget has focus, as to help
     * mitigate any strange bugs. This can be done as simply as
     * <code>
     *     if(!this.isState()) {
     *         return;
     *     }
     * </code>
     * but can be done in any way that is seen as fit for this purpose.
     *
     * @param state The new state of the widget.
     */
    void setState(boolean state);

    /**
     * Generic initialization method. However this is overridden by a subclass
     * is how it's used.
     */
    void initialize();
}
