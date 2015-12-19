package me.curlpipesh.pipe.gui.api.view.render.theme;

import me.curlpipesh.pipe.gui.api.model.base.Container;
import me.curlpipesh.pipe.gui.api.model.base.interfaces.IContainer;
import me.curlpipesh.pipe.gui.api.model.base.interfaces.IWidget;
import me.curlpipesh.pipe.gui.api.view.render.Renderer;
import me.curlpipesh.pipe.gui.api.view.render.state.RenderException;
import me.curlpipesh.pipe.util.helpers.Helper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static me.curlpipesh.pipe.util.GLRenderer.scissor;
import static org.lwjgl.opengl.GL11.*;

/**
 * The basic implementation of
 * {@link ITheme}. This class
 * provides the functionality that is required by its interface, and is a
 * suitable base to start making theme classes from.
 */
public abstract class Theme implements ITheme {
    /**
     * The name of this theme. This <b>must</b> be just a name, nothing more.
     */
    private final String name;

    /**
     * A {@link Map} of
     * {@link Renderer}s mapped to
     * type tags that specify which type of widget the corresponding
     * {@code Renderer} is supposed to render.
     */
    private final Map<String, Renderer<?>> rendererMap = new ConcurrentHashMap<>();

    /**
     * Creates a new
     * {@link Theme} with the
     * give name.
     *
     * @param name The name of this theme.
     */
    protected Theme(String name) {
        this.name = name;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends IWidget> void render(T widget) throws RenderException {
        if(widget instanceof Container) {
            renderContainer((Container) widget);
        } else {
            Helper.disableLightmap();
            glDisable(GL_LIGHTING);
            ((Renderer<T>) rendererMap.get(widget.getTagValue("type"))).render(widget);
            glEnable(GL_LIGHTING);
            Helper.enableLightmap();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends IContainer> void renderContainer(T container) throws RenderException {
        ((Renderer<T>) rendererMap.get(container.getTagValue("type"))).render(container);
        if(container.getMinimizeControl() != null) {
            if(container.getMinimizeControl().getState()) {
                return;
            }
        }
        glPushAttrib(GL_ALL_ATTRIB_BITS);
        glPushMatrix();
        glEnable(GL_SCISSOR_TEST);
        scissor(container.getArea().getX(), container.getArea().getY(),
                container.getArea().getWidth(), container.getArea().getHeight());
        {
            container.getChildren().stream().sequential().forEach(widget -> {
                try {
                    render(widget);
                } catch(RenderException e) {
                    e.printStackTrace();
                }
            });
        }
        glDisable(GL_SCISSOR_TEST);
        glPopMatrix();
        glPopAttrib();
    }

    @Override
    public void registerRenderer(String rendererTypeTag, Renderer<? extends IWidget> renderer) throws IllegalArgumentException {
        if(rendererMap.containsKey(rendererTypeTag)) {
            if(rendererMap.get(rendererTypeTag) != null) {
                throw new IllegalArgumentException(String.format("The type tag %s already has a renderer registered!",
                        rendererTypeTag));
            } else {
                rendererMap.put(rendererTypeTag, renderer);
            }
        } else {
            rendererMap.put(rendererTypeTag, renderer);
        }
    }

    @Override
    public String getName() {
        return name;
    }
}
