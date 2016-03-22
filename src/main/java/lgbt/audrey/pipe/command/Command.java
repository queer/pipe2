package lgbt.audrey.pipe.command;

import lombok.Getter;

/**
 * @author audrey
 * @since 10/8/15.
 */
public final class Command {
    @Getter
    private String name = "No name given!";
    @Getter
    private String description = "No description given!";
    @Getter
    private CommandExecutor executor;
    @Getter
    private String[] help;

    private Command() {
    }

    public static class CommandBuilder {
        private String name;
        private String desc;
        private CommandExecutor executor;
        private String[] help;

        public CommandBuilder setName(final String name) {
            this.name = name;
            return this;
        }

        public CommandBuilder setDesc(final String desc) {
            this.desc = desc;
            return this;
        }

        public CommandBuilder setExecutor(final CommandExecutor executor) {
            this.executor = executor;
            return this;
        }

        public CommandBuilder setHelp(final String[] help) {
            this.help = help;
            return this;
        }

        public Command build() {
            final Command command = new Command();
            command.name = name;
            command.description = desc;
            command.executor = executor;
            command.help = help;
            return command;
        }
    }
}
