package me.curlpipesh.pipe.gui.api.model.base;

import lombok.Getter;
import lombok.Setter;
import me.curlpipesh.pipe.gui.api.controller.action.*;
import me.curlpipesh.pipe.gui.api.controller.action.impl.ClickToggleAction;
import me.curlpipesh.pipe.gui.api.controller.registry.Taggable;
import me.curlpipesh.pipe.gui.api.model.Area;
import me.curlpipesh.pipe.gui.api.model.base.controls.interfaces.IControl;
import me.curlpipesh.pipe.gui.api.model.base.interfaces.IContainer;
import me.curlpipesh.pipe.gui.api.model.base.interfaces.IWidget;
import me.curlpipesh.pipe.gui.api.util.IAction;
import me.curlpipesh.pipe.gui.api.util.Tuple;
import me.curlpipesh.pipe.util.helpers.Helper;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Sample implementation of
 * {@link IWidget}. This is the most
 * common class that will be extended to create new widgets; while it is
 * possible to implement
 * {@link IWidget}, this is
 * discouraged because maintaining feature-parity with this implementation of
 * may be more work than expected.
 *
 * @author c
 * @since 08.16.2014
 */
@SuppressWarnings("unused")
public abstract class Widget extends Taggable implements IWidget {
    /**
     * The area of this widget.
     * {@link Area} is just a wrapper
     * around {@link java.awt.geom.Rectangle2D.Double}, so all the methods
     * available in that class are available here as well.
     * <p />
     */
    @Getter
    @Setter
    private Area area = new Area(0, 0, 0, 0);

    /**
     * A list of actions that this {@link Widget} has. The various actions
     * are invoked depending on what wrapper method is used to invoke; current
     * methods are {@link #tick()}, {@link #click(int)}, {@link #drag(int)},
     * {@link #release(int)}, and {@link #key(int, char)}, which are used with
     * {@link TickAction},
     * {@link MouseClickAction},
     * {@link MouseDragAction},
     * {@link MouseReleaseAction},
     * and {@link KeyAction},
     * respectively.
     * <p />
     */
    @Getter
    private final List<IAction> actions = new CopyOnWriteArrayList<>();

    /**
     * The name of this widget. This can be changed on-the-fly, and isn't
     * just a plain-old {@link String} so that it can be used in a
     * thread-safe manner without the end-user having to write a bunch of code
     * to make sure that it won't break.
     */
    private AtomicReference<String> text = new AtomicReference<>("DEFAULT_TEXT");

    /**
     * The {@link Container} that parents this widget. This should
     * <b>never</b> be {@code null}, unless the widget itself is a container,
     * and even then only if it is not nested inside of another container; only
     * top-level containers are allowed to have {@code null} as the value for
     * the value of this field.
     * <p />
     * TODO Make an exception be thrown if this isn't a top-level container
     */
    @Getter
    @Setter
    private IContainer parent = null;

    /**
     * Whether or not this {@code Widget} is <i>focused</i>. Being focused
     * means that the widget is allowed to receive mouse drag and release
     * "events" (more pseudo-events than anything, due to how the {@code Action}
     * system is set up). In general, components gain focus when they are
     * clicked and lose focus when the mouse is released.
     */
    @Getter
    @Setter
    private boolean focused = false;

    /**
     * Whether or not this {@code Widget} is visible. If a widget is visible,
     * it is allowed to be rendered, can have its actions called, and so on.
     * Visibility can be determined by a parent container, but can also be
     * changed by a top-level container's close
     * {@link IControl}.
     */
    @Getter
    @Setter
    private boolean visible = true;

    /**
     * Creates a new {@code Widget} with the given type, default text, and
     * tags.
     *
     * @param type The type of the {@code Widget}.
     * @param text The default text of the {@code Widget}.
     * @param tags The extra tags to add to the {@code Widget}.
     */
    @SafeVarargs
    public Widget(String type, String text, Tuple<String, String>... tags) {
        addTag("type", type);
        addTag("state", "false");
        setText(text);
        Arrays.stream(tags).forEach(tag -> addTag(tag.getKey(), tag.getValue()));
        // Adding stuff here is probably not a good idea, but initialize() is
        // used up by subclasses, and this helps avoid super-calls.
        addAction((MouseClickAction<? extends Widget>) (component, button) -> {
            if(button == 0) {
                component.setFocused(true);
            }
        });
        addAction((MouseReleaseAction<? extends Widget>) (component, button) -> {
            if(button == 0) {
                component.setFocused(false);
            }
        });
        if(type.equalsIgnoreCase("button")) {
            addAction(new ClickToggleAction());
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public final void tick() {
        if(!visible) {
            return;
        }
        actions.stream().filter(e -> e instanceof TickAction).forEach(e -> ((TickAction) e).tick(this));
    }

    @Override
    @SuppressWarnings("unchecked")
    public final void click(int button) {
        if(!visible) {
            return;
        }
        if(!isMouseOver()) {
            return;
        }
        actions.stream().filter(a -> a instanceof MouseClickAction).sequential()
                .forEach(t -> ((MouseClickAction) t).click(this, button));
    }

    @Override
    @SuppressWarnings("unchecked")
    public final void drag(int button) {
        if(!visible) {
            return;
        }
        if(!focused) {
            return;
        }
        actions.stream().filter(a -> a instanceof MouseDragAction).sequential()
                .forEach(t -> ((MouseDragAction) t).drag(this, button));
    }

    @Override
    @SuppressWarnings("unchecked")
    public final void release(int button) {
        if(!visible) {
            return;
        }
        if(!focused) {
            return;
        }
        actions.stream().filter(a -> a instanceof MouseReleaseAction).sequential()
                .forEach(t -> ((MouseReleaseAction) t).release(this, button));
    }

    @Override
    @SuppressWarnings("unchecked")
    public final void key(int keyCode, char keyChar) {
        if(!visible) {
            return;
        }
        if(!focused) {
            return;
        }
        actions.stream().filter(a -> a instanceof KeyAction).sequential()
                .forEach(a -> ((KeyAction)a).key(this, keyCode, keyChar));
    }

    @Override
    public final void addAction(IAction action) {
        if(action == null) {
            throw new IllegalArgumentException("Not allowed to add null actions to a widget!");
        }
        if (actions.contains(action)) {
            return;
        }
        actions.add(action);
    }

    @Override
    public final String getText() {
        return text.get();
    }

    @Override
    public final void setText(String text) {
        if(text == null) {
            throw new IllegalArgumentException("Not allowed to set a widget's text to null! Use \"\" instead.");
        }
        this.text.set(text);
    }

    /**
     * Returns a {@link Point} containing the current x- and
     * y-coordinates of the mouse cursor, scaled to work with Minecraft's GUI
     * scaling. Originally written by DarkStorm_.
     *
     * @return A {@link Point} containing x- and y-coordinates of the
     *         mouse cursor.
     */
    protected final Point calculateMouseLocation() {
        int scale = Helper.getScale();
        if (scale == 0) {
            scale = 1000;
        }
        int scaleFactor = 0;
        while ((scaleFactor < scale) && ((Helper.getWidth() / (scaleFactor + 1)) >= 320)
                && ((Helper.getHeight() / (scaleFactor + 1)) >= 240)) {
            scaleFactor++;
        }
        return new Point(Mouse.getX() / scaleFactor, (Helper.getHeight() / scaleFactor)
                - (Mouse.getY() / scaleFactor));
    }

    @Override
    public final boolean isMouseOver() {
        return getArea().contains(calculateMouseLocation()) && visible;
    }

    @Override
    public boolean isState() {
        return Boolean.parseBoolean(getTagValue("state"));
    }

    @Override
    public void setState(boolean state) {
        if(!focused) {
            return;
        }
        editTag("state", Boolean.toString(state));
    }
}
