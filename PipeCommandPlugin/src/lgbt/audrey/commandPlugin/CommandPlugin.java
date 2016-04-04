package lgbt.audrey.commandPlugin;

import lgbt.audrey.commandPlugin.external.CommandNames;
import lgbt.audrey.pipe.Pipe;
import lgbt.audrey.pipe.command.Command.CommandBuilder;
import lgbt.audrey.pipe.plugin.BasicPlugin;

/**
 * @author audrey
 * @since 3/20/16.
 */
public class CommandPlugin extends BasicPlugin {
    @Override
    public void onLoad() {
        Pipe.getInstance().setCommandManager(new GeneralCommandManager(this));
        Pipe.getInstance().getCommandManager().init();
    }

    @Override
    public void onEnable() {
        Pipe.getInstance().getCommandManager().registerCommand(this, new CommandBuilder().setName("names")
                .setDesc("Get name history").setExecutor(new CommandNames()).build());
    }
}
