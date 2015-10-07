package me.curlpipesh.pipe.plugin.module;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import me.curlpipesh.pipe.plugin.Plugin;
import me.curlpipesh.pipe.util.Keybind;

/**
 * @author c
 * @since 7/12/15
 */
public abstract class BasicModule implements Module {
    @Getter
    private final String name, description;

    @Getter
    @Setter
    private String status = "§aOk§r";

    @Getter
    @Setter
    private Keybind keybind = null;

    @Getter
    private final Plugin plugin;

    public BasicModule(@NonNull Plugin plugin, @NonNull String name, @NonNull String description) {
        this.name = name;
        this.description = description;
        this.plugin = plugin;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void setEnabled(boolean enabled) {}
}
