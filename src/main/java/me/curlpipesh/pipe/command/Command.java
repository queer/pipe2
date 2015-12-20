package me.curlpipesh.pipe.command;

import lombok.Data;
import lombok.NonNull;

/**
 * @author audrey
 * @since 10/8/15.
 */
@Data
public class Command {
    private final String name;
    private String description = "No description given!";
    private CommandExecutor executor;

    public Command(@NonNull String name) {
        this.name = name;
    }
}
