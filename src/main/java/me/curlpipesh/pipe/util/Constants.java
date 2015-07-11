package me.curlpipesh.pipe.util;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Constants about the game. These will (most likely) change from version to
 * version.
 *
 * @author c
 * @since 4/30/15
 */
@SuppressWarnings("unused")
public class Constants {
    /**
     * Mappings of obfuscated classes to usable names
     */
    private static final List<ObfusClass> OBFUSCATED_CLASSES = new CopyOnWriteArrayList<>();

    @SuppressWarnings("SpellCheckingInspection")
    public static final String
            /**
             * Dummy field to make sure that IntelliJ's formatter does it's job
             * correctly. ;-;
             */
            DUMMY = "WHATEVER",
    /**
     * Name of the Minecraft class
     */
    MINECRAFT = "ave",

    /**
     * Name of the GuiIngame class
     */
    GUIINGAME = "avo",

    /**
     * Name of the FontRenderer class
     */
    FONTRENDERER = "avn",

    /**
     * Name of the GameSettings class
     */
    GAMESETTINGS = "avh",

    /**
     * Name of the World class
     */
    WORLD = "bdb",

    /**
     * Name of the AbstractWorld class
     */
    ABSTRACTWORLD = "adm",

    /**
     * Name of the WorldProvider class
     */
    WORLDPROVIDER = "anm",

    /**
     * Name of the Entity class
     */
    ENTITY = "pk",

    /**
     * Name of the EntityClientPlayer class
     */
    ENTITYTHEPLAYER = "bew",

    /**
     * Name of the ScaledResolution class
     */
    SCALEDRESOLUTION = "avr",

    /**
     * Name of the EntityRenderer class
     */
    ENTITYRENDERER = "bfk",

    /**
     * Name of the EntityLiving class
     */
    ENTITYLIVING = "ps",

    /**
     * Name of the EntityLivingBase class
     */
    ENTITYLIVINGBASE = "pr",

    /**
     * Name of the EntityCreature class
     */
    ENTITYCREATURE = "py",

    /**
     * Name of the EntityAgeable class
     */
    ENTITYAGEABLE = "ph",

    /**
     * Name of the EntityAnimal class
     */
    ENTITYANIMAL = "tm",

    /**
     * Name of the EntityMonster class
     */
    ENTITYMONSTER = "vv",

    /**
     * Name of the BlockEntity class (formerly TileEntity)
     */
    BLOCKENTITY = "akw",

    /**
     * Name of the BlockEntityChest class
     */
    BLOCKENTITYCHEST = "aky",

    /**
     * Name of the BlockEntityEnderChest class
     */
    BLOCKENTITYENDERCHEST = "alf",

    /**
     * Name of the BlockPos class
     */
    BLOCKPOS = "cj",

    /**
     * Name of the Vec3i class
     */
    VEC3I = "df",

    /**
     * Name of the Gui class
     */
    GUI = "avp",

    /**
     * Name of the GuiScreen class
     */
    GUISCREEN = "axu",

    /**
     * Name of the GuiMainMenu class
     */
    GUIMAINMENU = "aya",

    /**
     * Name of the IChatComponent class
     */
    ICHATCOMPONENT = "eu",

    /**
     * Name of the ChatComponentStyle class
     */
    CHATCOMPONENTSTYLE = "es",

    /**
     * Name of the ChatComponentText class
     */
    CHATCOMPONENTTEXT = "fa",

    /**
     * Name of the InventoryPlayer class
     */
    INVENTORYPLAYER = "wm",

    /**
     * Name of the IInventory class
     */
    IINVENTORY = "og",

    /**
     * Name of the ItemStack class
     */
    ITEMSTACK = "zx",

    /**
     * Name of the Container class. Used for some inventory stuff
     */
    CONTAINER = "xi",

    /**
     * Name of the ContainerPlayer class
     */
    CONTAINERPLAYER = "xy",

    /**
     * Name of the EntityPlayer class
     */
    ENTITYPLAYER = "wn",

    /**
     * Name of the Slot class. Used for some inventory stuff
     */
    SLOT = "yg",

    /**
     * Name of the NetworkManager class
     */
    NETWORKMANAGER = "ek",

    /**
     * Name of the Packet class
     */
    PACKET = "ff",

    /**
     * Name of the PacketClientWindowClick class
     */
    PACKETWINDOWCLICK_CLIENT = "ik",

    /**
     * Name of the NetHandlerPlayClient class
     */
    NETHANDLERPLAYCLIENT = "bcy",

    /**
     * Name of the Item class
     */
    ITEM = "zw",

    /**
     * Name of the PacketClientChatMessage class
     */
    PACKETCHATMESSAGE_CLIENT = "ie",

    /**
     * Name of the GuiChatBox class
     */
    GUICHAT = "awv",

    /**
     * Name of the GuiOptions class
     */
    GUIOPTIONS = "axn",

    /**
     * Name of the GuiSingleplayer class
     */
    GUISINGLEPLAYER = "axv",

    /**
     * Name of the GuiMultiplayer class
     */
    GUIMULTIPLAYER = "azh",

    /**
     * Name of the DamageSource class
     */
    DAMAGESOURCE = "ow",

    /**
     * Name of the NBTBase class
     */
    NBTBASE = "eb",
    /**
     * Name of the NBTTagString class
     */
    NBTTAGSTRING = "ea",
    /**
     * Name of the NBTTagShort class
     */
    NBTTAGSHORT = "dz",
    /**
     * Name of the NBTTagLong class
     */
    NBTTAGLONG = "dv",
    /**
     * Name of the NBTTagList class
     */
    NBTTAGLIST = "du",
    /**
     * Name of the NBTTagInt class
     */
    NBTTAGINT = "dt",
    /**
     * Name of the NBTTagIntArray class
     */
    NBTTAGINTARRAY = "ds",
    /**
     * Name of the NBTTagFloat class
     */
    NBTTAGFLOAT = "dr",
    /**
     * Name of the NBTTagEnd class
     */
    NBTTAGEND = "dq",
    /**
     * Name of the NBTTagDouble class
     */
    NBTTAGDOUBLE = "dp",
    /**
     * Name of the NBTTagCompound class
     */
    NBTTAGCOMPOUND = "dn",
    /**
     * Name of the NBTTagByte class
     */
    NBTTAGBYTE = "dm",
    /**
     * Name of the NBTTagByteArray class
     */
    NBTTAGBYTEARRAY = "dl";

    /**
     * Gets an {@link ObfusClass} representation of a class based on a
     * non-obfuscated name
     *
     * @param name Non-obfuscated class name
     * @return <tt>ObfusClass</tt> representation of the obfuscated class
     */
    public static ObfusClass getByName(String name) {
        Optional<ObfusClass> o = OBFUSCATED_CLASSES.stream().filter(c -> c.getRealName().equalsIgnoreCase(name))
                .findFirst();
        if(o.isPresent()) {
            return o.get();
        } else {
            throw new IllegalArgumentException("No such class found: " + name);
        }
    }

    private static void add(String name, String obfusName) {
        OBFUSCATED_CLASSES.add(new ObfusClass(name, obfusName));
    }

    static {
        add("Minecraft", MINECRAFT);
        add("GuiIngame", GUIINGAME);
        add("FontRenderer", FONTRENDERER);
        add("GameSettings", GAMESETTINGS);
        add("World", WORLD);
        add("AbstractWorld", ABSTRACTWORLD);
        add("WorldProvider", WORLDPROVIDER);
        add("Entity", ENTITY);
        add("EntityThePlayer", ENTITYTHEPLAYER);
        add("ScaledResolution", SCALEDRESOLUTION);
        add("EntityRenderer", ENTITYRENDERER);
        add("EntityLivingBase", ENTITYLIVINGBASE);
        add("EntityLiving", ENTITYLIVING);
        add("EntityCreature", ENTITYCREATURE);
        add("EntityAgeable", ENTITYAGEABLE);
        add("EntityAnimal", ENTITYANIMAL);
        add("EntityMonster", ENTITYMONSTER);
        add("BlockEntity", BLOCKENTITY);
        add("BlockEntityChest", BLOCKENTITYCHEST);
        add("BlockEntityEnderChest", BLOCKENTITYENDERCHEST);
        add("BlockPos", BLOCKPOS);
        add("Vec3i", VEC3I);
        add("Gui", GUI);
        add("GuiScreen", GUISCREEN);
        add("GuiMainMenu", GUIMAINMENU);
        add("IChatComponent", ICHATCOMPONENT);
        add("ChatComponentStyle", CHATCOMPONENTSTYLE);
        add("ChatComponentText", CHATCOMPONENTTEXT);
        add("IInventory", IINVENTORY);
        add("InventoryPlayer", INVENTORYPLAYER);
        add("ItemStack", ITEMSTACK);
        add("Container", CONTAINER);
        add("ContainerPlayer", CONTAINERPLAYER);
        add("EntityPlayer", ENTITYPLAYER);
        add("Slot", SLOT);
        add("NetworkManager", NETWORKMANAGER);
        add("Packet", PACKET);
        add("PacketClientWindowClick", PACKETWINDOWCLICK_CLIENT);
        add("NetHandlerPlayClient", NETHANDLERPLAYCLIENT);
        add("Item", ITEM);
        add("PacketClientChatMessage", PACKETCHATMESSAGE_CLIENT);
        add("GuiChat", GUICHAT);
        add("GuiSingleplayer", GUISINGLEPLAYER);
        add("GuiMultiplayer", GUIMULTIPLAYER);
        add("GuiOptions", GUIOPTIONS);
        add("DamageSource", DAMAGESOURCE);
        add("NBTBase", NBTBASE);
        add("NBTTagByte", NBTTAGBYTE);
        add("NBTTagByteArray", NBTTAGBYTEARRAY);
        add("NBTTagCompound", NBTTAGCOMPOUND);
        add("NBTTagDouble", NBTTAGDOUBLE);
        add("NBTTagEnd", NBTTAGEND);
        add("NBTTagFloat", NBTTAGFLOAT);
        add("NBTTagInt", NBTTAGINT);
        add("NBTTagIntArray", NBTTAGINTARRAY);
        add("NBTTagList", NBTTAGLIST);
        add("NBTTagLong", NBTTAGLONG);
        add("NBTTagShort", NBTTAGSHORT);
        add("NBTTagString", NBTTAGSTRING);
    }
}
