package me.curlpipesh.pipe.command;

/**
 * @author audrey
 * @since 10/8/15.
 */
public interface CommandExecutor {
    boolean executeCommand(Command command, String commandString, String[] args);
}
