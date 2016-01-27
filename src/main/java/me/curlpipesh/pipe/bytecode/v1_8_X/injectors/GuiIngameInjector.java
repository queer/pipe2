package me.curlpipesh.pipe.bytecode.v1_8_X.injectors;

import me.curlpipesh.pipe.bytecode.Injector;
import me.curlpipesh.pipe.bytecode.map.MappedClass;
import me.curlpipesh.pipe.bytecode.map.MappedClass.MethodDef;
import me.curlpipesh.pipe.event.events.Render2D;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.*;

import java.util.Iterator;
import java.util.List;

/**
 * Adds the {@link Render2D} event firing.
 *
 * @author c
 * @since 4/30/15
 */
public class GuiIngameInjector extends Injector {
    public GuiIngameInjector(final MappedClass classToInject) {
        super(classToInject);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void inject(final ClassReader cr, final ClassNode cn) {
        final MethodDef renderGameOverlay = getClassToInject().getMethod("renderGameOverlay").get();

        ((List<MethodNode>) cn.methods).stream()
                .filter(m -> m.name.equals(renderGameOverlay.getName()) && m.desc.equals(renderGameOverlay.getDesc()))
                .forEach(m -> {
                    final InsnList list = new InsnList();
                    list.add(new MethodInsnNode(INVOKESTATIC, "me/curlpipesh/pipe/Pipe", "getInstance", "()Lme/curlpipesh/pipe/Pipe;", false));
                    list.add(new MethodInsnNode(INVOKEVIRTUAL, "me/curlpipesh/pipe/Pipe", "getEventBus", "()Lme/curlpipesh/pipe/event/EventBus;", false));
                    list.add(new FieldInsnNode(GETSTATIC, "me/curlpipesh/pipe/event/events/Render2D", "instance", "Lme/curlpipesh/pipe/event/events/Render2D;"));
                    list.add(new MethodInsnNode(INVOKEINTERFACE, "me/curlpipesh/pipe/event/EventBus", "push", "(Ljava/lang/Object;)Ljava/lang/Object;", true));
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
                });
    }
}
