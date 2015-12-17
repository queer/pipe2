package me.curlpipesh.pipe.bytecode.map;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author audrey
 * @since 12/17/15.
 */
@Value
public class MappedClass {
    private String deobfuscatedName;
    private String obfuscatedName;
    private String description;

    private Map<String, String> fields;
    private List<MethodDef> methods;

    @Value
    @RequiredArgsConstructor
    public static class MethodDef {
        private String name;
        private String deobfName;
        private String desc;

        @Override
        public String toString() {
            return deobfName + " " + "[" + name + desc + "]";
        }
    }

    public Optional<MethodDef> getMethod(String name) {
        return methods.stream().filter(m -> m.deobfName.equalsIgnoreCase(name)).findFirst();
    }
}
