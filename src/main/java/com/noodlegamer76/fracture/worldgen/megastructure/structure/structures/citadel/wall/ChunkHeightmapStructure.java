package com.noodlegamer76.fracture.worldgen.megastructure.structure.structures.citadel.wall;

import com.noodlegamer76.fracture.worldgen.megastructure.Node;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.Structure;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.StructureInstance;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.access.WorldAccess;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.ChunkHeightMap;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.polygon.PolygonChunkHeightSampler;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.polygon.Wall;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVar;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVarCache;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVarTypes;
import net.minecraft.util.RandomSource;

import java.util.List;

public class ChunkHeightmapStructure extends Structure {
    private final GenVar<ChunkHeightMap> heightMapGenVar = new GenVar<>(GenVarTypes.CHUNK_HEIGHT_MAP, "heightmap", true);

    public ChunkHeightmapStructure(int priority) {
        super(priority);
    }

    @Override
    public int getMaxSize() {
        return 1023;
    }

    @Override
    public void generate(WorldAccess access, Node n, RandomSource random, StructureInstance instance) {
        GenVar<Wall> wallVar = instance.getGenVar(n, "wall", GenVarTypes.WALL);
        if (wallVar == null) return;

        Wall wall = wallVar.getValue();
        if (wall == null) return;

        ChunkHeightMap heightMap = PolygonChunkHeightSampler.sampleAllChunks(wall, 4, 64);
        setVar(n, heightMapGenVar, heightMap, instance);
    }

    @Override
    public boolean shouldGenerate(WorldAccess access, RandomSource random, Node n, StructureInstance instance) {
        GenVar<Wall> wallVar = instance.getGenVar(n, "wall", GenVarTypes.WALL);
        GenVar<ChunkHeightMap> heightMapVar = GenVarCache.instance().getVar(n, "heightmap", GenVarTypes.CHUNK_HEIGHT_MAP);
        return (wallVar != null && wallVar.getValue() != null) && (heightMapVar == null || heightMapVar.getValue() == null);
    }

    @Override
    public List<GenVar<?>> getGenVariables() {
        return List.of(
                heightMapGenVar
        );
    }
}
