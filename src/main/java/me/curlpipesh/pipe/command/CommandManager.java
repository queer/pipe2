package me.curlpipesh.pipe.command;

/**
 * @author audrey
 * @since 10/8/15.
 */
public interface CommandManager {
    boolean executeCommand(String commandString);

    Command findCommand(String commandName);
}
