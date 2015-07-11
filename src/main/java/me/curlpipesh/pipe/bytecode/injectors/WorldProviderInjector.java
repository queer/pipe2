package me.curlpipesh.pipe.bytecode.injectors;

import me.curlpipesh.bytecodetools.inject.Inject;
import me.curlpipesh.bytecodetools.inject.Injector;
import me.curlpipesh.pipe.util.Constants;
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
@Inject(Constants.WORLDPROVIDER)
public class WorldProviderInjector extends Injector {
    @Override
    @SuppressWarnings("unchecked")
    protected void inject(ClassReader classReader, ClassNode classNode) {
        // f -> lightBrightnessTable
        ((List<FieldNode>) classNode.fields).stream().filter(f -> f.name.equals("f"))
                .forEach(f -> f.access = ACC_PUBLIC | ACC_FINAL);
    }
}
