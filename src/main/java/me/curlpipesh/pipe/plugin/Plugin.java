package me.curlpipesh.pipe.plugin;

import me.curlpipesh.pipe.plugin.module.Module;
import me.curlpipesh.pipe.plugin.router.Router;

import java.util.List;

/**
 * The base of all plugins. An individual plugin is not meant to be a "mod" or
 * "cheat" or "hack" or <insert term here> on its own; rather, it is meant to
 * have individual {@link me.curlpipesh.pipe.plugin.module.Module}s registered
 * to it. Each <tt>Module</tt> is then responsible for being the individual
 * "mod" or "cheat" that a given plugin provides. Each plugin will, by default,
 * provide a "manifest" of modules that it provides, so that an end-user (or a
 * developer) would be able to both acquire information about a given module
 * and also enable or disable individual modules essentially at will.
 *
 * @author c
 * @since 7/10/15
 */
public interface Plugin {
    /**
     * Returns the name of this plugin. May not be null
     *
     * @return The name of this plugin
     */
    String getName();

    /**
     * Returns the author of this plugin. May be null
     *
     * @return The author of this plugin
     */
    String getAuthor();

    /**
     * Returns the description of this plugin. May be null
     *
     * @return The description of this plugin
     */
    String getDescription();

    /**
     * Returns the router used by this plugin. May not be null. Used for
     * registering and unregistering routes, as well as routing events.
     *
     * @return The router used by this plugin.
     */
    Router getRouter();

    /**
     * Sets the router used by this plugin to the specified router. Note that
     * invocations of this method must also re-register all routes that modules
     * have previously registered
     *
     * @param router The new router to use. May not be null
     */
    void setRouter(Router router);

    /**
     * Returns the list of all modules that this plugin provides. May not be
     * null. May be empty.
     *
     * @return The list of all modules that this plugin provides.
     */
    List<Module> getProvidedModules();

    /**
     * Registers a new module for this plugin.
     *
     * @param module The module to register. May not be null.
     */
    void registerModule(Module module);

    /**
     * Unregisters a module from this plugin.
     *
     * @param module The plugin to unregister. May not be null.
     */
    void unregisterModule(Module module);

    /**
     * Returns the {@link PluginManifest} for this plugin. The manifest must
     * contain as much information as a given plugin is able to supply.
     *
     * @return The {@link PluginManifest} for this plugin
     */
    PluginManifest getManifest();

    /**
     * Returns a <tt>String</tt> containing the contents of the plugin's
     * manifest file.
     *
     * @return A String containing the contents of the plugin's manifest file.
     *         May not be null. May not be empty. May not be invalid JSON.
     */
    String getManifestFileContents();

    /**
     * Initializes this plugin. This method is mainly responsible for setting up
     * the {@link Module}s that this plugin provides.
     */
    void init();
}
