package com.noodlegamer76.fracture.entity.ai;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;

public class PlayerLikeMoveControl extends MoveControl {
    public PlayerLikeMoveControl(Mob mob) {
        super(mob);
    }

    @Override
    public void tick() {
        if (this.operation == MoveControl.Operation.MOVE_TO) {
            this.operation = MoveControl.Operation.WAIT;

            double dx = this.wantedX - mob.getX();
            double dz = this.wantedZ - mob.getZ();
            double dy = this.wantedY - mob.getY();
            double distanceSq = dx * dx + dy * dy + dz * dz;

            if (distanceSq < 0.0001D) {
                mob.setZza(0.0F);
                return;
            }

            float targetYaw = (float) (Mth.atan2(dz, dx) * (180D / Math.PI)) - 90.0F;
            mob.setYRot(this.rotlerp(mob.getYRot(), targetYaw, 90.0F));
            mob.yBodyRot = mob.getYRot();
            mob.yHeadRot = mob.getYRot();

            float speed = (float) (this.speedModifier * mob.getAttributeValue(Attributes.MOVEMENT_SPEED));

            if (mob.isSprinting()) {
                speed *= 1.3F;
            }
            else if (mob.isCrouching() || mob.isSwimming() || mob.isVisuallyCrawling()) {
                speed *= 0.3F;
            }

            mob.setSpeed(speed);

            mob.setZza(1.0F);

            if (dy > (double) mob.getStepHeight() && dx * dx + dz * dz < (double) Math.max(1.0F, mob.getBbWidth())) {
                mob.getJumpControl().jump();
            }
        } else {
            mob.setZza(0.0F);
            mob.setXxa(0.0F);
            mob.setSpeed(0.0F);
        }
    }
}