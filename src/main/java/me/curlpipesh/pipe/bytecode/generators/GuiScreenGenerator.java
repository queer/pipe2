package me.curlpipesh.pipe.bytecode.generators;

import org.objectweb.asm.*;

import static me.curlpipesh.pipe.util.Constants.getByName;
import static org.objectweb.asm.Opcodes.*;

/**
 * Generates bytecode to fill in the methods in
 * {@link me.curlpipesh.pipe.gui.GuiScreen}, as well as making it extend the
 * Minecraft GuiScreen class.
 *
 * @author c
 * @since 5/24/15
 */
@SuppressWarnings("unused")
public class GuiScreenGenerator {
    public static byte[] generate() {

        ClassWriter cw = new ClassWriter(0);
        FieldVisitor fv;
        MethodVisitor mv;
        AnnotationVisitor av0;

        cw.visit(52, ACC_PUBLIC + ACC_FINAL + ACC_SUPER, "me/curlpipesh/pipe/gui/GuiScreen", null, getByName("GuiScreen").getName(), null);

        cw.visitSource("GuiScreen.java", null);

        {
            fv = cw.visitField(ACC_PRIVATE, "currentModule", "Lme/curlpipesh/pipe/gui/GuiModule;", null, null);
            fv.visitEnd();
        }
        {
            fv = cw.visitField(ACC_PRIVATE + ACC_FINAL + ACC_STATIC, "instance", "Lme/curlpipesh/pipe/gui/GuiScreen;", null, null);
            fv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(14, l0);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, getByName("GuiScreen").getName(), "<init>", "()V", false);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLineNumber(15, l1);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitInsn(ACONST_NULL);
            mv.visitFieldInsn(PUTFIELD, "me/curlpipesh/pipe/gui/GuiScreen", "currentModule", "Lme/curlpipesh/pipe/gui/GuiModule;");
            mv.visitInsn(RETURN);
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitLocalVariable("this", "Lme/curlpipesh/pipe/gui/GuiScreen;", null, l0, l2, 0);
            mv.visitMaxs(2, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "initGui", "()V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(22, l0);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, "me/curlpipesh/pipe/gui/GuiScreen", "currentModule", "Lme/curlpipesh/pipe/gui/GuiModule;");
            mv.visitMethodInsn(INVOKEINTERFACE, "me/curlpipesh/pipe/gui/GuiModule", "init", "()V", true);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLineNumber(23, l1);
            mv.visitInsn(RETURN);
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitLocalVariable("this", "Lme/curlpipesh/pipe/gui/GuiScreen;", null, l0, l2, 0);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "drawScreen", "(IIF)V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(26, l0);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, "me/curlpipesh/pipe/gui/GuiScreen", "currentModule", "Lme/curlpipesh/pipe/gui/GuiModule;");
            mv.visitVarInsn(ILOAD, 1);
            mv.visitVarInsn(ILOAD, 2);
            mv.visitVarInsn(FLOAD, 3);
            mv.visitMethodInsn(INVOKEINTERFACE, "me/curlpipesh/pipe/gui/GuiModule", "render", "(IIF)V", true);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLineNumber(27, l1);
            mv.visitInsn(RETURN);
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitLocalVariable("this", "Lme/curlpipesh/pipe/gui/GuiScreen;", null, l0, l2, 0);
            mv.visitLocalVariable("i", "I", null, l0, l2, 1);
            mv.visitLocalVariable("j", "I", null, l0, l2, 2);
            mv.visitLocalVariable("f", "F", null, l0, l2, 3);
            mv.visitMaxs(4, 4);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "mouseClicked", "(III)V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(30, l0);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, "me/curlpipesh/pipe/gui/GuiScreen", "currentModule", "Lme/curlpipesh/pipe/gui/GuiModule;");
            mv.visitVarInsn(ILOAD, 1);
            mv.visitVarInsn(ILOAD, 2);
            mv.visitVarInsn(ILOAD, 3);
            mv.visitMethodInsn(INVOKEINTERFACE, "me/curlpipesh/pipe/gui/GuiModule", "mouseDown", "(III)V", true);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLineNumber(31, l1);
            mv.visitInsn(RETURN);
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitLocalVariable("this", "Lme/curlpipesh/pipe/gui/GuiScreen;", null, l0, l2, 0);
            mv.visitLocalVariable("mx", "I", null, l0, l2, 1);
            mv.visitLocalVariable("my", "I", null, l0, l2, 2);
            mv.visitLocalVariable("button", "I", null, l0, l2, 3);
            mv.visitMaxs(4, 4);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "mouseDownDrag", "(IIIJ)V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(34, l0);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, "me/curlpipesh/pipe/gui/GuiScreen", "currentModule", "Lme/curlpipesh/pipe/gui/GuiModule;");
            mv.visitVarInsn(ILOAD, 1);
            mv.visitVarInsn(ILOAD, 2);
            mv.visitVarInsn(ILOAD, 3);
            mv.visitVarInsn(LLOAD, 4);
            mv.visitMethodInsn(INVOKEINTERFACE, "me/curlpipesh/pipe/gui/GuiModule", "mouseDownMove", "(IIIJ)V", true);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLineNumber(35, l1);
            mv.visitInsn(RETURN);
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitLocalVariable("this", "Lme/curlpipesh/pipe/gui/GuiScreen;", null, l0, l2, 0);
            mv.visitLocalVariable("mx", "I", null, l0, l2, 1);
            mv.visitLocalVariable("my", "I", null, l0, l2, 2);
            mv.visitLocalVariable("button", "I", null, l0, l2, 3);
            mv.visitLocalVariable("timeSinceLastClick", "J", null, l0, l2, 4);
            mv.visitMaxs(6, 6);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "mouseReleased", "(III)V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(38, l0);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, "me/curlpipesh/pipe/gui/GuiScreen", "currentModule", "Lme/curlpipesh/pipe/gui/GuiModule;");
            mv.visitVarInsn(ILOAD, 1);
            mv.visitVarInsn(ILOAD, 2);
            mv.visitVarInsn(ILOAD, 3);
            mv.visitMethodInsn(INVOKEINTERFACE, "me/curlpipesh/pipe/gui/GuiModule", "mouseUp", "(III)V", true);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLineNumber(39, l1);
            mv.visitInsn(RETURN);
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitLocalVariable("this", "Lme/curlpipesh/pipe/gui/GuiScreen;", null, l0, l2, 0);
            mv.visitLocalVariable("mx", "I", null, l0, l2, 1);
            mv.visitLocalVariable("my", "I", null, l0, l2, 2);
            mv.visitLocalVariable("button", "I", null, l0, l2, 3);
            mv.visitMaxs(4, 4);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "keyPress", "(CI)V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(42, l0);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, "me/curlpipesh/pipe/gui/GuiScreen", "currentModule", "Lme/curlpipesh/pipe/gui/GuiModule;");
            mv.visitVarInsn(ILOAD, 1);
            mv.visitVarInsn(ILOAD, 2);
            mv.visitMethodInsn(INVOKEINTERFACE, "me/curlpipesh/pipe/gui/GuiModule", "keypress", "(CI)V", true);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLineNumber(43, l1);
            mv.visitInsn(RETURN);
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitLocalVariable("this", "Lme/curlpipesh/pipe/gui/GuiScreen;", null, l0, l2, 0);
            mv.visitLocalVariable("c", "C", null, l0, l2, 1);
            mv.visitLocalVariable("i", "I", null, l0, l2, 2);
            mv.visitMaxs(3, 3);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "a", "(IIF)V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(46, l0);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(ILOAD, 1);
            mv.visitVarInsn(ILOAD, 2);
            mv.visitVarInsn(FLOAD, 3);
            mv.visitMethodInsn(INVOKEVIRTUAL, "me/curlpipesh/pipe/gui/GuiScreen", "drawScreen", "(IIF)V", false);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLineNumber(47, l1);
            mv.visitInsn(RETURN);
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitLocalVariable("this", "Lme/curlpipesh/pipe/gui/GuiScreen;", null, l0, l2, 0);
            mv.visitLocalVariable("i", "I", null, l0, l2, 1);
            mv.visitLocalVariable("j1", "I", null, l0, l2, 2);
            mv.visitLocalVariable("f", "F", null, l0, l2, 3);
            mv.visitMaxs(4, 4);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "a", "(CI)V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(50, l0);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(ILOAD, 1);
            mv.visitVarInsn(ILOAD, 2);
            mv.visitMethodInsn(INVOKEVIRTUAL, "me/curlpipesh/pipe/gui/GuiScreen", "keyPress", "(CI)V", false);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLineNumber(51, l1);
            mv.visitInsn(RETURN);
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitLocalVariable("this", "Lme/curlpipesh/pipe/gui/GuiScreen;", null, l0, l2, 0);
            mv.visitLocalVariable("c", "C", null, l0, l2, 1);
            mv.visitLocalVariable("i", "I", null, l0, l2, 2);
            mv.visitMaxs(3, 3);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "a", "(III)V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(54, l0);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(ILOAD, 1);
            mv.visitVarInsn(ILOAD, 2);
            mv.visitVarInsn(ILOAD, 3);
            mv.visitMethodInsn(INVOKEVIRTUAL, "me/curlpipesh/pipe/gui/GuiScreen", "mouseClicked", "(III)V", false);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLineNumber(55, l1);
            mv.visitInsn(RETURN);
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitLocalVariable("this", "Lme/curlpipesh/pipe/gui/GuiScreen;", null, l0, l2, 0);
            mv.visitLocalVariable("i", "I", null, l0, l2, 1);
            mv.visitLocalVariable("j", "I", null, l0, l2, 2);
            mv.visitLocalVariable("k", "I", null, l0, l2, 3);
            mv.visitMaxs(4, 4);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "b", "(III)V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(58, l0);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(ILOAD, 1);
            mv.visitVarInsn(ILOAD, 2);
            mv.visitVarInsn(ILOAD, 3);
            mv.visitMethodInsn(INVOKEVIRTUAL, "me/curlpipesh/pipe/gui/GuiScreen", "mouseReleased", "(III)V", false);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLineNumber(59, l1);
            mv.visitInsn(RETURN);
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitLocalVariable("this", "Lme/curlpipesh/pipe/gui/GuiScreen;", null, l0, l2, 0);
            mv.visitLocalVariable("i", "I", null, l0, l2, 1);
            mv.visitLocalVariable("j", "I", null, l0, l2, 2);
            mv.visitLocalVariable("k", "I", null, l0, l2, 3);
            mv.visitMaxs(4, 4);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "a", "(IIIJ)V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(62, l0);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(ILOAD, 1);
            mv.visitVarInsn(ILOAD, 2);
            mv.visitVarInsn(ILOAD, 3);
            mv.visitVarInsn(LLOAD, 4);
            mv.visitMethodInsn(INVOKEVIRTUAL, "me/curlpipesh/pipe/gui/GuiScreen", "mouseDownDrag", "(IIIJ)V", false);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLineNumber(63, l1);
            mv.visitInsn(RETURN);
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitLocalVariable("this", "Lme/curlpipesh/pipe/gui/GuiScreen;", null, l0, l2, 0);
            mv.visitLocalVariable("i", "I", null, l0, l2, 1);
            mv.visitLocalVariable("j", "I", null, l0, l2, 2);
            mv.visitLocalVariable("k", "I", null, l0, l2, 3);
            mv.visitLocalVariable("l", "J", null, l0, l2, 4);
            mv.visitMaxs(6, 6);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "b", "()V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(66, l0);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKEVIRTUAL, "me/curlpipesh/pipe/gui/GuiScreen", "initGui", "()V", false);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLineNumber(67, l1);
            mv.visitInsn(RETURN);
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitLocalVariable("this", "Lme/curlpipesh/pipe/gui/GuiScreen;", null, l0, l2, 0);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "doesGuiPauseGame", "()Z", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(70, l0);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, "me/curlpipesh/pipe/gui/GuiScreen", "currentModule", "Lme/curlpipesh/pipe/gui/GuiModule;");
            mv.visitMethodInsn(INVOKEINTERFACE, "me/curlpipesh/pipe/gui/GuiModule", "isPauseGame", "()Z", true);
            mv.visitInsn(IRETURN);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLocalVariable("this", "Lme/curlpipesh/pipe/gui/GuiScreen;", null, l0, l1, 0);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "d", "()Z", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(74, l0);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKEVIRTUAL, "me/curlpipesh/pipe/gui/GuiScreen", "doesGuiPauseGame", "()Z", false);
            mv.visitInsn(IRETURN);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLocalVariable("this", "Lme/curlpipesh/pipe/gui/GuiScreen;", null, l0, l1, 0);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getInstance", "()Lme/curlpipesh/pipe/gui/GuiScreen;", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(78, l0);
            mv.visitFieldInsn(GETSTATIC, "me/curlpipesh/pipe/gui/GuiScreen", "instance", "Lme/curlpipesh/pipe/gui/GuiScreen;");
            mv.visitInsn(ARETURN);
            mv.visitMaxs(1, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "getCurrentModule", "()Lme/curlpipesh/pipe/gui/GuiModule;", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(15, l0);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, "me/curlpipesh/pipe/gui/GuiScreen", "currentModule", "Lme/curlpipesh/pipe/gui/GuiModule;");
            mv.visitInsn(ARETURN);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLocalVariable("this", "Lme/curlpipesh/pipe/gui/GuiScreen;", null, l0, l1, 0);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "setCurrentModule", "(Lme/curlpipesh/pipe/gui/GuiModule;)V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(16, l0);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitFieldInsn(PUTFIELD, "me/curlpipesh/pipe/gui/GuiScreen", "currentModule", "Lme/curlpipesh/pipe/gui/GuiModule;");
            mv.visitInsn(RETURN);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLocalVariable("this", "Lme/curlpipesh/pipe/gui/GuiScreen;", null, l0, l1, 0);
            mv.visitLocalVariable("currentModule", "Lme/curlpipesh/pipe/gui/GuiModule;", null, l0, l1, 1);
            mv.visitMaxs(2, 2);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(19, l0);
            mv.visitTypeInsn(NEW, "me/curlpipesh/pipe/gui/GuiScreen");
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESPECIAL, "me/curlpipesh/pipe/gui/GuiScreen", "<init>", "()V", false);
            mv.visitFieldInsn(PUTSTATIC, "me/curlpipesh/pipe/gui/GuiScreen", "instance", "Lme/curlpipesh/pipe/gui/GuiScreen;");
            mv.visitInsn(RETURN);
            mv.visitMaxs(2, 0);
            mv.visitEnd();
        }
        cw.visitEnd();

        return cw.toByteArray();
    }
}
