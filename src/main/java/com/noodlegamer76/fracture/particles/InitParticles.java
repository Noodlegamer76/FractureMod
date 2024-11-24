package com.noodlegamer76.fracture.particles;

import com.noodlegamer76.fracture.FractureMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class InitParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(Registries.PARTICLE_TYPE, FractureMod.MODID);

    public static final RegistryObject<SimpleParticleType> BLOOD_PARTICLES =
            PARTICLE_TYPES.register("blood", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> VOID_PARTICLES =
            PARTICLE_TYPES.register("void", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> CONFETTI_PARTICLES =
            PARTICLE_TYPES.register("confetti", () -> new SimpleParticleType(true));
}
