package com.noodlegamer76.fracture.worldgen.megastructure.structure.pool;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.ArrayList;
import java.util.List;

public class OffsetTemplatePool {
    private final List<Entry> entries = new ArrayList<>();
    private int totalWeight = 0;

    public void add(StructureTemplate template, int weight, BlockPos manualOffset, boolean normalize) {
        entries.add(new Entry(template, weight, manualOffset, normalize));
        totalWeight += weight;
    }

    public PlacementResult getRandom(RandomSource random,
                                     BlockPos desiredOrigin,
                                     StructurePlaceSettings settings) {

        if (entries.isEmpty()) return null;

        int r = random.nextInt(totalWeight);
        Entry selected = null;

        for (Entry e : entries) {
            r -= e.weight;
            if (r < 0) {
                selected = e;
                break;
            }
        }

        if (selected == null) return null;

        BlockPos finalPos = desiredOrigin;

        if (selected.normalize) {
            BoundingBox box = selected.template.getBoundingBox(settings, BlockPos.ZERO);
            finalPos = finalPos.offset(
                    -box.minX(),
                    -box.minY(),
                    -box.minZ()
            );
        }

        finalPos = finalPos.offset(selected.manualOffset);

        return new PlacementResult(selected.template, finalPos);
    }

    private record Entry(StructureTemplate template, int weight, BlockPos manualOffset, boolean normalize) {}

    public record PlacementResult(StructureTemplate template, BlockPos placementPos) {}
}
