package me.curlpipesh.basicmods.modules;

import lombok.NonNull;
import me.curlpipesh.pipe.Pipe;
import me.curlpipesh.pipe.event.Listener;
import me.curlpipesh.pipe.event.events.Keypress;
import me.curlpipesh.pipe.event.events.Render2D;
import me.curlpipesh.pipe.gui.GuiScreen;
import me.curlpipesh.pipe.gui.api.controller.action.MouseClickAction;
import me.curlpipesh.pipe.gui.api.controller.action.TickAction;
import me.curlpipesh.pipe.gui.api.model.base.interfaces.IContainer;
import me.curlpipesh.pipe.gui.api.model.base.interfaces.IWidget;
import me.curlpipesh.pipe.gui.api.model.impl.BasicWidget;
import me.curlpipesh.pipe.gui.api.model.impl.BasicWindow;
import me.curlpipesh.pipe.gui.api.view.layout.impl.StandardLayout;
import me.curlpipesh.pipe.gui.api.view.render.state.RenderException;
import me.curlpipesh.pipe.gui.module.ContainerGuiModule;
import me.curlpipesh.pipe.gui.theme.PipeTheme;
import me.curlpipesh.pipe.plugin.Plugin;
import me.curlpipesh.pipe.plugin.module.BasicModule;
import me.curlpipesh.pipe.plugin.module.Module;
import me.curlpipesh.pipe.plugin.module.ToggleModule;
import me.curlpipesh.pipe.util.Keybind;
import me.curlpipesh.pipe.util.helpers.Helper;
import me.curlpipesh.pipe.util.helpers.KeypressHelper;
import org.lwjgl.input.Keyboard;

/**
 * @author audrey
 * @since 12/17/15.
 */
public class ModuleGui extends BasicModule {
    private ContainerGuiModule clickGui;

    public ModuleGui(@NonNull final Plugin plugin) {
        super(plugin, "Gui", "Adds a clickable in-game GUI");
    }

    @Override
    public void init() {
        setKeybind(new Keybind(Keyboard.KEY_Y).withModifier(Keyboard.KEY_LSHIFT));

        clickGui = new ContainerGuiModule() {
            @Override
            public void init() {

            }

            @Override
            public boolean isPauseGame() {
                return false;
            }
        };
        for(Plugin plugin : Pipe.pluginManager().getPlugins()) {
            if(plugin.getName() != null) {
                IContainer container = new BasicWindow(plugin.getName());
                int counter = 0;
                for(Module module : plugin.getProvidedModules()) {
                    if(module instanceof ToggleModule) {
                        IWidget button = new BasicWidget("button", module.getName());
                        button.addAction((MouseClickAction<BasicWidget>) (basicWidget, i) -> ((ToggleModule) module).toggle());
                        button.addAction((TickAction<BasicWidget>) widget -> {
                            boolean state = widget.isFocused();
                            widget.setFocused(true);
                            widget.setState(module.isEnabled());
                            widget.setFocused(state);
                        });
                        container.addChild(button);
                        ++counter;
                    }
                }
                if(counter > 0) {
                    container.setLayout(new StandardLayout(72, 12, 4));
                    container.initialize();
                    clickGui.addContainer(container);
                }
            }
        }
        clickGui.setTheme(new PipeTheme());

        Pipe.eventBus().register(getPlugin(), new Listener<Keypress>() {
            @Override
            public void event(final Keypress keypress) {
                if(KeypressHelper.isKeyPlusModifiersDown(getKeybind(), keypress)) {
                    Pipe.getLogger().info("Size: " + clickGui.getContainers().size());
                    GuiScreen.getInstance().setCurrentModule(clickGui);
                    Helper.displayGuiScreen(GuiScreen.getInstance());
                }
            }
        });
        Pipe.eventBus().register(getPlugin(), new Listener<Render2D>() {
            @Override
            public void event(final Render2D event) {
                if(Helper.getCurrentScreen() == null) {
                    clickGui.getContainers().stream().filter(IContainer::isPinnable)
                            .filter(container -> container.getPinControl().getState()).forEach(container -> {
                        container.tick();
                        try {
                            clickGui.getTheme().renderContainer(container);
                        } catch(RenderException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        });
    }

    @Override
    public final boolean isStatusShown() {
        return false;
    }
}
