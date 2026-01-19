package com.noodlegamer76.fracture.fluid;

import com.noodlegamer76.fracture.FractureMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.joml.Vector3f;

import java.awt.*;

public class InitFluidTypes {
    public static final ResourceLocation BLOOD_STILL = ResourceLocation.withDefaultNamespace("block/water_still");
    public static final ResourceLocation BLOOD_FLOWING = ResourceLocation.withDefaultNamespace("block/water_flow");
    public static final ResourceLocation BLOOD_OVERLAY = ResourceLocation.fromNamespaceAndPath(FractureMod.MODID, "misc/in_soap_water");

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, FractureMod.MODID);

    public static final RegistryObject<FluidType> BLOOD_FLUID = register("blood_fluid",
            FluidType.Properties.create().density(15).viscosity(5));

    private static RegistryObject<FluidType> register(String name, FluidType.Properties properties) {
        return FLUID_TYPES.register(name, () -> new BloodFluidType(BLOOD_STILL, BLOOD_FLOWING, BLOOD_OVERLAY,
                Color.RED.getRGB(), new Vector3f(1.0F, 0.0F, 0.0F), properties));
    }
}
