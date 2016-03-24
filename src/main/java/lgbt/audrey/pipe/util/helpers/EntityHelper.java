package lgbt.audrey.pipe.util.helpers;

import lgbt.audrey.pipe.util.Vec2;
import lgbt.audrey.pipe.util.Vec3;

/**
 * @author audrey
 * @since 3/24/16.
 */
public final class EntityHelper {
    private EntityHelper() {
    }

    @SuppressWarnings("ConstantConditions")
    public static float[] getRotationsNeeded(final Object entity) {
        if(entity == null) {
            return null;
        }
        final Vec3 entityPos = Helper.getEntityVec(entity).clone();
        final Vec3 playerPos = Helper.getEntityVec(Helper.getPlayer()).clone();
        final Vec2 rot = Helper.getEntityRotation(Helper.getPlayer());
        final double diffX = entityPos.x() - playerPos.x();
        final double diffZ = entityPos.z() - playerPos.z();
        final double diffY;
        if(Helper.isEntityLiving(entity)) {
            //final EntityLivingBase elb = (EntityLivingBase) entity;
            //diffY = (elb.posY + elb.getEyeHeight()) - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
            diffY = entityPos.y() - (playerPos.y() + (Helper.isEntitySneaking(Helper.getPlayer()) ? 1.54D : 1.62D));
            //double dist = MathHelper.sqrt_double((diffX - diffX) + (diffZ - diffZ));
            final double dist = Math.sqrt(diffX * diffX + diffZ * diffZ);
            final float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D) - 90.0F;
            final float pitch = (float) -(Math.atan2(diffY, dist) * 180.0D / 3.141592653589793D);
            return new float[] {(float) (rot.x() + wrapAngleTo180_float((float) (yaw - rot.x()))), (float) (rot.y()
                    + wrapAngleTo180_float((float) (pitch - rot.y())))};
        } else {
            return null;
        }


        /* else {
            diffY = ((entity.getBoundingBox().minY + entity.getBoundingBox().maxY) / 2.0D) - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        }*/
    }

    @SuppressWarnings("ConstantConditions")
    public static float getDistanceFromMouse(final Object entity) {
        final float[] neededRotations = getRotationsNeeded(entity);
        if (neededRotations != null) {
            final Vec2 rot = Helper.getEntityRotation(Helper.getPlayer());
            final float neededYaw = (float) (rot.x() - neededRotations[0]);
            final float neededPitch = (float) (rot.y() - neededRotations[1]);
            return (float) Math.sqrt(neededYaw * neededYaw + neededPitch * neededPitch);
        }
        return -1.0F;
    }

    /**
     * Mojang, man ._.
     *
     * @param angle Angle to wrap
     *
     * @return Wrapped angle
     */
    public static float wrapAngleTo180_float(float angle) {
        angle %= 360.0F;
        if(angle >= 180.0F) {
            angle -= 360.0F;
        }
        if(angle < -180.0F) {
            angle += 360.0F;
        }
        return angle;
    }
}
