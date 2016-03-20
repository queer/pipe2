package lgbt.audrey.commandPlugin;

import lgbt.audrey.commandPlugin.internal.CommandDebug;
import lgbt.audrey.commandPlugin.internal.CommandSet;
import lgbt.audrey.pipe.Pipe;
import lgbt.audrey.pipe.command.Command.Builder;
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
        /*Pipe.getInstance().getCommandManager().registerCommand(this, new Builder()
                .setName("reload").setDesc("Reloads all plugins, this one included.")
                .setExecutor((command, commandString, args) -> {
                    ChatHelper.log("Reloading all plugins...");
                    Pipe.getInstance().setCommandManager(null);
                    Pipe.getInstance().getPluginManager().shutdown();
                    Pipe.getInstance().getPluginManager().init();
                    ChatHelper.log("Plugins reloaded!");
                    return true;
                }).build());*/
        Pipe.getInstance().getCommandManager().registerCommand(null, new Builder().setName("value")
                .setDesc("Manipulates values for a module. <plugin>.<module>.<property>.")
                .setExecutor(new CommandSet(Pipe.getInstance().getCommandManager().getCommandPrefix())).build());
        Pipe.getInstance().getCommandManager().registerCommand(null, new Builder().setName("debug")
                .setDesc("Debugging command flags").setExecutor(new CommandDebug()).build());
    }
}
