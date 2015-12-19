package me.curlpipesh.pipe.plugin.module;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import me.curlpipesh.pipe.Pipe;
import me.curlpipesh.pipe.config.Option;
import me.curlpipesh.pipe.plugin.Plugin;
import me.curlpipesh.pipe.util.Keybind;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

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

    @Getter
    private final Collection<Option<?>> options = new HashSet<>();

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

    @Override
    public void addOption(final Option<?> option) {
        if(!options.add(option)) {
            Pipe.getLogger().warning("Couldn't add '" + plugin.getName() + "." + name + "." + option.name() + "'.");
        }
    }

    @Override
    public Option<?> getOption(final String name) {
        Optional<Option<?>> optional = options.stream().filter(o -> o.name().equalsIgnoreCase(name)).findFirst();
        if(optional.isPresent()) {
            return optional.get();
        } else {
            throw new IllegalArgumentException("Couldn't find option with name '" + plugin.getName() + "." + this.name + "." + name + "'.");
        }
    }
}
