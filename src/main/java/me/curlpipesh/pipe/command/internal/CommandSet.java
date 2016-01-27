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
                                    Helper.addChatMessage("§7Value of §e" + pmp + "§7 is now: §c" + option + "§r");
                                } else {
                                    Helper.addChatMessage("§7Value of §e" + pmp + "§7: §c" + option + "§r");
                                }
                            } else {
                                Helper.addChatMessage("§7Couldn't find: §c" + pmp + "§r");
                            }
                        } else {
                            Helper.addChatMessage("§e" + split[0] + '.' + split[1] + "§7 has the following properties:");
                            for(final Option<?> o : module.getOptions()) {
                                Helper.addChatMessage("  §7*§e " + o.name());
                            }
                        }
                    } else {
                        Helper.addChatMessage("§7Couldn't find: §c" + split[0] + '.' + split[1] + "§r");
                    }
                } else {
                    Helper.addChatMessage("§e" + split[0] + "§7 has the following modules:");
                    for(final Module module : plugin.getProvidedModules()) {
                        Helper.addChatMessage("  §7*§e " + module.getName());
                    }
                }
            } else {
                Helper.addChatMessage("§7Couldn't find: §c" + split[0] + "§r");
            }
        } else {
            Helper.addChatMessage("Usage: " + commandPrefix + "value <plugin>[.<module>[.<property>]] [value]");
        }
        return true;
    }
}
