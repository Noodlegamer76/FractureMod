package com.noodlegamer76.fracture.worldgen.megastructure.structure.placers;

import com.noodlegamer76.fracture.worldgen.megastructure.structure.StructureInstance;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.AnchorPoint;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.StructureUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class StackedStructurePlacer extends StructurePlacer {
    private final int stackCount;
    private final Direction stackDirection;

    public StackedStructurePlacer(BlockPos structureOrigin,
                                  StructureTemplate template,
                                  StructurePlaceSettings settings,
                                  AnchorPoint anchorPoint,
                                  int stackCount,
                                  Direction stackDirection) {
        super(structureOrigin, template, settings, anchorPoint);
        this.stackCount = stackCount;
        this.stackDirection = stackDirection;
    }

    @Override
    public void place(FeaturePlaceContext<NoneFeatureConfiguration> ctx,
                      RandomSource random,
                      StructureInstance instance) {

        BlockPos chunkMin = ctx.origin();
        BlockPos chunkMax = chunkMin.offset(15, ctx.level().getMaxBuildHeight() - 1, 15);

        StructureTemplate template = getStructureTemplate();

        BoundingBox localBox = template.getBoundingBox(getSettings(), BlockPos.ZERO);
        int sizeX = localBox.getXSpan();
        int sizeY = localBox.getYSpan();
        int sizeZ = localBox.getZSpan();

        for (int i = 0; i < stackCount; i++) {

            BlockPos offset = StructureUtils.getDirectionalOffset(stackDirection, sizeX, sizeY, sizeZ).multiply(i);
            BlockPos placementPos = getStructureOrigin().offset(offset);

            StructureUtils.placeTemplateInChunk(
                    ctx.level(),
                    template,
                    placementPos,
                    getAnchorPoint(),
                    chunkMin,
                    chunkMax,
                    getSettings(),
                    random,
                    2
            );
        }
    }
}
