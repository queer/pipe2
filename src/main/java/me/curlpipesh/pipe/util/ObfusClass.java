package me.curlpipesh.pipe.util;

import lombok.Data;

/**
 * Simple representation of an obfuscated class
 *
 * @author c
 * @since 5/22/15
 */
@Data
public class ObfusClass {
    /**
     * The non-obfuscated name of the class
     */
    private final String realName;

    /**
     * The obfuscated name of the class
     */
    private final String name;

    /**
     * The bytecode description of the class, eg <tt>Ljava/lang/Object;</tt>.
     */
    private final String desc;

    /**
     * The actual class being described
     */
    private final Class<?> clazz;

    /**
     * Creates a new ObfusClass for the given name
     *
     * @param name The name of the class
     * @param obfName The obfuscated name of the class
     */
    public ObfusClass(String name, String obfName) {
        this.realName = name;
        this.name = obfName;
        this.desc = "L" + obfName + ";";
        try {
            clazz = Class.forName(obfName);
        } catch(ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "{ name: " + realName + ", obfusName: " + name + ", desc: " + desc + " }";
    }
}
