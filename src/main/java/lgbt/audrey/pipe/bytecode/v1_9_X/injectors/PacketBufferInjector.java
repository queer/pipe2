package lgbt.audrey.pipe.bytecode.v1_9_X.injectors;

import lgbt.audrey.pipe.bytecode.Injector;
import lgbt.audrey.pipe.bytecode.map.MappedClass;
import lgbt.audrey.pipe.bytecode.map.MappedClass.MethodDef;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.*;

import java.util.List;

/**
 * @author audrey
 * @since 12/15/15.
 */
public class PacketBufferInjector extends Injector {
    public PacketBufferInjector(final MappedClass classToInject) {
        super(classToInject);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void inject(final ClassReader classReader, final ClassNode classNode) {
        final MethodDef readStringFromBuffer = getClassToInject().getMethod("readStringFromBuffer").get();
        ((List<MethodNode>) classNode.methods).stream()
                .filter(m -> m.name.equals(readStringFromBuffer.getName()) &&
                        m.desc.contains(readStringFromBuffer.getDesc()))
                .forEach(m -> {
                    //Pipe.getLogger().info("Found PacketBuffer#readStringFromBuffer():" + m.name + " : " + m.desc);
                    final InsnList list = new InsnList();
                    list.add(new MethodInsnNode(INVOKESTATIC,
                            "lgbt/audrey/pipe/Pipe", "getInstance", "()Llgbt/audrey/pipe/Pipe;", false));
                    list.add(new MethodInsnNode(INVOKEVIRTUAL, "lgbt/audrey/pipe/Pipe", "getEventBus", "()Llgbt/audrey/pipe/event/EventBus;", false));
                    list.add(new TypeInsnNode(NEW, "lgbt/audrey/pipe/event/events/PacketBufferStringRead"));
                    list.add(new InsnNode(DUP));
                    list.add(new VarInsnNode(ALOAD, 3));
                    list.add(new MethodInsnNode(INVOKESPECIAL, "lgbt/audrey/pipe/event/events/PacketBufferStringRead", "<init>", "(Ljava/lang/String;)V", false));
                    list.add(new MethodInsnNode(INVOKEINTERFACE, "lgbt/audrey/pipe/event/EventBus", "push", "(Ljava/lang/Object;)Ljava/lang/Object;", true));
                    list.add(new InsnNode(POP));
                    m.instructions.insertBefore(m.instructions.getLast().getPrevious(), list);
                });
    }
}
