package lgbt.audrey.commandPlugin;

import lgbt.audrey.pipe.Pipe;
import lgbt.audrey.pipe.bytecode.map.ClassMap;
import lgbt.audrey.pipe.command.Command;
import lgbt.audrey.pipe.command.CommandException;
import lgbt.audrey.pipe.command.CommandManager;
import lgbt.audrey.pipe.event.Listener;
import lgbt.audrey.pipe.event.events.PacketSend;
import lgbt.audrey.pipe.plugin.Plugin;
import lgbt.audrey.pipe.util.helpers.ChatHelper;
import lgbt.audrey.pipe.util.helpers.StringHelper;
import lombok.Getter;
import lombok.NonNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author audrey
 * @since 3/20/16.
 */
@SuppressWarnings({"unused", "ConfusingOctalEscapeSequence"})
public class GeneralCommandManager implements CommandManager {
    private final List<CommandWrapper> commands;
    @SuppressWarnings("FieldMayBeFinal")
    private int threshold = 4;

    private final Plugin commandPlugin;

    public GeneralCommandManager(final Plugin commandPlugin) {
        this.commandPlugin = commandPlugin;
        commands = new ArrayList<>();
    }

    @Override
    public boolean executeCommand(@NonNull final String commandString) throws CommandException {
        final String[] split = commandString.split(" ");
        final Command command = findCommand(split[0].replaceFirst(Pattern.quote(getCommandPrefix()), ""));
        return command != null && command.getExecutor() != null && command.getExecutor().executeCommand(command, commandString, StringHelper.removeFirst(split));
    }

    @Override
    public Command findCommand(@NonNull final String commandName) {
        for(final CommandWrapper commandWrapper : commands) {
            if(commandWrapper.getCommand().getName().equalsIgnoreCase(commandName)) {
                return commandWrapper.getCommand();
            }
        }
        return null;
    }

    @Override
    public String getCommandPrefix() {
        return "\\";
    }

    @Override
    public void registerCommand(final Plugin plugin, @NonNull final Command command) {
        if(commands.stream().filter(c -> c.getCommand().getName().equalsIgnoreCase(command.getName())).count() == 0) {
            commands.add(new CommandWrapper(plugin, command));
            Pipe.getLogger().info("Registered command for plugin '" + (plugin == null ? "Built-in" : plugin.getName()) + "': '" + command.getName() + '\'');
        } else {
            Pipe.getLogger().warning("Ignoring command register for '" + command.getName() + "' because a command with that name already exists.");
        }
    }

    @Override
    public void init() {
        //noinspection deprecation
        Pipe.eventBus().register(commandPlugin, new Listener<PacketSend>() {
            @Override
            public void event(final PacketSend event) {
                if(event.getPacket().getClass().getSimpleName().equals(ClassMap.getClassByName("PacketClientChatMessage").getObfuscatedName())) {
                    try {
                        final Field msg = event.getPacket().getClass().getDeclaredField(ClassMap.getClassByName("PacketClientChatMessage").getFields().get("chatMessage"));
                        msg.setAccessible(true);
                        final String message = (String) msg.get(event.getPacket());
                        if(message.startsWith(getCommandPrefix())) {
                            event.setCancelled(true);
                            try {
                                if(!executeCommand(message)) {
                                    final String commandName = message.split(" ")[0].replaceFirst(Pattern.quote(getCommandPrefix()), "")
                                            .toLowerCase().replaceAll("\\s+", "");
                                    if(findCommand(commandName) == null) {
                                        ChatHelper.warn("\2477Command not found: '\247c" + commandName + "\2477'.");
                                        final Collection<String> possibilities = new ArrayList<>();
                                        for(final CommandWrapper commandWrapper : commands) {
                                            final int distance = StringHelper.levenshteinDistance(commandName, commandWrapper.getCommand().getName().toLowerCase().replaceAll("\\s+", ""));
                                            if(distance < threshold) {
                                                possibilities.add(commandWrapper.getCommand().getName().toLowerCase().replaceAll("\\s+", ""));
                                            }
                                        }
                                        if(!possibilities.isEmpty()) {
                                            ChatHelper.warn("\2477Did you perhaps mean: ");
                                            possibilities.forEach(p -> ChatHelper.warn("  \2477* \247c" + p));
                                        }
                                    } else {
                                        ChatHelper.warn("\2477Unable to run command '\247c" + commandName + "\2477'.");
                                    }
                                }
                            } catch(final CommandException e) {
                                ChatHelper.warn("\247cCouldn't run command '\2474" + message + "\247c':",
                                        "\247c" + e.getMessage());
                                e.printStackTrace();
                            }
                        }
                    } catch(NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void shutdown() {
        commands.clear();
    }

    private final class CommandWrapper {
        @Getter
        private final Plugin plugin;
        @Getter
        private final Command command;

        private CommandWrapper(final Plugin plugin, final Command command) {
            this.plugin = plugin;
            this.command = command;
        }
    }
}
