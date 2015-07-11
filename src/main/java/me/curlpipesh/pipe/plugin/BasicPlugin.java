package me.curlpipesh.pipe.plugin;

import lombok.Getter;
import lombok.Setter;
import me.curlpipesh.pipe.plugin.module.Module;
import me.curlpipesh.pipe.plugin.router.BasicRouter;
import me.curlpipesh.pipe.plugin.router.Router;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The basic implementation of {@link Plugin}. This class will generally be the
 * one that you want to extend, instead of implementing <tt>Plugin</tt>.
 *
 * @author c
 * @since 7/11/15
 */
public abstract class BasicPlugin implements Plugin {
    @Getter
    private PluginManifest manifest = new PluginManifest(this);

    @Getter
    private String name;

    @Getter
    private String description;

    @Getter
    private String author;

    @Getter
    @Setter
    private Router router;

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @Getter
    private List<Module> providedModules;

    public BasicPlugin(String name) {
        this(name, "This plugin still needs a description!");
    }

    public BasicPlugin(String name, String desc) {
        this(name, desc, "skirts");
    }

    public BasicPlugin(String name, String desc, String author) {
        this.name = name;
        this.description = desc;
        this.author = author;
        router = new BasicRouter();
        providedModules = new CopyOnWriteArrayList<>();
    }
}
