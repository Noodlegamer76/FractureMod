package com.noodlegamer76.fracture.worldgen.megastructure.structure.placers;

import com.noodlegamer76.fracture.worldgen.megastructure.structure.StructureInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.phys.AABB;

public class SurfaceBlockPlacer extends BlockPlacer {
    private final Heightmap.Types heightmapType;

    public SurfaceBlockPlacer(AABB boundingBox, BlockState state, Heightmap.Types heightmapType, BlockPlacerCondition condition) {
        super(boundingBox, state, condition);
        this.heightmapType = heightmapType;
    }

    public Heightmap.Types getHeightmapType() {
        return heightmapType;
    }

    @Override
    public void place(FeaturePlaceContext<NoneFeatureConfiguration> ctx, RandomSource random, StructureInstance instance) {
        int chunkMinX = ctx.origin().getX();
        int chunkMaxX = chunkMinX + 16;
        int chunkMinZ = ctx.origin().getZ();
        int chunkMaxZ = chunkMinZ + 16;

        int minX = Math.max(Mth.floor(getBoundingBox().minX), chunkMinX);
        int maxX = Math.min(Mth.ceil(getBoundingBox().maxX), chunkMaxX);
        int minZ = Math.max(Mth.floor(getBoundingBox().minZ), chunkMinZ);
        int maxZ = Math.min(Mth.ceil(getBoundingBox().maxZ), chunkMaxZ);

        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        for (int x = minX; x < maxX; x++) {
            for (int z = minZ; z < maxZ; z++) {
                int y = ctx.level().getHeight(getHeightmapType(), x, z);
                pos.set(x, y - 1, z);
                if (getCondition().shouldPlace(pos, ctx, random, instance)) {
                    ctx.level().setBlock(pos, getBlockState(), 2);
                }
            }
        }
    }

}
