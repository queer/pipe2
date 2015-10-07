package me.curlpipesh.pipe.plugin;

/**
 * @author audrey
 * @since 10/6/15.
 */
public interface Loadable {
    void onLoad();

    void onUnload();

    void onEnable();

    void onDisable();

    boolean isLoaded();

    void setLoaded(boolean loaded);

    boolean isEnabled();

    void setEnabled(boolean enabled);
}
