package lgbt.audrey.pipe.bytecode.v1_9_X.injectors;

import lgbt.audrey.pipe.bytecode.Injector;
import lgbt.audrey.pipe.bytecode.map.MappedClass;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.*;

import java.util.List;

/**
 * @author audrey
 * @since 12/21/15.
 */
public class BlockSoulSandInjector extends Injector {
    public BlockSoulSandInjector(final MappedClass classToInject) {
        super(classToInject);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void inject(final ClassReader cr, final ClassNode cn) {
        final MappedClass.MethodDef blockCollide = getClassToInject().getMethod("onEntityCollidedWithBlock").get();
        ((List<MethodNode>) cn.methods).stream()
                .filter(m -> m.name.equals(blockCollide.getName()) && m.desc.contains(blockCollide.getDesc()))
                .forEach(m -> {
                    final LabelNode l = new LabelNode();
                    final InsnList list = new InsnList();
                    list.add(new MethodInsnNode(INVOKESTATIC, "me/curlpipesh/pipe/Pipe", "getInstance", "()Lme/curlpipesh/pipe/Pipe;", false));
                    list.add(new MethodInsnNode(INVOKEVIRTUAL, "me/curlpipesh/pipe/Pipe", "getEventBus", "()Lme/curlpipesh/pipe/event/EventBus;", false));
                    list.add(new TypeInsnNode(NEW, "me/curlpipesh/pipe/event/events/SoulSandSpeed"));
                    list.add(new InsnNode(DUP));
                    list.add(new MethodInsnNode(INVOKESPECIAL, "me/curlpipesh/pipe/event/events/SoulSandSpeed", "<init>", "()V", false));
                    list.add(new MethodInsnNode(INVOKEINTERFACE, "me/curlpipesh/pipe/event/EventBus", "push", "(Ljava/lang/Object;)Ljava/lang/Object;", true));
                    list.add(new TypeInsnNode(CHECKCAST, "me/curlpipesh/pipe/util/Cancellable"));
                    list.add(new MethodInsnNode(INVOKEVIRTUAL, "me/curlpipesh/pipe/util/Cancellable", "isCancelled", "()Z", false));
                    list.add(new JumpInsnNode(IFEQ, l));
                    list.add(new InsnNode(RETURN));
                    list.add(l);
                    //list.add(new InsnNode(POP));
                    m.instructions.insert(list);
                });
    }
}