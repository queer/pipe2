package me.curlpipesh.pipe;

import me.curlpipesh.pipe.bytecode.definers.HelperRedefiner;
import me.curlpipesh.pipe.bytecode.injectors.*;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

/**
 * A reimplementation of the <a href="https://github.com/curlpipesh/pipe/">Pipe</a>
 * mod for the game <tt>Minecraft</tt>. Done mainly to improve on the structure
 * and implementation of its predecessor.
 *
 * @author c
 * @since 7/10/15
 */
public class Agent {
    public static void premain(String agentArgs, Instrumentation inst) {
        Pipe.getLogger().info("Adding transformers!");
        inst.addTransformer(new BlockEntityInjector());
        inst.addTransformer(new EntityRendererInjector());
        inst.addTransformer(new GuiChatInjector());
        inst.addTransformer(new GuiIngameInjector());
        inst.addTransformer(new GuiMainMenuInjector());
        inst.addTransformer(new MinecraftInjector());
        inst.addTransformer(new WorldProviderInjector());
        inst.addTransformer(new NetworkManagerInjector());

        Pipe.getLogger().info("Attempting to redefine classes!");
        try {
            inst.redefineClasses(new HelperRedefiner().redefine());
        } catch(ClassNotFoundException | UnmodifiableClassException e) {
            Pipe.getLogger().severe("Class redefinition failed! Not much you can do about this one.");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
