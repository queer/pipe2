package me.curlpipesh.pipe.plugin;

import lombok.Getter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author c
 * @since 7/11/15
 */
public class PluginManager {
    /**
     * The list of plugins that have been registered with the PluginManager
     * instance.
     */
    @Getter
    private final List<? extends Plugin> plugins = new CopyOnWriteArrayList<>();

    /**
     * The singleton instance of PluginManager. Guaranteed to never change.
     */
    private static final PluginManager instance = new PluginManager();

    private PluginManager() {
    }

    public void init() {
        // Find and register plugins.
        for(Plugin p : plugins) {
            // init();
        }
    }

    /**
     * Returns the singleton instance of this class.
     *
     * @return The singleton instance of this class.
     */
    public static PluginManager getInstance() {
        return instance;
    }
}
