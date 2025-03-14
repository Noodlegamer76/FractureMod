package com.noodlegamer76.fracture.entity.projectile;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class MagicBoltProjectile extends AbstractProjectileSpellEntity implements GeoEntity {
    AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public MagicBoltProjectile(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public MagicBoltProjectile(EntityType<? extends AbstractArrow> pEntityType, double pX, double pY, double pZ, Level pLevel) {
        this(pEntityType, pLevel);
        this.setPos(pX, pY, pZ);
    }

    public MagicBoltProjectile(EntityType<? extends AbstractArrow> pEntityType, Entity pShooter, Level pLevel) {
        this(pEntityType, pShooter.getX(), pShooter.getEyeY() - (double)0.1F, pShooter.getZ(), pLevel);
        this.setOwner(pShooter);
        if (pShooter instanceof Player) {
            this.pickup = Pickup.DISALLOWED;
        }
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
