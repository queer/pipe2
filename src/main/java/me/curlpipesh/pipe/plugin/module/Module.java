package me.curlpipesh.pipe.plugin.module;

import lombok.NonNull;
import me.curlpipesh.pipe.plugin.Plugin;
import me.curlpipesh.pipe.plugin.router.Route;
import me.curlpipesh.pipe.plugin.router.Router;
import me.curlpipesh.pipe.util.Keybind;

/**
 * The base of all modules. A module is not intended to be used in a standalone
 * context, but is instead intended to be parented by a
 * {@link me.curlpipesh.pipe.plugin.Plugin}. Because of this, modules are not
 * intended to register event listeners directly, but to instead register
 * {@link me.curlpipesh.pipe.plugin.router.Route}s with their parent plugin so
 * that events will be routed correctly.
 *
 * @author c
 * @since 7/10/15
 */
public interface Module {
    /**
     * Returns the name of this module.
     *
     * @return The name of this module. May not be null.
     */
    String getName();

    /**
     * Returns the description of this module.
     *
     * @return The description of this module. May not be null.
     */
    String getDescription();

    /**
     * Returns the keybind for this module. If this module does not need a
     * keybinding for its execution, implementations of this method may be
     * effectively empty.
     *
     * @return The keybind for this module. May be null.
     */
    Keybind getKeybind();

    /**
     * Sets the keybinding for this module. Is not necessary to invoke.
     *
     * @param keybind The new keybind to set. May be null.
     */
    void setKeybind(@NonNull Keybind keybind);

    /**
     * Registers the event routes with the parent plugin. This method does not
     * have to actually register any routes, but all routes must be registered
     * from this method in order to ensure that things such as
     * {@link me.curlpipesh.pipe.plugin.Plugin#setRouter(Router)} function
     * correctly.
     */
    void registerRoutes();

    /**
     * Initializes this module. This method should not call {@link #registerRoutes()},
     * due to the fact that invocation of that method is already handled
     * elsewhere. Note that this assumption relies on the encapsulating plugin
     * being an instance of {@link me.curlpipesh.pipe.plugin.BasicPlugin}, so
     * it may become necessary to do said invocation in this method.
     */
    void init();

    /**
     * Returns a String that represents the status of this module. This status
     * may be anything, from "Ok" to "47 potatoes eaten."
     *
     * @return The current status. May be null
     */
    String getStatus();

    /**
     * Sets the status for this module.
     *
     * @param status The status to set. May be null
     */
    void setStatus(@NonNull String status);

    /**
     * Intended to return the plugin that registered this module.
     *
     * @return The plugin that registered this module
     */
    Plugin getPlugin();

    /**
     * Whether or not this module is currently accepting events. With the
     * default implementation in {@link BasicModule}, this will always be true.
     *
     * @return Whether or not this module is currently accepting events.
     */
    boolean isEnabled();

    /**
     * Sets whether or not this module is currently accepting events. In the
     * default implementation in {@link BasicModule}, this will do nothing.
     *
     * @param enabled Whether or not the module should be accepting events.
     */
    void setEnabled(boolean enabled);

    /**
     * Convenience method. Equivalent to
     * <tt>getPlugin().getRouter().register(Route)</tt>.
     *
     * @param route
     */
    default void registerRoute(@NonNull Route<?> route) {
        getPlugin().getRouter().register(route);
    }
}
