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
 * The main theme of the client.
 *
 * @author c
 * @since 5/25/15
 */
public class PipeTheme extends Theme {
    /**
     * Renderer for both containers and windows, hence why it is a
     * <tt>private final</tt> field instead of a local variable.
     */
    @SuppressWarnings("FieldCanBeLocal")
    private final Renderer<IContainer> containerRenderer = container -> {
        // The area where components will be placed
        GLRenderer.drawRect(container.getComponentArea().getX(), container.getComponentArea().getY(),
                container.getComponentArea().getWidth(), container.getComponentArea().getHeight(), 0x77000000);
        // Controls
        for(IControl control : container.getControls()) {
            int color = 0xFFFFFFFF;
            String letter = "?";
            switch(control.getType()) {
                case "minimize":
                    color = control.getState() || container.isMouseOver() ? 0x77777777 : 0x77444444;
                    letter = control.getState() ? "+" : "-";
                    break;
                case "pin":
                    color = control.getState() ? 0x77777777 : 0x77444444;
                    letter = control.getState() ? "#" : "O";
                    break;
                case "close":
                    color = control.getState() ? 0x77777777 : 0x77444444;
                    letter = "x";
                    break;
                default:
                    break;
            }
            GLRenderer.drawRect(control.getArea().getX(), control.getArea().getY(), control.getArea().getWidth(),
                    control.getArea().getHeight(), color);
            GL11.glColor4d(1, 1, 1, 1);
            // Control indicator character
            Helper.drawString(letter, (float) control.getArea().getX() + (float) control.getArea().getWidth() / 4,
                    (float) control.getArea().getY() + (float) control.getArea().getHeight() / 4 - 1, 0xFFFFFFFF, false);
        }
        GL11.glColor4d(1, 1, 1, 1);
        // Title
        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
        GL11.glPushMatrix();
        GLRenderer.scissor(container.getTitleArea().getX(), container.getTitleArea().getY(),
                container.getTitleArea().getWidth(), container.getTitleArea().getHeight());
        GLRenderer.drawRect(container.getTitleArea().getX(), container.getTitleArea().getY(),
                container.getTitleArea().getWidth(), container.getTitleArea().getHeight(), 0x77000000);
        Helper.drawString(container.getText(), (float) container.getArea().getX() + 0.5F,
                (float) container.getArea().getY() + 0.5F, 0xFFFFFFFF, false);
        GL11.glPopMatrix();
        GL11.glPopAttrib();
    };

    public PipeTheme() {
        super("Pipe");
        registerRenderer("container", containerRenderer);
        registerRenderer("window", containerRenderer);
        registerRenderer("label", widget -> Helper.drawString(widget.getText(), (float) widget.getArea().getX() + 0.5F,
                (float) widget.getArea().getY() + 0.5F, 0xFFFFFFFF, false));
        registerRenderer("button", widget -> {
            GLRenderer.drawRect(widget.getArea().getX(), widget.getArea().getY(), widget.getArea().getWidth(),
                    widget.getArea().getHeight(), 0x34FFFFFF);
            Helper.drawString(widget.getText(), (float) widget.getArea().getX() + 0.5F,
                    (float) widget.getArea().getY() + 0.5F,
                    widget.isFocused() ? 0xFF00FF00 : widget.isState() ? 0xFF00CC00 : 0xFFFFFFFF, false);
        });
        //noinspection Convert2Lambda
        registerRenderer("slider", new Renderer<BasicSlider>() {
            @Override
            public void render(final BasicSlider widget) throws RenderException {
                GLRenderer.drawRect(widget.getArea().getX(), widget.getArea().getY(), widget.getArea().getWidth(),
                        widget.getArea().getHeight(), 0x34FFFFFF);
                GLRenderer.drawRect(widget.getArea().getX(), widget.getArea().getY(),
                        widget.getArea().getWidth() * widget.getAmountScrolled(), widget.getArea().getHeight(),
                        0x34770000);
                Helper.drawString(
                        String.format("%s: %s", widget.getText(), widget.getValue().get()),
                        (float) widget.getArea().getX() + 0.5F, (float) widget.getArea().getY() + 0.5F, 0xFFFFFFFF, false);
            }
        });
    }
}
