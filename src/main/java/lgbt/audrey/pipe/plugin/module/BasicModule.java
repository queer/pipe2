package lgbt.audrey.pipe.plugin.module;

import lgbt.audrey.pipe.util.Keybind;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lgbt.audrey.pipe.Pipe;
import lgbt.audrey.pipe.config.Option;
import lgbt.audrey.pipe.plugin.Plugin;
import lgbt.audrey.pipe.util.Keybind;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

/**
 * @author c
 * @since 7/12/15
 */
@SuppressWarnings("FieldMayBeFinal")
public abstract class BasicModule implements Module {
    @Getter
    private final String name;
    @Getter
    private final String description;

    @Getter
    @Setter
    private String status = "\247aOk\247r";

    @Getter
    @Setter
    private Keybind keybind;

    @Getter
    private final Plugin plugin;

    @Getter
    private final Collection<Option<?>> options = new HashSet<>();

    public BasicModule(@NonNull final Plugin plugin, @NonNull final String name, @NonNull final String description) {
        this.name = name;
        this.description = description;
        this.plugin = plugin;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void setEnabled(final boolean enabled) {}

    @Override
    public void addOption(final Option<?> option) {
        if(!options.add(option)) {
            Pipe.getLogger().warning("Couldn't add '" + plugin.getName() + '.' + name + '.' + option.name() + "'.");
        }
    }

    @Override
    public Option<?> getOption(final String name) {
        final Optional<Option<?>> optional = options.stream().filter(o -> o.name().equalsIgnoreCase(name)).findFirst();
        if(optional.isPresent()) {
            return optional.get();
        } else {
            return null;
        }
    }
}
