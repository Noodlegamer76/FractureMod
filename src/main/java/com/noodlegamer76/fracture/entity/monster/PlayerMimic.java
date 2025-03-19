package com.noodlegamer76.fracture.entity.monster;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimap;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.util.UUIDTypeAdapter;
import com.noodlegamer76.fracture.client.renderers.entity.util.SkinRegistry;
import com.noodlegamer76.fracture.entity.ai.behavior.playermimic.CritOrNormalAttack;
import com.noodlegamer76.fracture.entity.ai.behavior.playermimic.SprintJumpToTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FloatToSurfaceOfFluid;
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

import java.util.List;
import java.util.Map;

import static net.minecraft.world.entity.player.Player.STANDING_DIMENSIONS;

public class PlayerMimic extends Monster implements SmartBrainOwner<PlayerMimic> {
    public static final EntityDataAccessor<String> SKIN_DATA = SynchedEntityData.defineId(PlayerMimic.class, EntityDataSerializers.STRING);
    private static final String SKIN_URL_TEMPLATE = "http://skins.minecraft.net/MinecraftSkins/%s.png";
    protected Vec3 deltaMovementOnPreviousTick = Vec3.ZERO;
    //Player attack range is 3.0, this is lowered to decrease difficulty
    public static final float ATTACK_RANGE = 3;
    public boolean critAttack;
    public boolean usePreloadedSkin = true;
    public boolean useRandomSkin = true;
    public String toSetSkinName;
    public ResourceLocation skinTexture = new ResourceLocation("textures/entity/player/wide/steve.png");
    public String skinName = "N/A";
    private static final Map<Pose, EntityDimensions> POSES = ImmutableMap.<Pose, EntityDimensions>builder()
            .put(Pose.STANDING, STANDING_DIMENSIONS)
            .put(Pose.SLEEPING, SLEEPING_DIMENSIONS)
            .put(Pose.FALL_FLYING, EntityDimensions.scalable(0.6F, 0.6F))
            .put(Pose.SWIMMING, EntityDimensions.scalable(0.6F, 0.6F))
            .put(Pose.SPIN_ATTACK, EntityDimensions.scalable(0.6F, 0.6F))
            .put(Pose.CROUCHING, EntityDimensions.scalable(0.6F, 1.5F))
            .put(Pose.DYING, EntityDimensions.fixed(0.2F, 0.2F))
            .build();
    public static float WALK_SPEED = 0.36F;
    public static float CROUCH_SPEED_MOD = 0.3F;
    public Runnable onDeathAction;


    public PlayerMimic(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public void tick() {
        this.deltaMovementOnPreviousTick = this.getDeltaMovement();
        super.tick();
        setPersistenceRequired();
        if (usePreloadedSkin && (skinName.equals("N/A") || skinName.equals("")) && !level().isClientSide && useRandomSkin) {
            Map.Entry<String, ResourceLocation> skin = SkinRegistry.getRandomSkin();
            this.skinName = skin.getKey();
            this.skinTexture = skin.getValue();
            entityData.set(SKIN_DATA, skinName);
        }
        else if (usePreloadedSkin && !skinName.equals("N/A") && !skinName.equals("") && !level().isClientSide && toSetSkinName != null) {
            if (SkinRegistry.getUniqueSkin(toSetSkinName) != null) {
                this.skinName = toSetSkinName;
                this.skinTexture = SkinRegistry.getUniqueSkin(toSetSkinName);
                entityData.set(SKIN_DATA, skinName);
            }
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putString("skin_name", this.skinName);
        tag.putString("skin_texture", this.skinTexture.toString());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        skinName = tag.getString("skin_name");
        skinTexture = new ResourceLocation(tag.getString("skin_texture"));
        if (!skinName.isEmpty()) {
            entityData.set(SKIN_DATA, skinName);
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SKIN_DATA, "N/A");
    }

    public static AttributeSupplier.Builder createAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.ATTACK_DAMAGE, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.36F)
                .add(Attributes.ATTACK_SPEED)
                .add(Attributes.FLYING_SPEED, 0.4)
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.LUCK)
                .add(net.minecraftforge.common.ForgeMod.BLOCK_REACH.get())
                .add(Attributes.ATTACK_KNOCKBACK)
                .add(net.minecraftforge.common.ForgeMod.ENTITY_REACH.get())
                .add(Attributes.FOLLOW_RANGE, 128.0D);
    }

    @Override
    public List<? extends ExtendedSensor<? extends PlayerMimic>> getSensors() {
        return List.of(
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<PlayerMimic>()
                        .setPredicate((target, entity) ->
                                target instanceof Player)
        );
    }

    @Override
    public BrainActivityGroup<? extends PlayerMimic> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),
                new FirstApplicableBehaviour<>(
                        new FloatToSurfaceOfFluid<>()
                ),
                new FirstApplicableBehaviour<>(
                        new SprintJumpToTarget<>(ATTACK_RANGE)
                                .cooldownFor((entity) -> 0).noTimeout(),
                        new MoveToWalkTarget<>()
                )
        );
    }

    @Override
    public BrainActivityGroup<? extends PlayerMimic> getFightTasks() {
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>().stopTryingToPathAfter(10),
                new SetWalkTargetToAttackTarget<>(),
                new FirstApplicableBehaviour<>(
                        new CritOrNormalAttack<>(0, ATTACK_RANGE).attackInterval((entity) -> getAttackCooldownTicks(getMainHandItem())))
        );
    }

    @Override
    public BrainActivityGroup<? extends PlayerMimic> getIdleTasks() {
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
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    protected void customServerAiStep()  {
        tickBrain(this);

        setSpeed(WALK_SPEED);
        if (isCrouching()) {
            setSpeed(WALK_SPEED * CROUCH_SPEED_MOD);
        }
    }

    public static ResourceLocation getSkinTexture(GameProfile profile) {
        Minecraft minecraft = Minecraft.getInstance();
        SkinManager skinManager = minecraft.getSkinManager();

        // Force profile properties to be filled if missing
        minecraft.getMinecraftSessionService().fillProfileProperties(profile, false);

        // Now attempt to fetch the textures
        Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> textures = skinManager.getInsecureSkinInformation(profile);

        if (textures.containsKey(MinecraftProfileTexture.Type.SKIN)) {
            return skinManager.registerTexture(textures.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN);
        } else {
            return DefaultPlayerSkin.getDefaultSkin(profile.getId());
        }
    }

    public ResourceLocation getSkinTextureLocation() {
        GameProfile profile = new GameProfile(UUIDTypeAdapter.fromString("f9b4a424-8a26-40ac-baed-f86f1a19eb98"), "noodlegamer76");
        return getSkinTexture(profile);
    }

    public Vec3 getDeltaMovementLerped(float pPatialTick) {
        return this.deltaMovementOnPreviousTick.lerp(this.getDeltaMovement(), (double)pPatialTick);
    }

    public boolean doHurtTarget(Entity pEntity) {
        float f = (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        if (critAttack) {
            f *= 1.5f;
            crit(pEntity);
        }
        else {
            sweepIfCan(getMainHandItem(), f);
        }
        float f1 = (float)this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
        if (pEntity instanceof LivingEntity) {
            f += EnchantmentHelper.getDamageBonus(this.getMainHandItem(), ((LivingEntity)pEntity).getMobType());
            f1 += (float)EnchantmentHelper.getKnockbackBonus(this);
        }

        int i = EnchantmentHelper.getFireAspect(this);
        if (i > 0) {
            pEntity.setSecondsOnFire(i * 4);
        }

        boolean flag = pEntity.hurt(this.damageSources().mobAttack(this), f);
        if (flag) {
            if (f1 > 0.0F && pEntity instanceof LivingEntity) {
                ((LivingEntity)pEntity).knockback((f1 * 0.5F), Mth.sin(this.getYRot() * ((float)Math.PI / 180F)), (-Mth.cos(this.getYRot() * ((float)Math.PI / 180F))));
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
            }

            if (pEntity instanceof Player player) {
                this.maybeDisableShield(player, this.getMainHandItem(), player.isUsingItem() ? player.getUseItem() : ItemStack.EMPTY);
            }

            this.doEnchantDamageEffects(this, pEntity);
            this.setLastHurtMob(pEntity);
        }

        return flag;
    }

    private void maybeDisableShield(Player pPlayer, ItemStack pMobItemStack, ItemStack pPlayerItemStack) {
        if (!pMobItemStack.isEmpty() && !pPlayerItemStack.isEmpty() && pMobItemStack.getItem() instanceof AxeItem && pPlayerItemStack.is(Items.SHIELD)) {
            float f = 0.25F + (float)EnchantmentHelper.getBlockEfficiency(this) * 0.05F;
            if (this.random.nextFloat() < f) {
                pPlayer.getCooldowns().addCooldown(Items.SHIELD, 100);
                level().broadcastEntityEvent(pPlayer, (byte)30);
            }
        }

    }

    @Override
    public boolean isWithinMeleeAttackRange(LivingEntity pEntity) {
        return pEntity.distanceToSqr(this.getX(), getY(), getZ()) < ATTACK_RANGE * ATTACK_RANGE;
    }

    public void crit(Entity pEntityHit) {
        if (level().isClientSide) {
            Minecraft.getInstance().particleEngine.createTrackingEmitter(pEntityHit, ParticleTypes.CRIT);
        }
        else  {
            ((ServerLevel) level()).getChunkSource().broadcastAndSend(this, new ClientboundAnimatePacket(pEntityHit, 4));
        }
    }

    public static int getAttackCooldownTicks(ItemStack stack) {
        Multimap<Attribute, AttributeModifier> attributes = stack.getAttributeModifiers(EquipmentSlot.MAINHAND);
        for (Map.Entry<Attribute, AttributeModifier> entry : attributes.entries()) {
            if (entry.getKey().equals(Attributes.ATTACK_SPEED)) {
                double attackSpeed = entry.getValue().getAmount() + 4.0 ;
                return (int) Math.round(20 / attackSpeed);
            }
        }
        return (int) Math.round(20 / 4.0);
    }

    public void sweepIfCan(ItemStack stack, float damage) {
        boolean canSweep = stack.canPerformAction(net.minecraftforge.common.ToolActions.SWORD_SWEEP);
        if (canSweep) {
            float f3 = 1.0F + EnchantmentHelper.getSweepingDamageRatio(this) * damage;

            for(LivingEntity livingentity : this.level().getEntitiesOfClass(LivingEntity.class, getSweepHitbox())) {
                double range = self().getAttributeValue(ForgeMod.ENTITY_REACH.get());
                double entityReachSq = Mth.square(range);
                if (livingentity != this && livingentity != getTarget() && !this.isAlliedTo(livingentity) && (!(livingentity instanceof ArmorStand) || !((ArmorStand)livingentity).isMarker()) && this.distanceToSqr(livingentity) < entityReachSq) {
                    livingentity.knockback((double)0.4F, (double)Mth.sin(this.getYRot() * ((float)Math.PI / 180F)), (double)(-Mth.cos(this.getYRot() * ((float)Math.PI / 180F))));
                    livingentity.hurt(this.damageSources().mobAttack(this), f3);
                }
            }

            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, this.getSoundSource(), 1.0F, 1.0F);
            this.sweepAttack();
        }
    }

    public void sweepAttack() {
        double d0 = (-Mth.sin(this.getYRot() * ((float)Math.PI / 180F)));
        double d1 = Mth.cos(this.getYRot() * ((float)Math.PI / 180F));
        if (this.level() instanceof ServerLevel) {
            ((ServerLevel)this.level()).sendParticles(ParticleTypes.SWEEP_ATTACK, this.getX() + d0, this.getY(0.5D), this.getZ() + d1, 0, d0, 0.0D, d1, 0.0D);
        }

    }

    public AABB getSweepHitbox() {
        return getTarget().getBoundingBox().inflate(1.0D, 0.25D, 1.0D);
    }

    @Override
    public EntityDimensions getDimensions(Pose pPose) {
        return POSES.getOrDefault(pPose, super.getDimensions(pPose));
    }

    public void setCrouching(boolean crouching) {
        setPose(Pose.CROUCHING);
    }

    @Override
    public float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
        switch (pPose) {
            case SWIMMING:
            case FALL_FLYING:
            case SPIN_ATTACK:
                return 0.4F;
            case CROUCHING:
                return 1.27F;
            default:
                return 1.62F;
        }
    }

    public void setOnDeathAction(Runnable onDeathAction) {
        this.onDeathAction = onDeathAction;
    }

    @Override
    public boolean isDeadOrDying() {
        if (this.onDeathAction != null) {
            this.onDeathAction.run();
            onDeathAction = null;
        }
        return super.isDeadOrDying();
    }
}
