package me.curlpipesh.pipe.command;

import lombok.NonNull;
import me.curlpipesh.pipe.plugin.Plugin;

/**
 * @author audrey
 * @since 10/8/15.
 */
public interface CommandManager {
    boolean executeCommand(@NonNull String commandString);

    Command findCommand(@NonNull String commandName);

    String getCommandPrefix();

    void registerCommand(@NonNull Plugin plugin, @NonNull Command command);

    void init();
}
