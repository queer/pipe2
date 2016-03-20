package lgbt.audrey.pipe.gui;

import lgbt.audrey.pipe.util.helpers.Helper;
import lombok.Getter;
import lombok.Setter;
import lgbt.audrey.pipe.util.helpers.Helper;
import org.lwjgl.opengl.GL11;

/**
 * This class must not be present in the final JAR. The configuration in
 * <tt>pom.xml</tt> should take care of this for you.
 *
 * @author c
 * @since 5/24/15
 */
@SuppressWarnings("unused")
public final class GuiScreen {
    @SuppressWarnings("FieldMayBeFinal")
    @Getter
    @Setter
    private GuiModule currentModule;

    @SuppressWarnings("StaticVariableOfConcreteClass")
    private static final GuiScreen instance = new GuiScreen();

    public void initGui() {
        currentModule.init();
    }

    public void drawScreen(final int i, final int j, final float f) {
        Helper.disableLightmap();
        GL11.glDisable(GL11.GL_LIGHTING);
        currentModule.render(i, j, f);
    }

    public void mouseClicked(final int mx, final int my, final int button) {
        currentModule.mouseDown(mx, my, button);
    }

    public void mouseDownDrag(final int mx, final int my, final int button, final long timeSinceLastClick) {
        currentModule.mouseDownMove(mx, my, button, timeSinceLastClick);
    }

    public void mouseReleased(final int mx, final int my, final int button) {
        currentModule.mouseUp(mx, my, button);
    }

    public void keyPress(final char c, final int i) {
        currentModule.keypress(c, i);
    }

    public void a(final int i, final int j1, final float f) {
        drawScreen(i, j1, f);
    }

    public void a(final char c, final int i) {
        keyPress(c, i);
    }

    public void a(final int i, final int j, final int k) {
        mouseClicked(i, j, k);
    }

    public void b(final int i, final int j, final int k) {
        mouseReleased(i, j, k);
    }

    public void a(final int i, final int j, final int k, final long l) {
        mouseDownDrag(i, j, k, l);
    }

    public void b() {
        initGui();
    }

    public boolean doesGuiPauseGame() {
        return currentModule.isPauseGame();
    }

    public boolean d() {
        return doesGuiPauseGame();
    }

    public static GuiScreen getInstance() {
        return instance;
    }
}
