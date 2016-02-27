package me.curlpipesh.pipe;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import me.curlpipesh.pipe.bytecode.Injector;
import me.curlpipesh.pipe.bytecode.Redefiner;
import me.curlpipesh.pipe.bytecode.Version;
import me.curlpipesh.pipe.bytecode.map.ClassMap;
import me.curlpipesh.pipe.bytecode.map.MappedClass;
import me.curlpipesh.pipe.bytecode.version.Version1_8_X;
import me.curlpipesh.pipe.bytecode.version.Version1_9_X;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A reimplementation of the <a href="https://github.com/curlpipesh/pipe/">Pipe</a>
 * mod for the game <tt>Minecraft</tt>. Done mainly to improve on the structure
 * and implementation of its predecessor.
 *
 * @author c
 * @since 7/10/15
 */
public final class Agent {
    private static final Map<String, Version> versions = new HashMap<>();

    static {
        versions.put("1_8_X", new Version1_8_X());
        versions.put("1.8.X", new Version1_8_X());
        versions.put("1_9_X", new Version1_9_X());
        versions.put("1.9.X", new Version1_9_X());
    }

    private Agent() {
    }

    public static void premain(final String agentArgs, final Instrumentation inst) {
        final String propertyMappings = System.getProperty("pipe.mappings.path", "null");
        final String propertyVersion = System.getProperty("pipe.game.version", "null");
        Pipe.getLogger().info("Using mappings '" + propertyMappings + "' for game version '" + propertyVersion + '\'');
        if(propertyMappings.equals("null")) {
            throw new IllegalArgumentException("No mappings path passed! Restart with -Dpipe.mappings.path=/path/to/mapping.json");
        }
        if(propertyVersion.equals("null")) {
            throw new IllegalArgumentException("No version passed! Restart with -Dpipe.game.version=MAJOR_MINOR_X (Ex. 1_9_X)");
        }
        if(!versions.containsKey(propertyVersion)) {
            throw new IllegalArgumentException("Invalid version passed!");
        }
        Pipe.getInstance().setVersion(versions.get(propertyVersion));

        Pipe.getLogger().info("Reading class mappings!");

        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            ClassMap.getMappedClasses().addAll(gson.fromJson(Files.lines(new File(propertyMappings).toPath())
                    .reduce((t, u) -> t + u).get(), new TypeToken<ArrayList<MappedClass>>() {
            }.getType()));
        } catch(final IOException e) {
            Pipe.getLogger().severe("Class map reading failed!");
            throw new RuntimeException(e);
        }

        Pipe.getLogger().info("Adding transformers!");

        for(final Injector injector : Pipe.getInstance().getVersion().getInjectors()) {
            inst.addTransformer(injector);
            Pipe.getLogger().info("Added Injector: " + injector.getClassToInject().getDeobfuscatedName() + " : " + injector.getClassToInject().getObfuscatedName());
        }

        // TODO: More generic
        if(Pipe.getInstance().getVersion() instanceof Version1_9_X) {
            try {
                Class.forName(ClassMap.getClassByName("EntityRenderer").getObfuscatedName());
            } catch(final ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        Pipe.getLogger().info("Attempting to redefine classes!");
        try {
            for(final Redefiner r : Pipe.getInstance().getVersion().getRedefiners()) {
                inst.redefineClasses(r.redefine());
            }
        } catch(ClassNotFoundException | UnmodifiableClassException e) {
            Pipe.getLogger().severe("Class redefinition failed! Not much you can do about this one.");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void defineClass(final ClassLoader cl, final byte[] clazz, final String fullName) {
        final Method define;
        try {
            define = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, int.class, int.class);
            define.setAccessible(true);
            define.invoke(cl, fullName, clazz, 0, clazz.length);
        } catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
