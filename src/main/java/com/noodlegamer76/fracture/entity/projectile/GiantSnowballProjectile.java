package com.noodlegamer76.fracture.entity.projectile;

import com.noodlegamer76.fracture.entity.InitEntities;
import com.noodlegamer76.fracture.entity.monster.KnowledgeableSnowman;
import com.noodlegamer76.fracture.item.InitItems;
import com.noodlegamer76.fracture.util.ModVectors;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.List;

public class GiantSnowballProjectile extends AbstractHurtingProjectile implements GeoEntity {
    int life = 0;
    AnimatableInstanceCache animatableInstanceCache = GeckoLibUtil.createInstanceCache(this);
        public GiantSnowballProjectile(EntityType<? extends GiantSnowballProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        discard();
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
            if (pResult.getEntity() instanceof LivingEntity livingEntity && !(livingEntity instanceof KnowledgeableSnowman)) {
                livingEntity.hurt(level().damageSources().flyIntoWall(), 6);
                livingEntity.addDeltaMovement(ModVectors.getForwardVector(livingEntity).reverse().scale(2.0));
            }
        super.onHitEntity(pResult);
    }

    @Override
    protected float getInertia() {
        return 1.0f;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public void tick() {
        super.tick();
        life++;
        if (life > 500) {
            discard();
        }
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animatableInstanceCache;
    }
}
