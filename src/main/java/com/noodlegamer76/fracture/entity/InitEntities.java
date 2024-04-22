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
                    .build("flesh_rattler"));
}
