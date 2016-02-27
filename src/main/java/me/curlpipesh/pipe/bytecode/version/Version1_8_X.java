package me.curlpipesh.pipe.bytecode.version;

import me.curlpipesh.pipe.bytecode.Generator;
import me.curlpipesh.pipe.bytecode.Injector;
import me.curlpipesh.pipe.bytecode.Redefiner;
import me.curlpipesh.pipe.bytecode.Version;
import me.curlpipesh.pipe.bytecode.map.ClassMap;
import me.curlpipesh.pipe.bytecode.v1_8_X.definers.HelperRedefiner;
import me.curlpipesh.pipe.bytecode.v1_8_X.generators.GuiScreenGenerator;
import me.curlpipesh.pipe.bytecode.v1_8_X.injectors.*;

/**
 * @author audrey
 * @since 12/18/15.
 */
public class Version1_8_X implements Version {
    @Override
    public String getVersion() {
        return "1.8.X";
    }

    @Override
    public Injector[] getInjectors() {
        return new Injector[]{
                new BlockEntityInjector(ClassMap.getClassByName("BlockEntity")),
                new BlockSoulSandInjector(ClassMap.getClassByName("BlockSoulSand")),
                new EntityRendererInjector(ClassMap.getClassByName("EntityRenderer")),
                //new GuiChatInjector(ClassMap.getClassByName("GuiChat")),
                new GuiIngameInjector(ClassMap.getClassByName("GuiIngame")),
                //new GuiMainMenuInjector(ClassMap.getClassByName("GuiMainMenu")),
                new MinecraftInjector(ClassMap.getClassByName("Minecraft")),
                new NetworkManagerInjector(ClassMap.getClassByName("NetworkManager")),
                new PacketBufferInjector(ClassMap.getClassByName("PacketBuffer")),
                new WorldProviderInjector(ClassMap.getClassByName("WorldProvider")),
                new FramebufferInjector(ClassMap.getClassByName("Framebuffer"))
        };
    }

    @Override
    public Generator[] getGenerators() {
        return new Generator[] {
                new GuiScreenGenerator()
        };
    }

    @Override
    public Redefiner[] getRedefiners() {
        return new Redefiner[] {
                new HelperRedefiner()
        };
    }
}
