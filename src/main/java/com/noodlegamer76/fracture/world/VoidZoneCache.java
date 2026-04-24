package com.noodlegamer76.fracture.world;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;

public class VoidZoneCache {
    public static VoidZoneSavedData DATA;
    public static Holder<Biome> BIOME;

    public static void load(ServerLevel overworld) {
        DATA = VoidZoneSavedData.get(overworld);

        ResourceLocation id = ResourceLocation.withDefaultNamespace("the_void");

        BIOME = overworld.registryAccess()
                .registryOrThrow(Registries.BIOME)
                .getHolderOrThrow(ResourceKey.create(Registries.BIOME, id));
    }

    public static void clear() {
        DATA = null;
    }
}