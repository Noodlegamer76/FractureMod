package com.noodlegamer76.fracture.event;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.particles.BloodParticle;
import com.noodlegamer76.fracture.particles.ConfettiParticle;
import com.noodlegamer76.fracture.particles.InitParticles;
import com.noodlegamer76.fracture.particles.VoidParticle;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FractureMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ParticleProviders {

    @SubscribeEvent
    public static void RegisterParticleProviders(RegisterParticleProvidersEvent event) {
        Minecraft.getInstance().particleEngine.register(InitParticles.BLOOD_PARTICLES.get(),
                BloodParticle.Provider::new);

        Minecraft.getInstance().particleEngine.register(InitParticles.VOID_PARTICLES.get(),
                VoidParticle.Provider::new);

        Minecraft.getInstance().particleEngine.register(InitParticles.CONFETTI_PARTICLES.get(),
                ConfettiParticle.Provider::new);
    }
}
