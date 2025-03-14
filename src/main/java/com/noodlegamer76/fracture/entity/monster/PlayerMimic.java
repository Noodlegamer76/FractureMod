package com.noodlegamer76.fracture.entity.monster;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.util.UUIDTypeAdapter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
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
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.TargetOrRetaliate;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;

import java.util.List;
import java.util.Map;

public class PlayerMimic extends Monster implements SmartBrainOwner<PlayerMimic> {
    private static final String SKIN_URL_TEMPLATE = "http://skins.minecraft.net/MinecraftSkins/%s.png";
    protected Vec3 deltaMovementOnPreviousTick = Vec3.ZERO;

    public PlayerMimic(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public void tick() {
        this.deltaMovementOnPreviousTick = this.getDeltaMovement();
        super.tick();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.ATTACK_DAMAGE, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.36F)
                .add(Attributes.ATTACK_SPEED)
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.LUCK)
                .add(net.minecraftforge.common.ForgeMod.BLOCK_REACH.get())
                .add(Attributes.ATTACK_KNOCKBACK)
                .add(net.minecraftforge.common.ForgeMod.ENTITY_REACH.get())
                .add(Attributes.FOLLOW_RANGE, 16.0D);
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
                new MoveToWalkTarget<>()
        );
    }

    @Override
    public BrainActivityGroup<? extends PlayerMimic> getFightTasks() {
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>().stopTryingToPathAfter(10),
                new SetWalkTargetToAttackTarget<>(),
                new AnimatableMeleeAttack<>(0)
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

}
