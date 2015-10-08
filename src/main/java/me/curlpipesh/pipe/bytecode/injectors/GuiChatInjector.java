package me.curlpipesh.pipe.bytecode.injectors;

import me.curlpipesh.bytecodetools.inject.Inject;
import me.curlpipesh.bytecodetools.inject.Injector;
import me.curlpipesh.pipe.util.Constants;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.*;

import java.util.Iterator;
import java.util.List;

/**
 * @author c
 * @since 5/27/15
 */
//@Inject(Constants.GUICHAT)
public class GuiChatInjector extends Injector {
    @Override
    @SuppressWarnings("unchecked")
    protected void inject(ClassReader classReader, ClassNode classNode) {
        for(MethodNode m : (List<MethodNode>) classNode.methods) {
            if(m.name.equals("a") && m.desc.equals("(CI)V")) {
                InsnList list = new InsnList();
                list.add(new VarInsnNode(ALOAD, 3));
                list.add(new MethodInsnNode(INVOKESTATIC, "me/curlpipesh/pipe/util/helpers/ChatHelper", "handle", "(Ljava/lang/String;)V", false));
                AbstractInsnNode insn = null;
                Iterator<AbstractInsnNode> i = m.instructions.iterator();

                while(i.hasNext()) {
                    AbstractInsnNode n = i.next();
                    if(n instanceof MethodInsnNode) {
                        MethodInsnNode l = (MethodInsnNode) n;
                        if(l.name.equals("f") && l.desc.equals("(Ljava/lang/String;)V")) {
                            insn = n.getPrevious().getPrevious();
                            break;
                        }
                    }
                }
                if(insn == null) {
                    throw new IllegalStateException("insn was null?!");
                }

                m.instructions.remove(insn.getNext().getNext());
                m.instructions.remove(insn.getNext());
                m.instructions.insert(insn, list);
                m.instructions.remove(insn);
            }
        }
    }
}
