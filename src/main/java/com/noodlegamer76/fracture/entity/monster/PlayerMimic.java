package com.noodlegamer76.fracture.entity.monster;

import com.google.common.collect.ImmutableMap;
import com.mojang.authlib.GameProfile;
import com.noodlegamer76.fracture.entity.ai.PlayerLikeMoveControl;
import com.noodlegamer76.fracture.entity.ai.PlayerLikePathNavigation;
import com.noodlegamer76.fracture.entity.ai.WalkRunAndJumpOverGapsToWalkTarget;
import com.noodlegamer76.fracture.entity.ai.behavior.AnimatableMeleeAttackOrCrit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.PowderSnowBlock;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FloatToSurfaceOfFluid;
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
import org.jetbrains.annotations.NotNull;
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
    private boolean shouldJumpOverGap = false;
    private boolean wasOnGround = false;

    public PlayerMimic(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.moveControl = new PlayerLikeMoveControl(this);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_MOB_NAME, "");
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new PlayerLikePathNavigation(this, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.ATTACK_DAMAGE, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.1F)
                .add(Attributes.MAX_HEALTH, 20.0F)
                .add(Attributes.FOLLOW_RANGE, 32.0f)
                .add(Attributes.ATTACK_SPEED)
                .add(Attributes.LUCK)
                .add(ForgeMod.BLOCK_REACH.get())
                .add(Attributes.ATTACK_KNOCKBACK)
                .add(ForgeMod.ENTITY_REACH.get());
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
                new WalkRunAndJumpOverGapsToWalkTarget<>(),
                new LookAtTarget<>(),
                new FloatToSurfaceOfFluid<>()
        );
    }

    @Override
    public BrainActivityGroup<? extends PlayerMimic> getFightTasks() {
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(),
                new SetWalkTargetToAttackTarget<>(),
                new AnimatableMeleeAttackOrCrit<>(0)
                        .whenStarting(entity -> setAggressive(true))
                        .whenStarting(entity -> setAggressive(false))
        );
    }

    @Override
    public BrainActivityGroup<? extends PlayerMimic> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new TargetOrRetaliate<>(),
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

        ++attackStrengthTicker;

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
    public void move(MoverType pType, Vec3 pPos) {
        wasOnGround = onGround();
        super.move(pType, pPos);
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

    @Override
    public double getMeleeAttackRangeSqr(LivingEntity pEntity) {
        return getMeleeAttackRangeSqr();
    }

    public double getMeleeAttackRangeSqr() {
        return 3 * 3;
    }

    public boolean isShouldJumpOverGap() {
        return shouldJumpOverGap;
    }

    public void setShouldJumpOverGap(boolean shouldJumpOverGap) {
        this.shouldJumpOverGap = shouldJumpOverGap;
    }

    public void coyoteTime() {
        jumpFromGround();
    }

    public boolean isWasOnGround() {
        return wasOnGround;
    }

    public float getAttackStrengthScale(float pAdjustTicks) {
        return Mth.clamp(((float)this.attackStrengthTicker + pAdjustTicks) / this.getCurrentItemAttackStrengthDelay(), 0.0F, 1.0F);
    }

    public float getCurrentItemAttackStrengthDelay() {
        return (float)(1.0D / this.getAttributeValue(Attributes.ATTACK_SPEED) * 20.0D);
    }

    public void attack(Entity target) {
        if (!target.isAttackable() || target.skipAttackInteraction(this))
            return;

        float attackDamage = (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float enchantDamage;

        if (target instanceof LivingEntity living)
            enchantDamage = EnchantmentHelper.getDamageBonus(this.getMainHandItem(), living.getMobType());
        else
            enchantDamage = EnchantmentHelper.getDamageBonus(this.getMainHandItem(), MobType.UNDEFINED);

        float attackStrength = this.getAttackStrengthScale(0.5F);
        attackDamage *= 0.2F + attackStrength * attackStrength * 0.8F;
        enchantDamage *= attackStrength;

        if (attackDamage <= 0.0F && enchantDamage <= 0.0F)
            return;

        boolean fullyCharged = attackStrength > 0.9F;

        //Knockback
        float knockback = (float)this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
        knockback += EnchantmentHelper.getKnockbackBonus(this);

        boolean sprintKnockback = false;
        if (this.isSprinting() && fullyCharged) {
            knockback++;
            sprintKnockback = true;
        }

        //Critical hit
        boolean critical =
                fullyCharged &&
                        this.fallDistance > 0.0F &&
                        !this.onGround() &&
                        !this.onClimbable() &&
                        !this.isInWater() &&
                        !this.hasEffect(MobEffects.BLINDNESS) &&
                        !this.isPassenger() &&
                        !this.isSprinting() &&
                        target instanceof LivingEntity;

        if (critical) {
            attackDamage *= 1.5F;
        }

        attackDamage += enchantDamage;

        //Fire Aspect
        int fireAspect = EnchantmentHelper.getFireAspect(this);
        boolean setFire = false;

        if (target instanceof LivingEntity living) {
            if (fireAspect > 0 && !target.isOnFire()) {
                setFire = true;
                target.setSecondsOnFire(1);
            }
        }

        Vec3 oldMotion = target.getDeltaMovement();
        boolean damaged = target.hurt(this.damageSources().mobAttack(this), attackDamage);

        if (!damaged) {
            if (setFire)
                target.clearFire();
            return;
        }

        //Apply knockback
        if (knockback > 0) {
            if (target instanceof LivingEntity living) {
                living.knockback(
                        knockback * 0.5F,
                        Mth.sin(this.getYRot() * ((float)Math.PI / 180F)),
                        -Mth.cos(this.getYRot() * ((float)Math.PI / 180F))
                );
            } else {
                target.push(
                        -Mth.sin(this.getYRot() * ((float)Math.PI / 180F)) * knockback * 0.5F,
                        0.1D,
                        Mth.cos(this.getYRot() * ((float)Math.PI / 180F)) * knockback * 0.5F
                );
            }

            this.setDeltaMovement(this.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
            this.setSprinting(false);
        }

        //Critical particles + sound
        if (critical) {
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.PLAYER_ATTACK_CRIT, this.getSoundSource(), 1.0F, 1.0F);
        }
        else if (fullyCharged) {
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.PLAYER_ATTACK_STRONG, this.getSoundSource(), 1.0F, 1.0F);
        }
        else {
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.PLAYER_ATTACK_WEAK, this.getSoundSource(), 1.0F, 1.0F);
        }

        //Sweeping attack (if holding sword)
        if (fullyCharged && !critical && !sprintKnockback && this.onGround()) {
            ItemStack stack = this.getMainHandItem();

            if (stack.canPerformAction(net.minecraftforge.common.ToolActions.SWORD_SWEEP)) {
                float sweepDamage = 1.0F + EnchantmentHelper.getSweepingDamageRatio(this) * attackDamage;

                for (LivingEntity other : this.level().getEntitiesOfClass(
                        LivingEntity.class,
                        getSweepHitBox(target))) {

                    if (other != this &&
                            other != target &&
                            !this.isAlliedTo(other) &&
                            this.distanceToSqr(other) < getMeleeAttackRangeSqr()) {

                        other.knockback(
                                0.4F,
                                Mth.sin(this.getYRot() * ((float)Math.PI / 180F)),
                                -Mth.cos(this.getYRot() * ((float)Math.PI / 180F))
                        );

                        other.hurt(this.damageSources().mobAttack(this), sweepDamage);
                    }
                }

                this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                        SoundEvents.PLAYER_ATTACK_SWEEP, this.getSoundSource(), 1.0F, 1.0F);
                this.sweepAttack();
            }
        }

        //Post-enchantment hooks
        if (target instanceof LivingEntity living)
            EnchantmentHelper.doPostHurtEffects(living, this);

        EnchantmentHelper.doPostDamageEffects(this, target);

        if (fireAspect > 0)
            target.setSecondsOnFire(fireAspect * 4);

        this.resetAttackStrengthTicker();
        this.setLastHurtMob(target);
    }

    public void resetAttackStrengthTicker() {
        this.attackStrengthTicker = 0;
    }

    public void sweepAttack() {
        double d0 = -Mth.sin(this.getYRot() * ((float)Math.PI / 180F));
        double d1 = Mth.cos(this.getYRot() * ((float)Math.PI / 180F));
        if (this.level() instanceof ServerLevel) {
            ((ServerLevel)this.level()).sendParticles(ParticleTypes.SWEEP_ATTACK, this.getX() + d0, this.getY(0.5D), this.getZ() + d1, 0, d0, 0.0D, d1, 0.0D);
        }
    }

    private AABB getSweepHitBox(Entity target) {
        return target.getBoundingBox().inflate(1.0D, 0.25D, 1.0D);
    }
}
