package com.noodlegamer76.fracture.util;

import com.noodlegamer76.fracture.FractureMod;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

public class ModWoodTypes {
    public static final WoodType INKWOOD = WoodType.register(new WoodType(FractureMod.MODID + ":inkwood", BlockSetType.OAK));
}
