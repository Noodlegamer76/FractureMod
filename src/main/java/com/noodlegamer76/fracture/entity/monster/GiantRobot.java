package com.noodlegamer76.fracture.entity.monster;

import com.noodlegamer76.fracture.client.renderers.entity.MultiAttackMonster;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class GiantRobot extends MultiAttackMonster implements GeoEntity, SmartBrainOwner<GiantRobot> {
    AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public GiantRobot(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public void setAttackNumber() {

    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.2);
    }

    @Override
    public AABB getBoundingBoxForCulling() {
        Vec3 offset = new Vec3(10, 10, 10);
        return new AABB(position().add(offset), position().subtract(offset));
    }

    @Override
    public List<? extends ExtendedSensor<? extends GiantRobot>> getSensors() {
        return List.of();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
