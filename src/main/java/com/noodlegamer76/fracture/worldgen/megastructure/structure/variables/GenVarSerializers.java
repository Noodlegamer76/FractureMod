package com.noodlegamer76.fracture.worldgen.megastructure.structure.variables;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.event.FractureRegistries;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.ChunkHeightMap;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.polygon.Polygon;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.polygon.PolygonBuilder;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.polygon.Wall;
import com.noodlegamer76.fracture.worldgen.megastructure.visualizer.entries.HeightmapEntry;
import com.noodlegamer76.fracture.worldgen.megastructure.visualizer.entries.PolygonEntry;
import com.noodlegamer76.fracture.worldgen.megastructure.visualizer.entries.SimpleEntry;
import com.noodlegamer76.fracture.worldgen.megastructure.visualizer.entries.WallEntry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenVarSerializers {
    public static final DeferredRegister<GenVarSerializer<?>> GEN_VAR_SERIALIZERS = DeferredRegister.create(FractureRegistries.GEN_VAR_SERIALIZERS, FractureMod.MODID);

    public static final RegistryObject<GenVarSerializer<Integer>> INT = GEN_VAR_SERIALIZERS.register("int",
            () -> GenVarSerializer.create(CompoundTag::putInt, CompoundTag::getInt, () -> Integer.class, SimpleEntry::new));

    public static final RegistryObject<GenVarSerializer<Double>> DOUBLE = GEN_VAR_SERIALIZERS.register("double",
            () -> GenVarSerializer.create(CompoundTag::putDouble, CompoundTag::getDouble, () -> Double.class, SimpleEntry::new));

    public static final RegistryObject<GenVarSerializer<Float>> FLOAT = GEN_VAR_SERIALIZERS.register("float",
            () -> GenVarSerializer.create(CompoundTag::putFloat, CompoundTag::getFloat, () -> Float.class, SimpleEntry::new));

    public static final RegistryObject<GenVarSerializer<Boolean>> BOOLEAN = GEN_VAR_SERIALIZERS.register("boolean",
            () -> GenVarSerializer.create(CompoundTag::putBoolean, CompoundTag::getBoolean, () -> Boolean.class, SimpleEntry::new));

    public static final RegistryObject<GenVarSerializer<String>> STRING = GEN_VAR_SERIALIZERS.register("string",
            () -> GenVarSerializer.create(CompoundTag::putString, CompoundTag::getString, () -> String.class, SimpleEntry::new));

    public static final RegistryObject<GenVarSerializer<byte[]>> BYTE_ARRAY = GEN_VAR_SERIALIZERS.register("byte_array",
            () -> GenVarSerializer.create(CompoundTag::putByteArray, CompoundTag::getByteArray, () -> byte[].class, SimpleEntry::new));

    public static final RegistryObject<GenVarSerializer<Long>> LONG = GEN_VAR_SERIALIZERS.register("long",
            () -> GenVarSerializer.create(CompoundTag::putLong, CompoundTag::getLong, () -> Long.class, SimpleEntry::new));

    public static final RegistryObject<GenVarSerializer<Polygon>> POLYGON = GEN_VAR_SERIALIZERS.register("polygon",
            () -> GenVarSerializer.create(
                    (compoundTag, name, polygon) -> {
                        List<Vec3> verts = polygon.vertices();
                        CompoundTag tag = new CompoundTag();
                        for (int i = 0; i < verts.size(); i++) {
                            CompoundTag vertTag = new CompoundTag();
                            vertTag.putDouble("x", verts.get(i).x);
                            vertTag.putDouble("y", verts.get(i).y);
                            vertTag.putDouble("z", verts.get(i).z);
                            tag.put(String.valueOf(i), vertTag);
                        }
                        compoundTag.put(name, tag);
                    },
                    (tag, name) -> {
                        CompoundTag polyTag = tag.getCompound(name);
                        List<Vec3> verts = new ArrayList<>();
                        int i = 0;
                        while (polyTag.contains(String.valueOf(i))) {
                            CompoundTag vertTag = polyTag.getCompound(String.valueOf(i));
                            verts.add(new Vec3(vertTag.getDouble("x"), vertTag.getDouble("y"), vertTag.getDouble("z")));
                            i++;
                        }

                        if (verts.size() < 2) return null;

                        PolygonBuilder builder = new PolygonBuilder(verts.get(0));
                        for (int j = 1; j < verts.size(); j++) {
                            builder.addNextVertex(verts.get(j));
                        }

                        return builder.build();
                    }, () -> Polygon.class, PolygonEntry::new));

    public static final RegistryObject<GenVarSerializer<Wall>> WALL = GEN_VAR_SERIALIZERS.register("wall",
            () -> GenVarSerializer.create(
                    (compoundTag, name, wall) -> {
                        CompoundTag tag = new CompoundTag();

                        GenVarSerializer<Polygon> polygonSerializer = POLYGON.get();
                        polygonSerializer.serialize(tag, "polygon", wall.polygon());

                        tag.putIntArray("surface", wall.surface());
                        tag.putIntArray("edgeHeight", wall.edgeHeight());
                        tag.putIntArray("vertexHeight", wall.vertexHeight());

                        boolean[] isTower = wall.isTower();
                        byte[] isTowerBytes = new byte[isTower.length];
                        for (int i = 0; i < isTower.length; i++) {
                            isTowerBytes[i] = isTower[i] ? (byte) 1 : 0;
                        }
                        tag.putByteArray("isTower", isTowerBytes);

                        tag.putDouble("edgeLength", wall.edgeLength());

                        compoundTag.put(name, tag);
                    },
                    (compoundTag, name) -> {
                        CompoundTag tag = compoundTag.getCompound(name);

                        GenVarSerializer<Polygon> polygonSerializer = POLYGON.get();
                        Polygon polygon = polygonSerializer.deserialize(tag, "polygon");
                        if (polygon == null) return null;

                        int[] surface = tag.getIntArray("surface");
                        int[] edgeHeight = tag.getIntArray("edgeHeight");
                        int[] vertexHeight = tag.getIntArray("vertexHeight");

                        byte[] isTowerBytes = tag.getByteArray("isTower");
                        boolean[] isTower = new boolean[isTowerBytes.length];
                        for (int i = 0; i < isTowerBytes.length; i++) {
                            isTower[i] = isTowerBytes[i] == 1;
                        }

                        double edgeLength = tag.getDouble("edgeLength");

                        return new Wall(polygon, surface, edgeHeight, vertexHeight, isTower, edgeLength);
                    },
                    () -> Wall.class,
                    WallEntry::new
            )
    );

    public static final RegistryObject<GenVarSerializer<ChunkHeightMap>> CHUNK_HEIGHT_MAP = GEN_VAR_SERIALIZERS.register("chunk_height_map",
            () -> GenVarSerializer.create(
                    (compoundTag, name, map) -> {
                        CompoundTag tag = new CompoundTag();
                        Map<Long, Integer> heights = map.heights();
                        int i = 0;
                        for (Map.Entry<Long, Integer> e : heights.entrySet()) {
                            CompoundTag entryTag = new CompoundTag();
                            long packed = e.getKey();
                            int chunkX = (int)(packed >> 32);
                            int chunkZ = (int)packed;
                            entryTag.putInt("chunkX", chunkX);
                            entryTag.putInt("chunkZ", chunkZ);
                            entryTag.putInt("height", e.getValue());
                            tag.put(String.valueOf(i), entryTag);
                            i++;
                        }
                        compoundTag.put(name, tag);
                    },
                    (compoundTag, name) -> {
                        CompoundTag tag = compoundTag.getCompound(name);
                        Map<Long, Integer> map = new HashMap<>();
                        int i = 0;
                        while (tag.contains(String.valueOf(i))) {
                            CompoundTag entryTag = tag.getCompound(String.valueOf(i));
                            int chunkX = entryTag.getInt("chunkX");
                            int chunkZ = entryTag.getInt("chunkZ");
                            int height = entryTag.getInt("height");
                            long packed = (((long)chunkX) << 32) | (chunkZ & 0xffffffffL);
                            map.put(packed, height);
                            i++;
                        }
                        return new ChunkHeightMap(map);
                    },
                    () -> ChunkHeightMap.class,
                    HeightmapEntry::new
            )
    );

    public static RegistryObject<GenVarSerializer<?>> getGenVarSerializer(ResourceLocation id) {
        return GEN_VAR_SERIALIZERS
                .getEntries()
                .stream()
                .filter(entry -> entry.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
