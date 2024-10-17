package com.noodlegamer76.fracture.entity;

import com.noodlegamer76.fracture.FractureMod;
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

    public static final RegistryObject<EntityType<BloodSlimeEntity>> BLOOD_SLIME = ENTITIES.register("blood_slime",
            () -> EntityType.Builder.of(BloodSlimeEntity::new, MobCategory.MONSTER)
                    .sized(3F, 3F)
                    .clientTrackingRange(10)
                    .build("blood_slime"));
}
