package me.curlpipesh.pipe.gui.api.view.render.theme;

import lombok.Getter;
import lombok.Setter;
import me.curlpipesh.pipe.gui.api.model.base.controls.interfaces.IControl;
import me.curlpipesh.pipe.gui.api.model.base.interfaces.IContainer;
import me.curlpipesh.pipe.gui.api.model.impl.BasicSlider;
import me.curlpipesh.pipe.gui.api.view.render.Renderer;
import me.curlpipesh.pipe.gui.api.view.render.state.RenderException;
import me.curlpipesh.pipe.util.GLRenderer;
import me.curlpipesh.pipe.util.helpers.Helper;
import org.lwjgl.opengl.GL11;

public final class ThemeManager {
    @Getter
    @Setter
    private static ITheme theme = new DefaultTheme();

    public static final class DefaultTheme extends Theme {
        @SuppressWarnings("FieldCanBeLocal")
        private final Renderer<IContainer> containerRenderer = container -> {
            GLRenderer.drawRect(container.getArea().getX(), container.getArea().getY(),
                    container.getArea().getWidth(), container.getArea().getHeight(), 0x77000000);
            GLRenderer.drawRect(container.getComponentArea().getX(), container.getComponentArea().getY(),
                    container.getComponentArea().getWidth(), container.getComponentArea().getHeight(), 0x77FFFFFF);

            for(IControl control : container.getControls()) {
                int color = 0xFFFFFFFF;
                switch(control.getType()) {
                    case "minimize":
                        color = control.getState() ? 0xFF0000FF : 0xFF000077;
                        break;
                    case "pin":
                        color = control.getState() ? 0xFF00FF00 : 0xFF007700;
                        break;
                    case "close":
                        color = control.getState() ? 0xFFFF0000 : 0xFF770000;
                        break;
                    default:
                        break;
                }
                GLRenderer.drawRect(control.getArea().getX(), control.getArea().getY(), control.getArea().getWidth(),
                        control.getArea().getHeight(), color);
            }
            GL11.glColor4d(1, 1, 1, 1);

            Helper.drawString(container.getText(), (int) container.getArea().getX(),
                    (int) container.getArea().getY(), 0xFFFFFFFF, false);
        };

        public DefaultTheme() {
            super("Test");
            registerRenderer("container", containerRenderer);

            registerRenderer("window", containerRenderer);

            registerRenderer("label", (widget) ->
                    Helper.drawString(widget.getText(), (int)widget.getArea().getX(),
                            (int)widget.getArea().getY(), 0xFFFFFFFF, false));

            registerRenderer("button", (widget) -> {
                GLRenderer.drawRect(widget.getArea().getX(), widget.getArea().getY(), widget.getArea().getWidth(),
                        widget.getArea().getHeight(), 0xFF222222);
                Helper.drawString(widget.getText(), (int)widget.getArea().getX(),
                        (int)widget.getArea().getY(), widget.isFocused() ? 0xFF00FF00 : widget.isState() ? 0xFF009900 : 0xFFFFFFFF, false);
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
                            (int)widget.getArea().getX(), (int)widget.getArea().getY(), 0xFFFFFFFF, false);
                }
            });
        }
    }
}
