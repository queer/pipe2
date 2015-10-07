package me.curlpipesh.pipe.plugin;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import me.curlpipesh.pipe.Pipe;
import me.curlpipesh.pipe.event.Listener;
import me.curlpipesh.pipe.event.events.*;
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
    @Setter
    private PluginManifest manifest;

    @Getter
    private String name;

    @Getter
    private String description;

    @Getter
    private String author;

    @Getter
    @Setter
    private Router router;

    @Getter
    @Setter
    private boolean loaded = false;

    @Getter
    @Setter
    private boolean enabled = false;

    @Getter
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private List<Module> providedModules;

    public BasicPlugin() {
        router = new BasicRouter();
        providedModules = new CopyOnWriteArrayList<>();
    }

    @Override
    public void onEnable() {}

    @Override
    public void onDisable() {}

    @Override
    public void onLoad() {}

    @Override
    public void onUnload() {}

    @Override
    public void loadManifestData() {
        this.name = manifest.getName();
        this.description = manifest.getDescription();
        this.author = manifest.getAuthor();
    }

    @Override
    public void registerModule(@NonNull Module module) {
        if(providedModules.contains(module)) {
            if(!providedModules.add(module)) {
                Pipe.getLogger().warning(String.format("[%s] Unable to register module \"%s\"!", name, module.getName()));
            } else {
                Pipe.getLogger().info(String.format("[%s] Registered module \"%s\"", name, module.getName()));
            }
        } else {
            Pipe.getLogger().warning(String.format("[%s] Ignoring register for registered module \"%s\"!", name, module.getName()));
        }
    }

    @Override
    public void unregisterModule(@NonNull Module module) {
        if(providedModules.contains(module)) {
            if(!providedModules.remove(module)) {
                Pipe.getLogger().warning(String.format("[%s] Unable to unregister module \"%s\"!", name, module.getName()));
            } else {
                Pipe.getLogger().info(String.format("[%s] Unregistered module \"%s\"", name, module.getName()));
            }
        } else {
            Pipe.getLogger().warning(String.format("[%s] Ignoring unregister for non-registered module \"%s\"!", name, module.getName()));
        }
    }

    @Override
    public final void finishEnabling() {
        providedModules.forEach(Module::registerRoutes);
        providedModules.forEach(Module::init);
        Pipe.getInstance().getEventBus().register(new Listener<Tick>() {
            @Override
            public void event(Tick event) {
                router.route(event);
            }
        });
        Pipe.getInstance().getEventBus().register(new Listener<Render2D>() {
            @Override
            public void event(Render2D event) {
                router.route(event);
            }
        });
        Pipe.getInstance().getEventBus().register(new Listener<Render3D>() {
            @Override
            public void event(Render3D event) {
                router.route(event);
            }
        });
        Pipe.getInstance().getEventBus().register(new Listener<Keypress>() {
            @Override
            public void event(Keypress event) {
                router.route(event);
            }
        });
        Pipe.getInstance().getEventBus().register(new Listener<ChatSend>() {
            @Override
            public void event(ChatSend event) {
                router.route(event);
            }
        });
        Pipe.getInstance().getEventBus().register(new Listener<ModFinishedLoading>() {
            @Override
            public void event(ModFinishedLoading event) {
                router.route(event);
            }
        });
    }
}
