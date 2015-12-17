package me.curlpipesh.pipe.bytecode.injectors;

import me.curlpipesh.pipe.bytecode.Injector;
import me.curlpipesh.pipe.bytecode.map.MappedClass;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.*;

import java.util.List;

/**
 * @author audrey
 * @since 10/8/15.
 */
public class NetworkManagerInjector extends Injector {
    public NetworkManagerInjector(final MappedClass classToInject) {
        super(classToInject);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void inject(ClassReader classReader, ClassNode classNode) {
        MappedClass.MethodDef sendPacket = getClassToInject().getMethod("sendPacket").get();
        MappedClass.MethodDef channelRead0 = getClassToInject().getMethod("channelRead0").get();

        ((List<MethodNode>) classNode.methods).stream()
                .filter(m -> m.name.equals(sendPacket.getName()) && m.desc.contains(sendPacket.getDesc()))
                .forEach(m -> {
                    //Pipe.getLogger().info("Found NetworkManager#sendPacket(Packet): " + m.name + " : " + m.desc);
                    LabelNode l = new LabelNode();
                    InsnList list = new InsnList();
                    list.add(new MethodInsnNode(INVOKESTATIC, "me/curlpipesh/pipe/Pipe", "getInstance", "()Lme/curlpipesh/pipe/Pipe;", false));
                    list.add(new MethodInsnNode(INVOKEVIRTUAL, "me/curlpipesh/pipe/Pipe", "getEventBus", "()Lme/curlpipesh/pipe/event/EventBus;", false));
                    list.add(new TypeInsnNode(NEW, "me/curlpipesh/pipe/event/events/PacketSend"));
                    list.add(new InsnNode(DUP));
                    list.add(new VarInsnNode(ALOAD, 1));
                    list.add(new MethodInsnNode(INVOKESPECIAL, "me/curlpipesh/pipe/event/events/PacketSend", "<init>", "(Ljava/lang/Object;)V", false));
                    list.add(new MethodInsnNode(INVOKEINTERFACE, "me/curlpipesh/pipe/event/EventBus", "push", "(Ljava/lang/Object;)Ljava/lang/Object;", true));
                    list.add(new TypeInsnNode(CHECKCAST, "me/curlpipesh/pipe/event/events/PacketSend"));
                    list.add(new MethodInsnNode(INVOKEVIRTUAL, "me/curlpipesh/pipe/event/events/PacketSend", "isCancelled", "()Z", false));
                    list.add(new JumpInsnNode(IFEQ, l));
                    list.add(new InsnNode(RETURN));
                    list.add(l);
                    //list.add(new InsnNode(POP));
                    m.instructions.insert(list);
                });
        ((List<MethodNode>) classNode.methods).stream()
                .filter(m -> m.name.equals(channelRead0.getName()) &&
                        m.desc.contains(channelRead0.getDesc()))
                .forEach(m -> {
                    //Pipe.getLogger().info("Found NetworkManager#channelRead0(ChannelHandlerContext, Packet):" + m.name + " : " + m.desc);
                    LabelNode l = new LabelNode();
                    InsnList list = new InsnList();
                    list.add(new MethodInsnNode(INVOKESTATIC,
                            "me/curlpipesh/pipe/Pipe", "getInstance", "()Lme/curlpipesh/pipe/Pipe;", false));
                    list.add(new MethodInsnNode(INVOKEVIRTUAL, "me/curlpipesh/pipe/Pipe", "getEventBus", "()Lme/curlpipesh/pipe/event/EventBus;", false));
                    list.add(new TypeInsnNode(NEW, "me/curlpipesh/pipe/event/events/PacketReceive"));
                    list.add(new InsnNode(DUP));
                    list.add(new VarInsnNode(ALOAD, 2));
                    list.add(new MethodInsnNode(INVOKESPECIAL, "me/curlpipesh/pipe/event/events/PacketReceive", "<init>", "(Ljava/lang/Object;)V", false));
                    list.add(new MethodInsnNode(INVOKEINTERFACE, "me/curlpipesh/pipe/event/EventBus", "push", "(Ljava/lang/Object;)Ljava/lang/Object;", true));
                    list.add(new TypeInsnNode(CHECKCAST, "me/curlpipesh/pipe/event/events/PacketReceive"));
                    list.add(new MethodInsnNode(INVOKEVIRTUAL, "me/curlpipesh/pipe/event/events/PacketReceive", "isCancelled", "()Z", false));
                    list.add(new JumpInsnNode(IFEQ, l));
                    list.add(new InsnNode(RETURN));
                    list.add(l);
                    //list.add(new InsnNode(POP));
                    m.instructions.insert(list);
                });
    }
}