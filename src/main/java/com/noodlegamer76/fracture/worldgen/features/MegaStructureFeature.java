package com.noodlegamer76.fracture.worldgen.features;

import com.mojang.serialization.Codec;
import com.noodlegamer76.fracture.worldgen.megastructure.MegaStructureGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.jetbrains.annotations.NotNull;

public class MegaStructureFeature extends Feature<NoneFeatureConfiguration> {
    public MegaStructureFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(@NotNull FeaturePlaceContext<NoneFeatureConfiguration> context) {
        MegaStructureGenerator.generate(context);
        return true;
    }
}
