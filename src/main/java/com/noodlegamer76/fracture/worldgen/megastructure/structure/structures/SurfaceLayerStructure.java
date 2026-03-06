package com.noodlegamer76.fracture.worldgen.megastructure.structure.structures;

import com.noodlegamer76.fracture.worldgen.megastructure.Node;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.Structure;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.StructureInstance;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.access.WorldAccess;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.placers.SurfaceBlockPlacer;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.StructureUtils;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVar;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class SurfaceLayerStructure extends Structure {
    private final int xSize;
    private final int zSize;
    private final BlockState state;
    private final Heightmap.Types heightmapType;
    private final SurfaceBlockPlacer.SurfaceBlockPlacerCondition condition;

    public SurfaceLayerStructure(int priority, int xSize, int zSize, BlockState state, Heightmap.Types heightmapType, SurfaceBlockPlacer.SurfaceBlockPlacerCondition condition) {
        super(priority);
        this.xSize = xSize;
        this.zSize = zSize;
        this.state = state;
        this.condition = condition;
        this.heightmapType = heightmapType;
    }

    @Override
    public int getMaxSize() {
        return Math.max(xSize, zSize);
    }

    @Override
    public void generate(WorldAccess access, Node n, RandomSource random, StructureInstance instance) {
        int originX = n.getX();
        int originZ = n.getZ();

        AABB boundingBox = new AABB(
                originX - (double) xSize / 2,
                access.getMinBuildHeight(),
                originZ - (double) zSize / 2,
                originX + (double) xSize / 2,
                access.getMaxBuildHeight(),
                originZ + (double) zSize / 2
        );

        boundingBox = StructureUtils.getChunkIntersection(access, boundingBox);

        SurfaceBlockPlacer placer = new SurfaceBlockPlacer(boundingBox, state, heightmapType, condition);
        instance.addPlacer(placer);
    }

    @Override
    public List<GenVar<?>> getGenVariables() {
        return List.of();
    }
}
