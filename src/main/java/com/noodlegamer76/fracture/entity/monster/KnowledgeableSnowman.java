package com.noodlegamer76.fracture.entity.monster;

import com.noodlegamer76.fracture.client.renderers.entity.MultiAttackMonster;
import com.noodlegamer76.fracture.entity.ai.behavior.KnowledgeableSnowmanSpellCast;
import com.noodlegamer76.fracture.item.InitItems;
import com.noodlegamer76.fracture.spellcrafting.CreateWand;
import com.noodlegamer76.fracture.spellcrafting.wand.Wand;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
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
import org.jetbrains.annotations.Nullable;
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
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        setItemInHand(InteractionHand.MAIN_HAND, InitItems.WAND.get().getDefaultInstance());
        if (getMainHandItem().getItem() instanceof Wand) {
            CompoundTag nbt = getMainHandItem().getOrCreateTag();
            if (!nbt.contains("isCreated")) {
                new CreateWand().createStats(nbt, getMainHandItem());
            }
        }
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    protected float getEquipmentDropChance(EquipmentSlot pSlot) {
        return 0.0f;
    }

    @Override
    public void setAttackNumber() {
        if (!resettingSpells) {
            return;
        }
        resettingSpells = false;
        attackNumber = random.nextInt(1, 3);
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
                new KnowledgeableSnowmanSpellCast<>()
        );
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "casting", this::casting));
    }

    private PlayState casting(AnimationState<KnowledgeableSnowman> state) {
        if (entityData.get(DATA_ATTACK) == 0) {
            state.resetCurrentAnimation();
            return PlayState.STOP;
        }
        state.setAnimation(CASTING);
        return PlayState.CONTINUE;
    }

    @Override
    public void tick() {
        super.tick();

        ItemStack wand = getMainHandItem();
        if (getMainHandItem().getItem() instanceof Wand) {
            CompoundTag nbt = getMainHandItem().getOrCreateTag();
                if (wand.getOrCreateTag().getBoolean("isCreated")) {
                    float currentMana = wand.getTag().getFloat("currentMana");
                    float manaRechargeSpeed = wand.getTag().getFloat("manaRechargeSpeed");
                    float maxMana = wand.getTag().getFloat("maxMana");
                    if (currentMana + manaRechargeSpeed >= maxMana) {
                        wand.getTag().putFloat("currentMana", maxMana);
                    }
                    else {
                        wand.getTag().putFloat("currentMana", currentMana + manaRechargeSpeed);
                    }
                    float currentCastDelay = wand.getTag().getFloat("currentCastDelay");
                    if (currentCastDelay > 0) {
                        wand.getTag().putFloat("currentCastDelay", currentCastDelay - 1);
                    }
            }
        }
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animatableInstanceCache;
    }
}
