package lgbt.audrey.pipe.command;

import lgbt.audrey.pipe.util.ArgumentTokenizer;
import lgbt.audrey.pipe.util.ArgumentTokenizer;

import java.util.List;

/**
 * @author audrey
 * @since 10/8/15.
 */
@FunctionalInterface
public interface CommandExecutor {
    boolean executeCommand(Command command, String commandString, String[] args);

    default List<String> tokenize(final String commandString) {
        return ArgumentTokenizer.tokenize(commandString, true);
    }
}
