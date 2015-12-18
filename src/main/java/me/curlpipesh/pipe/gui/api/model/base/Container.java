package me.curlpipesh.pipe.gui.api.model.base;

import lombok.Getter;
import lombok.Setter;
import me.curlpipesh.pipe.Pipe;
import me.curlpipesh.pipe.gui.api.controller.action.*;
import me.curlpipesh.pipe.gui.api.model.Area;
import me.curlpipesh.pipe.gui.api.model.base.controls.interfaces.IControl;
import me.curlpipesh.pipe.gui.api.model.base.filter.ComponentFilter;
import me.curlpipesh.pipe.gui.api.model.base.interfaces.IContainer;
import me.curlpipesh.pipe.gui.api.model.base.interfaces.IWidget;
import me.curlpipesh.pipe.gui.api.util.Tuple;
import me.curlpipesh.pipe.gui.api.view.layout.ILayout;
import me.curlpipesh.pipe.gui.api.view.render.theme.ITheme;
import me.curlpipesh.pipe.util.helpers.Helper;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A container that holds {@link Widget}s. A container may hold any number
 * of components, up to and including other containers. By default, a container
 * will do nothing but hold components; things such as minimizing, pinning to
 * the screen, scrolling, and so on must be provided either by subclasses,
 * {@link ILayout}s, or some
 * combination thereof. {@code Container}s maintain an internal
 * {@code List<IControl>} of controls, which may only be updated by a subclass
 * thereof; external access to the list is not permitted.
 *
 * @author c
 * @see Widget
 * @since 08.16.2014
 */
@SuppressWarnings("unused")
public abstract class Container extends Widget implements IContainer {
    /**
     * The current layout of components for this container. Currently defaults
     * to {@code null}, so ensure that this is set prior to any usage of the
     * container.
     *
     * @see {@link #setLayout(ILayout)}
     */
    @Getter
    private ILayout layout;

    /**
     * A {@link List} of
     * {@link ComponentFilter}s
     * that are used to determine whether a given {@link Widget} is allowed
     * to be added to this container. If this {@link List} is empty,
     * a component can be added no matter what. If filters have been added to
     * it, however, a given component must match <b>all</b> the conditions that
     * are enforced by the filters before it can be added to the container's
     * children. This list is a {@link CopyOnWriteArrayList}
     * so that it will be thread-safe.
     */
    @Getter
    private final List<ComponentFilter> filters = new CopyOnWriteArrayList<>();

    /**
     * A {@link List} of all the {@link Widget}s that are the
     * children of this container. Each child's {@link Widget#parent} is set
     * to be {@code this}, so that it is apparent what the child/parent
     * relationship is.
     */
    @Getter
    private final List<IWidget> children = new CopyOnWriteArrayList<>();

    /**
     * The area in which components are allowed to be placed.
     * Ideally, {@link ITheme}s
     * would target this {@link Area} with
     * {@code GL_SCISSOR_TEST}, so that components cannot exceed the bounds of
     * the container. Components should never be positioned outside of this
     * area.
     */
    @Getter
    @Setter
    private Area componentArea = new Area(0, 0, 0, 0);

    /**
     * The controls that this container has. This {@code List} is not exposed
     * to external classes (in accordance with the standards set down by
     * {@link IContainer#getControls()},
     * and therefore should never be touched.
     */
    private final List<IControl> controls = new CopyOnWriteArrayList<>();

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
    public Container(String text, Tuple<String, String>... tags) {
        super("container", text, tags);
        getArea().x = 0;
        getArea().y = 0;
    }

    int counter = 0;

    @Override
    public final void initialize() {
        addAction((TickAction<Container>) (component) -> {
            if(layout == null) {
                throw new IllegalStateException("The layout of a container may not be null!");
            }
            if(!isMinimizable() && getMinimizeControl().getState()) {
                getMinimizeControl().setState(false);
            }
            resize();
            layout.resize(this, getChildren());
            layout.layout(this, getChildren());
            children.parallelStream().sequential().forEach(IWidget::tick);
        });
        addAction((MouseClickAction<Container>) (component, button)
                -> children.parallelStream().sequential().forEach(c -> c.click(button)));
        addAction((MouseClickAction<Container>) (component, button) -> {
            if(button == 0) {
                if(getMinimizeControl() != null) {
                    if(getMinimizeControl().getArea().contains(calculateMouseLocation())) {
                        getMinimizeControl().setState(!getMinimizeControl().getState());
                    }
                }
                if(getPinControl() != null) {
                    if(getPinControl().getArea().contains(calculateMouseLocation())) {
                        getPinControl().setState(!getPinControl().getState());
                    }
                }
                if(getCloseControl() != null) {
                    if(getCloseControl().getArea().contains(calculateMouseLocation())) {
                        setVisible(false);
                    }
                }
            }
        });
        addAction((MouseDragAction<Container>) (component, button)
                -> children.parallelStream().sequential().forEach(c -> c.drag(button)));
        addAction((MouseReleaseAction<Container>) (component, button)
                -> children.parallelStream().sequential().forEach(c -> c.release(button)));
        addAction((KeyAction<Container>) (component, keyCode, keyChar)
                -> children.parallelStream().sequential().forEach(c -> c.key(keyCode, keyChar)));
        layout.initialize();
    }

    @Override
    public void resize() {
        // 8 is to ensure that there is enough room for fonts and stuff.
        // TODO Make that 8 customizable!
        final double TITLE_SPACING = 8 + layout.getPadding();
        if(isMinimizable() && getMinimizeControl().getState()) {
            setArea(new Area(getArea().getX(), getArea().getY(), getArea().getWidth(), TITLE_SPACING));
            setComponentArea(new Area(0, 0, 0, 0));
        } else {
            double maxX = getArea().getX() + Helper.getStringWidth(getText()) + (getControls().length * getControls()[0].getArea().getWidth()) + (getControls().length * getLayout().getPadding()),
                    maxY = getArea().getY() + TITLE_SPACING;
            for(IWidget child : children) {
                if(child.getArea().getX() + child.getArea().getWidth() > maxX) {
                    maxX = child.getArea().getX() + child.getArea().getWidth();
                }
                if(child.getArea().getY() + child.getArea().getHeight() > maxY) {
                    maxY = child.getArea().getY() + child.getArea().getHeight();
                }
            }
            maxX += layout.getPadding();
            maxY += layout.getPadding();
            maxX -= getArea().getX();
            maxY -= getArea().getY();
            setArea(new Area(getArea().getX(), getArea().getY(), maxX, maxY));
            setComponentArea(new Area(getArea().getX(), getArea().getY() + TITLE_SPACING, maxX, maxY - TITLE_SPACING));
        }
        if(isMinimizable()) {
            double initialX = getArea().getX() + getArea().getWidth();
            for(IControl control : getControls()) {
                control.setArea(new Area(initialX - TITLE_SPACING, getArea().getY(), TITLE_SPACING, TITLE_SPACING));
                initialX -= TITLE_SPACING + 1;
            }
        } else {
            for(IControl control : getControls()) {
                control.setArea(new Area(0, 0, 0, 0));
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public final void addChild(IWidget child) {
        if(child == null) {
            return;
        }
        if(filters.size() == 0) {
            child.setParent(this);
            children.add(child);
            return;
        }
        for(ComponentFilter f : filters) {
            if(!f.filter(child)) {
                Pipe.getLogger().warning("Attempted to add a component, but it was filtered out!");
                return;
            }
        }
        child.setParent(this);
        children.add(child);
    }

    public final void removeChild(IWidget child) {
        if(child == null) {
            return;
        }
        children.remove(child);
    }

    @Override
    public void addFilter(ComponentFilter filter) {
        if(filter == null) {
            Pipe.getLogger().warning("Attempted to add a null filter!");
            return;
        }
        if(!filters.contains(filter)) {
            filters.add(filter);
        }
    }

    @Override
    public void removeFilter(ComponentFilter filter) {
        if(filter == null) {
            Pipe.getLogger().warning("Attempted to remove a null filter!");
            return;
        }
        if(filters.contains(filter)) {
            filters.remove(filter);
        }
    }

    @Override
    public void setLayout(ILayout layout) {
        this.layout = layout;
        this.layout.initialize();
    }

    @Override
    public IControl[] getControls() {
        return controls.toArray(new IControl[controls.size()]);
    }

    /**
     * Adds a new control to this container. If the given control is already in
     * the list of controls, it will be ignored, and a warning message will be
     * written to the console. Note that this is <b><u><i>NOT</i></u></b> a
     * standard part of the API! Any code written should not rely on this
     * method existing for major functional aspects, as it may disappear at any
     * time.
     *
     * @param control The control to be added.
     */
    protected void addControl(IControl control) {
        if(!controls.contains(control)) {
            if(controls.stream().filter(c -> c.getType().equals(control.getType())).toArray().length == 0) {
                controls.add(control);
            } else {
                System.out.println(String
                        .format("The given control type (%s) is not applicable in this context - it already exists!",
                                control.getType()));
            }
        } else {
            System.out.println(String
                    .format("The given control (%s) is not applicable in this context - it already exists!",
                            control.toString()));
        }
    }

    /**
     * Removes a control from this container. If the given control is not in
     * the list of controls, it will be ignored, and a warning message will be
     * written to the console. Note that this is <b><u><i>NOT</i></u></b> a
     * standard part of the API! Any code written should not rely on this
     * method existing for major functional aspects, as it may disappear at any
     * time.
     *
     * @param control The control to be removed.
     */
    protected void removeControl(IControl control) {
        if(!controls.contains(control)) {
            controls.add(control);
        } else {
            System.out.println(String
                    .format("The given control (%s) is not applicable in this context - it doesn't exist!",
                            control.toString()));
        }
    }

    /**
     * Adds multiple controls to this container at once by repeatedly invoking
     * {@link #addControl(IControl)}.
     * Note that this is <b><u><i>NOT</i></u></b> a standard part of the API!
     * Any code written should not rely on this method existing for major
     * functional aspects, as it may disappear at any time.
     *
     * @param controls The controls to be added.
     */
    protected void addControls(IControl... controls) {
        Arrays.stream(controls).forEach(this::addControl);
    }

    /**
     * Removes multiple controls to this container at once by repeatedly
     * invoking
     * {@link #removeControl(IControl)}.
     * Note that this is <b><u><i>NOT</i></u></b> a standard part of the API!
     * Any code written should not rely on this method existing for major
     * functional aspects, as it may disappear at any time.
     *
     * @param controls The controls to be added.
     */
    protected void removeControls(IControl... controls) {
        Arrays.stream(controls).forEach(this::removeControl);
    }

    @Override
    public final boolean isMinimizable() {
        return controls.stream().filter(c -> c.getType().equals("minimize")).toArray().length > 0;
    }

    @Override
    public final boolean isPinnable() {
        return controls.stream().filter(c -> c.getType().equals("minimize")).toArray().length > 0;
    }

    @Override
    public final boolean isCloseable() {
        return controls.stream().filter(c -> c.getType().equals("minimize")).toArray().length > 0;
    }

    @Override
    public Area getTitleArea() {
        return new Area(getArea().getX(), getArea().getY(),
                Area.diff(getArea().getWidth(), getControlTotalWidth()),
                Area.diff(getArea().getHeight(), getComponentArea().getHeight()));
    }

    /**
     * Returns a double that contains the total X-space taken up by the
     * controls on this container. Note that this method is
     * <b><u><i>NOT</i></u></b> a part of the standard API; it may be removed
     * at any time!
     *
     * @return a double that contains the total X-space taken up by the
     * controls on this container.
     */
    private double getControlTotalWidth() {
        double xSpace = 0;
        for(IControl control : controls) {
            xSpace += control.getArea().getWidth() + 1;
        }
        return xSpace;
    }

    @Override
    public IControl getMinimizeControl() {
        for(IControl control : getControls()) {
            if(control.getType().equals("minimize")) {
                return control;
            }
        }
        return null;
    }

    @Override
    public IControl getPinControl() {
        for(IControl control : getControls()) {
            if(control.getType().equals("pin")) {
                return control;
            }
        }
        return null;
    }

    @Override
    public IControl getCloseControl() {
        for(IControl control : getControls()) {
            if(control.getType().equals("close")) {
                return control;
            }
        }
        return null;
    }
}
