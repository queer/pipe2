package me.curlpipesh.pipe.bytecode.v1_9_X.injectors;

import me.curlpipesh.pipe.Pipe;
import me.curlpipesh.pipe.bytecode.Injector;
import me.curlpipesh.pipe.bytecode.map.MappedClass;
import me.curlpipesh.pipe.event.events.Keypress;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.*;

import java.util.Iterator;
import java.util.List;

/**
 * Adds the instructions necessary for starting the client. Also adds the
 * {@link Keypress} event firing.
 *
 * @author c
 * @since 4/30/15
 */
@SuppressWarnings("unused")
public class MinecraftInjector extends Injector {
    public MinecraftInjector(final MappedClass classToInject) {
        super(classToInject);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void inject(ClassReader cr, ClassNode cn) {
        MappedClass.MethodDef startGame = getClassToInject().getMethod("startGame").get();
        MappedClass.MethodDef runGame = getClassToInject().getMethod("runGame").get();

        for(MethodNode m : (List<MethodNode>) cn.methods) {
            if(m.name.equals(startGame.getName()) && m.desc.equals(startGame.getDesc())) {
                InsnList list = new InsnList();
                list.add(new MethodInsnNode(INVOKESTATIC, "me/curlpipesh/pipe/Pipe", "getInstance", "()Lme/curlpipesh/pipe/Pipe;", false));
                list.add(new MethodInsnNode(INVOKEVIRTUAL, "me/curlpipesh/pipe/Pipe", "init", "()V", false));
                Iterator<AbstractInsnNode> i = m.instructions.iterator();
                AbstractInsnNode node = null;
                while(i.hasNext()) {
                    AbstractInsnNode n = i.next();
                    if(n.getOpcode() == RETURN) {
                        node = n;
                        break;
                    }
                }
                if(node == null) {
                    throw new IllegalStateException("RETURN insn node was null?!");
                }
                m.instructions.insertBefore(node, list);
            } else if(m.name.equals(runGame.getName()) && m.desc.equals(runGame.getDesc())) {
                // Tick event
                InsnList list = new InsnList();
                list.add(new MethodInsnNode(INVOKESTATIC, "me/curlpipesh/pipe/Pipe", "getInstance", "()Lme/curlpipesh/pipe/Pipe;", false));
                list.add(new MethodInsnNode(INVOKEVIRTUAL, "me/curlpipesh/pipe/Pipe", "getEventBus", "()Lme/curlpipesh/pipe/event/EventBus;", false));
                list.add(new FieldInsnNode(GETSTATIC, "me/curlpipesh/pipe/event/events/Tick", "instance", "Lme/curlpipesh/pipe/event/events/Tick;"));
                list.add(new MethodInsnNode(INVOKEINTERFACE, "me/curlpipesh/pipe/event/EventBus", "push", "(Ljava/lang/Object;)Ljava/lang/Object;", true));
                m.instructions.insert(list);

                // Key press event
                list.clear();
                list.add(new MethodInsnNode(INVOKESTATIC, "me/curlpipesh/pipe/Pipe", "getInstance", "()Lme/curlpipesh/pipe/Pipe;", false));
                list.add(new MethodInsnNode(INVOKEVIRTUAL, "me/curlpipesh/pipe/Pipe", "getEventBus", "()Lme/curlpipesh/pipe/event/EventBus;", false));
                list.add(new TypeInsnNode(NEW, "me/curlpipesh/pipe/event/events/Keypress"));
                list.add(new InsnNode(DUP));
                list.add(new MethodInsnNode(INVOKESTATIC, "org/lwjgl/input/Keyboard", "getEventKey", "()I", false));
                list.add(new MethodInsnNode(INVOKESPECIAL, "me/curlpipesh/pipe/event/events/Keypress", "<init>", "(I)V", false));
                list.add(new MethodInsnNode(INVOKEINTERFACE, "me/curlpipesh/pipe/event/EventBus", "push", "(Ljava/lang/Object;)Ljava/lang/Object;", true));
                list.add(new InsnNode(POP));

                Iterator<AbstractInsnNode> i = m.instructions.iterator();
                AbstractInsnNode node = null;
                while(i.hasNext()) {
                    AbstractInsnNode n = i.next();
                    if(n.getOpcode() == INVOKESTATIC) {
                        if(n instanceof MethodInsnNode) {
                            MethodInsnNode m2 = (MethodInsnNode) n;
                            if(m2.owner.equals("org/lwjgl/input/Keyboard")) {
                                if(m2.name.equals("getEventKeyState")) {
                                    Pipe.getLogger().info("Found node!");
                                    node = n.getNext().getNext().getNext().getNext().getNext();
                                    break;
                                }
                            }
                        }
                    }
                }
                if(node == null) {
                    throw new IllegalStateException("Insn node was null?!");
                }
                m.instructions.insert(node, list);
            }
        }
    }
}
