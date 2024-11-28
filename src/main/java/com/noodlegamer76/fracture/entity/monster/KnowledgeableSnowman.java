package com.noodlegamer76.fracture.entity.monster;

import com.noodlegamer76.fracture.client.renderers.entity.MultiAttackMonster;
import com.noodlegamer76.fracture.entity.ai.behavior.GiantSnowballLaunch;
import com.noodlegamer76.fracture.entity.ai.behavior.GiantSnowballSpreadLaunch;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
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

public class KnowledgeableSnowman extends MultiAttackMonster implements GeoEntity, SmartBrainOwner<KnowledgeableSnowman> {
    AnimatableInstanceCache animatableInstanceCache = GeckoLibUtil.createInstanceCache(this);
    public static final RawAnimation CASTING = RawAnimation.begin().thenPlay("casting");

    public final int SNOWBALL_LAUNCH = 1;
    public final int SNOWBALL_SPREAD_LAUNCH = 2;
    public final int FROST_BEAM = 3;
    public final int ICE_CICLE = 4;

    public KnowledgeableSnowman(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void setAttackNumber() {
        if (attackTimeout > 0 || attackNumber != 0) {
            return;
        }
        attackNumber = SNOWBALL_LAUNCH;
    }

    public static AttributeSupplier.Builder  createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.115D)
                .add(Attributes.ATTACK_DAMAGE, 7.0D)
                .add(Attributes.MAX_HEALTH, 30)
                .add(Attributes.ARMOR, 1.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                .add(Attributes.FOLLOW_RANGE, 32);
    }

    @Override
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    protected void customServerAiStep()  {
        tickBrain(this);
    }

    @Override
    public List<? extends ExtendedSensor<? extends KnowledgeableSnowman>> getSensors() {
        return List.of(
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<KnowledgeableSnowman>()
                        .setPredicate((target, entity) ->
                                target instanceof Player)
        );
    }

    @Override
    public BrainActivityGroup<? extends KnowledgeableSnowman> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),
                new MoveToWalkTarget<>()
        );
    }

    @Override
    public BrainActivityGroup<? extends KnowledgeableSnowman> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<KnowledgeableSnowman>(
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
    public BrainActivityGroup<? extends KnowledgeableSnowman> getFightTasks() {
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>().stopTryingToPathAfter(10),
                new SetWalkTargetToAttackTarget<>(),
                new FirstApplicableBehaviour<>(
                        new GiantSnowballLaunch<>()
                                .startCondition((e) -> e.attackNumber == SNOWBALL_LAUNCH),
                        new GiantSnowballSpreadLaunch<>()
                                .startCondition((e) -> e.attackNumber == SNOWBALL_SPREAD_LAUNCH)
                )
        );
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "casting", this::casting));
    }

    private PlayState casting(AnimationState<KnowledgeableSnowman> state) {
        if (entityData.get(DATA_ATTACK) != 0) {
            state.setAnimation(CASTING);
            return PlayState.CONTINUE;
        }
        state.resetCurrentAnimation();
        return PlayState.STOP;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animatableInstanceCache;
    }
}
