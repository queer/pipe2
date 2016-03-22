package lgbt.audrey.pipe.command;

/**
 * @author audrey
 * @since 3/21/16.
 */
public class CommandException extends Exception {
    public CommandException() {
    }

    public CommandException(final String reason) {
        super(reason);
    }
}
