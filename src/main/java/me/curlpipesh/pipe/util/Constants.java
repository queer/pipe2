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
    MINECRAFT = "ave",
    GUIINGAME = "avo",
    FONTRENDERER = "avn",
    GAMESETTINGS = "avh",
    WORLD = "bdb",
    ABSTRACTWORLD = "adm",
    WORLDPROVIDER = "anm",
    ENTITY = "pk",
    ENTITYTHEPLAYER = "bew",
    SCALEDRESOLUTION = "avr",
    ENTITYRENDERER = "bfk",
    ENTITYLIVING = "ps",
    ENTITYLIVINGBASE = "pr",
    ENTITYCREATURE = "py",
    ENTITYAGEABLE = "ph",
    ENTITYANIMAL = "tm",
    ENTITYMONSTER = "vv",
    BLOCKENTITY = "akw",
    BLOCKENTITYCHEST = "aky",
    BLOCKENTITYENDERCHEST = "alf",
    BLOCKPOS = "cj",
    VEC3I = "df",
    GUI = "avp",
    GUISCREEN = "axu",
    GUIMAINMENU = "aya",
    ICHATCOMPONENT = "eu",
    CHATCOMPONENTSTYLE = "es",
    CHATCOMPONENTTEXT = "fa",
    INVENTORYPLAYER = "wm",
    IINVENTORY = "og",
    ITEMSTACK = "zx",
    CONTAINER = "xi",
    CONTAINERPLAYER = "xy",
    ENTITYPLAYER = "wn",
    SLOT = "yg",
    NETWORKMANAGER = "ek",
    PACKET = "ff",
    PACKETWINDOWCLICK_CLIENT = "ik",
    NETHANDLERPLAYCLIENT = "bcy",
    ITEM = "zw",
    PACKETCHATMESSAGE_CLIENT = "ie",
    GUICHAT = "awv",
    GUIOPTIONS = "axn",
    GUISINGLEPLAYER = "axv",
    GUIMULTIPLAYER = "azh",
    DAMAGESOURCE = "ow",
    NBTBASE = "eb",
    NBTTAGSTRING = "ea",
    NBTTAGSHORT = "dz",
    NBTTAGLONG = "dv",
    NBTTAGLIST = "du",
    NBTTAGINT = "dt",
    NBTTAGINTARRAY = "ds",
    NBTTAGFLOAT = "dr",
    NBTTAGEND = "dq",
    NBTTAGDOUBLE = "dp",
    NBTTAGCOMPOUND = "dn",
    NBTTAGBYTE = "dm",
    NBTTAGBYTEARRAY = "dl",
    PACKETHANDSHAKE_CLIENT = "jc",
    ENUMCONNECTIONSTATE = "el";

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
            String message = "No such class found: \"" + name + "\". ";
            if(name.length() > 3) {
                message += "The class name specified is longer than three letters. This probably means that you're " +
                        "trying to use the wrong deobfuscated name.";
            } else {
                message += "The class name specified was short enough to be an obfuscated name, but you can't use " +
                        "the obfuscated class names for this method.";
            }
            throw new IllegalArgumentException(message);
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
