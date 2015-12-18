package me.curlpipesh.pipe.gui.api.model.base.interfaces;

import me.curlpipesh.pipe.gui.api.model.Area;
import me.curlpipesh.pipe.gui.api.model.base.Container;
import me.curlpipesh.pipe.gui.api.model.base.Widget;
import me.curlpipesh.pipe.gui.api.model.base.controls.interfaces.IControl;
import me.curlpipesh.pipe.gui.api.model.base.filter.ComponentFilter;
import me.curlpipesh.pipe.gui.api.view.layout.ILayout;
import me.curlpipesh.pipe.gui.api.view.layout.arrangers.Arranger;
import me.curlpipesh.pipe.gui.api.view.layout.resizers.Resizer;

import java.util.List;

/**
 * A container that holds {@link Widget}s. A container may hold any number
 * of components, up to and including other containers. By default, a container
 * will do nothing but hold components; things such as minimizing, pinning to
 * the screen, scrolling, and so on must be provided either by subclasses,
 * {@link ILayout}s, or some
 * combination thereof.
 *
 * @author c
 * @since 08.18.2014
 * @see Widget
 */
@SuppressWarnings("unused")
public interface IContainer extends IWidget {
    /**
     * Returns a {@link List} of all the {@link Widget}s that are the
     * children of this container.
     *
     * @return A List of child components.
     */
    List<IWidget> getChildren();

    /**
     * Resizes the {@link IContainer}
     * to fit its children. Ideally, this method would find the child at the
     * bottom-right-most corner, then use those bounds to resize itself.
     * This would work because the screen is laid out like so:
     * (0,0,0)--------------(x,0,0)
     *    |                    |
     *    |                    |
     *    |                    |
     * (0,y,0)--------------(x,y,0)
     * essentially a Cartesian coordinate plane with an inverted y-axis.
     */
    void resize();

    /**
     * Adds a child {@link Widget} to this {@link Container}. If the child
     * is {@code null} or does not meet all the conditions set by this
     * container's filters, it will be rejected, or not added to the
     * {@link List} of children.
     *
     * @param child The component to be added.
     */
    void addChild(IWidget child);

    /**
     * Removes a child {@link Widget} to this {@link Container}. If the child
     * is {@code null}, it will be rejected.
     *
     * @param child The component to be removed.
     */
    void removeChild(IWidget child);

    /**
     * Adds a filter to the list of component filters.
     *
     * @param filter The filter to be added. This may not be {@code null}.
     */
    void addFilter(ComponentFilter filter);

    /**
     * Removes a filter from the list of component filters.
     *
     * @param filter The filter to be removed. This may not be {@code null}.
     */
    void removeFilter(ComponentFilter filter);

    /**
     * This method is not generated with {@code Lombok} for one reason -
     * because {@link ILayout}s need
     * to be {@code initialize}d prior to being used, else their
     * {@link Arranger}s
     * and {@link Resizer}s
     * are not added.
     *
     * @param layout The layout to be changed to.
     */
    void setLayout(ILayout layout);

    /**
     * Returns an array of
     * {@link IControl}s
     * that represent the controls that this container has. While the internal
     * representation of this array may be a {@link List}, the return
     * value is an array because there is no need for an external class to have
     * direct access to the list. The main reason for even exposing this much
     * is entirely for rendering; things such as checking for control clicks
     * should be handled internally by the container using various
     * {@link me.curlpipesh.pipe.gui.api.util.IAction}s.
     *
     * @return An array representing the controls that this container has.
     */
    IControl[] getControls();

    /**
     * Returns whether the container is pinnable. This is should be determined
     * by querying the tags stored in each control to check if a control with
     * the "pinnable" type exists.
      *
     * @return Whether the container is pinnable.
     */
    boolean isPinnable();

    /**
     * Returns whether the container is minimizable. This is should be determined
     * by querying the tags stored in each control to check if a control with
     * the "minimizable" type exists.
     *
     * @return Whether the container is minimizable.
     */
    boolean isMinimizable();

    /**
     * Returns whether the container is closeable. This is should be determined
     * by querying the tags stored in each control to check if a control with
     * the "closeable" type exists.
     *
     * @return Whether the container is closeable.
     */
    boolean isCloseable();

    /**
     * Returns an {@link Area} that
     * represents the area taken up by the container's title. This area is used
     * for things such as control placement, window dragging, and so on.
     *
     * @return The area that represents the container's title.
     */
    Area getTitleArea();

    /**
     * Returns the control responsible for minimizing the container. Only one
     * such control is allowed to exist.
     *
     * @return The control responsible for minimizing the container.
     */
    IControl getMinimizeControl();

    /**
     * Returns the control responsible for pinning the container. Only one
     * such control is allowed to exist.
     *
     * @return The control responsible for pinning the container.
     */
    IControl getPinControl();

    /**
     * Returns the control responsible for closing the container. Only one
     * such control is allowed to exist.
     *
     * @return The control responsible for closing the container.
     */
    IControl getCloseControl();

    /**
     * Returns the area in which components are allowed to be placed.
     *
     * @return The area in which components are allowed to be placed.
     */
    Area getComponentArea();
}
