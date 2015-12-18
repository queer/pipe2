package me.curlpipesh.pipe.gui.api.view.layout.impl;

import me.curlpipesh.pipe.gui.api.model.Area;
import me.curlpipesh.pipe.gui.api.model.base.interfaces.IWidget;
import me.curlpipesh.pipe.gui.api.view.layout.Layout;

/**
 * Simple layout that places {@code Widget}s into two columns.
 *
 * @author c
 * @since 08.17.2014
 */
public class TwoColumnLayout extends Layout {
    @Override
    public double getPadding() {
        return 4; // Chosen by dice roll. Guaranteed to be random.
    }

    @Override
    public void initialize() {
        final double WIDTH = 72;
        final double HEIGHT = 8;
        setResizer((container, layout, components) -> {
            for(IWidget e : components) {
                e.setArea(new Area(container.getComponentArea().getX(), e.getArea().getY(), WIDTH, HEIGHT));
            }
        });
        setArranger((container, layout, components) -> {
            double lastY = container.getComponentArea().getY() + layout.getPadding();
            int index = 0;
            for(IWidget e : components) {
                double offsetX = index % 2 != 0 ? WIDTH + getPadding() : 0;
                e.getArea().x = container.getComponentArea().x + layout.getPadding() + offsetX;
                e.getArea().y = lastY;
                lastY = (offsetX == 0 ? e.getArea().getMinY() : e.getArea().getMaxY() + layout.getPadding());
                ++index;
            }
        });
    }
}
