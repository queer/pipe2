package me.curlpipesh.pipe.config;

import me.curlpipesh.pipe.util.Toggleable;

/**
 * An extension of {@link BooleanOption} that can be toggled between states
 * easily.
 *
 * @author c
 * @since 5/23/15
 */
public class ToggleOption extends BooleanOption implements Toggleable {
    public ToggleOption(String name, Boolean defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public void onEnable() {}

    @Override
    public void onDisable() {}

    @Override
    public boolean isEnabled() {
        return get();
    }

    @Override
    public void setEnabled(boolean e) {
        set(e);
    }

    @Override
    public void set(String string) {
        set(Boolean.valueOf(string));
    }
}
