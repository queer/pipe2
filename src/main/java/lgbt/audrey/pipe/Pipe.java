package lgbt.audrey.pipe;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lgbt.audrey.pipe.bytecode.Generator;
import lgbt.audrey.pipe.bytecode.Version;
import lgbt.audrey.pipe.command.CommandManager;
import lgbt.audrey.pipe.event.EventBus;
import lgbt.audrey.pipe.event.PipeEventBus;
import lgbt.audrey.pipe.event.events.ModFinishedLoading;
import lgbt.audrey.pipe.plugin.PluginManager;
import lgbt.audrey.pipe.plugin.PluginManifest;
import lgbt.audrey.pipe.plugin.serialization.ManifestDeserializer;
import lgbt.audrey.pipe.util.helpers.ChatHelper;
import lgbt.audrey.pipe.util.helpers.Helper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * The main class of the mod. Note that this class is different from {@link Agent}:
 * While <tt>Agent</tt> aims to take care of instrumenting the loaded bytecode,
 * this class is the actual "main" class of the mod, responsible for actually
 * handling everything.
 * <p>
 * TODO: Make not singleton?
 *
 * @author c
 * @since 7/10/15
 */
@SuppressWarnings({"unused", "Singleton"})
public final class Pipe {
    /**
     * The logger for the entire mod.
     */
    private static final Logger logger = Logger.getLogger("PipeConsole");

    /**
     * The singleton instance of Pipe. Guaranteed to never change.
     * <p>
     * TODO: Interface?
     */
    @SuppressWarnings("StaticVariableOfConcreteClass")
    private static final Pipe instance = new Pipe();

    static {
        logger.setUseParentHandlers(false);
        //noinspection AnonymousInnerClassWithTooManyMethods
        logger.addHandler(new Handler() {
            @Override
            public void publish(final LogRecord logRecord) {
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

    @Getter
    private final PluginManager pluginManager;

    @Getter
    private final Gson gson;
    /**
     * The {@link EventBus} that will be used by the client. Defaults to being
     * an instance of {@link PipeEventBus}.
     */
    @Getter
    @Setter
    @SuppressWarnings("FieldMayBeFinal")
    private EventBus eventBus = new PipeEventBus();

    @Getter
    private File pipeDataDir;

    // TODO Better way to reference the two directories below?
    @Getter
    private File pipePluginDir;

    @Getter
    private File pipeConfigDir;

    @Getter
    @Setter
    private CommandManager commandManager;

    @Getter
    @Setter
    private boolean isInDebugMode;

    @Getter
    @Setter(AccessLevel.PACKAGE)
    private Version version;

    private Pipe() {
        pluginManager = new PluginManager(this);
        gson = new GsonBuilder()
                .registerTypeAdapter(PluginManifest.class, new ManifestDeserializer())
                .setPrettyPrinting().create();
    }

    /**
     * Initializes the client. Responsible for delegation of responsibilities
     * like plugin initialization, file structure creation, and so on.
     */
    public void init() {
        logger.info("Starting up Pipe...");
        setupDirectories();
        pluginManager.init();
        for(final Generator generator : version.getGenerators()) {
            Agent.defineClass(Pipe.class.getClassLoader(), generator.generate(), generator.getClassName());
            logger.info("Generated: " + generator.getClassName());
        }
        eventBus.push(new ModFinishedLoading());
    }

    /**
     * Creates the directories required for the client to function
     */
    private void setupDirectories() {
        final String exceptionMessage = "Unable to create directories required for the client to function. " +
                "Do you have permission to write in the directory? Is the disk full? If running out of a RAMDISK, " +
                "do you have enough space?";
        pipeDataDir = new File(Helper.getMinecraftDataDir() + File.separator + "pipe");
        pipePluginDir = new File(pipeDataDir + File.separator + "plugins");
        pipeConfigDir = new File(pipeDataDir + File.separator + "config");
        if(!pipeDataDir.exists()) {
            if(!pipeDataDir.mkdirs()) {
                throw new IllegalStateException(exceptionMessage);
            } else {
                logger.info("Created Pipe data dir!");
            }
        }
        if(!pipeConfigDir.exists()) {
            if(!pipeConfigDir.mkdir()) {
                throw new IllegalStateException(exceptionMessage);
            } else {
                logger.info("Created Pipe config dir!");
            }
        }
        if(!pipePluginDir.exists()) {
            if(!pipePluginDir.mkdir()) {
                throw new IllegalStateException(exceptionMessage);
            } else {
                logger.info("Created Pipe plugin dir!");
            }
        }
    }

    public void reload() {
        getCommandManager().shutdown();
        setCommandManager(null);
        getEventBus().clear();
        getPluginManager().shutdown();
        getPluginManager().init();
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

    /**
     * Alias of <tt>getInstance().getEventBus()</tt>
     *
     * @return The event bus
     */
    public static EventBus eventBus() {
        return getInstance().getEventBus();
    }

    /**
     * Alias of <tt>getInstance().getPluginManager()</tt>
     *
     * @return The plugin manager
     */
    public static PluginManager pluginManager() {
        return getInstance().getPluginManager();
    }

    /**
     * Alias of <tt>getInstance().getPipeConfigDir()</tt>
     *
     * @return The config dir
     */
    public static File configDir() {
        return getInstance().getPipeConfigDir();
    }

    /**
     * Alias of <tt>getInstance().getPluginDir()</tt>
     *
     * @return The plugin dir
     */
    public static File pluginDir() {
        return getInstance().getPipePluginDir();
    }

    /**
     * Alias of <tt>getInstance().getDataDir()</tt>
     *
     * @return The data dir
     */
    public static File dataDir() {
        return getInstance().getPipeDataDir();
    }

    /**
     * Alias of <tt>getInstance().getGson()</tt>
     *
     * @return The Gson instance
     */
    public static Gson gson() {
        return getInstance().getGson();
    }
}
