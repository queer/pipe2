package me.curlpipesh.pipe.command;

/**
 * @author audrey
 * @since 10/10/15.
 */
public class PipeCommandManager implements CommandManager {
    @Override
    public boolean executeCommand(String commandString) {
        return false;
    }

    @Override
    public Command findCommand(String commandName) {
        return null;
    }
}
