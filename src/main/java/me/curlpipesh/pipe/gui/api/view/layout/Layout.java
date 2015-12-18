package me.curlpipesh.pipe.gui.api.view.layout;

import lombok.Getter;
import lombok.Setter;
import me.curlpipesh.pipe.gui.api.model.base.Widget;
import me.curlpipesh.pipe.gui.api.model.base.interfaces.IContainer;
import me.curlpipesh.pipe.gui.api.model.base.interfaces.IWidget;
import me.curlpipesh.pipe.gui.api.view.layout.arrangers.Arranger;
import me.curlpipesh.pipe.gui.api.view.layout.resizers.Resizer;

import java.util.List;

/**
 * A more concrete {@link ILayout}. This class takes care of having an
 * {@link Arranger} and a
 * {@link Resizer} field, so
 * that the only things that need to be done in a subclass are provision of
 * Arranger/Resizer implementations, and an implementation of
 * {@link #getPadding()}, so that widgets can be moved around accurately.
 */
public abstract class Layout implements ILayout {
    /**
     * The {@link Arranger}
     * implementation that this {@link Layout} uses.
     */
    @Getter
    @Setter
    private Arranger arranger;

    /**
     * The {@link Resizer}
     * implementation that this {@link Layout} uses.
     */
    @Getter
    @Setter
    private Resizer resizer;

    /**
     * Arranges the widgets that are passed in, if and only if the
     * {@link #arranger} is not {@code null}.
     *
     * @param children The {@link Widget}s
     */
    @Override
    public final void layout(IContainer container, List<IWidget> children) {
        if(arranger == null) {
            return;
        }
        arranger.arrange(container, this, children);
    }

    /**
     * Resizes the widgets that are passed in, if and only if the
     * {@link #resizer} is not {@code null}.
     *
     * @param children The {@link Widget}s
     */
    @Override
    public final void resize(IContainer container, List<IWidget> children) {
        if(resizer == null) {
            return;
        }
        resizer.resize(container, this, children);
    }
}
