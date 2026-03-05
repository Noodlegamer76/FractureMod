package com.noodlegamer76.fracture.worldgen.megastructure.structure.variables;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.ChunkHeightMap;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.polygon.Polygon;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.polygon.Wall;
import com.noodlegamer76.fracture.worldgen.megastructure.visualizer.entries.HeightmapEntry;
import com.noodlegamer76.fracture.worldgen.megastructure.visualizer.entries.PolygonEntry;
import com.noodlegamer76.fracture.worldgen.megastructure.visualizer.entries.SimpleEntry;
import com.noodlegamer76.fracture.worldgen.megastructure.visualizer.entries.WallEntry;
import net.minecraft.resources.ResourceLocation;

public class GenVarTypes {
    public static final GenVarType<Integer> INT =
            GenVarRegistry.register(new GenVarType<>(GenVarCodecs.INT, SimpleEntry::new, ResourceLocation.fromNamespaceAndPath(FractureMod.MODID, "int")));

    public static final GenVarType<Double> DOUBLE =
            GenVarRegistry.register(new GenVarType<>(GenVarCodecs.DOUBLE, SimpleEntry::new, ResourceLocation.fromNamespaceAndPath(FractureMod.MODID, "double")));

    public static final GenVarType<Float> FLOAT =
            GenVarRegistry.register(new GenVarType<>(GenVarCodecs.FLOAT, SimpleEntry::new, ResourceLocation.fromNamespaceAndPath(FractureMod.MODID, "float")));

    public static final GenVarType<Boolean> BOOLEAN =
            GenVarRegistry.register(new GenVarType<>(GenVarCodecs.BOOLEAN, SimpleEntry::new, ResourceLocation.fromNamespaceAndPath(FractureMod.MODID, "boolean")));

    public static final GenVarType<String> STRING =
            GenVarRegistry.register(new GenVarType<>(GenVarCodecs.STRING, SimpleEntry::new, ResourceLocation.fromNamespaceAndPath(FractureMod.MODID, "string")));

    public static final GenVarType<byte[]> BYTE_ARRAY =
            GenVarRegistry.register(new GenVarType<>(GenVarCodecs.BYTE_ARRAY, SimpleEntry::new, ResourceLocation.fromNamespaceAndPath(FractureMod.MODID, "byte_array")));

    public static final GenVarType<Long> LONG =
            GenVarRegistry.register(new GenVarType<>(GenVarCodecs.LONG, SimpleEntry::new, ResourceLocation.fromNamespaceAndPath(FractureMod.MODID, "long")));

    public static final GenVarType<Polygon> POLYGON =
            GenVarRegistry.register(new GenVarType<>(GenVarCodecs.POLYGON, PolygonEntry::new, ResourceLocation.fromNamespaceAndPath(FractureMod.MODID, "polygon")));

    public static final GenVarType<Wall> WALL =
            GenVarRegistry.register(new GenVarType<>(GenVarCodecs.WALL, WallEntry::new, ResourceLocation.fromNamespaceAndPath(FractureMod.MODID, "wall")));

    public static final GenVarType<ChunkHeightMap> CHUNK_HEIGHT_MAP =
            GenVarRegistry.register(new GenVarType<>(GenVarCodecs.CHUNK_HEIGHT_MAP, HeightmapEntry::new, ResourceLocation.fromNamespaceAndPath(FractureMod.MODID, "chunk_height_map")));
}
