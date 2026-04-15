package com.noodlegamer76.fracture.entity.projectile;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class IceCube extends AbstractHurtingProjectile implements GeoEntity {
    AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private float gravity = -0.02f;

    public IceCube(EntityType<? extends AbstractHurtingProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public IceCube(EntityType<? extends AbstractHurtingProjectile> pEntityType, double pX, double pY, double pZ, double pOffsetX, double pOffsetY, double pOffsetZ, Level pLevel) {
        super(pEntityType, pX, pY, pZ, pOffsetX, pOffsetY, pOffsetZ, pLevel);
    }

    public IceCube(EntityType<? extends AbstractHurtingProjectile> pEntityType, LivingEntity pShooter, double pOffsetX, double pOffsetY, double pOffsetZ, Level pLevel) {
        super(pEntityType, pShooter, pOffsetX, pOffsetY, pOffsetZ, pLevel);
    }

    @Override
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (pResult.getType() == HitResult.Type.ENTITY) {
            Entity entity = ((EntityHitResult) pResult).getEntity();

            if (!entity.equals(getOwner())) {
                LivingEntity owner = getOwner() instanceof LivingEntity ? (LivingEntity) entity : null;
                playSound(SoundEvents.GLASS_BREAK);
                entity.hurt(level().damageSources().mobProjectile(this, owner), 2.0F);

                if (entity.canFreeze() && !entity.isInvulnerable()) {
                    int freezeTicks = 500;
                    entity.setTicksFrozen(freezeTicks);
                }
            }
        }
        discard();
    }

    @Override
    public void tick() {
        super.tick();
        setDeltaMovement(getDeltaMovement().add(0, gravity, 0));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return ParticleTypes.SNOWFLAKE;
    }

    @Override
    protected float getInertia() {
        return 1.00f;
    }
}
