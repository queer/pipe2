package lgbt.audrey.pipe.command;

import lgbt.audrey.pipe.plugin.Plugin;
import lombok.NonNull;
import lgbt.audrey.pipe.plugin.Plugin;

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
