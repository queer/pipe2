package lgbt.audrey.pipe.plugin;

import lgbt.audrey.pipe.command.Command;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lgbt.audrey.pipe.Pipe;
import lgbt.audrey.pipe.command.Command;
import lgbt.audrey.pipe.plugin.module.Module;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The basic implementation of {@link Plugin}. This class will generally be the
 * one that you want to extend, instead of implementing <tt>Plugin</tt>.
 *
 * @author c
 * @since 7/11/15
 */
@SuppressWarnings("FieldMayBeFinal")
public abstract class BasicPlugin implements Plugin {
    @Getter
    @Setter
    private PluginManifest manifest;

    @Getter
    private String name;

    @Getter
    private String description;

    @Getter
    private String author;

    @Getter
    @Setter
    private boolean loaded;

    @Getter
    @Setter
    private boolean enabled;

    @Getter
    @SuppressWarnings({"MismatchedQueryAndUpdateOfCollection", "TypeMayBeWeakened"})
    private List<Module> providedModules = new CopyOnWriteArrayList<>();

    @Getter
    private List<Command> registeredCommands = new CopyOnWriteArrayList<>();

    @Override
    public void onEnable() {}

    @Override
    public void onDisable() {}

    @Override
    public void onLoad() {}

    @Override
    public void onUnload() {}

    @Override
    public void loadManifestData() {
        name = manifest.getName();
        description = manifest.getDescription();
        author = manifest.getAuthor();
    }

    @Override
    public void registerModule(@NonNull final Module module) {
        if(!providedModules.contains(module)) {
            if(!providedModules.add(module)) {
                Pipe.getLogger().warning(String.format("[%s] Unable to register module \"%s\"!", name, module.getName()));
            } else {
                Pipe.getLogger().info(String.format("[%s] Registered module \"%s\"", name, module.getName()));
            }
        } else {
            Pipe.getLogger().warning(String.format("[%s] Ignoring register for registered module \"%s\"!", name, module.getName()));
        }
    }

    @Override
    public void unregisterModule(@NonNull final Module module) {
        if(providedModules.contains(module)) {
            if(!providedModules.remove(module)) {
                Pipe.getLogger().warning(String.format("[%s] Unable to unregister module \"%s\"!", name, module.getName()));
            } else {
                Pipe.getLogger().info(String.format("[%s] Unregistered module \"%s\"", name, module.getName()));
            }
        } else {
            Pipe.getLogger().warning(String.format("[%s] Ignoring unregister for non-registered module \"%s\"!", name, module.getName()));
        }
    }

    @Override
    public final void finishEnabling() {
        providedModules.forEach(Module::init);
    }
}
