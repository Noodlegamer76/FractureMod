package com.noodlegamer76.fracture.entity.monster;

import com.noodlegamer76.fracture.entity.InitEntities;
import com.noodlegamer76.fracture.item.InitItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.UUID;
import java.util.function.Predicate;

public class AnkleBiterEntity extends Wolf implements GeoEntity {
    public static final Predicate<LivingEntity> PREY_SELECTOR = (p_289448_) -> {
        EntityType<?> entitytype = p_289448_.getType();
        return entitytype == EntityType.PLAYER;
    };
    AnimatableInstanceCache animatableInstanceCache = GeckoLibUtil.createInstanceCache(this);
    public static final RawAnimation WALK = RawAnimation.begin().thenLoop("walk");
    public static final RawAnimation IDLE_1 = RawAnimation.begin().thenLoop("idle_1");
    public static final RawAnimation IDLE_2 = RawAnimation.begin().thenLoop("idle_2");
    public static final RawAnimation CHOMP = RawAnimation.begin().thenPlay("chomp");
    public static final RawAnimation SIT = RawAnimation.begin().thenPlay("sit");
    public static final RawAnimation STAND = RawAnimation.begin().thenPlay("stand");
    private boolean wasSitting = false;
    public AnkleBiterEntity(EntityType<? extends AnkleBiterEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(7, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(5, new NonTameRandomTargetGoal<>(this, Player.class, true, PREY_SELECTOR));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.MAX_HEALTH, 15.0D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D)
                .add(Attributes.FOLLOW_RANGE, 3.0D);
    }

    @Nullable
    @Override
    public AnkleBiterEntity getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        AnkleBiterEntity ankleBiter = InitEntities.ANKLE_BITER.get().create(pLevel);
        if (ankleBiter != null) {
            UUID uuid = this.getOwnerUUID();
            if (uuid != null) {
                ankleBiter.setOwnerUUID(uuid);
                ankleBiter.setTame(true);
            }
        }

        return ankleBiter;
    }

    public void aiStep() {
        this.updateSwingTime();
        super.aiStep();
    }

    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (this.level().isClientSide) {
            boolean flag = this.isOwnedBy(pPlayer) || this.isTame() || itemstack.is(InitItems.LIVING_FLESH.get()) && !this.isTame() && !this.isAngry();
            return flag ? InteractionResult.CONSUME : InteractionResult.PASS;
        } else if (this.isTame()) {
            if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                this.heal((float)itemstack.getFoodProperties(this).getNutrition());
                if (!pPlayer.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }

                this.gameEvent(GameEvent.EAT, this);
                return InteractionResult.SUCCESS;
            } else {
                InteractionResult interactionresult = super.mobInteract(pPlayer, pHand);
                if ((!interactionresult.consumesAction() || this.isBaby()) && this.isOwnedBy(pPlayer)) {
                    this.setOrderedToSit(!this.isOrderedToSit());
                    this.jumping = false;
                    this.navigation.stop();
                    this.setTarget(null);
                    return InteractionResult.SUCCESS;
                } else {
                    return interactionresult;
                }
            }
        } else if (itemstack.is(InitItems.LIVING_FLESH.get())) {
            if (!pPlayer.getAbilities().instabuild) {
                itemstack.shrink(1);
            }
            if (this.random.nextInt(4) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, pPlayer)) {
                this.tame(pPlayer);
                this.navigation.stop();
                this.setTarget(null);
                this.setOrderedToSit(true);
                this.level().broadcastEntityEvent(this, (byte)7);
            } else {
                this.level().broadcastEntityEvent(this, (byte)6);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "walk_or_idle", this::walkOrIdle));
        controllers.add(new AnimationController<>(this, "sit_or_stand", this::sit));
        controllers.add(new AnimationController<>(this, "attack", this::attack));
    }

    private PlayState sit(AnimationState<AnkleBiterEntity> state) {
        state.setControllerSpeed(1.5F);
        if (isInSittingPose()) {
            wasSitting = true;
            return state.setAndContinue(SIT);
        }
        if (!isInSittingPose() && wasSitting) {
            wasSitting = false;
            return state.setAndContinue(STAND);
        }
        return PlayState.CONTINUE;
    }

    private PlayState walkOrIdle(AnimationState<AnkleBiterEntity> state) {
        if (!state.isMoving() && !isInSittingPose()) {
            state.setControllerSpeed(1F);
            return state.setAndContinue(IDLE_2);
        }
        else if (state.isMoving() && !isInSittingPose()) {
            state.setControllerSpeed(10F);
            return state.setAndContinue(WALK);
        }
        return PlayState.STOP;
    }

    private PlayState attack(AnimationState<AnkleBiterEntity> state) {
        if (swinging || !state.getController().hasAnimationFinished()) {
            state.setAnimation(CHOMP);
            state.setControllerSpeed(4);
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
