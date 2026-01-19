package com.noodlegamer76.fracture.worldgen.features;

import com.mojang.serialization.Codec;
import com.noodlegamer76.fracture.block.InitBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.phys.Vec3;

public class GiantCrystal extends Feature<NoneFeatureConfiguration> {
    public GiantCrystal(Codec<NoneFeatureConfiguration> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        RandomSource random = context.random();
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();

        for (int i = 0; i < 6; i++) {
            BlockPos.MutableBlockPos current = origin.mutable();

            Vec3 direction = new Vec3(
                    random.nextDouble() - 0.5,
                    random.nextDouble() - 0.2,
                    random.nextDouble() - 0.5
            ).normalize();

            for (int j = 0; j < 100; j++) {
                double offsetX = direction.x * j;
                double offsetY = direction.y * j;
                double offsetZ = direction.z * j;

                double clampedX = Math.min(Math.max(offsetX, -16), 16);
                double clampedZ = Math.min(Math.max(offsetZ, -16), 16);

                current.set(
                        origin.getX() + Math.round(clampedX),
                        origin.getY() + Math.round(offsetY),
                        origin.getZ() + Math.round(clampedZ)
                );

                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        for (int dz = -1; dz <= 1; dz++) {
                            if (random.nextFloat() > 0.35) {
                                BlockPos thickPos = current.offset(dx, dy, dz);
                                level.setBlock(thickPos, InitBlocks.ICE_CRYSTAL_BLOCK.get().defaultBlockState(), 2);
                            }
                            if (random.nextFloat() > 0.98) {
                                BlockPos thickPos = current.offset(dx, dy, dz);
                                level.setBlock(thickPos, InitBlocks.RADIANT_ICE.get().defaultBlockState(), 2);
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

}
