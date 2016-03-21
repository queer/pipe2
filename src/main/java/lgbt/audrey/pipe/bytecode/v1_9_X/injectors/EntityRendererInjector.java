package lgbt.audrey.pipe.bytecode.v1_9_X.injectors;

import lgbt.audrey.pipe.bytecode.AccessHelper;
import lgbt.audrey.pipe.bytecode.Injector;
import lgbt.audrey.pipe.bytecode.map.MappedClass;
import lgbt.audrey.pipe.bytecode.map.MappedClass.MethodDef;
import lgbt.audrey.pipe.event.events.Render3D;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.*;

import java.util.Iterator;
import java.util.List;

/**
 * Adds the {@link Render3D} event firing.
 *
 * @author c
 * @since 5/21/15
 */
public class EntityRendererInjector extends Injector {
    public EntityRendererInjector(final MappedClass classToInject) {
        super(classToInject);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void inject(final ClassReader classReader, final ClassNode classNode) {
        final MethodDef doWorldRender = getClassToInject().getMethod("doWorldRender").get();
        final MethodDef applyViewBobbing = getClassToInject().getMethod("applyViewBobbing").get();

        for(final MethodNode m : (List<MethodNode>) classNode.methods) {
            if(m.name.equals(doWorldRender.getName()) && m.desc.equals(doWorldRender.getDesc())) {
                final InsnList list = new InsnList();
                list.add(new MethodInsnNode(INVOKESTATIC, "org/lwjgl/opengl/GL11", "glPushMatrix", "()V", false));
                list.add(new MethodInsnNode(INVOKESTATIC, "lgbt/audrey/pipe/Pipe", "getInstance", "()Llgbt/audrey/pipe/Pipe;", false));
                list.add(new MethodInsnNode(INVOKEVIRTUAL, "lgbt/audrey/pipe/Pipe", "getEventBus", "()Llgbt/audrey/pipe/event/EventBus;", false));
                list.add(new TypeInsnNode(NEW, "lgbt/audrey/pipe/event/events/Render3D"));
                list.add(new InsnNode(DUP));
                list.add(new VarInsnNode(FLOAD, 2));
                list.add(new MethodInsnNode(INVOKESPECIAL, "lgbt/audrey/pipe/event/events/Render3D", "<init>", "(F)V", false));
                list.add(new MethodInsnNode(INVOKEINTERFACE, "lgbt/audrey/pipe/event/EventBus", "push", "(Ljava/lang/Object;)Ljava/lang/Object;", true));
                list.add(new InsnNode(POP));
                list.add(new MethodInsnNode(INVOKESTATIC, "org/lwjgl/opengl/GL11", "glPopMatrix", "()V", false));
                final Iterator<AbstractInsnNode> i = m.instructions.iterator();
                AbstractInsnNode injectInsn = null;
                while(i.hasNext()) {
                    final AbstractInsnNode insn = i.next();
                    if(insn instanceof LdcInsnNode) {
                        final LdcInsnNode linsn = (LdcInsnNode) insn;
                        if(linsn.cst.equals(/*"hand"*/"entities")) {
                            //injectInsn = insn.getNext();
                            injectInsn = insn.getNext().getNext().getNext().getNext().getNext().getNext().getNext()
                                    .getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext()
                                    .getNext().getNext().getNext().getNext().getNext();
                        }
                    }
                }

                if(injectInsn == null) {
                    throw new IllegalStateException("Instruction was null?!");
                }
                m.instructions.insertBefore(injectInsn, list);
            } else if(m.name.equals(applyViewBobbing.getName()) && m.desc.equals(applyViewBobbing.getDesc()) && AccessHelper.isPrivate(m.access)) {
                m.instructions.clear();
                m.instructions.insert(new InsnNode(RETURN));
            }
        }
    }
}
