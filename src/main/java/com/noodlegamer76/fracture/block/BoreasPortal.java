package com.noodlegamer76.fracture.block;

import com.noodlegamer76.fracture.FractureMod;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.portal.PortalForcer;
import net.minecraftforge.common.util.ITeleporter;

import java.util.Optional;

public class BoreasPortal extends Block {
    ResourceKey<Level> BOREAS = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(FractureMod.MODID, "boreas_dimension"));

    public BoreasPortal(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        super.entityInside(state, level, pos, entity);
        if (level instanceof ServerLevel serverLevel) {
            PortalForcer portalForcer = null;
            ServerLevel boreas = serverLevel.getServer().getLevel(BOREAS);
            ServerLevel overworld = serverLevel.getServer().overworld();

            if (boreas == null) {
                return;
            }
            if (level.dimension() == Level.OVERWORLD) {
                portalForcer = new PortalForcer(overworld);

                if (entity.canChangeDimensions()) {
                    entity.changeDimension(boreas, portalForcer);
                }
            }
            if (level.dimension() == BOREAS) {
                portalForcer = new PortalForcer(boreas);

                if (entity.canChangeDimensions()) {
                    entity.changeDimension(overworld, portalForcer);
                }
            }

            if (portalForcer != null) {
                Optional<BlockUtil.FoundRectangle> optional = portalForcer.findPortalAround(pos, false, serverLevel.getWorldBorder());
                if (optional.isPresent()) {
                    BlockUtil.FoundRectangle rect = optional.get();
                }
            }
        }
    }
}
