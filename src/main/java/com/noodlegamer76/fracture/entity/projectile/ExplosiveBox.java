package com.noodlegamer76.fracture.entity.projectile;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class ExplosiveBox extends Entity implements GeoEntity {
    AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public ExplosiveBox(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public void tick() {
        super.tick();
        applyGravity();

        // Get nearby entities
        List<Entity> nearbyEntities = level().getEntities(this, getBoundingBox().inflate(0.05, 0, 0.05));
        Vec3 totalPush = Vec3.ZERO;

        for (Entity entity : nearbyEntities) {
            double xPush = this.getX() - entity.getX();
            double zPush = this.getZ() - entity.getZ();

            Vec3 pushDir = new Vec3(xPush, 0, zPush).normalize();
            totalPush = totalPush.add(pushDir);
        }

        if (!nearbyEntities.isEmpty()) {
            System.out.println("Nearby entities detected: " + nearbyEntities.size());

            // Apply push force
            double pushStrength = 0.01;
            Vec3 finalPush = totalPush.scale(pushStrength);
            this.setDeltaMovement(this.getDeltaMovement().add(finalPush));
        }

        // Apply friction
        applyFriction();

        // Update position based on velocity
        this.move(MoverType.SELF, this.getDeltaMovement());
    }

    private void applyFriction() {
        double friction = 0.70; // Control how much friction slows down movement

        // Calculate new movement after applying friction, but not too aggressively
        Vec3 currentMovement = this.getDeltaMovement();
        Vec3 newMovement = currentMovement.scale(friction);

        // Ensure it doesn't stop too abruptly due to precision issues
        if (newMovement.lengthSqr() < 0.0001) {
            newMovement = Vec3.ZERO;
        }

        this.setDeltaMovement(newMovement);
    }

    private void applyGravity() {
        double gravityStrength = -0.08; // Default gravity value
        Vec3 currentMovement = this.getDeltaMovement();
        Vec3 gravityApplied = currentMovement.add(0, gravityStrength, 0);

        this.setDeltaMovement(gravityApplied);
    }


    public boolean canBeCollidedWith() {
        return this.isAlive();
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
