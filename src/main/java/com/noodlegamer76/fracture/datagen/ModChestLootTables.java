package com.noodlegamer76.fracture.datagen;

import com.noodlegamer76.fracture.FractureMod;
import net.minecraft.data.loot.packs.VanillaChestLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.function.BiConsumer;

public class ModChestLootTables extends VanillaChestLoot {
    public static final ResourceLocation FRACTURE_MAP = ResourceLocation.fromNamespaceAndPath(FractureMod.MODID, "chests/fracture_map");

    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> lootTableBuilder) {

    }
}
