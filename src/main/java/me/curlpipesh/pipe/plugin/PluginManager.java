package me.curlpipesh.pipe.plugin;

import lombok.Getter;
import lombok.NonNull;
import me.curlpipesh.bytecodetools.util.ClassEnumerator;
import me.curlpipesh.pipe.Pipe;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

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

    private final Pipe pipe;

    public PluginManager(@NonNull Pipe pipe) {
        this.pipe = pipe;
    }

    public void init() {
        File[] files = pipe.getPipePluginDir().listFiles();
        if(files == null) {
            Pipe.getLogger().warning("Couldn't get files from plugin directory! Skipping loading...");
            return;
        }
        for(File file : files)
            if(file.getName().toLowerCase().endsWith(".jar")) {
                List<Class<?>> classes;
                try {
                    classes = ClassEnumerator.getClassesFromJar(file, URLClassLoader.newInstance(new URL[]{
                            new URL("jar:file:" + file.getAbsoluteFile().getAbsolutePath() + "!/")
                    }, Pipe.class.getClassLoader()));
                } catch(Exception e) {
                    e.printStackTrace();
                    continue;
                }
                JarFile jarFile;
                try {
                    jarFile = new JarFile(file);
                } catch(IOException e) {
                    Pipe.getLogger().warning("Error loading JAR (" + file.getName() + "):");
                    e.printStackTrace();
                    continue;
                }
                ZipEntry entry = jarFile.getEntry("plugin.json");
                if(entry == null) {
                    Pipe.getLogger().warning("No plugin.json in " + file.getName() + ", skipping.");
                    continue;
                }
                InputStream manifestInputStream;
                try {
                    manifestInputStream = jarFile.getInputStream(entry);
                } catch(IOException e) {
                    Pipe.getLogger().warning("Error reading manifest in JAR (" + file.getName() + "):");
                    e.printStackTrace();
                    continue;
                }
                String manifestContents = readFromInputStream(manifestInputStream);
                try {
                    manifestInputStream.close();
                } catch(IOException e) {
                    Pipe.getLogger().warning("Error reading manifest in JAR (" + file.getName() + "):");
                    e.printStackTrace();
                    continue;
                }
                PluginManifest pluginManifest;
                try {
                    pluginManifest = pipe.getGson().fromJson(manifestContents, PluginManifest.class);
                } catch(IllegalArgumentException e) {
                    Pipe.getLogger().warning("Error loading manifest from JAR (" + file.getName() + "):");
                    e.printStackTrace();
                    continue;
                }

                classes.stream().filter(p -> p.getName().equalsIgnoreCase(pluginManifest.getMainClass())).forEach(clazz -> {
                    try {
                        if(!Plugin.class.isAssignableFrom(clazz) || !isInstantiable(clazz)) {
                            Pipe.getLogger().warning("Unable to load plugin \"" + pluginManifest.getName()
                                    + "\": No main class found");
                        }
                        Plugin plugin = (Plugin) clazz.getDeclaredConstructor().newInstance();
                        plugin.setManifest(pluginManifest);
                        try {
                            plugin.onLoad();
                        } catch(Exception e) {
                            Pipe.getLogger().warning("Error loading plugin: " + clazz.getName());
                            e.printStackTrace();
                            return;
                        }
                        plugin.setLoaded(true);
                        plugins.add(plugin);
                    } catch(InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
            }
        Pipe.getLogger().info("Done!");

        plugins.forEach(p -> {
            try {
                p.loadManifestData();
                p.onEnable();
                p.finishEnabling();
                p.setEnabled(true);
                Pipe.getLogger().info("Enabled plugin: " + p.getName());
            } catch(Exception e) {
                Pipe.getLogger().warning("Error enabling plugin (" + p.getClass().getName() + "):");
                e.printStackTrace();
            }
        });
    }

    private boolean isInstantiable(@NonNull Class<?> clazz) {
        return !Modifier.isInterface(clazz.getModifiers())
                && !Modifier.isAbstract(clazz.getModifiers());
    }

    private String readFromInputStream(@NonNull InputStream in) {
        char[] buffer = new char[4096];
        Reader reader = new InputStreamReader(in);
        StringBuilder out = new StringBuilder();
        try {
            while(true) {
                int rsz = reader.read(buffer, 0, buffer.length);
                if(rsz < 0) {
                    break;
                }
                out.append(buffer, 0, rsz);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        return out.toString();
    }

    public void shutdown() {
        plugins.forEach(this::disablePlugin);
        plugins.forEach(this::unloadPlugin);
    }

    private void disablePlugin(Plugin plugin) {
        plugin.onDisable();
        pipe.getEventBus().unregister(plugin);
    }

    private void unloadPlugin(Plugin plugin) {
        plugin.onUnload();
        plugins.remove(plugin);
    }
}
