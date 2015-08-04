package me.curlpipesh.pipe;

import lombok.Getter;
import lombok.Setter;
import me.curlpipesh.pipe.event.EventBus;
import me.curlpipesh.pipe.event.PipeEventBus;
import me.curlpipesh.pipe.plugin.PluginManager;
import me.curlpipesh.pipe.util.helpers.Helper;

import java.io.File;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * The main class of the mod. Note that this class is different from {@link Agent}:
 * While <tt>Agent</tt> aims to take care of instrumenting the loaded bytecode,
 * this class is the actual "main" class of the mod, responsible for actually
 * handling everything.
 *
 * @author c
 * @since 7/10/15
 */
public final class Pipe {
    /**
     * The logger for the entire mod.
     */
    private static final Logger logger = Logger.getLogger("Pipe");

    /**
     * The singleton instance of Pipe. Guaranteed to never change.
     */
    private static final Pipe instance = new Pipe();

    /**
     * The {@link EventBus} that will be used by the client. Defaults to being
     * an instance of {@link PipeEventBus}.
     */
    @Getter
    @Setter
    private EventBus eventBus = new PipeEventBus();

    @Getter
    private File pipeDataDir;

    // TODO Better way to reference the two directories below?
    @Getter
    private File pipePluginDir;

    @Getter
    private File pipeConfigDir;

    private Pipe() {
    }

    /**
     * Initializes the client. Responsible for delegation of responsibilities
     * like plugin initialization, file structure creation, and so on.
     */
    public void init() {
        logger.info("Starting up Pipe...");
        pipeDataDir = new File(Helper.getMinecraftDataDir() + File.separator + "pipe");
        pipePluginDir = new File(pipeDataDir + File.separator + "plugins");
        pipeConfigDir = new File(pipeDataDir + File.separator + "config");
        if(!pipeDataDir.exists()) {
            if(!pipeDataDir.mkdirs()) {
                throw new IllegalStateException("Unable to create directories required for the client to function. " +
                        "Do you have permission to write in the directory? Is the disk full? If running out of a RAMDISK, " +
                        "do you have enough space?");
            }
        }
        PluginManager.getInstance().init();
    }

    /**
     * Returns the singleton instance of this class.
     *
     * @return The singleton instance of this class.
     */
    public static Pipe getInstance() {
        return instance;
    }

    /**
     * Returns the logger for the entire mod. This is intended for use by
     * everything that has a need to log to the "console" output.
     *
     * @return The logger for the entire mod.
     */
    public static Logger getLogger() {
        return logger;
    }

    static {
        logger.setUseParentHandlers(false);
        logger.addHandler(new Handler() {
            @Override
            public void publish(LogRecord logRecord) {
                System.out.println(String.format("[Pipe] [%s] %s", logRecord.getLevel().getName(), logRecord.getMessage()));
            }

            @Override
            public void flush() {
            }

            @Override
            public void close() throws SecurityException {
            }
        });
    }
}
