package com.noodlegamer76.fracture.block;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.entity.block.BoreasPortalEntity;
import com.noodlegamer76.fracture.util.BoreasTeleporter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class BoreasPortal extends Block implements EntityBlock {
    protected static final VoxelShape SHAPE = Block.box(0.0D, 6.0D, 0.0D, 16.0D, 9.0D, 16.0D);
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
                    entity.setPortalCooldown();
                    return;
                }

                if (level.dimension() == BOREAS) {
                    entity.changeDimension(serverLevel.getServer().overworld(), new BoreasTeleporter(pos, InitBlocks.BOREAS_PORTAL_FRAME.get(), this));
                }
                else {
                    entity.changeDimension(targetLevel, new BoreasTeleporter(pos, InitBlocks.BOREAS_PORTAL_FRAME.get(), this));
                }


            }
        }
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new BoreasPortalEntity(pPos, pState);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public boolean canBeReplaced(BlockState pState, Fluid pFluid) {
        return false;
    }
}