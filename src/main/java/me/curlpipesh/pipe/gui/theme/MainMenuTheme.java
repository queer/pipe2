package me.curlpipesh.pipe.gui.theme;

import me.curlpipesh.pipe.gui.api.model.base.controls.interfaces.IControl;
import me.curlpipesh.pipe.gui.api.model.base.interfaces.IContainer;
import me.curlpipesh.pipe.gui.api.model.impl.BasicSlider;
import me.curlpipesh.pipe.gui.api.view.render.Renderer;
import me.curlpipesh.pipe.gui.api.view.render.state.RenderException;
import me.curlpipesh.pipe.gui.api.view.render.theme.Theme;
import me.curlpipesh.pipe.util.GLRenderer;
import me.curlpipesh.pipe.util.helpers.Helper;
import org.lwjgl.opengl.GL11;

/**
 * Theme for rendering the main menu of the game.
 *
 * @author c
 * @since 6/3/15
 */
@SuppressWarnings({"unused", "FieldCanBeLocal"})

public class MainMenuTheme extends Theme {
    /*private static final int LIGHT_GRADIENT_GREY = 0xFFE5E5E5, DARK_GRADIENT_GREY = 0xFFBDBDBD;
    private static final int SOLID_GREY = 0xFFDEDEDE, BORDER_GREY = 0xFF6F6F6F;
    private static final int CONTROL_ON = 0xFF424242, CONTROL_OFF = 0xFF858585;
    private static final int BUTTON_LIGHT_GRADIENT_OFF = 0xFF999999, BUTTON_DARK_GRADIENT_OFF = 0xFF777777;
    private static final int BUTTON_LIGHT_GRADIENT_ON = 0xFF4A90D9, BUTTON_DARK_GRADIENT_ON = 0xFF377FC9;*/

    private static final int LIGHT_GRADIENT_GREY = 0xAA959595, DARK_GRADIENT_GREY = 0xAA656565;
    private static final int SOLID_GREY = 0xAA9A9A9A, BORDER_GREY = 0xAA393939;
    private static final int CONTROL_ON = 0xAA212121, CONTROL_OFF = 0xAA424242;
    private static final int BUTTON_LIGHT_GRADIENT_OFF = 0xAA555555, BUTTON_DARK_GRADIENT_OFF = 0xAA333333;
    private static final int BUTTON_LIGHT_GRADIENT_ON = 0xAA777777, BUTTON_DARK_GRADIENT_ON = 0xAA555555;

    @SuppressWarnings("FieldCanBeLocal")
    private final Renderer<IContainer> containerRenderer = container -> {
        GLRenderer.drawGradientRect(container.getArea().getX(), container.getArea().getY(),
                container.getArea().getWidth(), container.getTitleArea().getHeight(), LIGHT_GRADIENT_GREY | 0xFF000000,
                DARK_GRADIENT_GREY | 0xFF000000);
        GLRenderer.drawRect(container.getComponentArea().getX(), container.getComponentArea().getY(),
                container.getComponentArea().getWidth(), container.getComponentArea().getHeight(), SOLID_GREY);


        for(IControl control : container.getControls()) {
            int color = 0xFFFFFFFF;
            switch(control.getType()) {
                case "minimize":
                    color = control.getState() ? CONTROL_ON : CONTROL_OFF;
                    break;

                case "pin":
                    color = control.getState() ? CONTROL_ON : CONTROL_OFF;
                    break;
                case "close":
                    color = control.getState() ? CONTROL_ON : CONTROL_OFF;
                    break;
                default:
                    break;
            }
            GLRenderer.drawRect(control.getArea().getX(), control.getArea().getY(), control.getArea().getWidth(),
                    control.getArea().getHeight(), color);
        }
        GL11.glColor4d(1, 1, 1, 1);

        GLRenderer.drawEmbossedString(container.getText(), (float) container.getArea().getX() + 3,
                (float) (container.getTitleArea().getY() + (container.getTitleArea().getHeight() / 2F) - (Helper.getFontHeight() / 2F)) + 1F,
                0xFFFFFFFF, 0xFF5B5B5B, 0.5F);
    };

    public MainMenuTheme() {
        super("Test");
        registerRenderer("container", containerRenderer);

        registerRenderer("window", containerRenderer);

        registerRenderer("label", (widget) ->
                Helper.drawString(widget.getText(), (int) widget.getArea().getX(),
                        (int) widget.getArea().getY(), 0xFFFFFFFF, false));

        registerRenderer("button", (widget) -> {
            if(!widget.getTagValue("render-focus").equals("true")) {
                GLRenderer.drawGradientRect(widget.getArea().getX(), widget.getArea().getY(), widget.getArea().getWidth(),
                        widget.getArea().getHeight(), BUTTON_LIGHT_GRADIENT_OFF, BUTTON_DARK_GRADIENT_OFF);
            } else {
                GLRenderer.drawGradientRect(widget.getArea().getX(), widget.getArea().getY(), widget.getArea().getWidth(),
                        widget.getArea().getHeight(),
                        widget.isFocused() ? BUTTON_LIGHT_GRADIENT_ON : widget.isState() ? BUTTON_DARK_GRADIENT_ON : BUTTON_LIGHT_GRADIENT_OFF,
                        widget.isFocused() ? BUTTON_DARK_GRADIENT_ON : widget.isState() ? BUTTON_LIGHT_GRADIENT_ON : BUTTON_DARK_GRADIENT_OFF);
            }
            Helper.drawString(widget.getText(), (float) widget.getArea().getX() + 3,
                    (float) (widget.getArea().getY() + (widget.getArea().getHeight() / 2) - (Helper.getFontHeight() / 2F) + 1),
                    0xFFFFFFFF, false);
        });

        //noinspection Convert2Lambda
        registerRenderer("slider", new Renderer<BasicSlider>() {
            @Override
            public void render(final BasicSlider widget) throws RenderException {
                GLRenderer.drawRect(widget.getArea().getX(), widget.getArea().getY(), widget.getArea().getWidth(),
                        widget.getArea().getHeight(), 0xFF222222);
                GLRenderer.drawRect(widget.getArea().getX(), widget.getArea().getY(),
                        widget.getArea().getWidth() * widget.getAmountScrolled(), widget.getArea().getHeight(),
                        0xFF444488);
                Helper.drawString(
                        String.format("%s: %s", widget.getText(), widget.getValue().get()),
                        (int) widget.getArea().getX(), (int) widget.getArea().getY(), 0xFFFFFFFF, false);
            }
        });
    }
}
