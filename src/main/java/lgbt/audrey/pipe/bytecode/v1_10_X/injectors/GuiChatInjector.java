package lgbt.audrey.pipe.bytecode.v1_10_X.injectors;

import lgbt.audrey.pipe.bytecode.Injector;
import lgbt.audrey.pipe.bytecode.map.MappedClass;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.*;

import java.util.Iterator;
import java.util.List;

/**
 * @author c
 * @since 5/27/15
 */
public class GuiChatInjector extends Injector {
    public GuiChatInjector(final MappedClass classToInject) {
        super(classToInject);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void inject(final ClassReader classReader, final ClassNode classNode) {
        // TODO: Fix
        for(final MethodNode m : (List<MethodNode>) classNode.methods) {
            if(m.name.equals("a") && m.desc.equals("(CI)V")) {
                final InsnList list = new InsnList();
                list.add(new VarInsnNode(ALOAD, 3));
                list.add(new MethodInsnNode(INVOKESTATIC, "lgbt/audrey/pipe/util/helpers/ChatHelper", "handle", "(Ljava/lang/String;)V", false));
                AbstractInsnNode insn = null;
                final Iterator<AbstractInsnNode> i = m.instructions.iterator();

                while(i.hasNext()) {
                    final AbstractInsnNode n = i.next();
                    if(n instanceof MethodInsnNode) {
                        final MethodInsnNode l = (MethodInsnNode) n;
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
