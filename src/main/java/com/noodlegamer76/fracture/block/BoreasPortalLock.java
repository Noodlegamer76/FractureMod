package com.noodlegamer76.fracture.block;

import com.google.common.base.Predicates;
import com.noodlegamer76.fracture.item.BoreasKey;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BoreasPortalLock extends Block {
    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D);
    public BoreasPortalLock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide && pPlayer.getItemInHand(pHand).getItem() instanceof BoreasKey) {
            return InteractionResult.SUCCESS;
        }
        if (pPlayer.getItemInHand(pHand).getItem() instanceof BoreasKey) {
            buildPortal(pPos, pLevel);
            if (!pPlayer.isCreative()) {
                pPlayer.getItemInHand(pHand).shrink(1);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    public static void buildPortal(BlockPos pos, Level level) {

       for (int x = pos.getX() - 2;x <= pos.getX() + 2; x++ ) {
          for (int z = pos.getZ() - 2;z <= pos.getZ() + 2; z++ ) {
              if (x != pos.getX() || z != pos.getZ()) {
                  level.setBlock(new BlockPos(x, pos.getY(), z), InitBlocks.VOID_BLOCK.get().defaultBlockState(), 2);
              }
          }
       }


    }
}
