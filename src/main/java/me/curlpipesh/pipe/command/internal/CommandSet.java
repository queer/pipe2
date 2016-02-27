package me.curlpipesh.pipe.command.internal;

import me.curlpipesh.pipe.Pipe;
import me.curlpipesh.pipe.command.Command;
import me.curlpipesh.pipe.command.CommandExecutor;
import me.curlpipesh.pipe.config.Option;
import me.curlpipesh.pipe.plugin.Plugin;
import me.curlpipesh.pipe.plugin.module.Module;
import me.curlpipesh.pipe.util.helpers.Helper;

import java.util.Optional;

/**
 * @author audrey
 * @since 1/26/16.
 */
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
                                    Helper.addChatMessage("\2477Value of \247e" + pmp + "\2477 is now: \247c" + option + "\247r");
                                } else {
                                    Helper.addChatMessage("\2477Value of \247e" + pmp + "\2477: \247c" + option + "\247r");
                                }
                            } else {
                                Helper.addChatMessage("\2477Couldn't find: \247c" + pmp + "\247r");
                            }
                        } else {
                            Helper.addChatMessage("\247e" + split[0] + '.' + split[1] + "\2477 has the following properties:");
                            for(final Option<?> o : module.getOptions()) {
                                Helper.addChatMessage("  \2477*\247e " + o.name());
                            }
                        }
                    } else {
                        Helper.addChatMessage("\2477Couldn't find: \247c" + split[0] + '.' + split[1] + "\247r");
                    }
                } else {
                    Helper.addChatMessage("\247e" + split[0] + "\2477 has the following modules:");
                    for(final Module module : plugin.getProvidedModules()) {
                        Helper.addChatMessage("  \2477*\247e " + module.getName());
                    }
                }
            } else {
                Helper.addChatMessage("\2477Couldn't find: \247c" + split[0] + "\247r");
            }
        } else {
            Helper.addChatMessage("Usage: " + commandPrefix + "value <plugin>[.<module>[.<property>]] [value]");
        }
        return true;
    }
}
