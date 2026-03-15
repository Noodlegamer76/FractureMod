package com.noodlegamer76.fracture.block;

import com.noodlegamer76.fracture.item.BoreasKey;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
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
            if (pPlayer.getItemInHand(pHand).getItem() instanceof BoreasKey) {
                Minecraft mc = Minecraft.getInstance();
                if (mc.player != null) {
                    mc.player.displayClientMessage(
                            Component.literal("The portal remains locked, The dimension isn't ready yet!"),
                            true
                    );
                }
            }
            return InteractionResult.SUCCESS;
        }
        if (pPlayer.getItemInHand(pHand).getItem() instanceof BoreasKey) {
            if (true) {
                return InteractionResult.SUCCESS;
            }
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
                  level.setBlock(new BlockPos(x, pos.getY(), z), InitBlocks.BOREAS_PORTAL.get().defaultBlockState(), 2);
              }
          }
       }


    }
}
