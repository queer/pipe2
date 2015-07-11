package me.curlpipesh.pipe.plugin;

import lombok.Data;
import me.curlpipesh.pipe.plugin.module.Module;

import java.util.List;

/**
 * An automatically-generated manifest that contains information about the
 * plugin that generated it. Information such as name, modules, author (if
 * given), and anything else that can be gathered may be present.
 *
 * @author c
 * @since 7/10/15
 */
@Data
public class PluginManifest {
    /**
     * The name of the plugin
     */
    private final String name;

    /**
     * The author of the plugin
     */
    private final String author;

    /**
     * The description of the plugin
     */
    private final String description;

    /**
     * A {@link List} of all {@link Module}s that the plugin for this manifest
     * provides.
     */
    private final List<Module> providedModules;

    /**
     * The actual plugin for which this is the manifest.
     */
    private final Plugin plugin;

    public PluginManifest(Plugin plugin) {
        this.name = plugin.getName();
        this.author = plugin.getAuthor();
        this.description = plugin.getDescription();
        this.providedModules = plugin.getProvidedModules();
        this.plugin = plugin;
    }
}
