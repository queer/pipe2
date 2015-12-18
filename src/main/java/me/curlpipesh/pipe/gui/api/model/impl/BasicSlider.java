package me.curlpipesh.pipe.gui.api.model.impl;

import lombok.Getter;
import me.curlpipesh.pipe.config.RangeOption;
import me.curlpipesh.pipe.gui.api.controller.action.MouseClickAction;
import me.curlpipesh.pipe.gui.api.controller.action.MouseDragAction;
import me.curlpipesh.pipe.gui.api.controller.action.MouseReleaseAction;
import me.curlpipesh.pipe.gui.api.util.Tuple;

/**
 * A simple slider that is usable for most tasks.
 *
 * @author c
 * @since 09.09.2014
 */
public class BasicSlider extends BasicWidget {
    @Getter
    private final RangeOption<Double> value;
    @Getter
    private double amountScrolled = 0.0D;
    private boolean dragging = false;

    @SafeVarargs
    public BasicSlider(final RangeOption<Double> value, final Tuple<String, String>... tags) {
        super("slider", value.name(), tags);
        this.value = value;
        amountScrolled = value.get() / value.getUpperLimit();

        this.addAction((MouseClickAction<BasicSlider>) (component, button) -> {
            getParent().getChildren().stream().filter(c -> c instanceof BasicSlider && !c.equals(component))
                    .sequential().forEach(w -> {
                w.setFocused(false);
                w.setState(false);
            });
            setFocused(true);
            setState(true);
            double mouseX = calculateMouseLocation().x;
            double sliderX = getArea().getX();
            if (mouseX > sliderX && getArea().contains(calculateMouseLocation())) {
                dragging = true;
                double diff = mouseX - sliderX;
                amountScrolled = diff / getArea().getWidth();
                amountScrolled = amountScrolled < 0 ? 0 : amountScrolled > 1 ? 1 : amountScrolled;
                set();
            }

        });
        this.addAction((MouseDragAction<BasicSlider>) (component, button) -> {
            double mouseX = calculateMouseLocation().x;
            double sliderX = getArea().getX();
            if (mouseX > sliderX && dragging) {
                double diff = mouseX - sliderX;
                amountScrolled = diff / getArea().getWidth();
                amountScrolled = amountScrolled < 0 ? 0 : amountScrolled > 1 ? 1 : amountScrolled;
                set();
            }

        });
        this.addAction((MouseReleaseAction<BasicSlider>) (component, button) -> {
            dragging = false;
            setFocused(false);
            setState(false);
        });
    }

    private void set() {
        if (!dragging) {
            return;
        }
        @SuppressWarnings("unused")
        final double incrementValue = value.getIncrement();
        final double calculatedValue = amountScrolled * (value.getUpperLimit() - value.getLowerLimit());

        if (((calculatedValue % incrementValue) != 0) && (incrementValue != -1)) {
            value.set(((calculatedValue) - ((calculatedValue) % incrementValue))
                    + value.getLowerLimit());
            value.set(((calculatedValue) - ((calculatedValue) % incrementValue))
                    + value.getLowerLimit());
        } else {
            value.set(calculatedValue + value.getLowerLimit());
            value.set(calculatedValue + value.getLowerLimit());
        }
    }
}
