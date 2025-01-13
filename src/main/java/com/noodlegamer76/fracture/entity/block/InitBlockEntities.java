package com.noodlegamer76.fracture.entity.block;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.block.FrostedIceCrystals;
import com.noodlegamer76.fracture.block.InitBlocks;
import com.noodlegamer76.fracture.block.ModificationStationBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, FractureMod.MODID);

    public static final RegistryObject<BlockEntityType<FogEmitterEntity>> FOG_EMITTER = BLOCK_ENTITIES.register("fog_emitter",
            () -> BlockEntityType.Builder.of(FogEmitterEntity::new, InitBlocks.FOG_EMITTER.get()).build(null));

    public static final RegistryObject<BlockEntityType<SkyboxGeneratorEntity>> SKYBOX_GENERATOR = BLOCK_ENTITIES.register("skybox_generator",
            () -> BlockEntityType.Builder.of(SkyboxGeneratorEntity::new, InitBlocks.SKYBOX_GENERATOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<ModHangingSignBlockEntity>> INKWOOK_HANGING_SIGN = BLOCK_ENTITIES.register("inkwood_hanging_sign",
            () -> BlockEntityType.Builder.of(ModHangingSignBlockEntity::new, InitBlocks.INKWOOD_WALL_HANGING_SIGN_BLOCK.get(), InitBlocks.INKWOOD_CEILING_HANGING_SIGN_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<ModSignBlockEntity>> INKWOOD_SIGN = BLOCK_ENTITIES.register("inkwood_sign",
            () -> BlockEntityType.Builder.of(ModSignBlockEntity::new, InitBlocks.INKWOOD_WALL_SIGN_BLOCK.get(), InitBlocks.INKWOOD_STANDING_SIGN_BLOCK.get()).build(null));


    public static final RegistryObject<BlockEntityType<SmokeStackEntity>> SMOKE_STACK = BLOCK_ENTITIES.register("smoke_stack",
            () -> BlockEntityType.Builder.of(SmokeStackEntity::new, InitBlocks.SMOKE_STACK.get()).build(null));


    public static final RegistryObject<BlockEntityType<VoidBlockEntity>> VOID_BLOCK_ENTITY = BLOCK_ENTITIES.register("void_block",
            () -> BlockEntityType.Builder.of(VoidBlockEntity::new, InitBlocks.VOID_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<FrostedIceCrystalsEntity>> FROSTED_ICE_CRYSTALS = BLOCK_ENTITIES.register("frosted_ice_crystals",
            () -> BlockEntityType.Builder.of(FrostedIceCrystalsEntity::new, InitBlocks.FROSTED_ICE_CRYSTALS.get()).build(null));

    public static final RegistryObject<BlockEntityType<ModificationStationEntity>> MODIFICATION_STATION = BLOCK_ENTITIES.register("modification_station",
            () -> BlockEntityType.Builder.of(ModificationStationEntity::new, InitBlocks.MODIFICATION_STATION.get()).build(null));

    public static final RegistryObject<BlockEntityType<BoreasPortalEntity>> BOREAS_PORTAL = BLOCK_ENTITIES.register("boreas_portal",
            () -> BlockEntityType.Builder.of(BoreasPortalEntity::new, InitBlocks.BOREAS_PORTAL.get()).build(null));

}
