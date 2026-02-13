package com.noodlegamer76.fracture.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;

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

        if (path != null && !path.isDone()) {
            int currentIndex = path.getNextNodeIndex();
            Node nextNode = path.getNextNode();
            Node peekNode = (currentIndex + 1 < path.getNodeCount()) ? path.getNode(currentIndex + 1) : null;

            boolean atJumpEdge = nextNode.type == ModBlockPathTypes.JUMP_OVER_IT;
            boolean jumpIsComing = peekNode != null && peekNode.type == ModBlockPathTypes.JUMP_OVER_IT;
            isParkourJump = atJumpEdge || jumpIsComing;

            if (isParkourJump) {
                mob.setSprinting(true);

                if (peekNode != null) {
                    this.wantedX = peekNode.x + 0.5D;
                    this.wantedY = peekNode.y;
                    this.wantedZ = peekNode.z + 0.5D;
                }

                if (mob.onGround()) {
                    double vX = mob.getDeltaMovement().x;
                    double vZ = mob.getDeltaMovement().z;

                    BlockPos immediateEdge = BlockPos.containing(mob.getX() + vX * 0.8D, mob.getY() - 0.5D, mob.getZ() + vZ * 0.8D);
                    boolean offTheEdge = mob.level().getBlockState(immediateEdge).isAir();

                    double dxNode = (nextNode.x + 0.5D) - mob.getX();
                    double dzNode = (nextNode.z + 0.5D) - mob.getZ();
                    double distToEdgeSq = dxNode * dxNode + dzNode * dzNode;

                    if (atJumpEdge && (offTheEdge || distToEdgeSq < 0.25D)) {
                        mob.getJumpControl().jump();
                        path.advance();
                    }
                }
            }
        }

        double dx = this.wantedX - mob.getX();
        double dz = this.wantedZ - mob.getZ();
        double dy = this.wantedY - mob.getY();
        double horizontalDistSq = dx * dx + dz * dz;

        if (horizontalDistSq + dy * dy < 0.0001D) {
            this.operation = MoveControl.Operation.WAIT;
            mob.setZza(0.0F);
            return;
        }

        float targetYaw = (float) (Mth.atan2(dz, dx) * (180D / Math.PI)) - 90.0F;
        float rotationSpeed = isParkourJump ? 180.0F : 90.0F;
        mob.setYRot(this.rotlerp(mob.getYRot(), targetYaw, rotationSpeed));
        mob.yBodyRot = mob.getYRot();
        mob.yHeadRot = mob.getYRot();

        float baseSpeed = (float) mob.getAttributeValue(Attributes.MOVEMENT_SPEED);
        float multiplier = 1;
        if (mob.isSprinting()) multiplier *= 1.3F;
        if (mob.isVisuallyCrawling() || mob.isCrouching()) multiplier *= 0.2F;

        mob.setSpeed(baseSpeed * multiplier);
        mob.setZza(1.0F);

        if (!mob.onGround() && isParkourJump) {
            return;
        }

        if (dy > (double) mob.getStepHeight() && horizontalDistSq < (double) Math.max(1.0F, mob.getBbWidth())) {
            mob.getJumpControl().jump();
        }
    }
}