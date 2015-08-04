package me.curlpipesh.pipe.util;

import me.curlpipesh.bytecodetools.util.ClassEnumerator;
import me.curlpipesh.pipe.Pipe;

import java.util.Arrays;
import java.util.List;

/**
 * @author c
 * @since 7/11/15
 */
public final class ClassMapper {
    private static List<Class<?>> MAPPED_CLASSES;

    private static boolean mapped = false;

    public static void map() {
        if(mapped) {
            Pipe.getLogger().warning("Asked to map classes, but they're already mapped!");
            return;
        }
        mapped = true;
        MAPPED_CLASSES = Arrays.asList(ClassEnumerator.getClassesFromPackage(Pipe.class));
    }

    public static List<Class<?>> getMappedClasses() {
        return MAPPED_CLASSES;
    }
}
