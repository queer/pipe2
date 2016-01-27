package me.curlpipesh.pipe.command;

import lombok.Data;

/**
 * @author audrey
 * @since 10/8/15.
 */
@Data
@SuppressWarnings("ClassWithOnlyPrivateConstructors")
public class Command {
    private String name;
    private String description = "No description given!";
    private CommandExecutor executor;

    private Command() {
    }

    public static class Builder {
        private String name;
        private String desc;
        private CommandExecutor executor;

        public Builder setName(final String name) {
            this.name = name;
            return this;
        }

        public Builder setDesc(final String desc) {
            this.desc = desc;
            return this;
        }

        public Builder setExecutor(final CommandExecutor executor) {
            this.executor = executor;
            return this;
        }

        public Command build() {
            final Command command = new Command();
            command.name = name;
            command.description = desc;
            command.executor = executor;
            return command;
        }
    }
}
