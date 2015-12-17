package me.curlpipesh.pipe.bytecode.injectors;

import me.curlpipesh.pipe.bytecode.Injector;
import me.curlpipesh.pipe.bytecode.map.MappedClass;
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
    protected void inject(ClassReader classReader, ClassNode classNode) {
        for(MethodNode m : (List<MethodNode>)classNode.methods) {
            if(m.name.equals("a") && m.desc.equals("(IIF)V")) {
                Iterator<AbstractInsnNode> i = m.instructions.iterator();
                while(i.hasNext()) {
                    AbstractInsnNode insn = i.next();
                    if(insn instanceof LdcInsnNode) {
                        LdcInsnNode ldc = (LdcInsnNode) insn;
                        if(ldc.cst instanceof String) {
                            String cst = (String) ldc.cst;
                            if(cst.contains("Minecraft")) {
                                cst += " (Pipe)"/* v§a" + Pipe.getVersion() + "§r)";*/;
                            }
                            ldc.cst = cst;
                        }
                    }
                }
            }
        }
    }
}
