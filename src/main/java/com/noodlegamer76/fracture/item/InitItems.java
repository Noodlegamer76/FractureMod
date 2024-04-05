package com.noodlegamer76.fracture.item;

import com.noodlegamer76.fracture.FractureMod;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.awt.*;

public class InitItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FractureMod.MODID);


    //how to add a block item
    //public static final RegistryObject<Item> BROWN_COSMIC_LEAVES = ITEMS.register("brown_cosmic_leaves",
    //        () -> new BlockItem(InitBlocks.BROWN_COSMIC_LEAVES.get(), new Item.Properties()));
}
