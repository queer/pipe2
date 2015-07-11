package me.curlpipesh.pipe.gui;

/**
 * Since we can't make classes dynamically extend Minecraft's `GuiScreen` class
 * at runtime (due to JVM limitations), we can instead generate one class that
 * does extend it, and then just "plug in" different "modules" to control what
 * renders on a given "screen."
 *
 * @author c
 * @since 5/24/15
 */
public interface GuiModule {
    /**
     * Initializes the <tt>GuiModule</tt>. Overrides of this method must take
     * care of things such as component initialization (with whatever GUI
     * toolkit one happens to be using).
     */
    void init();

    /**
     * Actually renders the <tt>module</tt> to the screen.
     *
     * @param mx Scaled x-coordinate of the mouse
     * @param my Scaled y-coordinate of the mouse
     * @param ptt Partial tick time (?)
     */
    void render(int mx, int my, float ptt);

    /**
     * Handles keypresses.
     *
     * @param c The <tt>char</tt> of the key that was pressed
     * @param k The <tt>int</tt> id of the key that was pressed. Corresponds to
     *          values in {@link org.lwjgl.input.Keyboard}.
     */
    void keypress(char c, int k);

    /**
     * Called whenever a mouse button is pressed (but <b>NOT</b> released).
     *
     * @param mx Scaled x-coordinate of the mouse
     * @param my Scaled y-coordinate of the mouse
     * @param mb Mouse button that was pressed
     */
    void mouseDown(int mx, int my, int mb);

    /**
     * Called when a mouse button is down and the mouse is moved.
     *
     * @param mx Scaled x-coordinate of the mouse
     * @param my Scaled y-coordinate of the mouse
     * @param mb Mouse button that was pressed
     * @param t Time since the mouse button was last pressed (?)
     */
    void mouseDownMove(int mx, int my, int mb, long t);

    /**
     * Called when a mouse button that was pressed is released.
     *
     * @param mx Scaled x-coordinate of the mouse
     * @param my Scaled y-coordinate of the mouse
     * @param mb Mouse button that was pressed
     */
    void mouseUp(int mx, int my, int mb);

    /**
     * Whether or not the module should pause the game when it is displayed
     *
     * @return <tt>true</tt> if the game should pause, <tt>false</tt> if not.
     */
    boolean isPauseGame();
}
