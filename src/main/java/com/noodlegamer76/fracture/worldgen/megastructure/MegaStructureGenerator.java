package com.noodlegamer76.fracture.worldgen.megastructure;

import com.noodlegamer76.fracture.worldgen.megastructure.structure.StructureDefinition;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.StructureInstance;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.Structures;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.access.FeatureWorldAccess;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.access.WorldAccess;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVarCache;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class MegaStructureGenerator {
    private static StructureInstance lastInstance;

    public static void generate(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        for (StructureDefinition def : Structures.getInstance().getStructures()) {
            generateInstance(ctx, def);
            GenVarCache.instance().removePendingRemoval();
        }
    }

    private static void generateInstance(FeaturePlaceContext<NoneFeatureConfiguration> ctx, StructureDefinition definition) {
        StructureInstance instance = new StructureInstance(definition);
        WorldAccess access = new FeatureWorldAccess(ctx);
        if (!definition.getSpawnCondition().shouldGenerate(access, definition)) return;
        instance.generate(access);
        lastInstance = instance;
    }

    public static StructureInstance getLastInstance() {
        return lastInstance;
    }
}
