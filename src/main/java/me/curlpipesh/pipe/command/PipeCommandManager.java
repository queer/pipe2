package me.curlpipesh.pipe.command;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import me.curlpipesh.pipe.Pipe;
import me.curlpipesh.pipe.bytecode.map.ClassMap;
import me.curlpipesh.pipe.command.Command.Builder;
import me.curlpipesh.pipe.command.internal.CommandDebug;
import me.curlpipesh.pipe.command.internal.CommandSet;
import me.curlpipesh.pipe.event.Listener;
import me.curlpipesh.pipe.event.PipeEventBus;
import me.curlpipesh.pipe.event.events.PacketSend;
import me.curlpipesh.pipe.plugin.Plugin;
import me.curlpipesh.pipe.util.helpers.Helper;
import me.curlpipesh.pipe.util.helpers.StringHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author audrey
 * @since 10/10/15.
 */
public class PipeCommandManager implements CommandManager {
    private final List<CommandWrapper> commands;
    @SuppressWarnings("FieldMayBeFinal")
    private int threshold = 4;

    public PipeCommandManager() {
        commands = new ArrayList<>();
    }

    @Override
    public boolean executeCommand(@NonNull final String commandString) {
        final String[] split = commandString.split(" ");
        final Command command = findCommand(split[0].replaceFirst(Pattern.quote(getCommandPrefix()), ""));
        if(command != null) {
            Pipe.getLogger().info("Found command: " + command.getName());
            if(command.getExecutor() != null) {
                Pipe.getLogger().info(" * With executor");
            }
        }
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
        ((PipeEventBus) Pipe.eventBus()).addDirectListener(new Listener<PacketSend>() {
            @Override
            public void event(final PacketSend event) {
                if(event.getPacket().getClass().getSimpleName().equals(ClassMap.getClassByName("PacketClientChatMessage").getObfuscatedName())) {
                    try {
                        final Field msg = event.getPacket().getClass().getDeclaredField(ClassMap.getClassByName("PacketClientChatMessage").getFields().get("chatMessage"));
                        msg.setAccessible(true);
                        final String message = (String) msg.get(event.getPacket());
                        if(message.startsWith(getCommandPrefix())) {
                            event.setCancelled(true);
                            if(!executeCommand(message)) {
                                final String commandName = message.split(" ")[0].replaceFirst(Pattern.quote(getCommandPrefix()), "")
                                        .toLowerCase().replaceAll("\\s+", "");
                                if(findCommand(commandName) == null) {
                                    Helper.addChatMessage("§7Command not found: '§c" + commandName + "§7'.");
                                    final Collection<String> possibilities = new ArrayList<>();
                                    for(final CommandWrapper commandWrapper : commands) {
                                        final int distance = StringHelper.levenshteinDistance(commandName, commandWrapper.getCommand().getName().toLowerCase().replaceAll("\\s+", ""));
                                        if(distance < threshold) {
                                            possibilities.add(commandWrapper.getCommand().getName().toLowerCase().replaceAll("\\s+", ""));
                                        }
                                    }
                                    if(!possibilities.isEmpty()) {
                                        Helper.addChatMessage("§7Did you perhaps mean: ");
                                        possibilities.forEach(p -> Helper.addChatMessage("  §7* §c" + p));
                                    }
                                } else {
                                    Helper.addChatMessage("§7Unable to run command '§c" + commandName + "§7'.");
                                }
                            }
                        }
                    } catch(NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        registerCommand(null,
                new Builder().setName("value")
                        .setDesc("Manipulates values for a module. <plugin>.<module>.<property>.")
                        .setExecutor(new CommandSet(getCommandPrefix())).build());
        registerCommand(null, new Builder().setName("debug").setDesc("Debugging command flags")
                .setExecutor(new CommandDebug()).build());
    }

    @Value
    @RequiredArgsConstructor
    private final class CommandWrapper {
        private Plugin plugin;
        private Command command;
    }
}
