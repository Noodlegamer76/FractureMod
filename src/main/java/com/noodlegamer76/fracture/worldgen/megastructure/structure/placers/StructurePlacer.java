package com.noodlegamer76.fracture.worldgen.megastructure.structure.placers;

import com.noodlegamer76.fracture.worldgen.megastructure.structure.StructureInstance;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.AnchorPoint;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.StructureUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class StructurePlacer extends Placer {
    private final StructureTemplate structureTemplate;
    private final BlockPos structureOrigin;
    private final StructurePlaceSettings settings;
    private final AnchorPoint anchorPoint;

    public StructurePlacer(BlockPos structureOrigin, StructureTemplate structureTemplate, StructurePlaceSettings settings, AnchorPoint anchorPoint) {
        super(StructureUtils.calculateBoundingBox(structureTemplate, structureOrigin, anchorPoint, settings));
        this.structureTemplate = structureTemplate;
        this.structureOrigin = structureOrigin;
        this.settings = settings;
        this.anchorPoint = anchorPoint;
    }

    public StructureTemplate getStructureTemplate() {
        return structureTemplate;
    }

    public BlockPos getStructureOrigin() {
        return structureOrigin;
    }

    public StructurePlaceSettings getSettings() {
        return settings;
    }

    public AnchorPoint getAnchorPoint() {
        return anchorPoint;
    }

    @Override
    public void place(FeaturePlaceContext<NoneFeatureConfiguration> ctx,
                      RandomSource random,
                      StructureInstance instance) {
        BlockPos chunkMin = ctx.origin();
        BlockPos chunkMax = chunkMin.offset(15, ctx.level().getMaxBuildHeight() - 1, 15);

        StructureUtils.placeTemplateInChunk(ctx.level(), structureTemplate, structureOrigin, getAnchorPoint(), chunkMin, chunkMax, settings, random, 2);
    }
}
