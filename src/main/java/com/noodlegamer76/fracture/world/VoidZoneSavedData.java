package com.noodlegamer76.fracture.world;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.saveddata.SavedData;

public class VoidZoneSavedData extends SavedData {
    private static final String NAME = "fracture_void_zone";

    private BlockPos center;
    private float width;

    public VoidZoneSavedData() {
        this.center = new BlockPos(0, 64, 0);
        this.width = 200f;
    }

    public static VoidZoneSavedData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(
                VoidZoneSavedData::load,
                () -> create(level.getSeed()),
                NAME
        );
    }

    public BlockPos getCenter() {
        return center;
    }

    public float getWidth() {
        return width;
    }

    public void setZone(BlockPos start, float width) {
        this.center = start;
        this.width = width;
        setDirty();
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.putLong("center", center.asLong());
        tag.putFloat("width", width);
        return tag;
    }

    public static VoidZoneSavedData load(CompoundTag tag) {
        VoidZoneSavedData data = new VoidZoneSavedData();

        if (tag.contains("center", Tag.TAG_LONG)) {
            data.center = BlockPos.of(tag.getLong("center"));
        }

        data.width = tag.getFloat("width");

        return data;
    }

    public static VoidZoneSavedData create(long worldSeed) {
        VoidZoneSavedData data = new VoidZoneSavedData();

        RandomSource random = RandomSource.create(worldSeed ^ 0xA5A5A5A5L);

        int centerX = random.nextInt(500) - 250;
        int centerZ = random.nextInt(500) - 250;

        data.center = new BlockPos(centerX, 64, centerZ);
        data.width = 200f + random.nextFloat() * 300f;

        return data;
    }

    public boolean isInside(int x, int z) {
        int dx = x - center.getX();
        int dz = z - center.getZ();
        return (long) dx * dx + (long) dz * dz < (long) width * width;
    }
}