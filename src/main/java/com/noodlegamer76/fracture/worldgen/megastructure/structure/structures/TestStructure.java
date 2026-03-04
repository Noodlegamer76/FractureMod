package com.noodlegamer76.fracture.worldgen.megastructure.structure.structures;

import com.noodlegamer76.fracture.worldgen.megastructure.Node;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.Structure;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.StructureInstance;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.placers.StructurePlacer;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.AnchorPoint;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVar;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.List;

public class TestStructure extends Structure {
    private final ResourceLocation structureTemplate;

    public TestStructure(int priority, ResourceLocation structureTemplate) {
        super(priority);
        this.structureTemplate = structureTemplate;
    }

    @Override
    public int getMaxSize() {
        return 31;
    }

    @Override
    public List<GenVar<?>> getGenVariables() {
        return List.of();
    }

    @Override
    public void generate(FeaturePlaceContext<NoneFeatureConfiguration> ctx, Node n, RandomSource random, StructureInstance instance) {
        var server = ctx.level().getServer();
        if (server == null) return;

        StructureTemplate template = server
                .getStructureManager()
                .get(structureTemplate)
                .orElse(null);

        if (template == null) return;

        int originX = random.nextIntBetweenInclusive(n.getX(), n.getX() + n.getSize());
        int originY = 100;
        int originZ = random.nextIntBetweenInclusive(n.getZ(), n.getZ() + n.getSize());

        StructurePlaceSettings settings = new StructurePlaceSettings();

        StructurePlacer placer = new StructurePlacer(new BlockPos(originX, originY, originZ), template, settings, AnchorPoint.CORNER);
        instance.addPlacer(placer);
    }
}
