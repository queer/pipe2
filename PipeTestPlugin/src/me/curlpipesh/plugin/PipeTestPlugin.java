package me.curlpipesh.plugin;

import me.curlpipesh.pipe.event.events.Keypress;
import me.curlpipesh.pipe.event.events.Render2D;
import me.curlpipesh.pipe.plugin.BasicPlugin;
import me.curlpipesh.pipe.plugin.module.BasicModule;
import me.curlpipesh.pipe.plugin.router.Route;
import me.curlpipesh.pipe.util.GLRenderer;
import me.curlpipesh.pipe.util.Keybind;
import org.lwjgl.input.Keyboard;

/**
 * @author audrey
 * @since 10/6/15.
 */
public class PipeTestPlugin extends BasicPlugin {
    @Override
    public void onLoad() {
        System.out.println("Pipe test plugin loaded!");
    }

    @Override
    public void onEnable() {
        registerModule(new BasicModule("Test module", "Test module for keypresses") {
            @Override
            public void registerRoutes() {
                getRouter().register(new Route<Keypress>() {
                    @Override
                    public void route(Keypress event) {
                        System.out.println("Got keypress: " + Keyboard.getKeyName(event.getKey()));
                    }
                });
                getRouter().register(new Route<Render2D>() {
                    @Override
                    public void route(Render2D render2D) {
                        GLRenderer.drawRect(0, 0, 100, 100, 0x77FFFFFF);
                    }
                });
            }

            @Override
            public void init() {
                setKeybind(new Keybind(Keyboard.KEY_V));
                System.out.println("Test module ready!");
            }
        });
        System.out.print("Pipe test plugin enabled!");
    }

    @Override
    public void onDisable() {
        System.out.println("Pipe test plugin disabled!");
    }

    @Override
    public void onUnload() {
        System.out.println("Pipe test plugin unloaded!");
    }
}
