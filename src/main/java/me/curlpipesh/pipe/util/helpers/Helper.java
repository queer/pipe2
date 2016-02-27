package me.curlpipesh.pipe.util.helpers;

import me.curlpipesh.pipe.event.events.ChatMessage;
import me.curlpipesh.pipe.gui.GuiModule;
import me.curlpipesh.pipe.gui.GuiScreen;
import me.curlpipesh.pipe.util.Vec2;
import me.curlpipesh.pipe.util.Vec3;

import java.io.File;
import java.util.List;

/**
 * Helper class. Methods in here will be generated at runtime.
 *
 * @author c
 * @since 5/2/15
 */
@SuppressWarnings({"unused", "StaticVariableOfConcreteClass", "ClassWithOnlyPrivateConstructors", "NonFinalUtilityClass", "UtilityClassWithoutPrivateConstructor"})
public class Helper {
    private static final Vec3 entityVec = new Vec3(0, 0, 0);
    private static final Vec3 entityPrevVec = new Vec3(0, 0, 0);
    private static final Vec3 blockEntityVec = new Vec3(0, 0, 0);
    private static final Vec2 rotVec = new Vec2(0, 0);

    /**
     * Returns true if the world is null, false otherwise
     *
     * @return True if the world is null, false otherwise
     */
    public static boolean isWorldNull() {
        return false;
    }

    /**
     * Gets the instance of Minecraft
     *
     * @return The instance of Minecraft
     */
    public static Object getMinecraft() {
        return null;
    }

    /**
     * Returns the player. May be null
     *
     * @return The player
     */
    public static Object getPlayer() {
        return null;
    }

    /**
     * Returns the GameSettings instance
     *
     * @return The GameSettings instance
     */
    public static Object getGameSettings() {
        return null;
    }

    /**
     * Returns whether or not the in-game GUI is in debug mode
     *
     * @return True if the in-game GUI is in debug mode, false otherwise
     */
    public static boolean isIngameGuiInDebugMode() {
        return false;
    }

    /**
     * Returns the normal FontRenderer that the game uses
     *
     * @return The normal FontRenderer that the game uses
     */
    public static Object getFontRenderer() {
        return null;
    }

    /**
     * Returns the height of the font in use by the game
     *
     * @return The height of the font in use by the game
     */
    public static int getFontHeight() {
        return 0;
    }

    /**
     * Returns the width of the supplied String
     *
     * @param string The String whose width will be returned
     *
     * @return The width of the String, as determined by Minecraft's
     * FontRenderer
     */
    public static int getStringWidth(final String string) {
        return 0;
    }

    /**
     * Renders a String on the screen using Minecraft's FontRenderer
     *
     * @param string The String to render
     * @param x      The x-coordinate to render at
     * @param y      The y-coordinate to render at
     * @param color  The color to draw the String in. 0xAARRGGBB format.
     * @param shadow Whether or not the String should have a shadow drawn
     *               underneath it
     */
    public static void drawString(final String string, final float x, final float y, final int color, final boolean shadow) {
    }

    /**
     * Returns the current world loaded by the game. May be null
     *
     * @return The current world loaded by the game
     */
    public static Object getWorld() {
        return null;
    }

    /**
     * Returns the list of entities loaded into the game world.
     *
     * @return The list of entities loaded into the game world
     */
    public static List<?> getLoadedEntities() {
        return null;
    }

    /**
     * A {@link Vec3} representing the position of an entity in the world
     *
     * @param entity The entity whose position is needed
     *
     * @return A Vec3 containing the entity's position
     */
    public static Vec3 getEntityVec(final Object entity) {
        return null;
    }

    /**
     * A {@link Vec3} representing the previous position of an entity in the
     * world
     *
     * @param entity The entity whose position is needed
     *
     * @return A Vec3 containing the entity's position
     */
    public static Vec3 getEntityPrevVec(final Object entity) {
        return null;
    }

    /**
     * Returns the light brightness table for the current world. Note that any
     * changes to this must not be done without a backup of the initial state
     * thereof.
     *
     * @return The light brightness table for the current world
     */
    public static float[] getLightBrightnessTable() {
        return new float[] {};
    }

    /**
     * Returns whether or not the supplied entity is an instance of
     * EntityLiving
     *
     * @param entity The entity to check
     *
     * @return True if the entity is an instance of EntityLiving, false
     * otherwise
     */
    public static boolean isEntityLiving(final Object entity) {
        return false;
    }

    /**
     * Returns whether or not the supplied entity is an instance of
     * EntityAnimal
     *
     * @param entity The entity to check
     *
     * @return True if the entity is an instance of EntityAnimal, false
     * otherwise
     */
    public static boolean isEntityAnimal(final Object entity) {
        return false;
    }

    /**
     * Returns whether or not the supplied entity is an instance of
     * EntityMonster
     *
     * @param entity The entity to check
     *
     * @return True if the entity is an instance of EntityMonster, false
     * otherwise
     */
    public static boolean isEntityMonster(final Object entity) {
        return false;
    }

    /**
     * Returns whether or not the supplied entity is an instance of
     * EntityPlayer
     *
     * @param entity The entity to check
     *
     * @return True if the entity is an instance of EntityPlayer, false
     * otherwise
     */
    public static boolean isEntityPlayer(final Object entity) {
        return false;
    }

    /**
     * Returns the list of block entities loaded into the current game world
     *
     * @return The list of block entities loaded into the current game world
     */
    public static List<?> getLoadedBlockEntities() {
        return null;
    }

    /**
     * A {@link Vec3} representing the position of a block entity in the world
     *
     * @param blockEntity The block entity whose position is needed
     *
     * @return A Vec3 containing the block entity's position
     */
    public static Vec3 getBlockEntityVec(final Object blockEntity) {
        return null;
    }

    /**
     * Returns whether or not the supplied block entity is a chest
     *
     * @param blockEntity The block entity to check
     *
     * @return True if the block entity is a chest, false otherwise
     */
    public static boolean isBlockEntityChest(final Object blockEntity) {
        return false;
    }

    /**
     * Enables the lightmap for 3D rendering
     */
    public static void enableLightmap() {
    }

    /**
     * Disables the lightmap for 3D rendering
     */
    public static void disableLightmap() {
    }

    /**
     * Adds a message to the in-game chat
     *
     * @param message The message to be added
     */
    public static void addChatMessage(final String message) {
    }

    /**
     * Sends an in-game chat message
     *
     * @param message The message to be sent
     */
    public static void sendChatMessage(final String message) {
    }

    /**
     * Sends a packet to the server
     *
     * @param packet The packet to be sent. May be any valid packet.
     */
    public static void sendPacket(final Object packet) {
    }

    /**
     * Returns the current width of the screen
     *
     * @return The current width of the screen
     */
    public static int getWidth() {
        return 0;
    }

    /**
     * Returns the current height of the screen
     *
     * @return The current height of the screen
     */
    public static int getHeight() {
        return 0;
    }

    /**
     * Displays a {@link GuiScreen} on the screen. This method probably should
     * not be called unless you know what you're doing.
     *
     * @param gui The GuiScreen to display
     */
    public static void displayGuiScreen(final GuiScreen gui) {
    }

    /**
     * Displays a "Minecraft-native" GUI. Since this requires instantiating the
     * GuiScreens manually, usage of this method is not recommended.
     *
     * @param screen The GuiScreen to display
     */
    public static void mc_displayGuiScreen(final Object screen) {
    }

    /**
     * Displays a {@link GuiModule} on the screen.
     *
     * @param module The GuiModule to display
     */
    public static void displayGuiModule(final GuiModule module) {
    }

    /**
     * Returns the current scale of the game's GUI
     *
     * @return The current scale factor of the game's GUI
     */
    public static int getScale() {
        return 0;
    }

    /**
     * Returns whether or not view bobbing is enabled in the in-game settings.
     *
     * @return True if enabled, false otherwise
     */
    public static boolean isViewBobbingEnabled() {
        return false;
    }

    /**
     * Sets view bobbing to be either enabled or disabled
     *
     * @param state The new state of view bobbing
     */
    public static void setViewBobbing(final boolean state) {
    }

    /**
     * Returns the currently displayed GuiScreen (Minecraft GuiScreen, not the
     * implementation here).
     *
     * @return The currently displayed GuiScreen
     */
    public static Object getCurrentScreen() {
        return null;
    }

    /**
     * Returns whether or not the given entity is sneaking
     *
     * @param entity The entity to get the sneaking status of
     *
     * @return <tt>true</tt> if the entity is sneaking, <tt>false</tt>
     * otherwise
     */
    public static boolean isEntitySneaking(final Object entity) {
        return false;
    }

    /**
     * Sends a chat message directly. This method is not meant to be called by
     * anything outside of {@link ChatHelper}, because it bypasses
     * {@link ChatMessage} events firing, which can cause
     * a large number of issues.
     *
     * @param message The message to be sent
     */
    static void _sendChatMessage(final String message) {
    }

    /**
     * Returns the <tt>Container</tt> object representing the player's
     * inventory.
     *
     * @return The player's inventory container object
     */
    public static Object getInventoryContainer() {
        return null;
    }

    /**
     * Returns the ItemStack in the given slot
     *
     * @param player unused
     * @param slot   The slot to get the ItemStack from
     *
     * @return The ItemStack in the slot at the given index
     */
    public static Object getStackInSlot(final Object player, final int slot) {
        return null;
    }

    /**
     * Returns the currently selected inventory slot
     *
     * @return The currently selected inventory slot
     */
    public static int getCurrentSlot() {
        return 0;
    }

    /**
     * Returns the file that represents the directory where game data is
     * stored.
     *
     * @return The Minecraft data directory
     */
    public static File getMinecraftDataDir() {
        return null;
    }

    /**
     * Returns the version of Minecraft that was launched
     *
     * @return The version of Minecraft that was launched
     */
    public static String getMinecraftVersion() {
        return null;
    }

    /**
     * Sends a tab-complete packet to the server
     *
     * @param toComplete The string to be completed
     */
    public static void sendTabComplete(final String toComplete) {
    }

    /**
     * Returns the given entity's pitch/yaw.
     *
     * @param entity Entity to check
     * @return The entity's rotations
     */
    public static Vec2 getEntityRotation(final Object entity) {
        return null;
    }
}
