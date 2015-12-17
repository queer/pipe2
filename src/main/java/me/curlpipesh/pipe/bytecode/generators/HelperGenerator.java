package me.curlpipesh.pipe.bytecode.generators;

import me.curlpipesh.pipe.util.helpers.Helper;
import org.objectweb.asm.*;

import static me.curlpipesh.pipe.bytecode.map.ClassMap.getClassByName;
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
            fv = cw.visitField(ACC_PRIVATE + ACC_FINAL + ACC_STATIC, "entityVec", "Lme/curlpipesh/pipe/util/Vec3;", null, null);
            fv.visitEnd();
        }
        {
            fv = cw.visitField(ACC_PRIVATE + ACC_FINAL + ACC_STATIC, "entityPrevVec", "Lme/curlpipesh/pipe/util/Vec3;", null, null);
            fv.visitEnd();
        }
        {
            fv = cw.visitField(ACC_PRIVATE + ACC_FINAL + ACC_STATIC, "blockEntityVec", "Lme/curlpipesh/pipe/util/Vec3;", null, null);
            fv.visitEnd();
        }
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
            mv.visitMethodInsn(INVOKESTATIC, getClassByName("Minecraft").getObfuscatedName(), "A", "()" + getClassByName("Minecraft").getDescription(), false);
            mv.visitInsn(ARETURN);
            mv.visitMaxs(1, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getWorld", "()Ljava/lang/Object;", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, getClassByName("Minecraft").getObfuscatedName(), "A", "()" + getClassByName("Minecraft").getDescription(), false);
            mv.visitFieldInsn(GETFIELD, getClassByName("Minecraft").getDescription(), "f", getClassByName("World").getDescription());
            mv.visitInsn(ARETURN);
            mv.visitMaxs(1, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getPlayer", "()Ljava/lang/Object;", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, getClassByName("Minecraft").getObfuscatedName(), "A", "()" + getClassByName("Minecraft").getDescription(), false);
            mv.visitFieldInsn(GETFIELD, getClassByName("Minecraft").getDescription(), "h", getClassByName("EntityClientPlayer").getDescription());
            mv.visitInsn(ARETURN);
            mv.visitMaxs(1, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getGameSettings", "()Ljava/lang/Object;", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, getClassByName("Minecraft").getObfuscatedName(), "A", "()" + getClassByName("Minecraft").getDescription(), false);
            mv.visitFieldInsn(GETFIELD, getClassByName("Minecraft").getDescription(), "t", getClassByName("GameSettings").getDescription());
            mv.visitInsn(ARETURN);
            mv.visitMaxs(1, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "isIngameGuiInDebugMode", "()Z", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, getClassByName("Minecraft").getObfuscatedName(), "A", "()" + getClassByName("Minecraft").getDescription(), false);
            mv.visitFieldInsn(GETFIELD, getClassByName("Minecraft").getDescription(), "t", getClassByName("GameSettings").getDescription());
            mv.visitFieldInsn(GETFIELD, getClassByName("GameSettings").getDescription(), "aA", "Z");
            mv.visitInsn(IRETURN);
            mv.visitMaxs(3, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getFontRenderer", "()Ljava/lang/Object;", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, getClassByName("Minecraft").getObfuscatedName(), "A", "()" + getClassByName("Minecraft").getDescription(), false);
            mv.visitFieldInsn(GETFIELD, getClassByName("Minecraft").getDescription(), "k", getClassByName("FontRenderer").getDescription());
            mv.visitInsn(ARETURN);
            mv.visitMaxs(1, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getFontHeight", "()I", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "getFontRenderer", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, getClassByName("FontRenderer").getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, getClassByName("FontRenderer").getDescription(), "a", "I");
            mv.visitInsn(IRETURN);
            mv.visitMaxs(1, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getStringWidth", "(Ljava/lang/String;)I", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "getFontRenderer", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, getClassByName("FontRenderer").getObfuscatedName());
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKEVIRTUAL, getClassByName("FontRenderer").getObfuscatedName(), "a", "(Ljava/lang/String;)I", false);
            mv.visitInsn(IRETURN);
            mv.visitMaxs(4, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "drawString", "(Ljava/lang/String;FFIZ)V", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "getFontRenderer", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, getClassByName("FontRenderer").getObfuscatedName());
            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(FLOAD, 1);
            mv.visitVarInsn(FLOAD, 2);
            mv.visitVarInsn(ILOAD, 3);
            mv.visitVarInsn(ILOAD, 4);
            mv.visitMethodInsn(INVOKEVIRTUAL, getClassByName("FontRenderer").getObfuscatedName(), "a", "(Ljava/lang/String;FFIZ)I", false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(6, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getLoadedEntities", "()Ljava/util/List;", "()Ljava/util/List<*>;", null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "getWorld", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, getClassByName("AbstractWorld").getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, getClassByName("AbstractWorld").getDescription(), "f", "Ljava/util/List;");
            mv.visitInsn(ARETURN);
            mv.visitMaxs(1, 0);
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getEntityVec", "(Ljava/lang/Object;)Lme/curlpipesh/pipe/util/Vec3;", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitFieldInsn(GETSTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "entityVec", "Lme/curlpipesh/pipe/util/Vec3;");
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, getClassByName("Entity").getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, getClassByName("Entity").getDescription(), "s", "D");
            mv.visitMethodInsn(INVOKEVIRTUAL, "me/curlpipesh/pipe/util/Vec3", "x", "(D)V", false);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitFieldInsn(GETSTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "entityVec", "Lme/curlpipesh/pipe/util/Vec3;");
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, getClassByName("Entity").getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, getClassByName("Entity").getDescription(), "t", "D");
            mv.visitMethodInsn(INVOKEVIRTUAL, "me/curlpipesh/pipe/util/Vec3", "y", "(D)V", false);
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitFieldInsn(GETSTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "entityVec", "Lme/curlpipesh/pipe/util/Vec3;");
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, getClassByName("Entity").getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, getClassByName("Entity").getDescription(), "u", "D");
            mv.visitMethodInsn(INVOKEVIRTUAL, "me/curlpipesh/pipe/util/Vec3", "z", "(D)V", false);
            Label l3 = new Label();
            mv.visitLabel(l3);
            mv.visitFieldInsn(GETSTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "entityVec", "Lme/curlpipesh/pipe/util/Vec3;");
            mv.visitInsn(ARETURN);
            Label l4 = new Label();
            mv.visitLabel(l4);
            mv.visitLocalVariable("entity", "Ljava/lang/Object;", null, l0, l4, 0);
            mv.visitMaxs(3, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getEntityPrevVec", "(Ljava/lang/Object;)Lme/curlpipesh/pipe/util/Vec3;", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitFieldInsn(GETSTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "entityPrevVec", "Lme/curlpipesh/pipe/util/Vec3;");
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, getClassByName("Entity").getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, getClassByName("Entity").getDescription(), "p", "D");
            mv.visitMethodInsn(INVOKEVIRTUAL, "me/curlpipesh/pipe/util/Vec3", "x", "(D)V", false);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitFieldInsn(GETSTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "entityPrevVec", "Lme/curlpipesh/pipe/util/Vec3;");
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, getClassByName("Entity").getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, getClassByName("Entity").getDescription(), "q", "D");
            mv.visitMethodInsn(INVOKEVIRTUAL, "me/curlpipesh/pipe/util/Vec3", "y", "(D)V", false);
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitFieldInsn(GETSTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "entityPrevVec", "Lme/curlpipesh/pipe/util/Vec3;");
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, getClassByName("Entity").getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, getClassByName("Entity").getDescription(), "r", "D");
            mv.visitMethodInsn(INVOKEVIRTUAL, "me/curlpipesh/pipe/util/Vec3", "z", "(D)V", false);
            Label l3 = new Label();
            mv.visitLabel(l3);
            mv.visitFieldInsn(GETSTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "entityPrevVec", "Lme/curlpipesh/pipe/util/Vec3;");
            mv.visitInsn(ARETURN);
            Label l4 = new Label();
            mv.visitLabel(l4);
            mv.visitLocalVariable("entity", "Ljava/lang/Object;", null, l0, l4, 0);
            mv.visitMaxs(3, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getLightBrightnessTable", "()[F", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "getWorld", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, getClassByName("AbstractWorld").getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, getClassByName("AbstractWorld").getDescription(), "t", getClassByName("WorldProvider").getDescription());
            mv.visitFieldInsn(GETFIELD, getClassByName("WorldProvider").getDescription(), "f", "[F");
            mv.visitInsn(ARETURN);
            mv.visitMaxs(3, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "isEntityLiving", "(Ljava/lang/Object;)Z", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(INSTANCEOF, getClassByName("EntityLiving").getObfuscatedName());
            mv.visitInsn(IRETURN);
            mv.visitMaxs(1, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "isEntityAnimal", "(Ljava/lang/Object;)Z", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(INSTANCEOF, getClassByName("EntityAnimal").getObfuscatedName());
            mv.visitInsn(IRETURN);
            mv.visitMaxs(1, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "isEntityMonster", "(Ljava/lang/Object;)Z", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(INSTANCEOF, getClassByName("EntityMonster").getObfuscatedName());
            mv.visitInsn(IRETURN);
            mv.visitMaxs(1, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "isEntityPlayer", "(Ljava/lang/Object;)Z", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(INSTANCEOF, getClassByName("EntityPlayer").getObfuscatedName());
            mv.visitInsn(IRETURN);
            mv.visitMaxs(1, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getLoadedBlockEntities", "()Ljava/util/List;", "()Ljava/util/List<*>;", null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "getWorld", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, getClassByName("AbstractWorld").getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, getClassByName("AbstractWorld").getDescription(), "h", "Ljava/util/List;");
            mv.visitInsn(ARETURN);
            mv.visitMaxs(1, 0);
        }

        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getBlockEntityVec", "(Ljava/lang/Object;)Lme/curlpipesh/pipe/util/Vec3;", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitFieldInsn(GETSTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "blockEntityVec", "Lme/curlpipesh/pipe/util/Vec3;");
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, getClassByName("BlockEntity").getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, getClassByName("BlockEntity").getObfuscatedName(), "c", getClassByName("BlockPos").getDescription());
            mv.visitTypeInsn(CHECKCAST, getClassByName("Vec3i").getObfuscatedName());
            mv.visitMethodInsn(INVOKEVIRTUAL, getClassByName("Vec3i").getObfuscatedName(), "n", "()I", false);
            mv.visitInsn(I2D);
            mv.visitMethodInsn(INVOKEVIRTUAL, "me/curlpipesh/pipe/util/Vec3", "x", "(D)V", false);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitFieldInsn(GETSTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "blockEntityVec", "Lme/curlpipesh/pipe/util/Vec3;");
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, getClassByName("BlockEntity").getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, getClassByName("BlockEntity").getObfuscatedName(), "c", getClassByName("BlockPos").getDescription());
            mv.visitTypeInsn(CHECKCAST, getClassByName("Vec3i").getObfuscatedName());
            mv.visitMethodInsn(INVOKEVIRTUAL, getClassByName("Vec3i").getObfuscatedName(), "o", "()I", false);
            mv.visitInsn(I2D);
            mv.visitMethodInsn(INVOKEVIRTUAL, "me/curlpipesh/pipe/util/Vec3", "y", "(D)V", false);
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitFieldInsn(GETSTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "blockEntityVec", "Lme/curlpipesh/pipe/util/Vec3;");
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, getClassByName("BlockEntity").getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, getClassByName("BlockEntity").getObfuscatedName(), "c", getClassByName("BlockPos").getDescription());
            mv.visitTypeInsn(CHECKCAST, getClassByName("Vec3i").getObfuscatedName());
            mv.visitMethodInsn(INVOKEVIRTUAL, getClassByName("Vec3i").getObfuscatedName(), "p", "()I", false);
            mv.visitInsn(I2D);
            mv.visitMethodInsn(INVOKEVIRTUAL, "me/curlpipesh/pipe/util/Vec3", "z", "(D)V", false);
            Label l3 = new Label();
            mv.visitLabel(l3);
            mv.visitFieldInsn(GETSTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "blockEntityVec", "Lme/curlpipesh/pipe/util/Vec3;");
            mv.visitInsn(ARETURN);
            Label l4 = new Label();
            mv.visitLabel(l4);
            mv.visitLocalVariable("entity", "Ljava/lang/Object;", null, l0, l4, 0);
            mv.visitMaxs(3, 1);
            mv.visitEnd();
        }

        /*{
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getBlockEntityVec", "(Ljava/lang/Object;)Lme/curlpipesh/pipe/util/Vec3;", null, null);
            mv.visitCode();
            mv.visitTypeInsn(NEW, "me/curlpipesh/pipe/util/Vec3");
            mv.visitInsn(DUP);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, getClassByName("BlockEntity").getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, getClassByName("BlockEntity").getObfuscatedName(), "c", getClassByName("BlockPos").getDescription());
            mv.visitTypeInsn(CHECKCAST, getClassByName("Vec3i").getObfuscatedName());
            mv.visitMethodInsn(INVOKEVIRTUAL, getClassByName("Vec3i").getObfuscatedName(), "n", "()I", false);
            mv.visitInsn(I2D);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, getClassByName("BlockEntity").getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, getClassByName("BlockEntity").getObfuscatedName(), "c", getClassByName("BlockPos").getDescription());
            mv.visitTypeInsn(CHECKCAST, getClassByName("Vec3i").getObfuscatedName());
            mv.visitMethodInsn(INVOKEVIRTUAL, getClassByName("Vec3i").getObfuscatedName(), "o", "()I", false);
            mv.visitInsn(I2D);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, getClassByName("BlockEntity").getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, getClassByName("BlockEntity").getObfuscatedName(), "c", getClassByName("BlockPos").getDescription());
            mv.visitTypeInsn(CHECKCAST, getClassByName("Vec3i").getObfuscatedName());
            mv.visitMethodInsn(INVOKEVIRTUAL, getClassByName("Vec3i").getObfuscatedName(), "p", "()I", false);
            mv.visitInsn(I2D);
            mv.visitMethodInsn(INVOKESPECIAL, "me/curlpipesh/pipe/util/Vec3", "<init>", "(DDD)V", false);
            mv.visitInsn(ARETURN);
            mv.visitMaxs(8, 1);
            mv.visitEnd();
        }*/
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "isBlockEntityChest", "(Ljava/lang/Object;)Z", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(INSTANCEOF, getClassByName("BlockEntityChest").getObfuscatedName());
            Label l1 = new Label();
            mv.visitJumpInsn(IFNE, l1);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(INSTANCEOF, getClassByName("BlockEntityEnderChest").getObfuscatedName());
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
            mv.visitMethodInsn(INVOKESTATIC, getClassByName("Minecraft").getObfuscatedName(), "A", "()" + getClassByName("Minecraft").getDescription(), false);
            mv.visitFieldInsn(GETFIELD, getClassByName("Minecraft").getObfuscatedName(), "o", getClassByName("EntityRenderer").getDescription());
            mv.visitMethodInsn(INVOKEVIRTUAL, getClassByName("EntityRenderer").getObfuscatedName(), "i", "()V", false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(2, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "disableLightmap", "()V", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, getClassByName("Minecraft").getObfuscatedName(), "A", "()" + getClassByName("Minecraft").getDescription(), false);
            mv.visitFieldInsn(GETFIELD, getClassByName("Minecraft").getObfuscatedName(), "o", getClassByName("EntityRenderer").getDescription());
            mv.visitMethodInsn(INVOKEVIRTUAL, getClassByName("EntityRenderer").getObfuscatedName(), "i", "()V", false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(2, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "addChatMessage", "(Ljava/lang/String;)V", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "getPlayer", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, getClassByName("EntityClientPlayer").getObfuscatedName());
            mv.visitTypeInsn(NEW, getClassByName("ChatComponentText").getObfuscatedName());
            mv.visitInsn(DUP);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, getClassByName("ChatComponentText").getObfuscatedName(), "<init>", "(Ljava/lang/String;)V", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, getClassByName("EntityClientPlayer").getObfuscatedName(), "a", "(" + getClassByName("IChatComponent").getDescription() + ")V", false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(3, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "sendChatMessage", "(Ljava/lang/String;)V", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "getPlayer", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, getClassByName("EntityClientPlayer").getObfuscatedName());
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKEVIRTUAL, getClassByName("EntityClientPlayer").getObfuscatedName(), "e", "(Ljava/lang/String;)V", false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(2, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "sendPacket", "(Ljava/lang/Object;)V", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "getPlayer", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, getClassByName("EntityClientPlayer").getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, getClassByName("EntityClientPlayer").getDescription(), "a", getClassByName("NetClientPlayHandler").getDescription());
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, getClassByName("Packet").getObfuscatedName());
            mv.visitMethodInsn(INVOKEVIRTUAL, getClassByName("NetClientPlayHandler").getObfuscatedName(), "a", "(" + getClassByName("Packet").getDescription() + ")V", false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(5, 5);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getWidth", "()I", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "getMinecraft", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, getClassByName("Minecraft").getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, getClassByName("Minecraft").getDescription(), "d", "I");
            mv.visitInsn(IRETURN);
            mv.visitMaxs(2, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getHeight", "()I", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "getMinecraft", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, getClassByName("Minecraft").getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, getClassByName("Minecraft").getDescription(), "e", "I");
            mv.visitInsn(IRETURN);
            mv.visitMaxs(2, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "displayGuiScreen", "(Lme/curlpipesh/pipe/gui/GuiScreen;)V", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, getClassByName("Minecraft").getObfuscatedName(), "A", "()" + getClassByName("Minecraft").getDescription(), false);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, getClassByName("GuiScreen").getObfuscatedName());
            mv.visitMethodInsn(INVOKEVIRTUAL, getClassByName("Minecraft").getObfuscatedName(), "a", "(" + getClassByName("GuiScreen").getDescription() + ")V", false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(2, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "mc_displayGuiScreen", "(Ljava/lang/Object;)V", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, getClassByName("Minecraft").getObfuscatedName(), "A", "()" + getClassByName("Minecraft").getDescription(), false);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, getClassByName("GuiScreen").getObfuscatedName());
            mv.visitMethodInsn(INVOKEVIRTUAL, getClassByName("Minecraft").getObfuscatedName(), "a", "(" + getClassByName("GuiScreen").getDescription() + ")V", false);
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
            mv.visitTypeInsn(NEW, getClassByName("ScaledResolution").getObfuscatedName());
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESTATIC, getClassByName("Minecraft").getObfuscatedName(), "A", "()" + getClassByName("Minecraft").getDescription(), false);
            mv.visitMethodInsn(INVOKESPECIAL, getClassByName("ScaledResolution").getObfuscatedName(), "<init>", "(" + getClassByName("Minecraft").getDescription() + ")V", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, getClassByName("ScaledResolution").getObfuscatedName(), "e", "()I", false);
            mv.visitInsn(IRETURN);
            mv.visitMaxs(4, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "isViewBobbingEnabled", "()Z", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "getGameSettings", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, getClassByName("GameSettings").getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, getClassByName("GameSettings").getDescription(), "d", "Z");
            mv.visitInsn(IRETURN);
            mv.visitMaxs(4, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "setViewBobbing", "(Z)V", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "getGameSettings", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, getClassByName("GameSettings").getObfuscatedName());
            mv.visitVarInsn(ILOAD, 0);
            mv.visitFieldInsn(PUTFIELD, getClassByName("GameSettings").getDescription(), "d", "Z");
            mv.visitInsn(RETURN);
            mv.visitMaxs(2, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getCurrentScreen", "()Ljava/lang/Object;", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "getMinecraft", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, getClassByName("Minecraft").getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, getClassByName("Minecraft").getDescription(), "m", getClassByName("GuiScreen").getDescription());
            mv.visitInsn(ARETURN);
            mv.visitMaxs(4, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "isEntitySneaking", "(Ljava/lang/Object;)Z", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, getClassByName("Entity").getObfuscatedName());
            mv.visitMethodInsn(INVOKEVIRTUAL, getClassByName("Entity").getObfuscatedName(), "av", "()Z", false);
            mv.visitInsn(IRETURN);
            mv.visitMaxs(4, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_STATIC, "_sendChatMessage", "(Ljava/lang/String;)V", null, null);
            mv.visitCode();
            mv.visitTypeInsn(NEW, getClassByName("PacketClientChatMessage").getObfuscatedName());
            mv.visitInsn(DUP);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, getClassByName("PacketClientChatMessage").getObfuscatedName(), "<init>", "(Ljava/lang/String;)V", false);
            mv.visitMethodInsn(INVOKESTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "sendPacket", "(Ljava/lang/Object;)V", false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(5, 5);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getInventoryContainer", "()Ljava/lang/Object;", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "getPlayer", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, getClassByName("EntityPlayer").getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, getClassByName("EntityPlayer").getObfuscatedName(), "bj", getClassByName("Container").getDescription());
            mv.visitInsn(ARETURN);
            mv.visitMaxs(2, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getStackInSlot", "(Ljava/lang/Object;I)Ljava/lang/Object;", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "getInventoryContainer", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, getClassByName("Container").getObfuscatedName());
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, getClassByName("EntityPlayer").getObfuscatedName());
            mv.visitVarInsn(ILOAD, 1);
            mv.visitMethodInsn(INVOKEVIRTUAL, getClassByName("Container").getObfuscatedName(),
                    "b", "(" + getClassByName("EntityPlayer").getDescription() + "I)" + getClassByName("ItemStack").getDescription(), false);
            mv.visitInsn(ARETURN);
            mv.visitMaxs(5, 5);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getCurrentSlot", "()I", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "getPlayer", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, getClassByName("EntityPlayer").getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, getClassByName("EntityPlayer").getDescription(), "bi", getClassByName("InventoryPlayer").getDescription());
            mv.visitFieldInsn(GETFIELD, getClassByName("InventoryPlayer").getDescription(), "c", "I");
            mv.visitInsn(IRETURN);
            mv.visitMaxs(1, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getMinecraftDataDir", "()Ljava/io/File;", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "Lme/curlpipesh/pipe/util/helpers/Helper;", "getMinecraft", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, getClassByName("Minecraft").getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, getClassByName("Minecraft").getDescription(), "v", "Ljava/io/File;");
            mv.visitInsn(ARETURN);
            mv.visitMaxs(1, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitTypeInsn(NEW, "me/curlpipesh/pipe/util/Vec3");
            mv.visitInsn(DUP);
            mv.visitInsn(DCONST_0);
            mv.visitInsn(DCONST_0);
            mv.visitInsn(DCONST_0);
            mv.visitMethodInsn(INVOKESPECIAL, "me/curlpipesh/pipe/util/Vec3", "<init>", "(DDD)V", false);
            mv.visitFieldInsn(PUTSTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "entityVec", "Lme/curlpipesh/pipe/util/Vec3;");
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitTypeInsn(NEW, "me/curlpipesh/pipe/util/Vec3");
            mv.visitInsn(DUP);
            mv.visitInsn(DCONST_0);
            mv.visitInsn(DCONST_0);
            mv.visitInsn(DCONST_0);
            mv.visitMethodInsn(INVOKESPECIAL, "me/curlpipesh/pipe/util/Vec3", "<init>", "(DDD)V", false);
            mv.visitFieldInsn(PUTSTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "entityPrevVec", "Lme/curlpipesh/pipe/util/Vec3;");
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitTypeInsn(NEW, "me/curlpipesh/pipe/util/Vec3");
            mv.visitInsn(DUP);
            mv.visitInsn(DCONST_0);
            mv.visitInsn(DCONST_0);
            mv.visitInsn(DCONST_0);
            mv.visitMethodInsn(INVOKESPECIAL, "me/curlpipesh/pipe/util/Vec3", "<init>", "(DDD)V", false);
            mv.visitFieldInsn(PUTSTATIC, "me/curlpipesh/pipe/util/helpers/Helper", "blockEntityVec", "Lme/curlpipesh/pipe/util/Vec3;");
            mv.visitInsn(RETURN);
            mv.visitMaxs(8, 0);
            mv.visitEnd();
        }

        cw.visitEnd();

        return cw.toByteArray();
    }
}
