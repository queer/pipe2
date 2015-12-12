package me.curlpipesh.pipe.bytecode.generators;

import me.curlpipesh.pipe.util.helpers.Helper;
import org.objectweb.asm.*;

import static me.curlpipesh.pipe.util.Constants.getByName;
import static org.objectweb.asm.Opcodes.*;

/**
 * Generates bytecode to fill in the methods in
 * {@link Helper}.
 *
 * @author c
 * @since 5/20/15
 */
@SuppressWarnings({"unused", "Duplicates"})
public class HelperGenerator {
    public static byte[] generate() {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        @SuppressWarnings("unused")
        FieldVisitor fv;
        MethodVisitor mv;
        @SuppressWarnings("unused")
        AnnotationVisitor av0;

        cw.visit(52, ACC_PUBLIC + ACC_SUPER, "me/curlpipesh/pipe/util/helpers/Helper", null, "java/lang/Object", null);

        cw.visitSource("Helper.java", null);

        {
            mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "isWorldNull", "()Z", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "getWorld", "()Ljava/lang/Object;", false);
            Label l1 = new Label();
            mv.visitJumpInsn(IFNONNULL, l1);
            mv.visitInsn(ICONST_1);
            Label l2 = new Label();
            mv.visitJumpInsn(GOTO, l2);
            mv.visitLabel(l1);
            mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            mv.visitInsn(ICONST_0);
            mv.visitLabel(l2);
            mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[]{Opcodes.INTEGER});
            mv.visitInsn(IRETURN);
            mv.visitMaxs(1, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getMinecraft", "()Ljava/lang/Object;", null, null);
            mv.visitCode();
            mv.visitLabel(new Label());
            mv.visitMethodInsn(INVOKESTATIC, getByName("Minecraft").getName(), "A", "()" + getByName("Minecraft").getDesc(), false);
            mv.visitInsn(ARETURN);
            mv.visitMaxs(1, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getWorld", "()Ljava/lang/Object;", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, getByName("Minecraft").getName(), "A", "()" + getByName("Minecraft").getDesc(), false);
            mv.visitFieldInsn(GETFIELD, getByName("Minecraft").getDesc(), "f", getByName("World").getDesc());
            mv.visitInsn(ARETURN);
            mv.visitMaxs(1, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getPlayer", "()Ljava/lang/Object;", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, getByName("Minecraft").getName(), "A", "()" + getByName("Minecraft").getDesc(), false);
            mv.visitFieldInsn(GETFIELD, getByName("Minecraft").getDesc(), "h", getByName("EntityThePlayer").getDesc());
            mv.visitInsn(ARETURN);
            mv.visitMaxs(1, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getGameSettings", "()Ljava/lang/Object;", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, getByName("Minecraft").getName(), "A", "()" + getByName("Minecraft").getDesc(), false);
            mv.visitFieldInsn(GETFIELD, getByName("Minecraft").getDesc(), "t", getByName("GameSettings").getDesc());
            mv.visitInsn(ARETURN);
            mv.visitMaxs(1, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "isIngameGuiInDebugMode", "()Z", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, getByName("Minecraft").getName(), "A", "()" + getByName("Minecraft").getDesc(), false);
            mv.visitFieldInsn(GETFIELD, getByName("Minecraft").getDesc(), "t", getByName("GameSettings").getDesc());
            mv.visitFieldInsn(GETFIELD, getByName("GameSettings").getDesc(), "aA", "Z");
            mv.visitInsn(IRETURN);
            mv.visitMaxs(3, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getFontRenderer", "()Ljava/lang/Object;", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, getByName("Minecraft").getName(), "A", "()" + getByName("Minecraft").getDesc(), false);
            mv.visitFieldInsn(GETFIELD, getByName("Minecraft").getDesc(), "k", getByName("FontRenderer").getDesc());
            mv.visitInsn(ARETURN);
            mv.visitMaxs(1, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getFontHeight", "()I", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "getFontRenderer", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, getByName("FontRenderer").getName());
            mv.visitFieldInsn(GETFIELD, getByName("FontRenderer").getDesc(), "a", "I");
            mv.visitInsn(IRETURN);
            mv.visitMaxs(1, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getStringWidth", "(Ljava/lang/String;)I", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "getFontRenderer", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, getByName("FontRenderer").getName());
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKEVIRTUAL, getByName("FontRenderer").getName(), "a", "(Ljava/lang/String;)I", false);
            mv.visitInsn(IRETURN);
            mv.visitMaxs(4, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "drawString", "(Ljava/lang/String;FFIZ)V", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "getFontRenderer", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, getByName("FontRenderer").getName());
            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(FLOAD, 1);
            mv.visitVarInsn(FLOAD, 2);
            mv.visitVarInsn(ILOAD, 3);
            mv.visitVarInsn(ILOAD, 4);
            mv.visitMethodInsn(INVOKEVIRTUAL, getByName("FontRenderer").getName(), "a", "(Ljava/lang/String;FFIZ)I", false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(6, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getLoadedEntities", "()Ljava/util/List;", "()Ljava/util/List<*>;", null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "getWorld", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, getByName("AbstractWorld").getName());
            mv.visitFieldInsn(GETFIELD, getByName("AbstractWorld").getDesc(), "f", "Ljava/util/List;");
            mv.visitInsn(ARETURN);
            mv.visitMaxs(1, 0);
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getEntityVec", "(Ljava/lang/Object;)Lme/curlpipesh/pipe/util/Vec3;", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(INSTANCEOF, "me/curlpipesh/pipe/util/Vec3");
            Label l1 = new Label();
            mv.visitJumpInsn(IFNE, l1);
            mv.visitTypeInsn(NEW, "me/curlpipesh/pipe/util/Vec3");
            mv.visitInsn(DUP);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, getByName("Entity").getName());
            mv.visitFieldInsn(GETFIELD, getByName("Entity").getDesc(), "s", "D");
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, getByName("Entity").getName());
            mv.visitFieldInsn(GETFIELD, getByName("Entity").getDesc(), "t", "D");
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, getByName("Entity").getName());
            mv.visitFieldInsn(GETFIELD, getByName("Entity").getDesc(), "u", "D");
            mv.visitMethodInsn(INVOKESPECIAL, "me/curlpipesh/pipe/util/Vec3", "<init>", "(DDD)V", false);
            mv.visitInsn(ARETURN);
            mv.visitLabel(l1);
            mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            mv.visitInsn(ACONST_NULL);
            mv.visitInsn(ARETURN);
            mv.visitMaxs(8, 2);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getEntityPrevVec", "(Ljava/lang/Object;)Lme/curlpipesh/pipe/util/Vec3;", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(INSTANCEOF, "me/curlpipesh/pipe/util/Vec3");
            Label l1 = new Label();
            mv.visitJumpInsn(IFNE, l1);
            mv.visitTypeInsn(NEW, "me/curlpipesh/pipe/util/Vec3");
            mv.visitInsn(DUP);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, getByName("Entity").getName());
            mv.visitFieldInsn(GETFIELD, getByName("Entity").getDesc(), "p", "D");
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, getByName("Entity").getName());
            mv.visitFieldInsn(GETFIELD, getByName("Entity").getDesc(), "q", "D");
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, getByName("Entity").getName());
            mv.visitFieldInsn(GETFIELD, getByName("Entity").getDesc(), "r", "D");
            mv.visitMethodInsn(INVOKESPECIAL, "me/curlpipesh/pipe/util/Vec3", "<init>", "(DDD)V", false);
            mv.visitInsn(ARETURN);
            mv.visitLabel(l1);
            mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            mv.visitInsn(ACONST_NULL);
            mv.visitInsn(ARETURN);
            mv.visitMaxs(8, 2);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getLightBrightnessTable", "()[F", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "getWorld", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, getByName("AbstractWorld").getName());
            mv.visitFieldInsn(GETFIELD, getByName("AbstractWorld").getDesc(), "t", getByName("WorldProvider").getDesc());
            mv.visitFieldInsn(GETFIELD, getByName("WorldProvider").getDesc(), "f", "[F");
            mv.visitInsn(ARETURN);
            mv.visitMaxs(3, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "isEntityLiving", "(Ljava/lang/Object;)Z", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(INSTANCEOF, getByName("EntityLiving").getName());
            mv.visitInsn(IRETURN);
            mv.visitMaxs(1, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "isEntityAnimal", "(Ljava/lang/Object;)Z", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(INSTANCEOF, getByName("EntityAnimal").getName());
            mv.visitInsn(IRETURN);
            mv.visitMaxs(1, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "isEntityMonster", "(Ljava/lang/Object;)Z", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(INSTANCEOF, getByName("EntityMonster").getName());
            mv.visitInsn(IRETURN);
            mv.visitMaxs(1, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "isEntityPlayer", "(Ljava/lang/Object;)Z", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(INSTANCEOF, getByName("EntityPlayer").getName());
            mv.visitInsn(IRETURN);
            mv.visitMaxs(1, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getLoadedBlockEntities", "()Ljava/util/List;", "()Ljava/util/List<*>;", null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "getWorld", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, getByName("AbstractWorld").getName());
            mv.visitFieldInsn(GETFIELD, getByName("AbstractWorld").getDesc(), "h", "Ljava/util/List;");
            mv.visitInsn(ARETURN);
            mv.visitMaxs(1, 0);
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getBlockEntityVec", "(Ljava/lang/Object;)Lme/curlpipesh/pipe/util/Vec3;", null, null);
            mv.visitCode();
            mv.visitTypeInsn(NEW, "me/curlpipesh/pipe/util/Vec3");
            mv.visitInsn(DUP);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, getByName("BlockEntity").getName());
            mv.visitFieldInsn(GETFIELD, getByName("BlockEntity").getName(), "c", getByName("BlockPos").getDesc());
            mv.visitTypeInsn(CHECKCAST, getByName("Vec3i").getName());
            mv.visitMethodInsn(INVOKEVIRTUAL, getByName("Vec3i").getName(), "n", "()I", false);
            mv.visitInsn(I2D);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, getByName("BlockEntity").getName());
            mv.visitFieldInsn(GETFIELD, getByName("BlockEntity").getName(), "c", getByName("BlockPos").getDesc());
            mv.visitTypeInsn(CHECKCAST, getByName("Vec3i").getName());
            mv.visitMethodInsn(INVOKEVIRTUAL, getByName("Vec3i").getName(), "o", "()I", false);
            mv.visitInsn(I2D);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, getByName("BlockEntity").getName());
            mv.visitFieldInsn(GETFIELD, getByName("BlockEntity").getName(), "c", getByName("BlockPos").getDesc());
            mv.visitTypeInsn(CHECKCAST, getByName("Vec3i").getName());
            mv.visitMethodInsn(INVOKEVIRTUAL, getByName("Vec3i").getName(), "p", "()I", false);
            mv.visitInsn(I2D);
            mv.visitMethodInsn(INVOKESPECIAL, "me/curlpipesh/pipe/util/Vec3", "<init>", "(DDD)V", false);
            mv.visitInsn(ARETURN);
            mv.visitMaxs(8, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "isBlockEntityChest", "(Ljava/lang/Object;)Z", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(INSTANCEOF, getByName("BlockEntityChest").getName());
            Label l1 = new Label();
            mv.visitJumpInsn(IFNE, l1);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(INSTANCEOF, getByName("BlockEntityEnderChest").getName());
            Label l2 = new Label();
            mv.visitJumpInsn(IFEQ, l2);
            mv.visitLabel(l1);
            mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            mv.visitInsn(ICONST_1);
            Label l3 = new Label();
            mv.visitJumpInsn(GOTO, l3);
            mv.visitLabel(l2);
            mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            mv.visitInsn(ICONST_0);
            mv.visitLabel(l3);
            mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[]{Opcodes.INTEGER});
            mv.visitInsn(IRETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "enableLightmap", "()V", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, getByName("Minecraft").getName(), "A", "()" + getByName("Minecraft").getDesc(), false);
            mv.visitFieldInsn(GETFIELD, getByName("Minecraft").getName(), "o", getByName("EntityRenderer").getDesc());
            mv.visitMethodInsn(INVOKEVIRTUAL, getByName("EntityRenderer").getName(), "i", "()V", false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(2, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "disableLightmap", "()V", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, getByName("Minecraft").getName(), "A", "()" + getByName("Minecraft").getDesc(), false);
            mv.visitFieldInsn(GETFIELD, getByName("Minecraft").getName(), "o", getByName("EntityRenderer").getDesc());
            mv.visitMethodInsn(INVOKEVIRTUAL, getByName("EntityRenderer").getName(), "i", "()V", false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(2, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "addChatMessage", "(Ljava/lang/String;)V", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "getPlayer", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, getByName("EntityThePlayer").getName());
            mv.visitTypeInsn(NEW, getByName("ChatComponentText").getName());
            mv.visitInsn(DUP);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, getByName("ChatComponentText").getName(), "<init>", "(Ljava/lang/String;)V", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, getByName("EntityThePlayer").getName(), "a", "(" + getByName("IChatComponent").getDesc() + ")V", false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(3, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "sendChatMessage", "(Ljava/lang/String;)V", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "getPlayer", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, getByName("EntityThePlayer").getName());
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKEVIRTUAL, getByName("EntityThePlayer").getName(), "e", "(Ljava/lang/String;)V", false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(2, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "sendPacket", "(Ljava/lang/Object;)V", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "getPlayer", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, getByName("EntityThePlayer").getName());
            mv.visitFieldInsn(GETFIELD, getByName("EntityThePlayer").getDesc(), "a", getByName("NetHandlerPlayClient").getDesc());
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, getByName("Packet").getName());
            mv.visitMethodInsn(INVOKEVIRTUAL, getByName("NetHandlerPlayClient").getName(), "a", "(" + getByName("Packet").getDesc() + ")V", false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(5, 5);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getWidth", "()I", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "getMinecraft", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, getByName("Minecraft").getName());
            mv.visitFieldInsn(GETFIELD, getByName("Minecraft").getDesc(), "d", "I");
            mv.visitInsn(IRETURN);
            mv.visitMaxs(2, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getHeight", "()I", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "getMinecraft", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, getByName("Minecraft").getName());
            mv.visitFieldInsn(GETFIELD, getByName("Minecraft").getDesc(), "e", "I");
            mv.visitInsn(IRETURN);
            mv.visitMaxs(2, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "displayGuiScreen", "(Lme/curlpipesh/pipe/gui/GuiScreen;)V", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, getByName("Minecraft").getName(), "A", "()" + getByName("Minecraft").getDesc(), false);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, getByName("GuiScreen").getName());
            mv.visitMethodInsn(INVOKEVIRTUAL, getByName("Minecraft").getName(), "a", "(" + getByName("GuiScreen").getDesc() + ")V", false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(2, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "mc_displayGuiScreen", "(Ljava/lang/Object;)V", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, getByName("Minecraft").getName(), "A", "()" + getByName("Minecraft").getDesc(), false);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, getByName("GuiScreen").getName());
            mv.visitMethodInsn(INVOKEVIRTUAL, getByName("Minecraft").getName(), "a", "(" + getByName("GuiScreen").getDesc() + ")V", false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(2, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "displayGuiModule", "(Lme/curlpipesh/pipe/gui/GuiModule;)V", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "me/curlpipesh/pipe/gui/GuiScreen", "getInstance", "()Lme/curlpipesh/pipe/gui/GuiScreen;", false);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKEVIRTUAL, "me/curlpipesh/pipe/gui/GuiScreen", "setCurrentModule", "(Lme/curlpipesh/pipe/gui/GuiModule;)V", false);
            mv.visitMethodInsn(INVOKESTATIC, "me/curlpipesh/pipe/gui/GuiScreen", "getInstance", "()Lme/curlpipesh/pipe/gui/GuiScreen;", false);
            mv.visitMethodInsn(INVOKESTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "displayGuiScreen", "(Lme/curlpipesh/pipe/gui/GuiScreen;)V", false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(2, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getScale", "()I", null, null);
            mv.visitCode();
            mv.visitTypeInsn(NEW, getByName("ScaledResolution").getName());
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESTATIC, getByName("Minecraft").getName(), "A", "()" + getByName("Minecraft").getDesc(), false);
            mv.visitMethodInsn(INVOKESPECIAL, getByName("ScaledResolution").getName(), "<init>", "(" + getByName("Minecraft").getDesc() + ")V", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, getByName("ScaledResolution").getName(), "e", "()I", false);
            mv.visitInsn(IRETURN);
            mv.visitMaxs(4, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "isViewBobbingEnabled", "()Z", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "getGameSettings", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, getByName("GameSettings").getName());
            mv.visitFieldInsn(GETFIELD, getByName("GameSettings").getDesc(), "d", "Z");
            mv.visitInsn(IRETURN);
            mv.visitMaxs(4, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "setViewBobbing", "(Z)V", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "getGameSettings", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, getByName("GameSettings").getName());
            mv.visitVarInsn(ILOAD, 0);
            mv.visitFieldInsn(PUTFIELD, getByName("GameSettings").getDesc(), "d", "Z");
            mv.visitInsn(RETURN);
            mv.visitMaxs(2, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getCurrentScreen", "()Ljava/lang/Object;", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "getMinecraft", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, getByName("Minecraft").getName());
            mv.visitFieldInsn(GETFIELD, getByName("Minecraft").getDesc(), "m", getByName("GuiScreen").getDesc());
            mv.visitInsn(ARETURN);
            mv.visitMaxs(4, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "isEntitySneaking", "(Ljava/lang/Object;)Z", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, getByName("Entity").getName());
            mv.visitMethodInsn(INVOKEVIRTUAL, getByName("Entity").getName(), "av", "()Z", false);
            mv.visitInsn(IRETURN);
            mv.visitMaxs(4, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_STATIC, "_sendChatMessage", "(Ljava/lang/String;)V", null, null);
            mv.visitCode();
            mv.visitTypeInsn(NEW, getByName("PacketClientChatMessage").getName());
            mv.visitInsn(DUP);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, getByName("PacketClientChatMessage").getName(), "<init>", "(Ljava/lang/String;)V", false);
            mv.visitMethodInsn(INVOKESTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "sendPacket", "(Ljava/lang/Object;)V", false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(5, 5);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "setItemStackDisplayName", "(Ljava/lang/Object;Ljava/lang/String;)V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, getByName("ItemStack").getName());
            mv.visitVarInsn(ALOAD, 1);
            mv.visitMethodInsn(INVOKEVIRTUAL, getByName("ItemStack").getName(), "c", "(Ljava/lang/String;)" + getByName("ItemStack").getDesc(), false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(5, 5);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getInventoryContainer", "()Ljava/lang/Object;", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "getPlayer", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, getByName("EntityPlayer").getName());
            mv.visitFieldInsn(GETFIELD, getByName("EntityPlayer").getName(), "bj", getByName("Container").getDesc());
            mv.visitInsn(ARETURN);
            mv.visitMaxs(2, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getStackInSlot", "(Ljava/lang/Object;I)Ljava/lang/Object;", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "getInventoryContainer", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, getByName("Container").getName());
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, getByName("EntityPlayer").getName());
            mv.visitVarInsn(ILOAD, 1);
            mv.visitMethodInsn(INVOKEVIRTUAL, getByName("Container").getName(),
                    "b", "(" + getByName("EntityPlayer").getDesc() + "I)" + getByName("ItemStack").getDesc(), false);
            mv.visitInsn(ARETURN);
            mv.visitMaxs(5, 5);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getCurrentSlot", "()I", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "getPlayer", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, getByName("EntityPlayer").getName());
            mv.visitFieldInsn(GETFIELD, getByName("EntityPlayer").getDesc(), "bi", getByName("InventoryPlayer").getDesc());
            mv.visitFieldInsn(GETFIELD, getByName("InventoryPlayer").getDesc(), "c", "I");
            mv.visitInsn(IRETURN);
            mv.visitMaxs(1, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getMinecraftDataDir", "()Ljava/io/File;", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "Lme/curlpipesh/pipe/util/helpers/Helper;", "getMinecraft", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, getByName("Minecraft").getName());
            mv.visitFieldInsn(GETFIELD, getByName("Minecraft").getDesc(), "v", "Ljava/io/File;");
            mv.visitInsn(ARETURN);
            mv.visitMaxs(1, 0);
            mv.visitEnd();
        }

        cw.visitEnd();

        return cw.toByteArray();
    }
}
