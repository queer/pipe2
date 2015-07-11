package me.curlpipesh.pipe.gui;

import lombok.Getter;
import lombok.Setter;

/**
 * This class must not be present in the final JAR. The configuration in
 * <tt>pom.xml</tt> should take care of this for you.
 *
 * @author c
 * @since 5/24/15
 */
@SuppressWarnings("unused")
public final class GuiScreen {
    @Getter
    @Setter
    private GuiModule currentModule = null;

    private static final GuiScreen instance = new GuiScreen();

    public void initGui() {
        currentModule.init();
    }

    public void drawScreen(int i, int j, float f) {
        currentModule.render(i, j, f);
    }

    public void mouseClicked(int mx, int my, int button) {
        currentModule.mouseDown(mx, my, button);
    }

    public void mouseDownDrag(int mx, int my, int button, long timeSinceLastClick) {
        currentModule.mouseDownMove(mx, my, button, timeSinceLastClick);
    }

    public void mouseReleased(int mx, int my, int button) {
        currentModule.mouseUp(mx, my, button);
    }

    public void keyPress(char c, int i) {
        currentModule.keypress(c, i);
    }

    public void a(int i, int j1, float f) {
        drawScreen(i, j1, f);
    }

    public void a(char c, int i) {
        keyPress(c, i);
    }

    public void a(int i, int j, int k) {
        mouseClicked(i, j, k);
    }

    public void b(int i, int j, int k) {
        mouseReleased(i, j, k);
    }

    public void a(int i, int j, int k, long l) {
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
