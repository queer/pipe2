package me.curlpipesh.pipe.gui.api.view.layout.impl;

import me.curlpipesh.pipe.gui.api.model.Area;
import me.curlpipesh.pipe.gui.api.model.base.interfaces.IWidget;
import me.curlpipesh.pipe.gui.api.view.layout.Layout;

/**
 * The single most common type of layout used in Minecraft clients.
 *
 * @author c
 * @since 08.16.2014
 */
public class StandardLayout extends Layout {
    private final int widgetWidth, widgetHeight;

    public StandardLayout() {
        this(72);
    }

    public StandardLayout(int widgetWidth) {
        this(widgetWidth, 8);
    }

    public StandardLayout(int widgetWidth, int widgetHeight) {
        this.widgetWidth = widgetWidth;
        this.widgetHeight = widgetHeight;
    }

    @Override
    public double getPadding() {
        return 2;
    }

    @Override
    public final void initialize() {
        setArranger((container, layout, components) -> {
            double lastY = container.getComponentArea().getY() + layout.getPadding();
            for(IWidget e : components) {
                e.getArea().x = container.getComponentArea().x + layout.getPadding();
                e.getArea().y = lastY;
                lastY = e.getArea().getMaxY() + layout.getPadding();
            }
        });
        setResizer((container, layout, components) -> {
            for(IWidget e : components) {
                e.setArea(new Area(container.getComponentArea().getX(), e.getArea().getY(), widgetWidth, widgetHeight));
            }
        });
    }
}
