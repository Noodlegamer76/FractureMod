package com.noodlegamer76.fracture.fluid;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.block.InitBlocks;
import com.noodlegamer76.fracture.item.InitItems;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
        DeferredRegister.create(ForgeRegistries.FLUIDS, FractureMod.MODID);

    public static final RegistryObject<FlowingFluid> SOURCE_BLOOD = FLUIDS.register("source_blood",
            () -> new ForgeFlowingFluid.Source(InitFluids.SOAP_WATER_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_BLOOD = FLUIDS.register("flowing_blood",
            () -> new ForgeFlowingFluid.Flowing(InitFluids.SOAP_WATER_FLUID_PROPERTIES));


    public static final ForgeFlowingFluid.Properties SOAP_WATER_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            InitFluidTypes.BLOOD_FLUID, SOURCE_BLOOD, FLOWING_BLOOD)
            .slopeFindDistance(2).levelDecreasePerBlock(1).block(InitBlocks.BLOOD_BLOCK)
            .bucket(InitItems.BLOOD_BUCKET);
}
