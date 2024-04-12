package com.noodlegamer76.fracture.entity.block;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.block.InitBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, FractureMod.MODID);

    public static final RegistryObject<BlockEntityType<FleshyGroupSpawnerEntity>> FLESHY_GROUP_SPAWNER = BLOCK_ENTITIES.register("fleshy_group_spawner",
            () -> BlockEntityType.Builder.of(FleshyGroupSpawnerEntity::new, InitBlocks.FLESHY_GROUP_SPAWNER.get()).build(null));
}
