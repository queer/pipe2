package me.curlpipesh.pipe.command;

import lombok.NonNull;
import me.curlpipesh.pipe.Pipe;
import me.curlpipesh.pipe.bytecode.map.ClassMap;
import me.curlpipesh.pipe.config.Option;
import me.curlpipesh.pipe.event.Listener;
import me.curlpipesh.pipe.event.PipeEventBus;
import me.curlpipesh.pipe.event.events.PacketSend;
import me.curlpipesh.pipe.plugin.Plugin;
import me.curlpipesh.pipe.plugin.module.Module;
import me.curlpipesh.pipe.util.helpers.Helper;
import me.curlpipesh.pipe.util.helpers.StringHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * @author audrey
 * @since 10/10/15.
 */
public class PipeCommandManager implements CommandManager {
    private final List<Command> commands;

    public PipeCommandManager() {
        commands = new ArrayList<>();
    }

    @Override
    public boolean executeCommand(@NonNull final String commandString) {
        String[] split = commandString.split(" ");
        Command command = findCommand(split[0].replaceFirst(Pattern.quote(getCommandPrefix()), ""));
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
        for(Command command : commands) {
            if(command.getName().equalsIgnoreCase(commandName)) {
                return command;
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
        if(commands.stream().filter(c -> c.getName().equalsIgnoreCase(command.getName())).count() == 0) {
            commands.add(command);
            if(plugin != null) {
                plugin.getRegisteredCommands().add(command);
            }
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
                        Field msg = event.getPacket().getClass().getDeclaredField(ClassMap.getClassByName("PacketClientChatMessage").getFields().get("chatMessage"));
                        msg.setAccessible(true);
                        String message = (String) msg.get(event.getPacket());
                        if(message.startsWith(getCommandPrefix())) {
                            event.setCancelled(true);
                            if(!executeCommand(message)) {
                                Helper.addChatMessage("Command failed! :(");
                            }
                        }
                    } catch(NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Command set = new Command("value");
        set.setDescription("Manipulates values for a module. <plugin>.<module>.<property>.");
        set.setExecutor((command, commandString, args) -> {
            if(args.length >= 1) {
                String pmp = args[0];
                String[] split = pmp.split("\\.");
                Optional<Plugin> pluginOptional = Pipe.pluginManager().getPlugins().stream()
                        .filter(p -> p.getName().replaceAll("\\s+", "").equalsIgnoreCase(split[0])).findFirst();
                if(pluginOptional.isPresent()) {
                    Plugin plugin = pluginOptional.get();
                    if(split.length > 1 && split[1].length() > 0) {
                        Optional<Module> moduleOptional = plugin.getProvidedModules().stream()
                                .filter(m -> m.getName().replaceAll("\\s+", "").equalsIgnoreCase(split[1])).findFirst();
                        if(moduleOptional.isPresent()) {
                            Module module = moduleOptional.get();
                            if(split.length > 2 && split[2].length() > 0) {
                                Option option = module.getOption(split[2].replaceAll("\\s+", ""));
                                if(option != null) {
                                    if(args.length > 1 && args[1].length() > 0) {
                                        // Set
                                        option.set(args[1]);
                                        Helper.addChatMessage("§7Value of §e" + pmp + "§7 is now: §c" + option.toString() + "§r");
                                    } else {
                                        Helper.addChatMessage("§7Value of §e" + pmp + "§7: §c" + option.toString() + "§r");
                                    }
                                } else {
                                    Helper.addChatMessage("§7Couldn't find: §c" + pmp + "§r");
                                }
                            } else {
                                Helper.addChatMessage("§e" + split[0] + "." + split[1] + "§7 has the following properties:");
                                for(Option<?> o : module.getOptions()) {
                                    Helper.addChatMessage("  §7*§e " + o.name());
                                }
                            }
                        } else {
                            Helper.addChatMessage("§7Couldn't find: §c" + split[0] + "." + split[1] + "§r");
                        }
                    } else {
                        Helper.addChatMessage("§e" + split[0] + "§7 has the following modules:");
                        for(Module module : plugin.getProvidedModules()) {
                            Helper.addChatMessage("  §7*§e " + module.getName());
                        }
                    }
                } else {
                    Helper.addChatMessage("§7Couldn't find: §c" + split[0] + "§r");
                }
            } else {
                Helper.addChatMessage("Usage: " + getCommandPrefix() + set.getName() + " <plugin>[.<module>[.<property>]] [value]");
            }
            return true;
        });
        registerCommand(null, set);
    }
}
