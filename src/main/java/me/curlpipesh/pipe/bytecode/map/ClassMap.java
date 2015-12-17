package me.curlpipesh.pipe.bytecode.map;

import lombok.Getter;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author audrey
 * @since 12/17/15.
 */
public class ClassMap {
    @Getter
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static final List<MappedClass> mappedClasses = new CopyOnWriteArrayList<>();

    public static MappedClass getClassByName(String deobfuscatedName) {
        Optional<MappedClass> c = mappedClasses.stream().filter(m -> m.getDeobfuscatedName().equalsIgnoreCase(deobfuscatedName))
                .findFirst();
        if(!c.isPresent()) {
            throw new IllegalArgumentException("Could not find class with deobfuscated name '" + deobfuscatedName + "'. Are you sure it exists?");
        }
        return c.get();
    }
}
