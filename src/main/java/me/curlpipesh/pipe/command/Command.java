package me.curlpipesh.pipe.command;

import lombok.Data;

/**
 * @author audrey
 * @since 10/8/15.
 */
@Data
public abstract class Command {
    private final String name;
    private final String description;
    private CommandExecutor executor;

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
