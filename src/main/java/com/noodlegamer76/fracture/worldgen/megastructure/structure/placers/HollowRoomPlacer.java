package com.noodlegamer76.fracture.worldgen.megastructure.structure.placers;

import com.noodlegamer76.fracture.worldgen.megastructure.structure.StructureInstance;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.AnchorPoint;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.StructureUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.AABB;

public class HollowRoomPlacer extends Placer {
    private final BlockState state;

    public HollowRoomPlacer(AABB boundingBox, BlockState state) {
        super(boundingBox);
        this.state = state;
    }

    @Override
    public void place(FeaturePlaceContext<NoneFeatureConfiguration> ctx, RandomSource random, StructureInstance instance) {
        int minX = Mth.floor(getBoundingBox().minX);
        int maxX = Mth.ceil(getBoundingBox().maxX);
        int minY = Mth.floor(getBoundingBox().minY);
        int maxY = Mth.ceil(getBoundingBox().maxY);
        int minZ = Mth.floor(getBoundingBox().minZ);
        int maxZ = Mth.ceil(getBoundingBox().maxZ);

        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        ChunkPos chunkPos = new ChunkPos(ctx.origin());
        int minBuildHeight = ctx.level().getMinBuildHeight();
        int maxBuildHeight = ctx.level().getMaxBuildHeight();

        AABB chunkBoundingBox = new AABB(
                chunkPos.getMinBlockX(),
                minBuildHeight,
                chunkPos.getMinBlockZ(),
                chunkPos.getMaxBlockX() + 1,
                maxBuildHeight,
                chunkPos.getMaxBlockZ() + 1
        );

        int startX = Math.max(minX, Mth.floor(chunkBoundingBox.minX));
        int endX = Math.min(maxX, Mth.ceil(chunkBoundingBox.maxX));

        int startY = Math.max(minY, Mth.floor(chunkBoundingBox.minY));
        int endY = Math.min(maxY, Mth.ceil(chunkBoundingBox.maxY));

        int startZ = Math.max(minZ, Mth.floor(chunkBoundingBox.minZ));
        int endZ = Math.min(maxZ, Mth.ceil(chunkBoundingBox.maxZ));

        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                for (int z = startZ; z <= endZ; z++) {

                    boolean isEdgeX = (x == minX || x == maxX);
                    boolean isEdgeY = (y == minY || y == maxY);
                    boolean isEdgeZ = (z == minZ || z == maxZ);

                    mutablePos.set(x, y, z);

                    if (isEdgeX || isEdgeY || isEdgeZ) {
                        ctx.level().setBlock(mutablePos, this.state, 50);
                    } else {
                        ctx.level().setBlock(mutablePos, Blocks.AIR.defaultBlockState(), 50);
                    }
                }
            }
        }
    }
}