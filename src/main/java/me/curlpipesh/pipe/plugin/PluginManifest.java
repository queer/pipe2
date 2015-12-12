package me.curlpipesh.pipe.plugin;

import lombok.Data;

/**
 * JSON manifest that contains information about the plugin. Loaded from a file
 * in the root directory of the plugin's JAR.
 *
 * @author c
 * @since 7/10/15
 */
@Data
public final class PluginManifest {
    /**
     * The name of the plugin
     */
    private String name;

    /**
     * The author of the plugin
     */
    private String author;

    /**
     * The description of the plugin
     */
    private String description;

    /**
     * The main class of the plugin. Will be the class loaded by
     * {@link PluginManager#init()}.
     */
    private String mainClass;

    /**
     * Whether or not the plugin is marked as disabled in the manifest.
     */
    private boolean disabled;
}
