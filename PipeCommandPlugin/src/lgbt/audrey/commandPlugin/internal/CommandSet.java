package lgbt.audrey.commandPlugin.internal;

import lgbt.audrey.pipe.Pipe;
import lgbt.audrey.pipe.command.Command;
import lgbt.audrey.pipe.command.CommandExecutor;
import lgbt.audrey.pipe.config.Option;
import lgbt.audrey.pipe.plugin.Plugin;
import lgbt.audrey.pipe.plugin.module.Module;
import lgbt.audrey.pipe.util.helpers.ChatHelper;

import java.util.Optional;

/**
 * @author audrey
 * @since 1/26/16.
 */
@SuppressWarnings("ConfusingOctalEscapeSequence")
public class CommandSet implements CommandExecutor {
    private final String commandPrefix;

    public CommandSet(final String commandPrefix) {
        this.commandPrefix = commandPrefix;
    }

    @Override
    public boolean executeCommand(final Command command, final String commandString, final String[] args) {
        if(args.length >= 1) {
            final String pmp = args[0];
            final String[] split = pmp.split("\\.");
            final Optional<Plugin> pluginOptional = Pipe.pluginManager().getPlugins().stream()
                    .filter(p -> p.getName().replaceAll("\\s+", "").equalsIgnoreCase(split[0])).findFirst();
            if(pluginOptional.isPresent()) {
                final Plugin plugin = pluginOptional.get();
                if(split.length > 1 && !split[1].isEmpty()) {
                    final Optional<Module> moduleOptional = plugin.getProvidedModules().stream()
                            .filter(m -> m.getName().replaceAll("\\s+", "").equalsIgnoreCase(split[1])).findFirst();
                    if(moduleOptional.isPresent()) {
                        final Module module = moduleOptional.get();
                        if(split.length > 2 && !split[2].isEmpty()) {
                            final Option option = module.getOption(split[2].replaceAll("\\s+", ""));
                            if(option != null) {
                                if(args.length > 1 && !args[1].isEmpty()) {
                                    // Set
                                    option.set(args[1]);
                                    ChatHelper.log("\2477Value of \247e" + pmp + "\2477 is now: \247c" + option + "\247r");
                                } else {
                                    ChatHelper.log("\2477Value of \247e" + pmp + "\2477: \247c" + option + "\247r");
                                }
                            } else {
                                ChatHelper.warn("\2477Couldn't find: \247c" + pmp + "\247r");
                            }
                        } else {
                            ChatHelper.log("\247e" + split[0] + '.' + split[1] + "\2477 has the following properties:");
                            for(final Option<?> o : module.getOptions()) {
                                ChatHelper.log("  \2477*\247e " + o.name());
                            }
                        }
                    } else {
                        ChatHelper.warn("\2477Couldn't find: \247c" + split[0] + '.' + split[1] + "\247r");
                    }
                } else {
                    ChatHelper.log("\247e" + split[0] + "\2477 has the following modules:");
                    plugin.getProvidedModules().forEach(module -> ChatHelper.log("  \2477*\247e " + module.getName()));
                }
            } else {
                ChatHelper.warn("\2477Couldn't find: \247c" + split[0] + "\247r");
            }
        } else {
            ChatHelper.log("\2477Usage: " + commandPrefix + "value <plugin>[.<module>[.<property>]] [value]");
        }
        return true;
    }
}
