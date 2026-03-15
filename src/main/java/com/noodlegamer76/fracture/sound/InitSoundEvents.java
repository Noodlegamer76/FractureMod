package com.noodlegamer76.fracture.sound;

import com.noodlegamer76.fracture.FractureMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, FractureMod.MODID);

    public static final RegistryObject<SoundEvent> FLESH_OBELISK_LASER_MIDDLE =
            SOUND_EVENTS.register("entity.flesh_obelisk.laser_middle",
                    () -> SoundEvent.createVariableRangeEvent(
                            ResourceLocation.fromNamespaceAndPath(FractureMod.MODID, "entity.flesh_obelisk.laser_middle")));

    public static final RegistryObject<SoundEvent> FLESH_OBELISK_LASER_START =
            SOUND_EVENTS.register("entity.flesh_obelisk.laser_start",
                    () -> SoundEvent.createVariableRangeEvent(
                            ResourceLocation.fromNamespaceAndPath(FractureMod.MODID, "entity.flesh_obelisk.laser_start")));
}
