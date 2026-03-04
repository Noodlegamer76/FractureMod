package com.noodlegamer76.fracture.worldgen.megastructure.structure.structures.citadel.wall;

import com.noodlegamer76.fracture.worldgen.megastructure.Node;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.Structure;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.StructureInstance;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.ChunkHeightMap;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.polygon.PolygonChunkHeightSampler;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.polygon.Wall;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVar;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVarSerializers;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.List;

public class ChunkHeightmapStructure extends Structure {
    private final GenVar<ChunkHeightMap> heightMapGenVar = new GenVar<>(null, GenVarSerializers.CHUNK_HEIGHT_MAP, "heightmap");

    public ChunkHeightmapStructure(int priority) {
        super(priority);
    }

    @Override
    public int getMaxSize() {
        return 255;
    }

    @Override
    public void generate(FeaturePlaceContext<NoneFeatureConfiguration> ctx, Node n, RandomSource random, StructureInstance instance) {
        GenVar<Wall> wallVar = instance.getGenVar("wall", GenVarSerializers.WALL.get());
        if (wallVar == null) return;

        Wall wall = wallVar.getValue();
        if (wall == null) return;

        ChunkHeightMap heightMap = PolygonChunkHeightSampler.sampleAllChunks(wall, 4);
        heightMapGenVar.setValue(heightMap);
    }

    @Override
    public List<GenVar<?>> getGenVariables() {
        return List.of(
                heightMapGenVar
        );
    }
}
