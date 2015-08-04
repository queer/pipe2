package me.curlpipesh.pipe.plugin;

import lombok.Getter;
import me.curlpipesh.pipe.util.ClassMapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

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
    private final List<Plugin> plugins = new CopyOnWriteArrayList<>();

    /**
     * The singleton instance of PluginManager. Guaranteed to never change.
     */
    private static final PluginManager instance = new PluginManager();

    private PluginManager() {
    }

    public void init() {
        List<Class<?>> pluginClasses = ClassMapper.getMappedClasses().stream()
                .filter(c -> Plugin.class.isAssignableFrom(c) && !Modifier.isInterface(c.getModifiers()))
                .filter(c -> !Modifier.isAbstract(c.getModifiers()))
                .collect(Collectors.toList());
        for(Class<?> c : pluginClasses) {
            try {
                plugins.add((Plugin)c.getDeclaredConstructor().newInstance());
            } catch(InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        plugins.forEach(Plugin::init);
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
