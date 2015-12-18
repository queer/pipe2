package me.curlpipesh.pipe;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import me.curlpipesh.pipe.bytecode.definers.HelperRedefiner;
import me.curlpipesh.pipe.bytecode.injectors.*;
import me.curlpipesh.pipe.bytecode.map.ClassMap;
import me.curlpipesh.pipe.bytecode.map.MappedClass;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.ArrayList;

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
        String propertyMappings = System.getProperty("pipe.mappings.path", "null");
        if(propertyMappings.equals("null")) {
            throw new IllegalArgumentException("No mappings path passed! Restart with -Dpipe.mappings.path=/path/to/mapping.json");
        }

        Pipe.getLogger().info("Reading class mappings!");

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            ClassMap.getMappedClasses().addAll(gson.fromJson(Files.lines(new File(propertyMappings).toPath())
                    .reduce((t, u) -> t + u).get(), new TypeToken<ArrayList<MappedClass>>(){}.getType()));
        } catch(IOException e) {
            Pipe.getLogger().severe("Class map reading failed!");
            throw new RuntimeException(e);
        }

        Pipe.getLogger().info("Adding transformers!");
        inst.addTransformer(new BlockEntityInjector(ClassMap.getClassByName("BlockEntity")));
        inst.addTransformer(new EntityRendererInjector(ClassMap.getClassByName("EntityRenderer")));
        //inst.addTransformer(new GuiChatInjector(ClassMap.getClassByName("GuiChat")));
        inst.addTransformer(new GuiIngameInjector(ClassMap.getClassByName("GuiIngame")));
        //inst.addTransformer(new GuiMainMenuInjector(ClassMap.getClassByName("GuiMainMenu")));
        inst.addTransformer(new MinecraftInjector(ClassMap.getClassByName("Minecraft")));
        inst.addTransformer(new NetworkManagerInjector(ClassMap.getClassByName("NetworkManager")));
        inst.addTransformer(new PacketBufferInjector(ClassMap.getClassByName("PacketBuffer")));
        inst.addTransformer(new WorldProviderInjector(ClassMap.getClassByName("WorldProvider")));

        Pipe.getLogger().info("Attempting to redefine classes!");
        try {
            inst.redefineClasses(new HelperRedefiner().redefine());
        } catch(ClassNotFoundException | UnmodifiableClassException e) {
            Pipe.getLogger().severe("Class redefinition failed! Not much you can do about this one.");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void defineClass(ClassLoader cl, byte[] clazz, String fullName) {
        Method define;
        try {
            define = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, int.class, int.class);
            define.setAccessible(true);
            define.invoke(cl, fullName, clazz, 0, clazz.length);
        } catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
