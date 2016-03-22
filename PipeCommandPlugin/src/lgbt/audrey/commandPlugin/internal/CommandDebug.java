package lgbt.audrey.commandPlugin.internal;

import lgbt.audrey.pipe.Pipe;
import lgbt.audrey.pipe.command.Command;
import lgbt.audrey.pipe.command.CommandExecutor;
import lgbt.audrey.pipe.util.helpers.ChatHelper;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author audrey
 * @since 1/26/16.
 */
public class CommandDebug implements CommandExecutor {
    @Override
    public boolean executeCommand(final Command command, final String commandString, final String[] args) {
        final List<String> tokens = tokenize(commandString
                .replaceFirst(Pattern.quote(Pipe.getInstance().getCommandManager().getCommandPrefix()) + command.getName(), "").trim())
                .stream().map(String::trim).map(s -> s.replaceAll("^\"", "").replaceAll("\"$", ""))
                .collect(Collectors.<String>toList());
        if(!tokens.isEmpty()) {
            switch(tokens.get(0)) {
                case "--enable":
                    Pipe.getInstance().setInDebugMode(true);
                    ChatHelper.log("Debug has been enabled!");
                    break;
                case "--disable":
                    Pipe.getInstance().setInDebugMode(false);
                    ChatHelper.log("Debug has been disabled!");
                    break;
                case "--toggle":
                    Pipe.getInstance().setInDebugMode(!Pipe.getInstance().isInDebugMode());
                    ChatHelper.log("Debug has been " + (Pipe.getInstance().isInDebugMode() ? "enabled" : "disabled") + '!');
                    break;
                default:
                    ChatHelper.warn("Invalid debug flag: " + tokens.get(0));
                    break;
            }
        } else {
            ChatHelper.log("Usage: ", "--<enable|disable|toggle>");
        }
        return true;
    }
}
