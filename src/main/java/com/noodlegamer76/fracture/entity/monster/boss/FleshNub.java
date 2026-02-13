package com.noodlegamer76.fracture.entity.monster.boss;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import com.noodlegamer76.fracture.entity.monster.multiattack.AttackType;
import com.noodlegamer76.fracture.entity.monster.multiattack.MultiAttackMonster;
import com.noodlegamer76.fracture.entity.ai.FleshNubAi;
import com.noodlegamer76.fracture.entity.ai.memory.InitMemoryModuleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class FleshNub extends MultiAttackMonster implements GeoEntity {
    AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public static final RawAnimation START_WALKING = RawAnimation.begin().thenPlay("start_walking").thenLoop("walking");
    public static final RawAnimation STOP_WALKING = RawAnimation.begin().thenPlay("stop_walking");
    public static final RawAnimation BITE = RawAnimation.begin().thenPlay("bite");
    public static final RawAnimation PUKING = RawAnimation.begin().thenPlay("puking");

    protected static final ImmutableList<SensorType<? extends Sensor<? super FleshNub>>> SENSOR_TYPES =
            ImmutableList.of(
                    SensorType.NEAREST_LIVING_ENTITIES,
                    SensorType.NEAREST_PLAYERS,
                    SensorType.NEAREST_PLAYERS,
                    SensorType.NEAREST_PLAYERS
            );
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES =
            ImmutableList.of(
                    MemoryModuleType.LOOK_TARGET,
                    MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
                    MemoryModuleType.PATH,
                    MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
                    MemoryModuleType.WALK_TARGET,
                    MemoryModuleType.ATTACK_TARGET,
                    MemoryModuleType.ATTACK_COOLING_DOWN,
                    MemoryModuleType.NEAREST_PLAYERS,
                    MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER,
                    MemoryModuleType.NEAREST_VISIBLE_PLAYER,
                    InitMemoryModuleTypes.ATTACK_DELAY.get()
            );

    public FleshNub(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.ATTACK_DAMAGE, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.2)
                .add(Attributes.FLYING_SPEED, 0.4)
                .add(Attributes.MAX_HEALTH, 2.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 1);
    }

    @Override
    protected Brain.Provider<FleshNub> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> pDynamic) {
        return FleshNubAi.makeBrain(this.brainProvider().makeBrain(pDynamic));
    }

    @Override
    protected void customServerAiStep() {

        this.level().getProfiler().push("fleshNubBrain");
        this.getBrain().tick((ServerLevel) this.level(), this);
        this.level().getProfiler().pop();

        this.level().getProfiler().push("fleshNubActivityUpdate");
        FleshNubAi.updateActivity(this);
        this.level().getProfiler().pop();

        super.customServerAiStep();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Brain<FleshNub> getBrain() {
        return (Brain<FleshNub>) super.getBrain();
    }

    @Override
    public double getMeleeAttackRangeSqr(LivingEntity pEntity) {
        return (this.getBbWidth() * 2.0F * this.getBbWidth() * 2.0F + pEntity.getBbWidth());
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "attack", this::attack));
        controllers.add(new AnimationController<>(this, "walk", this::walk));

    }

    private PlayState walk(AnimationState<FleshNub> state) {
        if (state.isMoving()) {
            return state.setAndContinue(START_WALKING);
        }
        else if (!state.isMoving() && state.isCurrentAnimation(START_WALKING) && state.getController().hasAnimationFinished()) {
            return state.setAndContinue(STOP_WALKING);
        }
        return PlayState.STOP;
    }

    private PlayState attack(AnimationState<FleshNub> state) {
        if (getCurrentAttack() == AttackType.BITE) {
            state.setAnimation(BITE);
            return PlayState.CONTINUE;
        }
        state.getController().forceAnimationReset();
        return PlayState.STOP;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
