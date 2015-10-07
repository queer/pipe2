package me.curlpipesh.pipe.plugin;

/**
 * @author audrey
 * @since 10/7/15.
 */
public interface Toggleable {
    void onEnable();

    void onDisable();

    boolean isEnabled();

    void setEnabled(boolean enabled);
}
