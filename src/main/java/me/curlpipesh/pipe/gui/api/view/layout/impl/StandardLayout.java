package me.curlpipesh.pipe.gui.api.view.layout.impl;

import me.curlpipesh.pipe.gui.api.model.Area;
import me.curlpipesh.pipe.gui.api.model.base.Container;
import me.curlpipesh.pipe.gui.api.model.base.interfaces.IWidget;
import me.curlpipesh.pipe.gui.api.view.layout.Layout;
import me.curlpipesh.pipe.util.helpers.Helper;

/**
 * The single most common type of layout used in Minecraft clients.
 *
 * @author c
 * @since 08.16.2014
 */
public class StandardLayout extends Layout {
    private int widgetWidth;
    private int widgetHeight;
    private int padding;

    public StandardLayout() {
        this(72);
    }

    public StandardLayout(int widgetWidth) {
        this(widgetWidth, 8);
    }

    public StandardLayout(int widgetWidth, int widgetHeight) {
        this(widgetWidth, widgetHeight, 2);
    }

    public StandardLayout(int widgetWidth, int widgetHeight, int padding) {
        this.widgetWidth = widgetWidth;
        this.widgetHeight = widgetHeight;
        this.padding = padding;
    }

    @Override
    public double getPadding() {
        return padding;
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
                double w = Helper.getStringWidth(e.getText()) + (getPadding() * 2) + (container.getControls().length * container.getControls()[0].getArea().getWidth()) + (getPadding() * container.getControls().length);
                double h = Helper.getFontHeight() + (getPadding() * 2);
                if(w > widgetWidth) {
                    widgetWidth = (int) w;
                }
                if(h > widgetHeight) {
                    widgetHeight = (int) h;
                }
            }
            for(IWidget e : components) {
                double h = widgetHeight;
                if(e instanceof Container) {
                    if(((Container) e).getMinimizeControl() != null) {
                        h = ((Container) e).getMinimizeControl().getState() ?
                                ((Container) e).getTitleArea().getHeight() :
                                ((Container) e).getTitleArea().getHeight() + ((Container) e).getComponentArea().getHeight();
                    }
                }
                e.setArea(new Area(container.getComponentArea().getX(), e.getArea().getY(),
                        widgetWidth > e.getArea().getWidth() ? widgetWidth : e.getArea().getWidth(), h));
            }
        });
    }
}
