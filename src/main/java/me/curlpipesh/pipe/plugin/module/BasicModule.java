package me.curlpipesh.pipe.plugin.module;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
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
    private Keybind keybind = null;

    public BasicModule(@NonNull String name, @NonNull String description) {
        this.name = name;
        this.description = description;
    }
}
