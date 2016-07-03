package lgbt.audrey.pipe.bytecode.v1_10_X.injectors;

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
                    list.add(new MethodInsnNode(INVOKESTATIC, "lgbt/audrey/pipe/Pipe", "getInstance", "()Llgbt/audrey/pipe/Pipe;", false));
                    list.add(new MethodInsnNode(INVOKEVIRTUAL, "lgbt/audrey/pipe/Pipe", "getEventBus", "()Llgbt/audrey/pipe/event/EventBus;", false));
                    list.add(new TypeInsnNode(NEW, "lgbt/audrey/pipe/event/events/SoulSandSpeed"));
                    list.add(new InsnNode(DUP));
                    list.add(new MethodInsnNode(INVOKESPECIAL, "lgbt/audrey/pipe/event/events/SoulSandSpeed", "<init>", "()V", false));
                    list.add(new MethodInsnNode(INVOKEINTERFACE, "lgbt/audrey/pipe/event/EventBus", "push", "(Ljava/lang/Object;)Ljava/lang/Object;", true));
                    list.add(new TypeInsnNode(CHECKCAST, "lgbt/audrey/pipe/util/Cancellable"));
                    list.add(new MethodInsnNode(INVOKEVIRTUAL, "lgbt/audrey/pipe/util/Cancellable", "isCancelled", "()Z", false));
                    list.add(new JumpInsnNode(IFEQ, l));
                    list.add(new InsnNode(RETURN));
                    list.add(l);
                    //list.add(new InsnNode(POP));
                    m.instructions.insert(list);
                });
    }
}