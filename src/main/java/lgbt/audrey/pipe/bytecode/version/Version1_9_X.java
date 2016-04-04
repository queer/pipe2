package lgbt.audrey.pipe.bytecode.version;

import lgbt.audrey.pipe.bytecode.Generator;
import lgbt.audrey.pipe.bytecode.Injector;
import lgbt.audrey.pipe.bytecode.Redefiner;
import lgbt.audrey.pipe.bytecode.Version;
import lgbt.audrey.pipe.bytecode.map.ClassMap;
import lgbt.audrey.pipe.bytecode.v1_9_X.definers.HelperRedefiner;
import lgbt.audrey.pipe.bytecode.v1_9_X.generators.GuiScreenGenerator;
import lgbt.audrey.pipe.bytecode.v1_9_X.injectors.*;

/**
 * @author audrey
 * @since 12/18/15.
 */
public class Version1_9_X implements Version {
    @Override
    public String getVersion() {
        return "1.9.X-SNAPSHOT";
    }

    @Override
    public Injector[] getInjectors() {
        return new Injector[] {
                new BlockEntityInjector(ClassMap.getClassByName("BlockEntity")),
                new EntityRendererInjector(ClassMap.getClassByName("EntityRenderer")),
                //new GuiChatInjector(ClassMap.getClassByName("GuiChat")),
                new GuiIngameInjector(ClassMap.getClassByName("GuiIngame")),
                new GuiMainMenuInjector(ClassMap.getClassByName("GuiMainMenu")),
                new MinecraftInjector(ClassMap.getClassByName("Minecraft")),
                new NetworkManagerInjector(ClassMap.getClassByName("NetworkManager")),
                new PacketBufferInjector(ClassMap.getClassByName("PacketBuffer")),
                new WorldProviderInjector(ClassMap.getClassByName("WorldProvider")),
                new FramebufferInjector(ClassMap.getClassByName("Framebuffer")),
                new RenderGlobalInjector(ClassMap.getClassByName("RenderGlobal"))
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
