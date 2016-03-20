package lgbt.audrey.pipe.bytecode.v1_9_X.injectors;

import lgbt.audrey.pipe.bytecode.Injector;
import lgbt.audrey.pipe.bytecode.map.MappedClass;
import lgbt.audrey.pipe.bytecode.map.MappedClass.MethodDef;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.*;

import java.util.List;
import java.util.Optional;

/**
 * @author audrey
 * @since 2/27/16.
 */
public class FramebufferInjector extends Injector {
    public FramebufferInjector(final MappedClass classToInject) {
        super(classToInject);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void inject(final ClassReader cr, final ClassNode cn) {
        final Optional<MethodDef> framebufferRender = getClassToInject().getMethod("framebufferRender");
        if(framebufferRender.isPresent()) {
            final MethodDef md = framebufferRender.get();
            for(final MethodNode m : (List<MethodNode>) cn.methods) {
                if(m.name.equals(md.getName()) && m.desc.equals(md.getDesc())) {
                    final InsnList list = new InsnList();
                    list.add(new MethodInsnNode(INVOKESTATIC, "me/curlpipesh/pipe/Pipe", "getInstance", "()Lme/curlpipesh/pipe/Pipe;", false));
                    list.add(new MethodInsnNode(INVOKEVIRTUAL, "me/curlpipesh/pipe/Pipe", "getEventBus", "()Lme/curlpipesh/pipe/event/EventBus;", false));
                    list.add(new FieldInsnNode(GETSTATIC, "me/curlpipesh/pipe/event/events/RenderFramebuffer", "instance", "Lme/curlpipesh/pipe/event/events/RenderFramebuffer;"));
                    list.add(new MethodInsnNode(INVOKEINTERFACE, "me/curlpipesh/pipe/event/EventBus", "push", "(Ljava/lang/Object;)Ljava/lang/Object;", true));
                    m.instructions.insert(list);
                }
            }
        }
    }
}
