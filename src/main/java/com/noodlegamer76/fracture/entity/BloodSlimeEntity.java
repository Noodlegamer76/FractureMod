package com.noodlegamer76.fracture.entity;

import com.noodlegamer76.fracture.client.renderers.entity.MultiAttackMonster;
import com.noodlegamer76.fracture.entity.ai.behavior.BloodSlimeSplit;
import com.noodlegamer76.fracture.entity.ai.behavior.BounceToWalkTarget;
import com.noodlegamer76.fracture.entity.ai.behavior.SuperJump;
import com.noodlegamer76.fracture.particles.InitParticles;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidType;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
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
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class BloodSlimeEntity extends MultiAttackMonster implements GeoEntity, SmartBrainOwner<BloodSlimeEntity> {
    public static final EntityDataAccessor<Boolean> DATA_EXPLODE = SynchedEntityData.defineId(BloodSlimeEntity.class, EntityDataSerializers.BOOLEAN);

    AnimatableInstanceCache animatableInstanceCache = GeckoLibUtil.createInstanceCache(this);

    public static final RawAnimation JUMP = RawAnimation.begin().thenPlay("jump");
    public static final RawAnimation SUPER_JUMP = RawAnimation.begin().thenPlay("charge_jump");

    public final int MOVE_JUMP = 1;
    public final int ATTACK_SUPER_JUMP = 2;
    public final int ATTACK_SPIT = 3;

    public int animationDelay = 0;

    protected BloodSlimeEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.125D)
                .add(Attributes.ATTACK_DAMAGE, 10.0D)
                .add(Attributes.MAX_HEALTH, 250.0D)
                .add(Attributes.ARMOR, 6.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8D)
                .add(Attributes.FOLLOW_RANGE, 24);
    }

    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        return false;
    }

    @Override
    public boolean canDrownInFluidType(FluidType type) {
        return false;
    }

    @Override
    protected void jumpFromGround() {
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_EXPLODE, false);
    }

    @Override
    public void tick() {
        super.tick();
        if ((entityData.get(DATA_EXPLODE))) {
            for (int i = 0; i < 100; i++) {
                Vec3 direction = new Vec3(Math.random() - 0.5, Math.random() - 0.5, Math.random() - 0.5).normalize();
                level().addParticle(InitParticles.BLOOD_PARTICLES.get(), position().x, position().y, position().z, direction.x * 5, direction.y * 5, direction.z * 5);
            }
        }
        getEntityData().set(DATA_EXPLODE, false);
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
    public List<? extends ExtendedSensor<? extends BloodSlimeEntity>> getSensors() {
        return List.of(
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<BloodSlimeEntity>()
                        .setPredicate((target, entity) ->
                                target instanceof Player)
        );
    }

    @Override
    public BrainActivityGroup<? extends BloodSlimeEntity> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),
                new FirstApplicableBehaviour(
                        new BounceToWalkTarget<>().setBounceTimeout(25)
                                .startCondition((e) -> this.attackNumber == MOVE_JUMP && e.onGround()),
                        new SuperJump<BloodSlimeEntity>()
                                .startCondition((e) -> this.attackNumber == ATTACK_SUPER_JUMP && e.onGround())
                                .noTimeout(),
                        new BloodSlimeSplit<BloodSlimeEntity>()
                                .startCondition((e) -> this.attackNumber == ATTACK_SPIT)


        ));
    }

    @Override
    public BrainActivityGroup<? extends BloodSlimeEntity> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<BloodSlimeEntity>(
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
    public BrainActivityGroup<? extends BloodSlimeEntity> getFightTasks() {
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>().stopTryingToPathAfter(50),
                new SetWalkTargetToAttackTarget<>(),
                new AnimatableMeleeAttack<>(0).whenStarting(entity -> setAggressive(true)).whenStarting(entity -> setAggressive(false))
        );
    }

    @Override
    public double getMeleeAttackRangeSqr(LivingEntity pEntity) {
        return 7;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "jump", this::jump));
        controllers.add(new AnimationController<>(this, "super_jump", this::superJump));
    }

    private <T extends GeoAnimatable> PlayState superJump(AnimationState<T> state) {
        if ((entityData.get(DATA_ATTACK) == 2 || !state.getController().hasAnimationFinished())) {
            for (int i = 0; i < 100; i++) {
                Vec3 direction = new Vec3(Math.random() - 0.5, Math.random() - 0.5, Math.random() - 0.5).normalize();
                level().addParticle(InitParticles.BLOOD_PARTICLES.get(), position().x, position().y, position().z,
                        direction.x * (Math.random() * 5), direction.y * (Math.random() * 5), direction.z * (Math.random() * 5));
            }
            state.setAnimation(SUPER_JUMP);
            return PlayState.STOP;
        }
        state.getController().forceAnimationReset();
        return PlayState.STOP;
    }

    private <T extends GeoAnimatable> PlayState jump(AnimationState<T> state) {
        if (onGround() || !state.getController().hasAnimationFinished()) {
            state.setAnimation(JUMP);
            return PlayState.CONTINUE;
        }
        state.getController().forceAnimationReset();
        return PlayState.STOP;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animatableInstanceCache;
    }
}
