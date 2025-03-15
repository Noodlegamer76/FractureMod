package com.noodlegamer76.fracture.entity.ai.behavior.playermimic;

import com.google.common.collect.Multimap;
import com.noodlegamer76.fracture.entity.monster.PlayerMimic;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.WalkOrRunToWalkTarget;

import java.util.Map;

public class SprintJumpToTarget<T extends PlayerMimic> extends WalkOrRunToWalkTarget<T> {
    private final float sprintJumpDistance;

    public SprintJumpToTarget(float sprintJumpDistance) {
        this.sprintJumpDistance = sprintJumpDistance;
    }

    @Override
    protected void start(T entity) {
        super.start(entity);
        entity.setSprinting(true);
    }

    @Override
    protected void stop(T entity) {
        super.stop(entity);
        entity.setSprinting(false);
    }

    @Override
    protected void tick(T entity) {
        super.tick(entity);
        if (entity.isInWater()) {
            return;
        }
        if (entity.onGround()) {
            entity.getJumpControl().jump();
        }

        if (path != null) {
            Node node = path.getNextNode();
            if (entity.distanceToSqr(node.x, node.y, node.z) < sprintJumpDistance * sprintJumpDistance) {
                path.advance();
            }
        }

        if (!entity.onGround()) {
            Vec3 velocity = entity.getDeltaMovement();

            // Get the direction the entity is facing
            float yawRad = (float) Math.toRadians(entity.getYRot());
            Vec3 forward = new Vec3(-Math.sin(yawRad), 0, Math.cos(yawRad));

            double baseSprintSpeed = 0.13;
            double sprintJumpMultiplier = 2;

            double targetSpeed = baseSprintSpeed * sprintJumpMultiplier;

            Vec3 newVelocity = new Vec3(forward.x * targetSpeed, velocity.y, forward.z * targetSpeed);

            entity.setDeltaMovement(newVelocity);
        }
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, PlayerMimic entity) {
        boolean flag1 = !isEntityCloseToTarget(entity);
        boolean flag2 = super.checkExtraStartConditions(level, (T) entity);
        return flag1 && flag2 && !entity.isInWater();
    }

    @Override
    protected boolean shouldKeepRunning(T entity) {
        return !isEntityCloseToTarget(entity) && super.shouldKeepRunning(entity) && !entity.isInWater();
    }

    public boolean isEntityCloseToTarget(LivingEntity entity) {
        if (lastTargetPos == null) return false;
        return entity.position().distanceToSqr(lastTargetPos.getX(), lastTargetPos.getY(), lastTargetPos.getZ()) < (sprintJumpDistance * sprintJumpDistance);
    }

    public static int getAttackCooldownTicks(ItemStack stack) {
        Multimap<Attribute, AttributeModifier> attributes = stack.getAttributeModifiers(EquipmentSlot.MAINHAND);
        for (Map.Entry<Attribute, AttributeModifier> entry : attributes.entries()) {
            if (entry.getKey().equals(Attributes.ATTACK_SPEED)) {
                double attackSpeed = entry.getValue().getAmount() + 4.0;
                return (int) Math.round(20 / attackSpeed);
            }
        }
        return 20;
    }
}
