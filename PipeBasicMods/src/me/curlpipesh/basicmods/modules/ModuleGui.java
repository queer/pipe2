package me.curlpipesh.basicmods.modules;

import lombok.NonNull;
import me.curlpipesh.pipe.Pipe;
import me.curlpipesh.pipe.config.Option;
import me.curlpipesh.pipe.config.RangeOption;
import me.curlpipesh.pipe.event.Listener;
import me.curlpipesh.pipe.event.events.Keypress;
import me.curlpipesh.pipe.event.events.Render2D;
import me.curlpipesh.pipe.gui.GuiScreen;
import me.curlpipesh.pipe.gui.api.controller.action.MouseClickAction;
import me.curlpipesh.pipe.gui.api.controller.action.TickAction;
import me.curlpipesh.pipe.gui.api.model.base.interfaces.IContainer;
import me.curlpipesh.pipe.gui.api.model.base.interfaces.IWidget;
import me.curlpipesh.pipe.gui.api.model.impl.BasicContainer;
import me.curlpipesh.pipe.gui.api.model.impl.BasicSlider;
import me.curlpipesh.pipe.gui.api.model.impl.BasicWidget;
import me.curlpipesh.pipe.gui.api.model.impl.BasicWindow;
import me.curlpipesh.pipe.gui.api.util.Tuple;
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
import org.lwjgl.input.Mouse;

import java.awt.*;

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
                    IContainer optionsPane = new BasicContainer(module.getName() + " Options", new Tuple<>("minimizable", "true"));
                    int oCounter = 0;
                    for(Option<?> o : module.getOptions()) {
                        if(o instanceof RangeOption) {
                            optionsPane.addChild(new BasicSlider((RangeOption<?>) o));
                            ++oCounter;
                        }
                    }

                    if(oCounter > 0) {
                        if(module instanceof ToggleModule) {
                            optionsPane.addAction((MouseClickAction<IContainer>) (iContainer, i) -> {
                                if(i == 0) {
                                    if(container.getTitleArea().contains(calculateMouseLocation())) {
                                        ((ToggleModule) module).toggle();
                                    }
                                }
                            });
                            optionsPane.addAction((TickAction<BasicContainer>) widget -> {
                                boolean state = widget.isFocused();
                                widget.setFocused(true);
                                widget.setState(module.isEnabled());
                                widget.setFocused(state);
                            });
                        }
                        optionsPane.initialize();
                        optionsPane.getMinimizeControl().setState(true);
                        container.addChild(optionsPane);
                    } else {
                        IWidget button = new BasicWidget("button", module.getName());
                        if(module instanceof ToggleModule) {
                            button.addAction((MouseClickAction<BasicWidget>) (basicWidget, i) -> {
                                if(i == 0) {
                                    ((ToggleModule) module).toggle();
                                }
                            });
                            button.addAction((TickAction<BasicWidget>) widget -> {
                                boolean state = widget.isFocused();
                                widget.setFocused(true);
                                widget.setState(module.isEnabled());
                                widget.setFocused(state);
                            });
                            container.addChild(button);
                        }
                    }
                    if(module instanceof ToggleModule || oCounter > 0) {
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
            @SuppressWarnings("ConstantConditions")
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

    /**
     * Returns a {@link Point} containing the current x- and
     * y-coordinates of the mouse cursor, scaled to work with Minecraft's GUI
     * scaling. Originally written by DarkStorm_.
     *
     * @return A {@link Point} containing x- and y-coordinates of the
     *         mouse cursor.
     */
    private Point calculateMouseLocation() {
        int scale = Helper.getScale();
        if (scale == 0) {
            scale = 1000;
        }
        int scaleFactor = 0;
        while ((scaleFactor < scale) && ((Helper.getWidth() / (scaleFactor + 1)) >= 320)
                && ((Helper.getHeight() / (scaleFactor + 1)) >= 240)) {
            scaleFactor++;
        }
        return new Point(Mouse.getX() / scaleFactor, (Helper.getHeight() / scaleFactor)
                - (Mouse.getY() / scaleFactor));
    }
}
