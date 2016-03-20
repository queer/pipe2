package lgbt.audrey.pipe.plugin;

/**
 * @author audrey
 * @since 10/6/15.
 */
public interface Loadable {
    void onLoad();

    void onUnload();

    boolean isLoaded();

    void setLoaded(boolean loaded);
}
