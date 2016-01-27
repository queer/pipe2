package me.curlpipesh.pipe.config;

import me.curlpipesh.pipe.util.Toggleable;

/**
 * An extension of {@link BasicOption} that can be toggled between states
 * easily.
 *
 * @author c
 * @since 5/23/15
 */
public class ToggleOption extends BasicOption<Boolean> implements Toggleable {
    public ToggleOption(final String name, final Boolean defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    @Override
    public boolean isEnabled() {
        return get();
    }

    @Override
    public void setEnabled(final boolean e) {
        set(e);
    }


    @Override
    public void set(final String string) {
        set(Boolean.valueOf(string));
    }

    @Override
    public String toString() {
        return Boolean.toString(get());
    }
}
