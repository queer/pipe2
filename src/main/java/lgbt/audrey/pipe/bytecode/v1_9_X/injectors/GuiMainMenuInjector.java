package lgbt.audrey.pipe.bytecode.v1_9_X.injectors;

import lgbt.audrey.pipe.bytecode.Injector;
import lgbt.audrey.pipe.bytecode.map.MappedClass;
import lgbt.audrey.pipe.bytecode.Injector;
import lgbt.audrey.pipe.bytecode.map.MappedClass;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.Iterator;
import java.util.List;

/**
 * Adds a little bit of information to the main menu.
 *
 * @author c
 * @since 5/23/15
 */
public class GuiMainMenuInjector extends Injector {
    public GuiMainMenuInjector(final MappedClass classToInject) {
        super(classToInject);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void inject(final ClassReader classReader, final ClassNode classNode) {
        for(final MethodNode m : (List<MethodNode>)classNode.methods) {
            if(m.name.equals("a") && m.desc.equals("(IIF)V")) {
                final Iterator<AbstractInsnNode> i = m.instructions.iterator();
                while(i.hasNext()) {
                    final AbstractInsnNode insn = i.next();
                    if(insn instanceof LdcInsnNode) {
                        final LdcInsnNode ldc = (LdcInsnNode) insn;
                        if(ldc.cst instanceof String) {
                            String cst = (String) ldc.cst;
                            if(cst.contains("Minecraft")) {
                                cst += " (Pipe)"/* v\247a" + Pipe.getVersion() + "\247r)";*/;
                            }
                            ldc.cst = cst;
                        }
                    }
                }
            }
        }
    }
}
