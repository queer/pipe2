package lgbt.audrey.pipe.bytecode.v1_9_X.injectors;

import lgbt.audrey.pipe.Pipe;
import lgbt.audrey.pipe.event.events.Keypress;
import lgbt.audrey.pipe.Pipe;
import lgbt.audrey.pipe.bytecode.Injector;
import lgbt.audrey.pipe.bytecode.map.ClassMap;
import lgbt.audrey.pipe.bytecode.map.MappedClass;
import lgbt.audrey.pipe.bytecode.map.MappedClass.MethodDef;
import lgbt.audrey.pipe.event.events.Keypress;
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
    protected void inject(final ClassReader cr, final ClassNode cn) {
        final MethodDef startGame = getClassToInject().getMethod("startGame").get();
        final MethodDef runGame = getClassToInject().getMethod("runGame").get();

        for(final MethodNode m : (List<MethodNode>) cn.methods) {
            if(m.name.equals(startGame.getName()) && m.desc.equals(startGame.getDesc())) {
                final InsnList list = new InsnList();
                list.add(new MethodInsnNode(INVOKESTATIC, "lgbt/audrey/pipe/Pipe", "getInstance", "()Llgbt/audrey/pipe/Pipe;", false));
                list.add(new MethodInsnNode(INVOKEVIRTUAL, "lgbt/audrey/pipe/Pipe", "init", "()V", false));
                final Iterator<AbstractInsnNode> i = m.instructions.iterator();
                AbstractInsnNode node = null;
                while(i.hasNext()) {
                    final AbstractInsnNode n = i.next();
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
                final InsnList list = new InsnList();
                list.add(new MethodInsnNode(INVOKESTATIC, "lgbt/audrey/pipe/Pipe", "getInstance", "()Llgbt/audrey/pipe/Pipe;", false));
                list.add(new MethodInsnNode(INVOKEVIRTUAL, "lgbt/audrey/pipe/Pipe", "getEventBus", "()Llgbt/audrey/pipe/event/EventBus;", false));
                list.add(new FieldInsnNode(GETSTATIC, "lgbt/audrey/pipe/event/events/Tick", "instance", "Llgbt/audrey/pipe/event/events/Tick;"));
                list.add(new MethodInsnNode(INVOKEINTERFACE, "lgbt/audrey/pipe/event/EventBus", "push", "(Ljava/lang/Object;)Ljava/lang/Object;", true));
                m.instructions.insert(list);

                // Key press event
                list.clear();
                list.add(new MethodInsnNode(INVOKESTATIC, "lgbt/audrey/pipe/Pipe", "getInstance", "()Llgbt/audrey/pipe/Pipe;", false));
                list.add(new MethodInsnNode(INVOKEVIRTUAL, "lgbt/audrey/pipe/Pipe", "getEventBus", "()Llgbt/audrey/pipe/event/EventBus;", false));
                list.add(new TypeInsnNode(NEW, "lgbt/audrey/pipe/event/events/Keypress"));
                list.add(new InsnNode(DUP));
                list.add(new MethodInsnNode(INVOKESTATIC, "org/lwjgl/input/Keyboard", "getEventKey", "()I", false));
                list.add(new MethodInsnNode(INVOKESPECIAL, "lgbt/audrey/pipe/event/events/Keypress", "<init>", "(I)V", false));
                list.add(new MethodInsnNode(INVOKEINTERFACE, "lgbt/audrey/pipe/event/EventBus", "push", "(Ljava/lang/Object;)Ljava/lang/Object;", true));
                list.add(new InsnNode(POP));

                final Iterator<AbstractInsnNode> i = m.instructions.iterator();
                AbstractInsnNode node = null;
                while(i.hasNext()) {
                    final AbstractInsnNode n = i.next();
                    if(n.getOpcode() == INVOKESTATIC) {
                        if(n instanceof MethodInsnNode) {
                            final MethodInsnNode m2 = (MethodInsnNode) n;
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
