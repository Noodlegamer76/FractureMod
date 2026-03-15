package com.noodlegamer76.fracture.entity.monster.boss;

import com.noodlegamer76.fracture.entity.ai.behavior.ShootLaserBeamAtAttackTarget;
import com.noodlegamer76.fracture.entity.ai.behavior.SpawnObeliskLasersAroundTargetOnGround;
import com.noodlegamer76.fracture.entity.ai.behavior.fleshobelisk.FleshObeliskSetActiveAttackTarget;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.AllApplicableBehaviours;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.TargetOrRetaliate;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class FleshObelisk extends Monster implements GeoEntity, SmartBrainOwner<FleshObelisk> {
    private static final EntityDataAccessor<Integer> DATA_ID_ATTACK =
            SynchedEntityData.defineId(FleshObelisk.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_ID_ATTACK_TIME =
            SynchedEntityData.defineId(FleshObelisk.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_ID_ATTACK_TARGET =
            SynchedEntityData.defineId(FleshObelisk.class, EntityDataSerializers.INT);
    AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final ServerBossEvent bossBar = new ServerBossEvent(
            Component.literal("Flesh Obelisk"),
            BossEvent.BossBarColor.PURPLE,
            BossEvent.BossBarOverlay.PROGRESS
    );
    private static final int TIME_TILL_REGERATION = 200;
    private int timeSinceLastHit = 0;

    public static final int LASER_BEAM_FREEZE_TIME = 25;
    public static final int LASER_BEAM_CHARGE_UP = 5;
    public static final int LASER_BEAM_FIRE_TIME = 50;
    public static final int LASER_BEAM_COOLDOWN = 20;

    public FleshObelisk(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_ATTACK, 0);
        this.entityData.define(DATA_ID_ATTACK_TARGET, 0);
        this.entityData.define(DATA_ID_ATTACK_TIME, 0);
    }

    @Override
    public List<? extends ExtendedSensor<? extends FleshObelisk>> getSensors() {
        return List.of(
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<FleshObelisk>()
                        .setPredicate((target, entity) ->
                                target instanceof Player)
        );
    }

    @Override
    public BrainActivityGroup<? extends FleshObelisk> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new FleshObeliskSetActiveAttackTarget<>()
        );
    }

    @Override
    public BrainActivityGroup<? extends FleshObelisk> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<>(
                        new TargetOrRetaliate<>()
                )
        );
    }

    @Override
    public BrainActivityGroup<? extends FleshObelisk> getFightTasks() {
        OneRandomBehaviour<FleshObelisk> randomAttack = new OneRandomBehaviour<>(
                new ShootLaserBeamAtAttackTarget<>(
                        LASER_BEAM_CHARGE_UP,
                        LASER_BEAM_FIRE_TIME,
                        LASER_BEAM_COOLDOWN,
                        LASER_BEAM_FREEZE_TIME,
                        (entity) -> entity.getHealth() > entity.getMaxHealth() * 0.5f
                )
                        .whenStarting(entity -> {
                            getEntityData().set(DATA_ID_ATTACK, 1);
                            getEntityData().set(DATA_ID_ATTACK_TIME, 0);
                        })
                        .whenStopping(entity -> entity.getEntityData().set(DATA_ID_ATTACK, 0))
                        .noTimeout(),
                new SpawnObeliskLasersAroundTargetOnGround<>(
                        LASER_BEAM_FREEZE_TIME / 2,
                        LASER_BEAM_CHARGE_UP / 2,
                        LASER_BEAM_FIRE_TIME / 2,
                        LASER_BEAM_COOLDOWN / 2,
                        15,
                        7,
                        50,
                        5,
                        (entity) -> entity.getHealth() > entity.getMaxHealth() * 0.5f
                )
                        .whenStopping(entity -> entity.getEntityData().set(DATA_ID_ATTACK, 0))
                        .noTimeout()
        );
        randomAttack.startCondition(entity -> entity.getHealth() > entity.getMaxHealth() * 0.5f);

        AllApplicableBehaviours<FleshObelisk> bothAttacks = new AllApplicableBehaviours<>(
                new ShootLaserBeamAtAttackTarget<>(
                        LASER_BEAM_CHARGE_UP,
                        LASER_BEAM_FIRE_TIME,
                        10,
                        LASER_BEAM_FREEZE_TIME,
                        (entity) -> entity.getHealth() > entity.getMaxHealth() * 0.5f
                )
                        .whenStarting(entity -> {
                            getEntityData().set(DATA_ID_ATTACK, 1);
                            getEntityData().set(DATA_ID_ATTACK_TIME, 0);
                        })
                        .whenStopping(entity -> getEntityData().set(DATA_ID_ATTACK, 0))
                        .noTimeout(),
                new SpawnObeliskLasersAroundTargetOnGround<>(
                        LASER_BEAM_FREEZE_TIME / 2,
                        LASER_BEAM_CHARGE_UP / 2,
                        LASER_BEAM_FIRE_TIME / 2,
                        10,
                        15,
                        7,
                        30,
                        5,
                        (entity) -> entity.getHealth() > entity.getMaxHealth() * 0.5f
                )
                        .whenStopping(entity -> entity.getEntityData().set(DATA_ID_ATTACK, 0))
                        .noTimeout()
        );
        bothAttacks.startCondition(entity -> entity.getHealth() <= entity.getMaxHealth() * 0.5f);

        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>().stopTryingToPathAfter(10),
                new SetWalkTargetToAttackTarget<>(),
                randomAttack,
                bothAttacks
        );
    }

    @Override
    protected void customServerAiStep() {
        tickBrain(this);

        if (entityData.get(DATA_ID_ATTACK) == 1) {
            int time = entityData.get(DATA_ID_ATTACK_TIME);
            entityData.set(DATA_ID_ATTACK_TIME, time + 1);
        } else {
            entityData.set(DATA_ID_ATTACK_TIME, -1);
        }

        if (timeSinceLastHit >= TIME_TILL_REGERATION) {
            boolean regen = timeSinceLastHit % 20 == 0;
            if (regen) {
                heal(1F);
            }
        }

        timeSinceLastHit++;
    }

    @Override
    protected void actuallyHurt(DamageSource pDamageSource, float pDamageAmount) {
        super.actuallyHurt(pDamageSource, pDamageAmount);
        timeSinceLastHit = 0;
    }

    @Override
    public void onRemovedFromWorld() {
        super.onRemovedFromWorld();

        bossBar.setVisible(false);
        bossBar.removeAllPlayers();
    }

    @Override
    public void tick() {
        super.tick();

        bossBar.setProgress(this.getHealth() / this.getMaxHealth());

        if (!level().isClientSide) {
            for (ServerPlayer player: ((ServerLevel) level()).players()) {

                double dist = player.distanceTo(this);

                if (dist <= 50) {
                    bossBar.addPlayer(player);
                } else {
                    bossBar.removePlayer(player);
                }
            }
        }
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public int getExperienceReward() {
        return 225;
    }

    @Override
    public boolean isPersistenceRequired() {
        return true;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0)
                .add(Attributes.ATTACK_DAMAGE, 10.0)
                .add(Attributes.MAX_HEALTH, 350.0)
                .add(Attributes.ARMOR, 6.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
                .add(Attributes.FOLLOW_RANGE, 30.0);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    public boolean hasActiveAttackTarget() {
        return this.entityData.get(DATA_ID_ATTACK_TARGET) != 0;
    }

    public void setActiveAttackTarget(int pActiveAttackTargetId) {
        this.entityData.set(DATA_ID_ATTACK_TARGET, pActiveAttackTargetId);
    }

    public int getClientSideAttackTime() {
        return entityData.get(DATA_ID_ATTACK_TIME);
    }

    public int getAttack() {
        return entityData.get(DATA_ID_ATTACK);
    }

    @Override
    public AABB getBoundingBoxForCulling() {
        return super.getBoundingBoxForCulling().inflate(0.4);
    }

    @Override
    public boolean canBeHitByProjectile() {
        return false;
    }
}
