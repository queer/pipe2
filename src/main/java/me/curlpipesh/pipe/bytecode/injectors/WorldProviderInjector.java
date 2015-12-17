package me.curlpipesh.pipe.bytecode.injectors;

import me.curlpipesh.pipe.bytecode.Injector;
import me.curlpipesh.pipe.bytecode.map.MappedClass;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

import java.util.List;

/**
 * Makes the lightBrightnessTable field accessible
 *
 * @author c
 * @since 5/21/15
 */
public class WorldProviderInjector extends Injector {
    public WorldProviderInjector(final MappedClass classToInject) {
        super(classToInject);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void inject(ClassReader classReader, ClassNode classNode) {
        // f -> lightBrightnessTable
        String obfFieldName = getClassToInject().getFields().get("lightBrightnessTable");
        ((List<FieldNode>) classNode.fields).stream().filter(f -> f.name.equals(obfFieldName))
                .forEach(f -> f.access = ACC_PUBLIC | ACC_FINAL);
    }
}
