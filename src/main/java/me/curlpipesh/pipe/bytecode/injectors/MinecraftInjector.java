package me.curlpipesh.pipe.bytecode.injectors;

import me.curlpipesh.bytecodetools.inject.Inject;
import me.curlpipesh.bytecodetools.inject.Injector;
import me.curlpipesh.pipe.event.events.Keypress;
import me.curlpipesh.pipe.util.Constants;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.*;

import java.util.Iterator;
import java.util.List;

import static me.curlpipesh.bytecodetools.util.AccessHelper.*;

/**
 * Adds the instructions necessary for starting the client. Also adds the
 * {@link Keypress} event firing.
 *
 * @author c
 * @since 4/30/15
 */
@Inject(Constants.MINECRAFT)
@SuppressWarnings("unused")
public class MinecraftInjector extends Injector {
    @Override
    @SuppressWarnings("unchecked")
    protected void inject(ClassReader cr, ClassNode cn) {
        for(MethodNode m : (List<MethodNode>)cn.methods) {
            if(m.name.equals("am") && m.desc.equals("()V") && isVoid(m.desc) && isPrivate(m.access)) {
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
            } else if(m.name.equals("s") && m.desc.equals("()V") && isVoid(m.desc) && isPublic(m.access)) {
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
                boolean merp = false; // haveWeFoundThatStupidManuallyTriggeredDebugWhateverThingThatMeansWereInTheRightPlace
                while(i.hasNext()) {
                    AbstractInsnNode n = i.next();
                    if(n instanceof LdcInsnNode) {
                        if(((LdcInsnNode)n).cst.equals("Manually triggered debug crash")) {
                            merp = true;
                        }
                    }
                    if(merp) {
                        if (n.getOpcode() == INVOKESTATIC && n.getNext().getOpcode() == IFEQ) {
                            if(n instanceof MethodInsnNode) {
                                MethodInsnNode m2 = (MethodInsnNode) n;
                                if (m2.owner.equals("org/lwjgl/input/Keyboard")) {
                                    if (m2.name.equals("getEventKeyState")) {
                                        node = n.getNext().getNext().getNext();
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                if(node == null) {
                    throw new IllegalStateException("IFEQ insn node was null?!");
                }
                m.instructions.insert(node, list);
            }
        }
    }
}
