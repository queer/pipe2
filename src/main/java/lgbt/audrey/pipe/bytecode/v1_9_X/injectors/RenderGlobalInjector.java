package lgbt.audrey.pipe.bytecode.v1_9_X.injectors;

import lgbt.audrey.pipe.bytecode.Injector;
import lgbt.audrey.pipe.bytecode.map.ClassMap;
import lgbt.audrey.pipe.bytecode.map.MappedClass;
import lgbt.audrey.pipe.bytecode.map.MappedClass.MethodDef;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.*;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * @author audrey
 * @since 3/23/16.
 */
public class RenderGlobalInjector extends Injector {
    public RenderGlobalInjector(final MappedClass classToInject) {
        super(classToInject);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void inject(final ClassReader cr, final ClassNode cn) {
        final Optional<MethodDef> methodDef = getClassToInject().getMethod("renderEntitiesWithCulling");
        if(!methodDef.isPresent()) {
            throw new IllegalStateException("RenderGlobalInjector: No RenderGlobal#renderEntitiesWithCulling!?");
        }
        final MethodDef m = methodDef.get();

        //noinspection unchecked
        for(final MethodNode node : (List<MethodNode>) cn.methods) {
            if(node.desc.equals(m.getDesc())) {
                //noinspection unchecked
                final Iterator<AbstractInsnNode> i = node.instructions.iterator();
                AbstractInsnNode injectInsn = null;
                VarInsnNode varInsnNode = null;
                while(i.hasNext()) {
                    final AbstractInsnNode insn = i.next();
                    if(insn instanceof LdcInsnNode) {
                        final LdcInsnNode linsn = (LdcInsnNode) insn;
                        if(linsn.cst.equals("entityOutlines")) {
                            injectInsn = insn.getPrevious().getPrevious().getPrevious().getPrevious().getPrevious()
                                    .getPrevious().getPrevious().getPrevious().getPrevious().getPrevious().getPrevious()
                                    .getPrevious().getPrevious().getPrevious().getPrevious().getPrevious().getPrevious()
                                    .getPrevious().getPrevious().getPrevious().getPrevious();
                            varInsnNode = (VarInsnNode) injectInsn.getPrevious().getPrevious();
                        }
                    }
                }


                final InsnList list = new InsnList();
                list.add(new MethodInsnNode(INVOKESTATIC, "lgbt/audrey/pipe/Pipe", "eventBus", "()Llgbt/audrey/pipe/event/EventBus;", false));
                list.add(new TypeInsnNode(NEW, "Llgbt/audrey/pipe/event/events/RenderEntity;"));
                list.add(new InsnNode(DUP));
                list.add(new VarInsnNode(ALOAD, varInsnNode.var));
                list.add(new TypeInsnNode(CHECKCAST, ClassMap.getClassByName("Entity").getDescription()));
                list.add(new MethodInsnNode(INVOKESPECIAL, "lgbt/audrey/pipe/event/events/RenderEntity", "<init>", "(Ljava/lang/Object;)V", false));
                list.add(new MethodInsnNode(INVOKEINTERFACE, "lgbt/audrey/pipe/event/EventBus", "push", "(Ljava/lang/Object;)Ljava/lang/Object;", true));
                list.add(new InsnNode(POP));
                node.instructions.insert(injectInsn, list);
            }
        }
    }
}
