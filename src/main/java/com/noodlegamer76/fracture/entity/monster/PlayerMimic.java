package com.noodlegamer76.fracture.entity.monster;

import com.google.common.collect.ImmutableMap;
import com.mojang.authlib.GameProfile;
import com.noodlegamer76.fracture.entity.ai.PlayerLikeMoveControl;
import com.noodlegamer76.fracture.entity.ai.PlayerLikePathNavigation;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.PowderSnowBlock;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.TargetOrRetaliate;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.custom.GenericAttackTargetSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static net.minecraft.world.entity.player.Player.STANDING_DIMENSIONS;

public class PlayerMimic extends Monster implements SmartBrainOwner<PlayerMimic> {
    private static final Map<Pose, EntityDimensions> POSES = ImmutableMap.<Pose, EntityDimensions>builder()
            .put(Pose.STANDING, STANDING_DIMENSIONS)
            .put(Pose.SLEEPING, SLEEPING_DIMENSIONS)
            .put(Pose.FALL_FLYING, EntityDimensions.scalable(0.6F, 0.6F))
            .put(Pose.SWIMMING, EntityDimensions.scalable(0.6F, 0.6F))
            .put(Pose.SPIN_ATTACK, EntityDimensions.scalable(0.6F, 0.6F))
            .put(Pose.CROUCHING, EntityDimensions.scalable(0.6F, 1.5F))
            .put(Pose.DYING, EntityDimensions.fixed(0.2F, 0.2F))
            .build();
    public static final EntityDataAccessor<String> DATA_MOB_NAME = SynchedEntityData.defineId(PlayerMimic.class, EntityDataSerializers.STRING);
    @Nullable
    private GameProfile profile;
    protected Vec3 deltaMovementOnPreviousTick = Vec3.ZERO;

    public PlayerMimic(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.moveControl = new PlayerLikeMoveControl(this);
        this.navigation = new PlayerLikePathNavigation(this, pLevel);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_MOB_NAME, "");
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.13)
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D)
                .add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    @Override
    public List<? extends ExtendedSensor<? extends PlayerMimic>> getSensors() {
        return List.of(
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<PlayerMimic>()
                        .setPredicate((target, entity) ->
                                target instanceof Player ||
                                target instanceof IronGolem
                        )
        );
    }

    @Override
    public BrainActivityGroup<? extends PlayerMimic> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new MoveToWalkTarget<>(),
                new LookAtTarget<>()
        );
    }

    @Override
    public BrainActivityGroup<? extends PlayerMimic> getFightTasks() {
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(),
                new SetWalkTargetToAttackTarget<>(),
                new AnimatableMeleeAttack<>(0)
                        .whenStarting(entity -> setAggressive(true))
                        .whenStarting(entity -> setAggressive(false))
        );
    }

    @Override
    public BrainActivityGroup<? extends PlayerMimic> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<PlayerMimic>(
                        new TargetOrRetaliate<>()
                ),
                new OneRandomBehaviour<PlayerMimic>(
                        new SetRandomWalkTarget<>(),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 60))
                )
        );
    }

    @Override
    public void aiStep() {
        super.aiStep();
    }

    @Override
    public void tick() {
        this.deltaMovementOnPreviousTick = this.getDeltaMovement();

        if (level().isClientSide) {
            String mobName = entityData.get(DATA_MOB_NAME);
            if (!StringUtils.isEmpty(mobName) && !"NA".equals(mobName)) {
                if (profile == null || !Objects.equals(profile.getName(), mobName)) {
                    GameProfile skeletonProfile = new GameProfile(null, mobName);

                    this.profile = skeletonProfile;

                    SkullBlockEntity.updateGameprofile(skeletonProfile, (resolvedProfile) -> this.profile = resolvedProfile);
                }
            }
        }

        super.tick();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putString("MobName", entityData.get(DATA_MOB_NAME));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        entityData.set(DATA_MOB_NAME, pCompound.getString("MobName"));
    }

    @Override
    protected Brain.Provider<PlayerMimic> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    protected void customServerAiStep()  {
        tickBrain(this);
    }


    public Vec3 getDeltaMovementLerped(float pPatialTick) {
        return this.deltaMovementOnPreviousTick.lerp(this.getDeltaMovement(), pPatialTick);
    }

    public @Nullable GameProfile getProfile() {
        return profile;
    }

    public void setProfile(GameProfile profile) {
        this.profile = profile;
        if (!level().isClientSide) {
            entityData.set(DATA_MOB_NAME, profile.getName());
        }
    }

    @Override
    public void travel(Vec3 pTravelVector) {
        super.travel(pTravelVector);
    }

    @Override
    public Vec3 handleRelativeFrictionAndCalculateMovement(Vec3 pTravelVector, float pFriction) {
        this.moveRelative(this.getFrictionModifier(pFriction), pTravelVector);
        this.setDeltaMovement(handleOnClimbable(this.getDeltaMovement()));
        this.move(MoverType.SELF, this.getDeltaMovement());
        Vec3 delta = this.getDeltaMovement();

        if ((this.horizontalCollision || this.jumping) && (this.onClimbable() || this.isInPowderSnow && PowderSnowBlock.canEntityWalkOnPowderSnow(this))) {
            delta = new Vec3(delta.x, 0.2D, delta.z);
        }

        return delta;
    }

    private Vec3 handleOnClimbable(Vec3 pDeltaMovement) {
        if (this.onClimbable()) {
            this.resetFallDistance();
            double d0 = Mth.clamp(pDeltaMovement.x, -0.15F, 0.15F);
            double d1 = Mth.clamp(pDeltaMovement.z, -0.15F, 0.15F);
            double d2 = Math.max(pDeltaMovement.y, -0.15F);
            if (d2 < 0.0D && !this.getFeetBlockState().isScaffolding(this) && this.isSuppressingSlidingDownLadder() && this instanceof PlayerMimic) {
                d2 = 0.0D;
            }

            pDeltaMovement = new Vec3(d0, d2, d1);
        }

        return pDeltaMovement;
    }

    private float getFrictionModifier(float pFriction) {
        return this.onGround() ? this.getSpeed() * (0.16277136F / (pFriction * pFriction * pFriction)) : this.getFlyingSpeed();
    }

    @Override
    protected float getFlyingSpeed() {
        return 0.02f;
    }

    public void setCrouching(boolean crouching) {
        if (crouching) {
            this.setPose(Pose.CROUCHING);
        }
        else {
            this.setPose(Pose.STANDING);
        }
    }

    @Override
    public void setSwimming(boolean pSwimming) {
        this.setSharedFlag(4, pSwimming);
    }

    @Override
    public float getStandingEyeHeight(Pose pose, EntityDimensions pSize) {
        return switch (pose) {
            case SWIMMING, FALL_FLYING, SPIN_ATTACK -> 0.4F;
            case CROUCHING -> 1.27F;
            default -> 1.62F;
        };
    }

    @Override
    public EntityDimensions getDimensions(Pose pPose) {
        return POSES.getOrDefault(pPose, STANDING_DIMENSIONS);
    }
}
