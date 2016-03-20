package lgbt.audrey.pipe.bytecode.v1_9_X.injectors;

import lgbt.audrey.pipe.bytecode.Injector;
import lgbt.audrey.pipe.bytecode.map.MappedClass;
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
    protected void inject(final ClassReader classReader, final ClassNode classNode) {
        // f -> lightBrightnessTable
        final String obfFieldName = getClassToInject().getFields().get("lightBrightnessTable");
        ((List<FieldNode>) classNode.fields).stream().filter(f -> f.name.equals(obfFieldName))
                .forEach(f -> f.access = ACC_PUBLIC | ACC_FINAL);
    }
}
