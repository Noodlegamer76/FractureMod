package com.noodlegamer76.fracture.mixin.accessor;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(StructureTemplate.class)
public interface StructureTemplateAccessor {

    @Accessor(value = "palettes")
    List<StructureTemplate.Palette> noodleEngine$getPalettes();

    @Invoker(value = "addEntitiesToWorld")
    void noodleEngine$addEntitiesToWorld(ServerLevelAccessor pServerLevel, BlockPos pPos, StructurePlaceSettings placementIn);
}
