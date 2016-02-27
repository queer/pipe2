package me.curlpipesh.pipe.bytecode;

import lombok.Getter;
import me.curlpipesh.pipe.Pipe;
import me.curlpipesh.pipe.bytecode.map.MappedClass;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.util.CheckClassAdapter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @author audrey
 * @since 12/17/15.
 */
public abstract class Injector implements ClassFileTransformer, Opcodes {
    @Getter
    private final MappedClass classToInject;

    public Injector(final MappedClass classToInject) {
        this.classToInject = classToInject;
    }

    @Override
    @SuppressWarnings({"ConstantConditions", "unchecked"})
    public final byte[] transform(final ClassLoader classLoader, final String s, final Class<?> aClass, final ProtectionDomain protectionDomain, final byte[] bytes) throws IllegalClassFormatException {
        if(classToInject.getObfuscatedName().equals(s)) {
            Pipe.getLogger().info("Injecting mapped class [" + classToInject.getDeobfuscatedName() + ','
                    + classToInject.getObfuscatedName() + "]...");
            final ClassReader cr = new ClassReader(bytes);
            final ClassNode cn = new ClassNode();
            cr.accept(cn, 0);
            inject(cr, cn);
            final ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            cn.accept(cw);
            Pipe.getLogger().info("Done!");
            final byte[] cwBytes = cw.toByteArray();
            CheckClassAdapter.verify(new ClassReader(cwBytes), false, new PrintWriter(System.err));
            return cwBytes;
        } else {
            throw new IllegalStateException("@Inject isn't present!?");
        }
    }

    protected abstract void inject(ClassReader cr, ClassNode cn);
}
