package lgbt.audrey.pipe.bytecode.v1_10_X.generators;

import lgbt.audrey.pipe.bytecode.Generator;
import lgbt.audrey.pipe.bytecode.map.MappedClass;
import lgbt.audrey.pipe.bytecode.map.MappedClass.MethodDef;
import lgbt.audrey.pipe.util.helpers.Helper;
import org.objectweb.asm.*;
import org.objectweb.asm.util.CheckClassAdapter;

import java.io.PrintWriter;

import static lgbt.audrey.pipe.bytecode.map.ClassMap.getClassByName;
import static org.objectweb.asm.Opcodes.*;

/**
 * Generates bytecode to fill in the methods in
 * {@link Helper}.
 *
 * @author c
 * @since 5/20/15
 */
@SuppressWarnings({"unused", "Duplicates"})
public class HelperGenerator implements Generator {
    public byte[] generate() {
        final ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        @SuppressWarnings("unused")
        FieldVisitor fv;
        MethodVisitor mv;
        @SuppressWarnings("unused")
        AnnotationVisitor av0;

        /////////////////////////////////////////////////////
        // Because I was tired of putting these everywhere //
        /////////////////////////////////////////////////////

        final MappedClass minecraft = getClassByName("Minecraft");
        final MappedClass fontRenderer = getClassByName("FontRenderer");
        final MappedClass world = getClassByName("World");
        final MappedClass entityClientPlayer = getClassByName("EntityClientPlayer");
        final MappedClass gameSettings = getClassByName("GameSettings");
        final MappedClass abstractWorld = getClassByName("AbstractWorld");
        final MappedClass entity = getClassByName("Entity");
        final MappedClass worldProvider = getClassByName("WorldProvider");
        final MappedClass entityLiving = getClassByName("EntityLiving");
        final MappedClass entityAnimal = getClassByName("EntityAnimal");
        final MappedClass entityMonster = getClassByName("EntityMonster");
        final MappedClass entityPlayer = getClassByName("EntityPlayer");
        final MappedClass blockEntity = getClassByName("BlockEntity");
        final MappedClass blockPos = getClassByName("BlockPos");
        final MappedClass vec3i = getClassByName("Vec3i");
        final MappedClass blockEntityChest = getClassByName("BlockEntityChest");
        final MappedClass blockEntityEnderChest = getClassByName("BlockEntityEnderChest");
        final MappedClass entityRenderer = getClassByName("EntityRenderer");
        final MappedClass chatComponentText = getClassByName("ChatComponentText");
        final MappedClass ichatComponent = getClassByName("IChatComponent");
        final MappedClass packet = getClassByName("Packet");
        final MappedClass netClientPlayHandler = getClassByName("NetClientPlayHandler");
        final MappedClass guiScreen = getClassByName("GuiScreen");
        final MappedClass scaledResolution = getClassByName("ScaledResolution");
        final MappedClass packetClientChatMessage = getClassByName("PacketClientChatMessage");
        final MappedClass packetClientTabComplete = getClassByName("PacketClientTabComplete");
        final MappedClass container = getClassByName("Container");
        final MappedClass itemStack = getClassByName("ItemStack");
        final MappedClass inventoryPlayer = getClassByName("InventoryPlayer");

        final MethodDef getMinecraft = minecraft.getMethod("getMinecraft").get();
        
        ////////////////////////////////
        // Actual bytecode generation //
        ////////////////////////////////
        
        cw.visit(52, ACC_PUBLIC + ACC_SUPER, "lgbt/audrey/pipe/util/helpers/Helper", null, "java/lang/Object", null);

        cw.visitSource("Helper.java", null);

        {
            fv = cw.visitField(ACC_PRIVATE + ACC_FINAL + ACC_STATIC, "entityVec", "Llgbt/audrey/pipe/util/Vec3;", null, null);
            fv.visitEnd();
        }
        {
            fv = cw.visitField(ACC_PRIVATE + ACC_FINAL + ACC_STATIC, "entityPrevVec", "Llgbt/audrey/pipe/util/Vec3;", null, null);
            fv.visitEnd();
        }
        {
            fv = cw.visitField(ACC_PRIVATE + ACC_FINAL + ACC_STATIC, "blockEntityVec", "Llgbt/audrey/pipe/util/Vec3;", null, null);
            fv.visitEnd();
        }
        {
            fv = cw.visitField(ACC_PRIVATE + ACC_FINAL + ACC_STATIC, "rotVec", "Llgbt/audrey/pipe/util/Vec2;", null, null);
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
            mv.visitMethodInsn(INVOKESTATIC, "lgbt/audrey/pipe/util/helpers/Helper", "getWorld", "()Ljava/lang/Object;", false);
            final Label l1 = new Label();
            mv.visitJumpInsn(IFNONNULL, l1);
            mv.visitInsn(ICONST_1);
            final Label l2 = new Label();
            mv.visitJumpInsn(GOTO, l2);
            mv.visitLabel(l1);
            mv.visitFrame(F_SAME, 0, null, 0, null);
            mv.visitInsn(ICONST_0);
            mv.visitLabel(l2);
            mv.visitFrame(F_SAME1, 0, null, 1, new Object[] {INTEGER});
            mv.visitInsn(IRETURN);
            mv.visitMaxs(1, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getMinecraft", "()Ljava/lang/Object;", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, minecraft.getObfuscatedName(), getMinecraft.getName(), getMinecraft.getDesc(), false);
            mv.visitInsn(ARETURN);
            mv.visitMaxs(1, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getWorld", "()Ljava/lang/Object;", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, minecraft.getObfuscatedName(), getMinecraft.getName(), getMinecraft.getDesc(), false);
            mv.visitFieldInsn(GETFIELD, minecraft.getObfuscatedName(), minecraft.getFields().get("theWorld"), world.getDescription());
            mv.visitInsn(ARETURN);
            mv.visitMaxs(1, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getPlayer", "()Ljava/lang/Object;", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, minecraft.getObfuscatedName(), getMinecraft.getName(), getMinecraft.getDesc(), false);
            mv.visitFieldInsn(GETFIELD, minecraft.getObfuscatedName(), minecraft.getFields().get("thePlayer"), entityClientPlayer.getDescription());
            mv.visitInsn(ARETURN);
            mv.visitMaxs(1, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getGameSettings", "()Ljava/lang/Object;", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, minecraft.getObfuscatedName(), getMinecraft.getName(), getMinecraft.getDesc(), false);
            mv.visitFieldInsn(GETFIELD, minecraft.getObfuscatedName(), minecraft.getFields().get("gameSettings"), gameSettings.getDescription());
            mv.visitInsn(ARETURN);
            mv.visitMaxs(1, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "isIngameGuiInDebugMode", "()Z", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, minecraft.getObfuscatedName(), getMinecraft.getName(), getMinecraft.getDesc(), false);
            mv.visitFieldInsn(GETFIELD, minecraft.getObfuscatedName(), minecraft.getFields().get("gameSettings"), gameSettings.getDescription());
            mv.visitFieldInsn(GETFIELD, gameSettings.getObfuscatedName(), gameSettings.getFields().get("isIngameGuiInDebugMode"), "Z");
            mv.visitInsn(IRETURN);
            mv.visitMaxs(3, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getFontRenderer", "()Ljava/lang/Object;", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, minecraft.getObfuscatedName(), getMinecraft.getName(), getMinecraft.getDesc(), false);
            mv.visitFieldInsn(GETFIELD, minecraft.getObfuscatedName(), minecraft.getFields().get("fontRenderer"), fontRenderer.getDescription());
            mv.visitInsn(ARETURN);
            mv.visitMaxs(1, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getFontHeight", "()I", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "lgbt/audrey/pipe/util/helpers/Helper", "getFontRenderer", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, fontRenderer.getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, fontRenderer.getObfuscatedName(), fontRenderer.getFields().get("fontHeight"), "I");
            mv.visitInsn(IRETURN);
            mv.visitMaxs(1, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getStringWidth", "(Ljava/lang/String;)I", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "lgbt/audrey/pipe/util/helpers/Helper", "getFontRenderer", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, fontRenderer.getObfuscatedName());
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKEVIRTUAL, fontRenderer.getObfuscatedName(), fontRenderer.getMethod("getStringWidth").get().getName(), fontRenderer.getMethod("getStringWidth").get().getDesc(), false);
            mv.visitInsn(IRETURN);
            mv.visitMaxs(4, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "drawString", "(Ljava/lang/String;FFIZ)V", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "lgbt/audrey/pipe/util/helpers/Helper", "getFontRenderer", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, fontRenderer.getObfuscatedName());
            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(FLOAD, 1);
            mv.visitVarInsn(FLOAD, 2);
            mv.visitVarInsn(ILOAD, 3);
            mv.visitVarInsn(ILOAD, 4);
            mv.visitMethodInsn(INVOKEVIRTUAL, fontRenderer.getObfuscatedName(), fontRenderer.getMethod("drawString").get().getName(), fontRenderer.getMethod("drawString").get().getDesc(), false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(6, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getLoadedEntities", "()Ljava/util/List;", "()Ljava/util/List<*>;", null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "lgbt/audrey/pipe/util/helpers/Helper", "getWorld", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, abstractWorld.getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, abstractWorld.getObfuscatedName(), abstractWorld.getFields().get("loadedEntities"), "Ljava/util/List;");
            mv.visitInsn(ARETURN);
            mv.visitMaxs(1, 0);
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getEntityVec", "(Ljava/lang/Object;)Llgbt/audrey/pipe/util/Vec3;", null, null);
            mv.visitCode();
            mv.visitFieldInsn(GETSTATIC, "lgbt/audrey/pipe/util/helpers/Helper", "entityVec", "Llgbt/audrey/pipe/util/Vec3;");
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, entity.getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, entity.getObfuscatedName(), entity.getFields().get("curX"), "D");
            mv.visitMethodInsn(INVOKEVIRTUAL, "lgbt/audrey/pipe/util/Vec3", "x", "(D)V", false);
            mv.visitFieldInsn(GETSTATIC, "lgbt/audrey/pipe/util/helpers/Helper", "entityVec", "Llgbt/audrey/pipe/util/Vec3;");
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, entity.getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, entity.getObfuscatedName(), entity.getFields().get("curY"), "D");
            mv.visitMethodInsn(INVOKEVIRTUAL, "lgbt/audrey/pipe/util/Vec3", "y", "(D)V", false);
            mv.visitFieldInsn(GETSTATIC, "lgbt/audrey/pipe/util/helpers/Helper", "entityVec", "Llgbt/audrey/pipe/util/Vec3;");
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, entity.getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, entity.getObfuscatedName(), entity.getFields().get("curZ"), "D");
            mv.visitMethodInsn(INVOKEVIRTUAL, "lgbt/audrey/pipe/util/Vec3", "z", "(D)V", false);
            mv.visitFieldInsn(GETSTATIC, "lgbt/audrey/pipe/util/helpers/Helper", "entityVec", "Llgbt/audrey/pipe/util/Vec3;");
            mv.visitInsn(ARETURN);
            mv.visitMaxs(3, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getEntityPrevVec", "(Ljava/lang/Object;)Llgbt/audrey/pipe/util/Vec3;", null, null);
            mv.visitCode();
            mv.visitFieldInsn(GETSTATIC, "lgbt/audrey/pipe/util/helpers/Helper", "entityPrevVec", "Llgbt/audrey/pipe/util/Vec3;");
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, entity.getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, entity.getObfuscatedName(), entity.getFields().get("prevX"), "D");
            mv.visitMethodInsn(INVOKEVIRTUAL, "lgbt/audrey/pipe/util/Vec3", "x", "(D)V", false);
            mv.visitFieldInsn(GETSTATIC, "lgbt/audrey/pipe/util/helpers/Helper", "entityPrevVec", "Llgbt/audrey/pipe/util/Vec3;");
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, entity.getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, entity.getObfuscatedName(), entity.getFields().get("prevY"), "D");
            mv.visitMethodInsn(INVOKEVIRTUAL, "lgbt/audrey/pipe/util/Vec3", "y", "(D)V", false);
            mv.visitFieldInsn(GETSTATIC, "lgbt/audrey/pipe/util/helpers/Helper", "entityPrevVec", "Llgbt/audrey/pipe/util/Vec3;");
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, entity.getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, entity.getObfuscatedName(), entity.getFields().get("prevZ"), "D");
            mv.visitMethodInsn(INVOKEVIRTUAL, "lgbt/audrey/pipe/util/Vec3", "z", "(D)V", false);
            mv.visitFieldInsn(GETSTATIC, "lgbt/audrey/pipe/util/helpers/Helper", "entityPrevVec", "Llgbt/audrey/pipe/util/Vec3;");
            mv.visitInsn(ARETURN);
            mv.visitMaxs(3, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getLightBrightnessTable", "()[F", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "lgbt/audrey/pipe/util/helpers/Helper", "getWorld", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, abstractWorld.getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, abstractWorld.getObfuscatedName(), abstractWorld.getFields().get("worldProvider"), worldProvider.getDescription());
            mv.visitFieldInsn(GETFIELD, worldProvider.getObfuscatedName(), worldProvider.getFields().get("lightBrightnessTable"), "[F");
            mv.visitInsn(ARETURN);
            mv.visitMaxs(3, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "isEntityLiving", "(Ljava/lang/Object;)Z", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(INSTANCEOF, entityLiving.getObfuscatedName());
            mv.visitInsn(IRETURN);
            mv.visitMaxs(1, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "isEntityAnimal", "(Ljava/lang/Object;)Z", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(INSTANCEOF, entityAnimal.getObfuscatedName());
            mv.visitInsn(IRETURN);
            mv.visitMaxs(1, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "isEntityMonster", "(Ljava/lang/Object;)Z", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(INSTANCEOF, entityMonster.getObfuscatedName());
            mv.visitInsn(IRETURN);
            mv.visitMaxs(1, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "isEntityPlayer", "(Ljava/lang/Object;)Z", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(INSTANCEOF, entityPlayer.getObfuscatedName());
            mv.visitInsn(IRETURN);
            mv.visitMaxs(1, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getLoadedBlockEntities", "()Ljava/util/List;", "()Ljava/util/List<*>;", null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "lgbt/audrey/pipe/util/helpers/Helper", "getWorld", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, abstractWorld.getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, abstractWorld.getObfuscatedName(), abstractWorld.getFields().get("loadedBlockEntities"), "Ljava/util/List;");
            mv.visitInsn(ARETURN);
            mv.visitMaxs(1, 0);
        }

        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getBlockEntityVec", "(Ljava/lang/Object;)Llgbt/audrey/pipe/util/Vec3;", null, null);
            mv.visitCode();
            mv.visitFieldInsn(GETSTATIC, "lgbt/audrey/pipe/util/helpers/Helper", "blockEntityVec", "Llgbt/audrey/pipe/util/Vec3;");
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, blockEntity.getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, blockEntity.getObfuscatedName(), blockEntity.getFields().get("blockPos"), blockPos.getDescription());
            mv.visitTypeInsn(CHECKCAST, vec3i.getObfuscatedName());
            mv.visitMethodInsn(INVOKEVIRTUAL, vec3i.getObfuscatedName(), vec3i.getMethod("getX").get().getName(), vec3i.getMethod("getX").get().getDesc(), false);
            mv.visitInsn(I2D);
            mv.visitMethodInsn(INVOKEVIRTUAL, "lgbt/audrey/pipe/util/Vec3", "x", "(D)V", false);
            mv.visitFieldInsn(GETSTATIC, "lgbt/audrey/pipe/util/helpers/Helper", "blockEntityVec", "Llgbt/audrey/pipe/util/Vec3;");
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, blockEntity.getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, blockEntity.getObfuscatedName(), blockEntity.getFields().get("blockPos"), blockPos.getDescription());
            mv.visitTypeInsn(CHECKCAST, vec3i.getObfuscatedName());
            mv.visitMethodInsn(INVOKEVIRTUAL, vec3i.getObfuscatedName(), vec3i.getMethod("getY").get().getName(), vec3i.getMethod("getY").get().getDesc(), false);
            mv.visitInsn(I2D);
            mv.visitMethodInsn(INVOKEVIRTUAL, "lgbt/audrey/pipe/util/Vec3", "y", "(D)V", false);
            mv.visitFieldInsn(GETSTATIC, "lgbt/audrey/pipe/util/helpers/Helper", "blockEntityVec", "Llgbt/audrey/pipe/util/Vec3;");
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, blockEntity.getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, blockEntity.getObfuscatedName(), blockEntity.getFields().get("blockPos"), blockPos.getDescription());
            mv.visitTypeInsn(CHECKCAST, vec3i.getObfuscatedName());
            mv.visitMethodInsn(INVOKEVIRTUAL, vec3i.getObfuscatedName(), vec3i.getMethod("getZ").get().getName(), vec3i.getMethod("getZ").get().getDesc(), false);
            mv.visitInsn(I2D);
            mv.visitMethodInsn(INVOKEVIRTUAL, "lgbt/audrey/pipe/util/Vec3", "z", "(D)V", false);
            mv.visitFieldInsn(GETSTATIC, "lgbt/audrey/pipe/util/helpers/Helper", "blockEntityVec", "Llgbt/audrey/pipe/util/Vec3;");
            mv.visitInsn(ARETURN);
            mv.visitMaxs(3, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "isBlockEntityChest", "(Ljava/lang/Object;)Z", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(INSTANCEOF, blockEntityChest.getObfuscatedName());
            final Label l1 = new Label();
            mv.visitJumpInsn(IFNE, l1);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(INSTANCEOF, blockEntityEnderChest.getObfuscatedName());
            final Label l2 = new Label();
            mv.visitJumpInsn(IFEQ, l2);
            mv.visitLabel(l1);
            mv.visitFrame(F_SAME, 0, null, 0, null);
            mv.visitInsn(ICONST_1);
            final Label l3 = new Label();
            mv.visitJumpInsn(GOTO, l3);
            mv.visitLabel(l2);
            mv.visitFrame(F_SAME, 0, null, 0, null);
            mv.visitInsn(ICONST_0);
            mv.visitLabel(l3);
            mv.visitFrame(F_SAME1, 0, null, 1, new Object[] {INTEGER});
            mv.visitInsn(IRETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "enableLightmap", "()V", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, minecraft.getObfuscatedName(), getMinecraft.getName(), getMinecraft.getDesc(), false);
            mv.visitFieldInsn(GETFIELD, minecraft.getObfuscatedName(), minecraft.getFields().get("entityRenderer"), entityRenderer.getDescription());
            mv.visitMethodInsn(INVOKEVIRTUAL, entityRenderer.getObfuscatedName(), entityRenderer.getMethod("enableLightmap").get().getName(), entityRenderer.getMethod("enableLightmap").get().getDesc(), false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(2, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "disableLightmap", "()V", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, minecraft.getObfuscatedName(), getMinecraft.getName(), getMinecraft.getDesc(), false);
            mv.visitFieldInsn(GETFIELD, minecraft.getObfuscatedName(), minecraft.getFields().get("entityRenderer"), entityRenderer.getDescription());
            mv.visitMethodInsn(INVOKEVIRTUAL, entityRenderer.getObfuscatedName(), entityRenderer.getMethod("disableLightmap").get().getName(), entityRenderer.getMethod("disableLightmap").get().getDesc(), false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(2, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "addChatMessage", "(Ljava/lang/String;)V", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "lgbt/audrey/pipe/util/helpers/Helper", "getPlayer", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, entityClientPlayer.getObfuscatedName());
            mv.visitTypeInsn(NEW, chatComponentText.getObfuscatedName());
            mv.visitInsn(DUP);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, chatComponentText.getObfuscatedName(), "<init>", "(Ljava/lang/String;)V", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, entityClientPlayer.getObfuscatedName(), entityClientPlayer.getMethod("addChatMessage").get().getName(), entityClientPlayer.getMethod("addChatMessage").get().getDesc(), false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(3, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "sendChatMessage", "(Ljava/lang/String;)V", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "lgbt/audrey/pipe/util/helpers/Helper", "getPlayer", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, entityClientPlayer.getObfuscatedName());
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKEVIRTUAL, entityClientPlayer.getObfuscatedName(), entityClientPlayer.getMethod("sendChatMessage").get().getName(), entityClientPlayer.getMethod("sendChatMessage").get().getDesc(), false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(2, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "sendPacket", "(Ljava/lang/Object;)V", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "lgbt/audrey/pipe/util/helpers/Helper", "getPlayer", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, entityClientPlayer.getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, entityClientPlayer.getObfuscatedName(), entityClientPlayer.getFields().get("netClientPlayHandler"), netClientPlayHandler.getDescription());
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, packet.getObfuscatedName());
            mv.visitMethodInsn(INVOKEVIRTUAL, netClientPlayHandler.getObfuscatedName(), netClientPlayHandler.getMethod("sendPacket").get().getName(), netClientPlayHandler.getMethod("sendPacket").get().getDesc(), false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(5, 5);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getWidth", "()I", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "lgbt/audrey/pipe/util/helpers/Helper", "getMinecraft", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, minecraft.getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, minecraft.getObfuscatedName(), minecraft.getFields().get("width"), "I");
            mv.visitInsn(IRETURN);
            mv.visitMaxs(2, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getHeight", "()I", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "lgbt/audrey/pipe/util/helpers/Helper", "getMinecraft", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, minecraft.getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, minecraft.getObfuscatedName(), minecraft.getFields().get("height"), "I");
            mv.visitInsn(IRETURN);
            mv.visitMaxs(2, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "displayGuiScreen", "(Llgbt/audrey/pipe/gui/GuiScreen;)V", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, minecraft.getObfuscatedName(), getMinecraft.getName(), getMinecraft.getDesc(), false);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, guiScreen.getObfuscatedName());
            mv.visitMethodInsn(INVOKEVIRTUAL, minecraft.getObfuscatedName(), "a", '(' + guiScreen.getDescription() + ")V", false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(2, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "mc_displayGuiScreen", "(Ljava/lang/Object;)V", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, minecraft.getObfuscatedName(), getMinecraft.getName(), getMinecraft.getDesc(), false);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, guiScreen.getObfuscatedName());
            mv.visitMethodInsn(INVOKEVIRTUAL, minecraft.getObfuscatedName(), "a", '(' + guiScreen.getDescription() + ")V", false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(2, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "displayGuiModule", "(Llgbt/audrey/pipe/gui/GuiModule;)V", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "lgbt/audrey/pipe/gui/GuiScreen", "getInstance", "()Llgbt/audrey/pipe/gui/GuiScreen;", false);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKEVIRTUAL, "lgbt/audrey/pipe/gui/GuiScreen", "setCurrentModule", "(Llgbt/audrey/pipe/gui/GuiModule;)V", false);
            mv.visitMethodInsn(INVOKESTATIC, "lgbt/audrey/pipe/gui/GuiScreen", "getInstance", "()Llgbt/audrey/pipe/gui/GuiScreen;", false);
            mv.visitMethodInsn(INVOKESTATIC, "lgbt/audrey/pipe/util/helpers/Helper", "displayGuiScreen", "(Llgbt/audrey/pipe/gui/GuiScreen;)V", false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(2, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getScale", "()I", null, null);
            mv.visitCode();
            mv.visitTypeInsn(NEW, scaledResolution.getObfuscatedName());
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESTATIC, minecraft.getObfuscatedName(), getMinecraft.getName(), getMinecraft.getDesc(), false);
            mv.visitMethodInsn(INVOKESPECIAL, scaledResolution.getObfuscatedName(), "<init>", '(' + minecraft.getDescription() + ")V", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, scaledResolution.getObfuscatedName(), scaledResolution.getMethod("getScale").get().getName(), scaledResolution.getMethod("getScale").get().getDesc(), false);
            mv.visitInsn(IRETURN);
            mv.visitMaxs(4, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "isViewBobbingEnabled", "()Z", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "lgbt/audrey/pipe/util/helpers/Helper", "getGameSettings", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, gameSettings.getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, gameSettings.getObfuscatedName(), gameSettings.getFields().get("isViewBobbingEnabled"), "Z");
            mv.visitInsn(IRETURN);
            mv.visitMaxs(4, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "setViewBobbing", "(Z)V", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "lgbt/audrey/pipe/util/helpers/Helper", "getGameSettings", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, gameSettings.getObfuscatedName());
            mv.visitVarInsn(ILOAD, 0);
            mv.visitFieldInsn(PUTFIELD, gameSettings.getObfuscatedName(), gameSettings.getFields().get("isViewBobbingEnabled"), "Z");
            mv.visitInsn(RETURN);
            mv.visitMaxs(2, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getCurrentScreen", "()Ljava/lang/Object;", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "lgbt/audrey/pipe/util/helpers/Helper", "getMinecraft", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, minecraft.getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, minecraft.getObfuscatedName(), minecraft.getFields().get("currentScreen"), guiScreen.getDescription());
            mv.visitInsn(ARETURN);
            mv.visitMaxs(4, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "isEntitySneaking", "(Ljava/lang/Object;)Z", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, entity.getObfuscatedName());
            mv.visitMethodInsn(INVOKEVIRTUAL, entity.getObfuscatedName(), entity.getMethod("isSneaking").get().getName(), entity.getMethod("isSneaking").get().getDesc(), false);
            mv.visitInsn(IRETURN);
            mv.visitMaxs(4, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_STATIC, "_sendChatMessage", "(Ljava/lang/String;)V", null, null);
            mv.visitCode();
            mv.visitTypeInsn(NEW, packetClientChatMessage.getObfuscatedName());
            mv.visitInsn(DUP);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, packetClientChatMessage.getObfuscatedName(), "<init>", "(Ljava/lang/String;)V", false);
            mv.visitMethodInsn(INVOKESTATIC, "lgbt/audrey/pipe/util/helpers/Helper", "sendPacket", "(Ljava/lang/Object;)V", false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(5, 5);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getInventoryContainer", "()Ljava/lang/Object;", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "lgbt/audrey/pipe/util/helpers/Helper", "getPlayer", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, entityPlayer.getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, entityPlayer.getObfuscatedName(), entityPlayer.getFields().get("inventoryContainer"), container.getDescription());
            mv.visitInsn(ARETURN);
            mv.visitMaxs(2, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getStackInSlot", "(Ljava/lang/Object;I)Ljava/lang/Object;", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "lgbt/audrey/pipe/util/helpers/Helper", "getInventoryContainer", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, container.getObfuscatedName());
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, entityPlayer.getObfuscatedName());
            mv.visitVarInsn(ILOAD, 1);
            mv.visitMethodInsn(INVOKEVIRTUAL, container.getObfuscatedName(), container.getMethod("getStackInSlot").get().getName(), container.getMethod("getStackInSlot").get().getDesc(), false);
            mv.visitInsn(ARETURN);
            mv.visitMaxs(5, 5);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getCurrentSlot", "()I", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "lgbt/audrey/pipe/util/helpers/Helper", "getPlayer", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, entityPlayer.getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, entityPlayer.getObfuscatedName(), entityPlayer.getFields().get("inventoryPlayer"), inventoryPlayer.getDescription());
            mv.visitFieldInsn(GETFIELD, inventoryPlayer.getObfuscatedName(), inventoryPlayer.getFields().get("currentSlot"), "I");
            mv.visitInsn(IRETURN);
            mv.visitMaxs(1, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getMinecraftDataDir", "()Ljava/io/File;", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "lgbt/audrey/pipe/util/helpers/Helper", "getMinecraft", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, minecraft.getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, minecraft.getObfuscatedName(), minecraft.getFields().get("mcDataDir"), "Ljava/io/File;");
            mv.visitInsn(ARETURN);
            mv.visitMaxs(1, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getMinecraftVersion", "()Ljava/lang/String;", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "lgbt/audrey/pipe/util/helpers/Helper", "getMinecraft", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, minecraft.getObfuscatedName());
            mv.visitMethodInsn(INVOKEVIRTUAL, minecraft.getObfuscatedName(), minecraft.getMethod("getVersion").get().getName(), minecraft.getMethod("getVersion").get().getDesc(), false);
            mv.visitInsn(ARETURN);
            mv.visitMaxs(1, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "sendTabComplete", "(Ljava/lang/String;)V", null, null);
            mv.visitCode();
            mv.visitTypeInsn(NEW, packetClientTabComplete.getObfuscatedName());
            mv.visitInsn(DUP);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, packetClientTabComplete.getObfuscatedName(), "<init>", "(Ljava/lang/String;)V", false);
            mv.visitMethodInsn(INVOKESTATIC, "lgbt/audrey/pipe/util/helpers/Helper", "sendPacket", "(Ljava/lang/Object;)V", false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(5, 5);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getEntityRotation", "(Ljava/lang/Object;)Llgbt/audrey/pipe/util/Vec2;", null, null);
            mv.visitCode();
            mv.visitFieldInsn(GETSTATIC, "lgbt/audrey/pipe/util/helpers/Helper", "rotVec", "Llgbt/audrey/pipe/util/Vec2;");
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, entity.getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, entity.getObfuscatedName(), entity.getFields().get("rotationYaw"), "F");
            mv.visitInsn(F2D);
            mv.visitMethodInsn(INVOKEVIRTUAL, "lgbt/audrey/pipe/util/Vec2", "x", "(D)V", false);
            mv.visitFieldInsn(GETSTATIC, "lgbt/audrey/pipe/util/helpers/Helper", "rotVec", "Llgbt/audrey/pipe/util/Vec2;");
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(CHECKCAST, entity.getObfuscatedName());
            mv.visitFieldInsn(GETFIELD, entity.getObfuscatedName(), entity.getFields().get("rotationPitch"), "F");
            mv.visitInsn(F2D);
            mv.visitMethodInsn(INVOKEVIRTUAL, "lgbt/audrey/pipe/util/Vec2", "y", "(D)V", false);
            mv.visitFieldInsn(GETSTATIC, "lgbt/audrey/pipe/util/helpers/Helper", "rotVec", "Llgbt/audrey/pipe/util/Vec2;");
            mv.visitInsn(ARETURN);
            mv.visitMaxs(3, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null);
            mv.visitCode();
            mv.visitTypeInsn(NEW, "lgbt/audrey/pipe/util/Vec3");
            mv.visitInsn(DUP);
            mv.visitInsn(DCONST_0);
            mv.visitInsn(DCONST_0);
            mv.visitInsn(DCONST_0);
            mv.visitMethodInsn(INVOKESPECIAL, "lgbt/audrey/pipe/util/Vec3", "<init>", "(DDD)V", false);
            mv.visitFieldInsn(PUTSTATIC, "lgbt/audrey/pipe/util/helpers/Helper", "entityVec", "Llgbt/audrey/pipe/util/Vec3;");
            mv.visitTypeInsn(NEW, "lgbt/audrey/pipe/util/Vec3");
            mv.visitInsn(DUP);
            mv.visitInsn(DCONST_0);
            mv.visitInsn(DCONST_0);
            mv.visitInsn(DCONST_0);
            mv.visitMethodInsn(INVOKESPECIAL, "lgbt/audrey/pipe/util/Vec3", "<init>", "(DDD)V", false);
            mv.visitFieldInsn(PUTSTATIC, "lgbt/audrey/pipe/util/helpers/Helper", "entityPrevVec", "Llgbt/audrey/pipe/util/Vec3;");
            mv.visitTypeInsn(NEW, "lgbt/audrey/pipe/util/Vec3");
            mv.visitInsn(DUP);
            mv.visitInsn(DCONST_0);
            mv.visitInsn(DCONST_0);
            mv.visitInsn(DCONST_0);
            mv.visitMethodInsn(INVOKESPECIAL, "lgbt/audrey/pipe/util/Vec3", "<init>", "(DDD)V", false);
            mv.visitFieldInsn(PUTSTATIC, "lgbt/audrey/pipe/util/helpers/Helper", "blockEntityVec", "Llgbt/audrey/pipe/util/Vec3;");
            mv.visitTypeInsn(NEW, "lgbt/audrey/pipe/util/Vec2");
            mv.visitInsn(DUP);
            mv.visitInsn(DCONST_0);
            mv.visitInsn(DCONST_0);
            mv.visitMethodInsn(INVOKESPECIAL, "lgbt/audrey/pipe/util/Vec2", "<init>", "(DD)V", false);
            mv.visitFieldInsn(PUTSTATIC, "lgbt/audrey/pipe/util/helpers/Helper", "rotVec", "Llgbt/audrey/pipe/util/Vec2;");
            mv.visitInsn(RETURN);
            mv.visitMaxs(8, 0);
            mv.visitEnd();
        }

        cw.visitEnd();

        final byte[] bytes = cw.toByteArray();
        CheckClassAdapter.verify(new ClassReader(bytes), false, new PrintWriter(System.err));

        return bytes;
    }

    @Override
    public String getClassName() {
        return null;
    }
}
