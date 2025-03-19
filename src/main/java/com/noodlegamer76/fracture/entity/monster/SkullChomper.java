package com.noodlegamer76.fracture.entity.monster;

import com.noodlegamer76.fracture.entity.ai.behavior.SkullchomperWalk;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableRangedAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FloatToSurfaceOfFluid;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.TargetOrRetaliate;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class SkullChomper extends Monster implements GeoEntity, SmartBrainOwner<SkullChomper>, RangedAttackMob {
    public static final EntityDataAccessor<Integer> DATA_ATTACK = SynchedEntityData.defineId(SkullChomper.class, EntityDataSerializers.INT);
    AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public static final RawAnimation WALK = RawAnimation.begin().thenLoop("walk");
    public static final RawAnimation CHOMPERS = RawAnimation.begin().thenPlay("chompers");
    public static final RawAnimation PUKIN = RawAnimation.begin().thenPlay("pukin");
    public static final int ATTACK_BITE = 1;
    public static final int ATTACK_SPIT = 2;
    public int globalAttackTimeout;

    public SkullChomper(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.ATTACK_DAMAGE, 10.0D)
                .add(Attributes.MAX_HEALTH, 250.0D)
                .add(Attributes.ARMOR, 6.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8D)
                .add(Attributes.FOLLOW_RANGE, 24);
    }

    @Override
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    protected void customServerAiStep() {
        tickBrain(this);
        globalAttackTimeout--;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(DATA_ATTACK, 0);
    }

    @Override
    public List<? extends ExtendedSensor<? extends SkullChomper>> getSensors() {
        return List.of(
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<SkullChomper>()
                        .setPredicate((target, entity) ->
                                target instanceof Player)
        );
    }

    @Override
    public BrainActivityGroup<? extends SkullChomper> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),
                new FloatToSurfaceOfFluid<>(),
                new SkullchomperWalk<>(2)
        );
    }

    @Override
    public BrainActivityGroup<? extends SkullChomper> getFightTasks() {
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>().stopTryingToPathAfter(10),
                new SetWalkTargetToAttackTarget<>(),
                new FirstApplicableBehaviour<>(
                        new AnimatableMeleeAttack<SkullChomper>(23)
                                .startCondition(entity -> globalAttackTimeout <= 0)
                                .whenStarting(entity -> {
                                    entity.entityData.set(DATA_ATTACK, ATTACK_BITE);
                                    globalAttackTimeout = 40;
                                })
                                .whenStopping((entity -> entity.entityData.set(DATA_ATTACK, 0))),
                        new AnimatableRangedAttack<>(10)
                                .startCondition(entity -> globalAttackTimeout <= 0)
                                .whenStarting(entity -> {
                                    entity.getEntityData().set(DATA_ATTACK, ATTACK_SPIT);
                                    globalAttackTimeout = 80;
                                })
                                .whenStopping(entity -> entity.getEntityData().set(DATA_ATTACK, 0))
                                .cooldownFor(entity -> entity.getRandom().nextInt(175, 350))
                ));
    }

    @Override
    public BrainActivityGroup<? extends SkullChomper> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<>(
                        new TargetOrRetaliate<>(),
                        new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>()
                ),
                new OneRandomBehaviour<>(
                        new SetRandomWalkTarget<>(),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 60))
                )
        );
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "attack", this::attack));
        controllers.add(new AnimationController<>(this, "walk", this::walk));

    }

    private PlayState walk(AnimationState<SkullChomper> state) {
        if (state.isMoving()) {
            return state.setAndContinue(WALK);
        }
        return PlayState.STOP;
    }

    private PlayState attack(AnimationState<SkullChomper> state) {
        int attack = entityData.get(DATA_ATTACK);

        if (!state.getController().hasAnimationFinished() && state.getController().getCurrentAnimation() != null) {
            return PlayState.CONTINUE;
        }
        else {
            state.getController().forceAnimationReset();
        }

        if (attack == ATTACK_BITE) {
            return state.setAndContinue(CHOMPERS);
        }
        else if (attack == ATTACK_SPIT) {
            return state.setAndContinue(PUKIN);
        }

        return PlayState.STOP;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void performRangedAttack(LivingEntity pTarget, float pVelocity) {

    }

    @Override
    public void travel(Vec3 pTravelVector) {
        super.travel(pTravelVector);
    }
}
