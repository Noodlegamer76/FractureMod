package com.noodlegamer76.fracture.worldgen.megastructure.structure.variables;

import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.ChunkHeightMap;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.polygon.Polygon;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.polygon.PolygonBuilder;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.polygon.Wall;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class GenVarCodecs {
    public static final GenVarCodec<Integer> INT = GenVarCodec.of(CompoundTag::putInt, CompoundTag::getInt, Integer.class);
    public static final GenVarCodec<Double> DOUBLE = GenVarCodec.of(CompoundTag::putDouble, CompoundTag::getDouble, Double.class);
    public static final GenVarCodec<Float> FLOAT = GenVarCodec.of(CompoundTag::putFloat, CompoundTag::getFloat, Float.class);
    public static final GenVarCodec<Boolean> BOOLEAN = GenVarCodec.of(CompoundTag::putBoolean, CompoundTag::getBoolean, Boolean.class);
    public static final GenVarCodec<String> STRING = GenVarCodec.of(CompoundTag::putString, CompoundTag::getString, String.class);
    public static final GenVarCodec<byte[]> BYTE_ARRAY = GenVarCodec.of(CompoundTag::putByteArray, CompoundTag::getByteArray, byte[].class);
    public static final GenVarCodec<Long> LONG = GenVarCodec.of(CompoundTag::putLong, CompoundTag::getLong, Long.class);

    public static final GenVarCodec<Vec3> VEC3 = GenVarCodec.of((tag, key, v) -> {
        CompoundTag t = new CompoundTag();
        t.putDouble("x", v.x);
        t.putDouble("y", v.y);
        t.putDouble("z", v.z);
        tag.put(key, t);
    }, (tag, key) -> {
        CompoundTag t = tag.getCompound(key);
        return new Vec3(t.getDouble("x"), t.getDouble("y"), t.getDouble("z"));
    }, Vec3.class);

    public static final GenVarCodec<Polygon> POLYGON = GenVarCodec.of((tag, key, polygon) -> {
        ListTag list = new ListTag();
        for (Vec3 v : polygon.vertices()) {
            CompoundTag vt = new CompoundTag();
            vt.putDouble("x", v.x);
            vt.putDouble("y", v.y);
            vt.putDouble("z", v.z);
            list.add(vt);
        }
        tag.put(key, list);
    }, (tag, key) -> {
        if (!tag.contains(key, Tag.TAG_LIST)) return null;
        ListTag list = tag.getList(key, Tag.TAG_COMPOUND);
        List<Vec3> verts = new ArrayList<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            CompoundTag vt = list.getCompound(i);
            verts.add(new Vec3(vt.getDouble("x"), vt.getDouble("y"), vt.getDouble("z")));
        }
        if (verts.size() < 2) return null;
        PolygonBuilder builder = new PolygonBuilder(verts.get(0));
        for (int i = 1; i < verts.size(); i++) builder.addNextVertex(verts.get(i));
        return builder.build();
    }, Polygon.class);

    // Wall codec
    public static final GenVarCodec<Wall> WALL = GenVarCodec.of((tag, key, wall) -> {
        CompoundTag wt = new CompoundTag();
        POLYGON.encode(wt, "polygon", wall.polygon());
        wt.putIntArray("surface", wall.surface());
        wt.putIntArray("edgeHeight", wall.edgeHeight());
        wt.putIntArray("vertexHeight", wall.vertexHeight());
        // towers as byte array
        boolean[] towers = wall.isTower();
        byte[] towerBytes = new byte[towers.length];
        for (int i = 0; i < towers.length; i++) towerBytes[i] = towers[i] ? (byte)1 : (byte)0;
        wt.putByteArray("isTower", towerBytes);
        wt.putDouble("edgeLength", wall.edgeLength());
        tag.put(key, wt);
    }, (tag, key) -> {
        CompoundTag wt = tag.getCompound(key);
        Polygon poly = POLYGON.decode(wt, "polygon");
        if (poly == null) return null;
        int[] surface = wt.getIntArray("surface");
        int[] edgeHeight = wt.getIntArray("edgeHeight");
        int[] vertexHeight = wt.getIntArray("vertexHeight");
        byte[] towerBytes = wt.getByteArray("isTower");
        boolean[] isTower = new boolean[towerBytes.length];
        for (int i = 0; i < towerBytes.length; i++) isTower[i] = towerBytes[i] == 1;
        double edgeLength = wt.getDouble("edgeLength");
        return new Wall(poly, surface, edgeHeight, vertexHeight, isTower, edgeLength);
    }, Wall.class);

    // ChunkHeightMap codec
    public static final GenVarCodec<ChunkHeightMap> CHUNK_HEIGHT_MAP = GenVarCodec.of((tag, key, map) -> {
        ListTag list = new ListTag();
        for (Map.Entry<Long, Integer> e : map.heights().entrySet()) {
            CompoundTag entry = new CompoundTag();
            long packed = e.getKey();
            int chunkX = (int)(packed >> 32);
            int chunkZ = (int)packed;
            entry.putInt("chunkX", chunkX);
            entry.putInt("chunkZ", chunkZ);
            entry.putInt("height", e.getValue());
            list.add(entry);
        }
        tag.put(key, list);
    }, (tag, key) -> {
        if (!tag.contains(key, Tag.TAG_LIST)) return new ChunkHeightMap(new HashMap<>());
        ListTag list = tag.getList(key, Tag.TAG_COMPOUND);
        Map<Long, Integer> map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            CompoundTag entry = list.getCompound(i);
            int cx = entry.getInt("chunkX");
            int cz = entry.getInt("chunkZ");
            int h = entry.getInt("height");
            long packed = (((long)cx) << 32) | (cz & 0xffffffffL);
            map.put(packed, h);
        }
        return new ChunkHeightMap(map);
    }, ChunkHeightMap.class);
}