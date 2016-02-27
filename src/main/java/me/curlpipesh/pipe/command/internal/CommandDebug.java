package me.curlpipesh.pipe.command.internal;

import me.curlpipesh.pipe.Pipe;
import me.curlpipesh.pipe.command.Command;
import me.curlpipesh.pipe.command.CommandExecutor;
import me.curlpipesh.pipe.util.helpers.ChatHelper;

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
                    break;
                case "--disable":
                    Pipe.getInstance().setInDebugMode(false);
                    break;
                case "--toggle":
                    Pipe.getInstance().setInDebugMode(!Pipe.getInstance().isInDebugMode());
                    break;
            }
            // TODO: Map to flags
            /*for(int i = 0; i < tokens.size(); i++) {
                if(tokens.get(i).equalsIgnoreCase("--debug")) {
                    if(!tokens.get(i + 1).startsWith("-")) {
                        ++i;
                        ChatHelper.log(tokens.get(i));
                    } else {
                        ChatHelper.warn("Invalid token: '" + tokens.get(i + 1) + '\'');
                    }
                } else {
                    ChatHelper.warn("Invalid syntax.", "--debug <info>");
                    break;
                }
            }*/
        } else {
            ChatHelper.log("Usage: ", "--<enable|disable|toggle>");
        }
        return true;
    }
}
