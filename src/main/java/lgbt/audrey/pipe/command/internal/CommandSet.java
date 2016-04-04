package lgbt.audrey.pipe.command.internal;

import lgbt.audrey.pipe.Pipe;
import lgbt.audrey.pipe.command.Command;
import lgbt.audrey.pipe.command.CommandException;
import lgbt.audrey.pipe.command.CommandExecutor;
import lgbt.audrey.pipe.config.Option;
import lgbt.audrey.pipe.plugin.Plugin;
import lgbt.audrey.pipe.plugin.module.Module;
import lgbt.audrey.pipe.util.ArgumentTokenizer;
import lgbt.audrey.pipe.util.helpers.ChatHelper;

import java.util.List;
import java.util.Optional;

/**
 * @author audrey
 * @since 1/26/16.
 */
@SuppressWarnings("ConfusingOctalEscapeSequence")
public class CommandSet implements CommandExecutor {
    @Override
    public boolean executeCommand(final Command command, final String commandString, final String[] args) throws CommandException {
        if(!commandString.isEmpty()) {
            final List<String> tokenize = ArgumentTokenizer.tokenize(commandString);
            String pluginString = "";
            String moduleString = "";
            String optionString = "";
            String valueString = "";
            for(int i = 0; i < tokenize.size(); i++) {
                switch(tokenize.get(i)) {
                    case "-p":
                    case "--plugin":
                        ++i;
                        pluginString = tokenize.get(i);
                        break;
                    case "-m":
                    case "--module":
                        ++i;
                        moduleString = tokenize.get(i);
                        break;
                    case "-o":
                    case "--option":
                        ++i;
                        optionString = tokenize.get(i);
                        break;
                    case "-v":
                    case "--value":
                        ++i;
                        valueString = tokenize.get(i);
                        break;
                }
            }
            final String finalPluginString = pluginString;
            final Optional<Plugin> pluginOptional = Pipe.pluginManager().getPlugins().stream()
                    .filter(p -> p.getName().replaceAll("\\s+", "").equalsIgnoreCase(finalPluginString)).findFirst();

            if(pluginOptional.isPresent()) {
                final Plugin plugin = pluginOptional.get();
                if(moduleString.isEmpty()) {
                    ChatHelper.log("\2477Plugin '\247e" + plugin.getName() +
                            "\2477' has the following modules available:");
                    plugin.getProvidedModules().forEach(m -> ChatHelper.log(" \2477* \247e" + m.getName()));
                } else {
                    final String finalModuleString = moduleString;
                    final Optional<Module> moduleOptional = plugin.getProvidedModules().stream()
                            .filter(m -> m.getName().replaceAll("\\s+", "").equalsIgnoreCase(finalModuleString)).findFirst();
                    if(moduleOptional.isPresent()) {
                        final Module module = moduleOptional.get();
                        if(optionString.isEmpty()) {
                            ChatHelper.log("\2477Module '\247e" + pluginString + ':' + moduleString +
                                    "\2477' has the following options available:");
                            module.getOptions().forEach(o -> ChatHelper.log(" \2477* \247e" + o.name()));
                        } else {
                            final Option option = module.getOption(optionString.replaceAll("\\s+", ""));
                            if(option != null) {
                                if(valueString != null && !valueString.isEmpty()) {
                                    try {
                                        // Set
                                        option.set(valueString);
                                        ChatHelper.log("\2477Value of \247e" + option.name() + "\2477 is now: \247c" + option.get() + "\247r");
                                    } catch(final Exception e) {
                                        throw new CommandException('\'' + valueString + "' is not a valid value for '"
                                                + pluginString + ':' + moduleString + ':' + optionString + "'!");
                                    }
                                } else {
                                    ChatHelper.log("\2477Value of \247e" + option.name() + "\2477: \247c" + option.get() + "\247r");
                                }
                            } else {
                                ChatHelper.warn("\2477Unknown option: \247c" + pluginString + ':' + moduleString + ':' + optionString);
                                ChatHelper.log("\2477Module '\247e" + pluginString + ':' + moduleString +
                                        "\2477' has the following options available:");
                                module.getOptions().forEach(o -> ChatHelper.log(" \2477* \247e" + o.name()));
                            }
                        }
                    } else {
                        ChatHelper.warn("\2477Unknown module: \247c" + pluginString + ':' + moduleString);
                        ChatHelper.log("\2477Plugin '\247e" + plugin.getName() +
                                "\2477' has the following modules available:");
                        plugin.getProvidedModules().forEach(m -> ChatHelper.log(" \2477* \247e" + m.getName()));
                    }
                }
            } else {
                ChatHelper.warn("\2477Unknown plugin: \247c" + pluginString);
                ChatHelper.log("\2477The following plugins are available: ");
                Pipe.getInstance().getPluginManager().getPlugins().forEach(p -> ChatHelper.log(" \2477* \247e" + p.getName()));
            }
        } else {
            throw new CommandException("Not enough arguments!");
        }
        return true;
    }
}
