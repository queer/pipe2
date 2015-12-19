package me.curlpipesh.pipe.gui.api.model.impl;

import lombok.Getter;
import me.curlpipesh.pipe.config.RangeOption;
import me.curlpipesh.pipe.gui.api.controller.action.MouseClickAction;
import me.curlpipesh.pipe.gui.api.controller.action.MouseDragAction;
import me.curlpipesh.pipe.gui.api.controller.action.MouseReleaseAction;
import me.curlpipesh.pipe.gui.api.controller.action.TickAction;
import me.curlpipesh.pipe.gui.api.util.Tuple;

/**
 * A simple slider that is usable for most tasks.
 *
 * @author c
 * @since 09.09.2014
 */
public class BasicSlider extends BasicWidget {
    @Getter
    private final RangeOption<?> value;
    @Getter
    private double amountScrolled = 0.0D;
    private boolean dragging = false;

    @SafeVarargs
    public BasicSlider(final RangeOption<?> value, final Tuple<String, String>... tags) {
        super("slider", value.name(), tags);
        this.value = value;
        amountScrolled = value.get().doubleValue() / value.getUpperLimit().doubleValue();
        this.addAction((TickAction<BasicSlider>) component -> {
            if(value.getNumberType().equals(Double.class) || value.getNumberType().equals(Float.class)) {
                setText(String.format("%s: %.2f", value.name(), value.get().floatValue()));
            } else {
                setText(String.format("%s: %d", value.name(), value.get().longValue()));
            }
        });
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
            if(mouseX > sliderX && getArea().contains(calculateMouseLocation())) {
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
            if(mouseX > sliderX && dragging) {
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

    @SuppressWarnings("unchecked")
    private void set() {
        if(!dragging) {
            return;
        }
        // ;___;
        if(value.getNumberType().equals(Double.class) || value.getNumberType().equals(Float.class)) {
            final double incrementValue = value.getIncrement().doubleValue();
            final double calculatedValue = amountScrolled * (value.getUpperLimit().doubleValue() - value.getLowerLimit().doubleValue());

            if(((calculatedValue % incrementValue) != 0) && (incrementValue != -1)) {
                if(value.getNumberType().equals(Float.class)) {
                    ((RangeOption<Float>) value).set((float) (((calculatedValue) - ((calculatedValue) % incrementValue))
                            + value.getLowerLimit().floatValue()));
                } else {
                    ((RangeOption<Double>) value).set(((calculatedValue) - ((calculatedValue) % incrementValue))
                            + value.getLowerLimit().doubleValue());
                }
            } else {
                if(value.getNumberType().equals(Float.class)) {
                    ((RangeOption<Float>) value).set((float) (calculatedValue + value.getLowerLimit().floatValue()));
                } else {
                    ((RangeOption<Double>) value).set(calculatedValue + value.getLowerLimit().doubleValue());
                }
            }
        } else {
            final long incrementValue = value.getIncrement().longValue();
            final double calculatedValue = amountScrolled * (value.getUpperLimit().longValue() - value.getLowerLimit().longValue());

            if(((calculatedValue % incrementValue) != 0) && (incrementValue != -1)) {
                if(value.getNumberType().equals(Long.class)) {
                    ((RangeOption<Long>) value).set((long) (((calculatedValue) - ((calculatedValue) % incrementValue))
                            + value.getLowerLimit().longValue()));
                } else if(value.getNumberType().equals(Integer.class)) {
                    ((RangeOption<Integer>) value).set((int) (((calculatedValue) - ((calculatedValue) % incrementValue))
                            + value.getLowerLimit().intValue()));
                } else if(value.getNumberType().equals(Short.class)) {
                    ((RangeOption<Short>) value).set((short) (((calculatedValue) - ((calculatedValue) % incrementValue))
                            + value.getLowerLimit().shortValue()));
                } else if(value.getNumberType().equals(Byte.class)) {
                    ((RangeOption<Byte>) value).set((byte) (((calculatedValue) - ((calculatedValue) % incrementValue))
                            + value.getLowerLimit().byteValue()));
                }
            } else {
                if(value.getNumberType().equals(Long.class)) {
                    ((RangeOption<Long>) value).set((long) (calculatedValue + value.getLowerLimit().longValue()));
                } else if(value.getNumberType().equals(Integer.class)) {
                    ((RangeOption<Integer>) value).set((int) (calculatedValue + value.getLowerLimit().intValue()));
                } else if(value.getNumberType().equals(Short.class)) {
                    ((RangeOption<Short>) value).set((short) (calculatedValue + value.getLowerLimit().shortValue()));
                } else if(value.getNumberType().equals(Byte.class)) {
                    ((RangeOption<Byte>) value).set((byte) (calculatedValue + value.getLowerLimit().byteValue()));
                }
            }
        }
    }
}
