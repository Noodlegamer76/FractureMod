package com.noodlegamer76.fracture.entity.ai;

import com.noodlegamer76.fracture.entity.monster.PlayerMimic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PlayerLikeMoveControl extends MoveControl {
    public PlayerLikeMoveControl(Mob mob) {
        super(mob);
    }

    @Override
    public void tick() {
        if (this.operation != MoveControl.Operation.MOVE_TO) {
            mob.setZza(0.0F);
            mob.setXxa(0.0F);
            mob.setSpeed(0.0F);
            return;
        }

        Path path = mob.getNavigation().getPath();
        boolean isParkourJump = false;
        Node targetNode = null;

        if (path != null && !path.isDone()) {
            int currentIndex = path.getNextNodeIndex();
            Node nextNode = path.getNextNode();
            Node peekNode = (currentIndex + 1 < path.getNodeCount()) ? path.getNode(currentIndex + 1) : null;

            boolean atJumpEdge = nextNode.type == ModBlockPathTypes.JUMP_OVER_IT;
            boolean jumpIsComing = peekNode != null && peekNode.type == ModBlockPathTypes.JUMP_OVER_IT;
            isParkourJump = atJumpEdge || jumpIsComing;

            targetNode = atJumpEdge ? nextNode : (jumpIsComing ? peekNode : nextNode);

            if (mob instanceof PlayerMimic playerMimic) {
                playerMimic.setShouldJumpOverGap(isParkourJump);
            }
        }

        double dx = this.wantedX - mob.getX();
        double dz = this.wantedZ - mob.getZ();
        double dy = this.wantedY - mob.getY();
        double horizontalDistSq = dx * dx + dz * dz;

        if (horizontalDistSq + dy * dy < 0.0025D) {
            this.operation = MoveControl.Operation.WAIT;
            mob.setZza(0.0F);
            mob.setXxa(0.0F);
            return;
        }

        if (horizontalDistSq > 0.0001D) {
            float targetYaw = (float) (Mth.atan2(dz, dx) * (180D / Math.PI)) - 90.0F;
            float rotationSpeed = isParkourJump ? 180.0F : 90.0F;
            mob.setYRot(this.rotlerp(mob.getYRot(), targetYaw, rotationSpeed));
            mob.yBodyRot = mob.getYRot();
            mob.yHeadRot = mob.getYRot();
        }

        if (!mob.onGround()) {
            if (targetNode != null && isParkourJump) {
                Vec3 targetVec = new Vec3(targetNode.x + 0.5D, targetNode.y, targetNode.z + 0.5D);
                Vec3 motion = mob.getDeltaMovement();

                double tdx = targetVec.x - mob.getX();
                double tdy = targetVec.y - mob.getY();
                double tdz = targetVec.z - mob.getZ();
                double dist = Math.sqrt(tdx * tdx + tdz * tdz);

                if (dist > 0.001D) {
                    double dirX = tdx / dist;
                    double dirZ = tdz / dist;

                    double heightFactor = Mth.clamp(tdy / 3.0D, -0.5D, 0.5D);
                    double airSpeed = 0.02D + Math.abs(heightFactor) * 0.01D;

                    Vec3 desiredMotion = new Vec3(dirX * airSpeed, motion.y, dirZ * airSpeed);
                    Vec3 adjustment = desiredMotion.subtract(motion.x, 0, motion.z).scale(0.1D);

                    mob.setDeltaMovement(motion.add(adjustment.x, 0, adjustment.z));

                    double moveDot = dirX * motion.x + dirZ * motion.z;
                    mob.setZza(Mth.clamp((float) moveDot * 50.0F, -1.0F, 1.0F));

                    float radYaw = mob.getYRot() * (float) (Math.PI / 180.0D);
                    double sideX = Math.cos(radYaw);
                    double sideZ = Math.sin(radYaw);
                    double strafeDot = sideX * dirX + sideZ * dirZ;
                    mob.setXxa((float) strafeDot);
                } else {
                    mob.setZza(0.0F);
                    mob.setXxa(0.0F);
                }
            }
        }
        else {
            float baseSpeed = (float) mob.getAttributeValue(Attributes.MOVEMENT_SPEED);
            float multiplier = 1.0F;
            if (mob.isSprinting()) multiplier *= 1.3F;
            if (mob.isVisuallyCrawling() || mob.isCrouching()) multiplier *= 0.2F;

            mob.setSpeed(baseSpeed * multiplier);
            mob.setZza(1.0F);
            mob.setXxa(0.0F);

            BlockPos currentPos = mob.blockPosition();
            BlockPos targetPos = BlockPos.containing(this.wantedX, this.wantedY, this.wantedZ);

            boolean needsJump = false;

            Vec3 lookAhead = new Vec3(this.wantedX - mob.getX(), 0, this.wantedZ - mob.getZ()).normalize();
            BlockPos checkPos = currentPos.offset((int)Math.round(lookAhead.x), 0, (int)Math.round(lookAhead.z));

            BlockState checkState = mob.level().getBlockState(checkPos);
            if (!checkState.isAir() && checkState.isSolid()) {
                needsJump = true;
            }

            BlockState checkStateAbove = mob.level().getBlockState(currentPos.above());
            if (!checkStateAbove.isAir() && checkStateAbove.isSolid()) {
                needsJump = true;
            }

            if (needsJump || dy > (double) mob.getStepHeight()) {
                mob.getJumpControl().jump();
            }
        }
    }
}