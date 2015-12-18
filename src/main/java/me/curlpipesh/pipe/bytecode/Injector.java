package me.curlpipesh.pipe.bytecode;

import lombok.Getter;
import me.curlpipesh.pipe.Pipe;
import me.curlpipesh.pipe.bytecode.map.MappedClass;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @author audrey
 * @since 12/17/15.
 */
public abstract class Injector implements ClassFileTransformer, Opcodes {
    @Getter
    private MappedClass classToInject;

    public Injector(MappedClass classToInject) {
        this.classToInject = classToInject;
    }

    @Override
    @SuppressWarnings({"ConstantConditions", "unchecked"})
    public final byte[] transform(ClassLoader classLoader, String s, Class<?> aClass, ProtectionDomain protectionDomain, byte[] bytes) throws IllegalClassFormatException {
        if(classToInject.getObfuscatedName().equals(s)) {
            Pipe.getLogger().info("Injecting mapped class [" + classToInject.getDeobfuscatedName() + "," + classToInject.getObfuscatedName() + "]...");
            ClassReader cr = new ClassReader(bytes);
            ClassNode cn = new ClassNode();
            cr.accept(cn, 0);
            inject(cr, cn);
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            cn.accept(cw);
            Pipe.getLogger().info("Done!");
            return cw.toByteArray();
        } else {
            throw new IllegalStateException("@Inject isn't present!?");
        }
    }

    protected abstract void inject(ClassReader cr, ClassNode cn);
}
