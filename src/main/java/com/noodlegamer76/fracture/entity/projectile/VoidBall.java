package com.noodlegamer76.fracture.entity.projectile;

import com.noodlegamer76.fracture.particles.InitParticles;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.joml.Vector3d;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class VoidBall extends AbstractProjectileSpellEntity implements GeoEntity {
    AnimatableInstanceCache animatableInstanceCache = GeckoLibUtil.createInstanceCache(this);
    public VoidBall(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public VoidBall(EntityType<? extends AbstractArrow> pEntityType, double pX, double pY, double pZ, Level pLevel) {
        this(pEntityType, pLevel);
        this.setPos(pX, pY, pZ);
    }

    public VoidBall(EntityType<? extends AbstractArrow> pEntityType, Entity pShooter, Level pLevel) {
        this(pEntityType, pShooter.getX(), pShooter.getEyeY() - (double)0.1F, pShooter.getZ(), pLevel);
        this.setOwner(pShooter);
        if (pShooter instanceof Player) {
            this.pickup = Pickup.DISALLOWED;
        }
    }

    @Override
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (level().isClientSide) {
            for (int i = 0; i < 30; i++) {
                Vector3d vec = new Vector3d(getX(), getY(), getZ());
                vec.x = (Math.random() - 0.5) / 2;
                vec.y = (Math.random() - 0.75) / 2;
                vec.z = (Math.random() - 0.5) / 2;
                vec.normalize();
                level().addParticle(InitParticles.VOID_PARTICLES.get(),
                        getX(), getY(), getZ(),
                        vec.x(), vec.y(), vec.z());
            }
        }
    }



    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animatableInstanceCache;
    }
}
