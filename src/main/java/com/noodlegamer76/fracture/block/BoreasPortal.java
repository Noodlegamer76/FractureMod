package com.noodlegamer76.fracture.block;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.util.BoreasTeleporter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class BoreasPortal extends Block {
    private static final ResourceKey<Level> BOREAS = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(FractureMod.MODID, "boreas_dimension"));

    public BoreasPortal(Properties properties) {
        super(properties);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        super.entityInside(state, level, pos, entity);

        if (level instanceof ServerLevel serverLevel) {
            ServerLevel targetLevel = serverLevel.getServer().getLevel(BOREAS);
            if (targetLevel == null) return;

            if (entity.canChangeDimensions()) {
                if (entity.isOnPortalCooldown()) {
                    return;
                }
                entity.setPortalCooldown();

                if (level.dimension() == BOREAS) {
                    entity.changeDimension(serverLevel.getServer().overworld(), new BoreasTeleporter(pos, Blocks.OBSIDIAN, this));
                }
                else {
                    entity.changeDimension(targetLevel, new BoreasTeleporter(pos, Blocks.OBSIDIAN, this));
                }
            }
        }
    }
}