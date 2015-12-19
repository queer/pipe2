package me.curlpipesh.pipe.gui.api.model.impl;

import lombok.Getter;
import lombok.Setter;
import me.curlpipesh.pipe.gui.api.controller.action.MouseDragAction;
import me.curlpipesh.pipe.gui.api.controller.action.MouseReleaseAction;
import me.curlpipesh.pipe.gui.api.controller.action.TickAction;
import me.curlpipesh.pipe.gui.api.model.base.Container;
import me.curlpipesh.pipe.gui.api.model.base.controls.Control;
import me.curlpipesh.pipe.gui.api.util.Tuple;
import me.curlpipesh.pipe.gui.api.view.layout.impl.StandardLayout;

import java.awt.*;

/**
 * An implementation of
 * {@link Container} that allows for
 * the container to be dragged.
 *
 * @author c
 * @since 08.18.2014
 */
public class BasicWindow extends Container {
    /**
     * Whether or not the window is being dragged by the mouse.
     */
    @Getter
    @Setter
    private boolean dragging = false;

    /**
     * X-coordinate of the mouse pointer when this {@code Window} was clicked.
     * Used to help determine how the window is dragged,
     */
    private double mouseX;
    /**
     * Y-coordinate of the mouse pointer when this {@code Window} was clicked.
     * Used to help determine how the window is dragged,
     */
    private double mouseY;
    /**
     * X-coordinate of the {@code Window} when this {@code Window} was clicked.
     * Used to help determine how the window is dragged,
     */
    private double prevX;
    /**
     * Y-coordinate of the {@code Window} when this {@code Window} was clicked.
     * Used to help determine how the window is dragged,
     */
    private double prevY;

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
    public BasicWindow(String text, Tuple<String, String>... tags) {
        super(text, tags);
        addTag("type", "window");
        addAction((TickAction<BasicWindow>) window -> {
            if(!isPinnable() && getPinControl().getState()) {
                getPinControl().setState(false);
            }
            if(isDragging()) {
                drag();
            }
        });
        addAction((MouseDragAction<BasicWindow>) (window, button) -> {
            if(isFocused()) {
                if(!isDragging()) {
                    // Lazy title check
                    if(getTitleArea().contains(calculateMouseLocation())) {
                        startDragging();
                    }
                }
            }
        });
        addAction((MouseReleaseAction<BasicWindow>) (component, button) -> {
            if(isDragging()) {
                stopDragging();
            }
        });
        setLayout(new StandardLayout());
        if(getTagValue("closeable").equals("true")) {
            addControls(
                    new Control("pin"),
                    new Control("minimize"),
                    new Control("close")
            );
        } else {
            addControls(
                    new Control("pin"),
                    new Control("minimize")
            );
        }
    }

    /**
     * Starts dragging the container. When the container starts to be dragged,
     * the current mouse location is calculated, then the scaled mouse
     * coordinates and the pre-drag window coordinates of the window are
     * stored.
     */
    private void startDragging() {
        Point mousePosition = calculateMouseLocation();
        mouseX = mousePosition.getX();
        mouseY = mousePosition.getY();
        prevX = getArea().getX();
        prevY = getArea().getY();
        setDragging(true);
    }

    /**
     * Actually drags the container, using the various coordinates previously
     * saved to determine how to drag properly.
     */
    private void drag() {
        Point mousePosition = calculateMouseLocation();
        getArea().x = mousePosition.getX() - (mouseX - prevX);
        getArea().y = mousePosition.getY() - (mouseY - prevY);
        // Ensure that weird resizing stuff doesn't happen while moving the
        // window. Idfk why this works, but it does.
        resize();
        getLayout().resize(this, getChildren());
        getLayout().layout(this, getChildren());
    }

    /**
     * Stops dragging the container, and sets all previously used coordinates
     * to zero.
     */
    private void stopDragging() {
        setDragging(false);
        mouseX = mouseY = prevX = prevY = 0;
    }
}
