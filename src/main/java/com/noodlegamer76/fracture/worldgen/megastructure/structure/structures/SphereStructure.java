package com.noodlegamer76.fracture.worldgen.megastructure.structure.structures;

import com.noodlegamer76.fracture.worldgen.megastructure.Node;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.Structure;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.StructureInstance;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVar;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.joml.Vector3f;

import java.util.List;

public class SphereStructure extends Structure {
    public SphereStructure(int priority) {
        super(priority);
    }

    @Override
    public int getMaxSize() {
        return 256;
    }


    @Override
    public void generate(FeaturePlaceContext<NoneFeatureConfiguration> ctx, Node n, RandomSource random, StructureInstance instance) {
        int x = random.nextIntBetweenInclusive(n.getX(), n.getX() + n.getSize());
        int y = 100;
        int z = random.nextIntBetweenInclusive(n.getZ(), n.getZ() + n.getSize());
        Vector3f center = new Vector3f(x, y, z);

        int width  = getMaxSize() / 2;
        int radiusSquared = width * width;

        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        BlockState state = Blocks.SMOOTH_STONE.defaultBlockState();
        BlockState state2 = Blocks.DIAMOND_BLOCK.defaultBlockState();

        int chunkMinX = ctx.origin().getX();
        int chunkMaxX = chunkMinX + 16;
        int chunkMinZ = ctx.origin().getZ();
        int chunkMaxZ = chunkMinZ + 16;

        int minX = Math.max((int) Math.floor(center.x - width), chunkMinX);
        int maxX = Math.min((int) Math.ceil(center.x + width), chunkMaxX);
        int minZ = Math.max((int) Math.floor(center.z - width), chunkMinZ);
        int maxZ = Math.min((int) Math.ceil(center.z + width), chunkMaxZ);

        int minY = ctx.level().getMinBuildHeight();
        int maxY = ctx.level().getMaxBuildHeight();

        for (int xi = minX; xi < maxX; xi++) {
            int dx = xi - (int) center.x;
            for (int yi = minY; yi < maxY; yi++) {
                int dy = yi - (int) center.y;
                for (int zi = minZ; zi < maxZ; zi++) {
                    int dz = zi - (int) center.z;
                    if (dx * dx + dy * dy + dz * dz < radiusSquared) {
                        pos.set(xi, yi, zi);
                        ctx.level().setBlock(pos, state, 2);
                    }
                    //Vector3f p = new Vector3f(
                    //        (xi - center.x) / scale,
                    //        (yi - center.y) / scale,
                    //        (zi - center.z) / scale
                    //);
                    //if (mandelbulbSDF(p) < 0) {

                    //    pos.set(xi, yi, zi);
                    //    if (random.nextBoolean()) {
                    //        ctx.level().setBlock(pos, state, 2);
                    //    }
                    //    else {
                    //        ctx.level().setBlock(pos, state2, 2);
                    //    }
                    //}
                }
            }
        }
    }

    @Override
    public List<GenVar<?>> getGenVariables() {
        return List.of();
    }

    public static float mandelbulbSDF(Vector3f p) {
        Vector3f z = new Vector3f(p);
        float dr = 1.0f;
        float r = 0.0f;

        final int ITERATIONS = 8;
        final float POWER = 8.0f;

        for (int i = 0; i < ITERATIONS; i++) {
            r = z.length();
            if (r > 2.0f) break;

            // convert to polar coordinates
            float theta = (float) Math.acos(z.y / r);
            float phi = (float) Math.atan2(z.z, z.x);
            dr = (float) (Math.pow(r, POWER - 1.0) * POWER * dr + 1.0);

            // scale and rotate
            float zr = (float) Math.pow(r, POWER);
            theta *= POWER;
            phi *= POWER;

            // convert back to cartesian
            z.set(
                    zr * (float) Math.sin(theta) * (float) Math.cos(phi),
                    zr * (float) Math.cos(theta),
                    zr * (float) Math.sin(theta) * (float) Math.sin(phi)
            );
            z.add(p);
        }

        return 0.5f * (float) Math.log(r) * r / dr;
    }
}
