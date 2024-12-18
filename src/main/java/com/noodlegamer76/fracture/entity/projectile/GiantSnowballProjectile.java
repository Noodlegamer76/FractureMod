package com.noodlegamer76.fracture.entity.projectile;

import com.noodlegamer76.fracture.entity.monster.KnowledgeableSnowman;
import com.noodlegamer76.fracture.util.ModVectors;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class GiantSnowballProjectile extends AbstractProjectileSpellEntity implements GeoEntity {
    AnimatableInstanceCache animatableInstanceCache = GeckoLibUtil.createInstanceCache(this);

    public GiantSnowballProjectile(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public GiantSnowballProjectile(EntityType<? extends AbstractArrow> pEntityType, double pX, double pY, double pZ, Level pLevel) {
        this(pEntityType, pLevel);
        this.setPos(pX, pY, pZ);
    }

    public GiantSnowballProjectile(EntityType<? extends AbstractArrow> pEntityType, Entity pShooter, Level pLevel) {
        this(pEntityType, pShooter.getX(), pShooter.getEyeY() - (double)0.1F, pShooter.getZ(), pLevel);
        this.setOwner(pShooter);
        if (pShooter instanceof Player) {
            this.pickup = Pickup.DISALLOWED;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (isOnFire()) {
            discard();
        }
    }

    @Override
    protected @NotNull SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.SNOW_BREAK;
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {

        if (pResult.getEntity() instanceof LivingEntity livingEntity && !(livingEntity instanceof KnowledgeableSnowman)) {
            Vec3 direction = ModVectors.getForwardVector(this);
            Vec3 direction2 = new Vec3(-direction.x, direction.y, -direction.z);
            livingEntity.setDeltaMovement(direction2.reverse().scale(2.0));
        }
        isTriggered = false;

        super.onHitEntity(pResult);
    }

    @Override
    protected void onHit(@NotNull HitResult pResult) {
        if (level().getEntity(this.getId()) != null) {
            trigger();
        }
        super.onHit(pResult);
        if (level().isClientSide) {
            for (int i = 0; i < 30; i++) {
                Vec3 direction = new Vec3(random.nextDouble() - 0.5, random.nextDouble() - 0.5, random.nextDouble() - 0.5).normalize().scale(random.nextDouble() * 2);
                level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.SNOW_BLOCK.defaultBlockState()), getX(), getY() + 0.5, getZ(), direction.x, direction.y, direction.z);
            }
        }

    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animatableInstanceCache;
    }
}
