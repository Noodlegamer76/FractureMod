package com.noodlegamer76.fracture.entity;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.entity.animal.Moosicle;
import com.noodlegamer76.fracture.entity.misc.CompactPrimedTnt;
import com.noodlegamer76.fracture.entity.monster.*;
import com.noodlegamer76.fracture.entity.projectile.*;
import com.noodlegamer76.fracture.entity.vehicle.ModBoatEntity;
import com.noodlegamer76.fracture.entity.vehicle.ModChestBoatEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, FractureMod.MODID);

    public static final RegistryObject<EntityType<FleshRattlerEntity>> FLESH_RATTLER = ENTITIES.register("flesh_rattler",
            () -> EntityType.Builder.of(FleshRattlerEntity::new, MobCategory.MONSTER)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(8)
                    .build("flesh_rattler"));

    public static final RegistryObject<EntityType<AnkleBiterEntity>> ANKLE_BITER = ENTITIES.register("ankle_biter",
            () -> EntityType.Builder.of(AnkleBiterEntity::new, MobCategory.CREATURE)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(8)
                    .build("ankle_biter"));

    public static final RegistryObject<EntityType<FleshWalkerEntity>> FLESH_WALKER = ENTITIES.register("flesh_walker",
            () -> EntityType.Builder.of(FleshWalkerEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .clientTrackingRange(8)
                    .build("flesh_walker"));

    public static final RegistryObject<EntityType<FleshSlimeEntity>> FLESH_SLIME = ENTITIES.register("flesh_slime",
            () -> EntityType.Builder.of(FleshSlimeEntity::new, MobCategory.MONSTER)
                    .sized(2.04F, 2.04F)
                    .clientTrackingRange(10)
                    .build("flesh_slime"));

    public static final RegistryObject<EntityType<AbdominalSnowman>> ABDOMINAL_SNOWMAN = ENTITIES.register("abdominal_snowman",
            () -> EntityType.Builder.of(AbdominalSnowman::new, MobCategory.MONSTER)
                    .sized(1.5F, 2.3F)
                    .build("abdominal_snowman"));

    public static final RegistryObject<EntityType<KnowledgeableSnowman>> KNOWLEDGEABLE_SNOWMAN = ENTITIES.register("knowledgeable_snowman",
            () -> EntityType.Builder.of(KnowledgeableSnowman::new, MobCategory.MONSTER)
                    .sized(0.7F, 1.9F)
                    .build("knowledgeable_snowman"));

    public static final RegistryObject<EntityType<ComparableSnowman>> COMPARABLE_SNOWMAN = ENTITIES.register("comparable_snowman",
            () -> EntityType.Builder.of(ComparableSnowman::new, MobCategory.MONSTER)
                    .sized(0.7F, 1.9F)
                    .build("comparable_snowman"));

    public static final RegistryObject<EntityType<BloodSlimeEntity>> BLOOD_SLIME = ENTITIES.register("blood_slime",
            () -> EntityType.Builder.of(BloodSlimeEntity::new, MobCategory.MONSTER)
                    .sized(2.99F, 2.99F)
                    .clientTrackingRange(10)
                    .build("blood_slime"));

    public static final RegistryObject<EntityType<PlayerMimic>> PLAYER_MIMIC = ENTITIES.register("player_mimic",
            () -> EntityType.Builder.of(PlayerMimic::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.8F)
                    .clientTrackingRange(32)
                    .updateInterval(2)
                    .build("blood_slime"));


    public static final RegistryObject<EntityType<ModBoatEntity>> MOD_BOAT = ENTITIES.register(("mod_boat"),
            () -> EntityType.Builder.<ModBoatEntity>of(ModBoatEntity::new, MobCategory.MISC)
                    .sized(1.375f, 0.5625f)
                    .build("mod_boat"));

    public static final RegistryObject<EntityType<ModChestBoatEntity>> MOD_CHEST_BOAT = ENTITIES.register(("mod_chest_boat"),
            () -> EntityType.Builder.<ModChestBoatEntity>of(ModChestBoatEntity::new, MobCategory.MISC)
                    .sized(1.375f, 0.5625f)
                    .build("mod_chest_boat"));

    public static final RegistryObject<EntityType<CompactPrimedTnt>> COMPACT_TNT = ENTITIES.register(("compact_tnt"),
            () -> EntityType.Builder.<CompactPrimedTnt>of(CompactPrimedTnt::new, MobCategory.MISC)
                    .sized(1F, 1F)
                    .build("compact_tnt"));

    public static final RegistryObject<EntityType<VoidBall>> VOID_BALL = ENTITIES.register(("void_ball"),
            () -> EntityType.Builder.<VoidBall>of(VoidBall::new, MobCategory.MISC)
                    .sized(0.375f, 0.375f)
                    .build("void_ball"));

    public static final RegistryObject<EntityType<GiantSnowballProjectile>> GIANT_SLOWBALL = ENTITIES.register(("giant_snowball"),
            () -> EntityType.Builder.<GiantSnowballProjectile>of(GiantSnowballProjectile::new, MobCategory.MISC)
                    .sized(1f, 1f)
                    .build("giant_snowball"));

    public static final RegistryObject<EntityType<MagicBoltProjectile>> MAGIC_BOLT = ENTITIES.register(("magic_bolt"),
            () -> EntityType.Builder.<MagicBoltProjectile>of(MagicBoltProjectile::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .build("magic_bolt"));

    public static final RegistryObject<EntityType<IceSpikeEntity>> ICE_SPIKE = ENTITIES.register(("ice_spike"),
            () -> EntityType.Builder.<IceSpikeEntity>of(IceSpikeEntity::new, MobCategory.MISC)
                    .sized(0.8f, 0.8f)
                    .build("ice_spike"));

    public static final RegistryObject<EntityType<Moosicle>> MOOSICLE = ENTITIES.register(("moosicle"),
            () -> EntityType.Builder.<Moosicle>of(Moosicle::new, MobCategory.CREATURE)
                    .sized(0.9F, 1.4F)
                    .clientTrackingRange(10)
                    .build("moosicle"));

    public static final RegistryObject<EntityType<GiantRobot>> GIANT_ROBOT = ENTITIES.register(("giant_robot"),
            () -> EntityType.Builder.<GiantRobot>of(GiantRobot::new, MobCategory.MONSTER)
                    .sized(1.25F, 3F)
                    .build("giant_robot"));

    public static final RegistryObject<EntityType<ExplosiveBox>> EXPLOSIVE_BOX = ENTITIES.register(("explosive_box"),
            () -> EntityType.Builder.of(ExplosiveBox::new, MobCategory.MISC)
                    .sized(1f, 1f)
                    .build("explosive_box"));
}
