package com.noodlegamer76.fracture.block;

import com.noodlegamer76.fracture.entity.block.ModificationStationEntity;
import com.noodlegamer76.fracture.entity.block.SkyboxGeneratorEntity;
import com.noodlegamer76.fracture.gui.modificationstation.ModificationStationMenu;
import com.noodlegamer76.fracture.gui.skyboxgenerator.SkyboxGeneratorMenu;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class ModificationStationBlock extends Block implements EntityBlock {
    private static final Component TITLE = Component.translatable("gui.fracture.modification_station");
    public ModificationStationBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ModificationStationEntity(pPos, pState);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.getBlockEntity(pPos) instanceof ModificationStationEntity be) {
            if (pPlayer instanceof ServerPlayer serverPlayer) {


                NetworkHooks.openScreen(serverPlayer, new MenuProvider() {
                    @Override
                    public Component getDisplayName() {
                        return TITLE;
                    }

                    @Override
                    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
                        FriendlyByteBuf packetBuffer = new FriendlyByteBuf(Unpooled.buffer());
                        packetBuffer.writeBlockPos(be.getBlockPos());
                        return new ModificationStationMenu(id, inventory, packetBuffer);
                    }
                }, buf -> {
                    buf.writeBlockPos(be.getBlockPos());

                });
            }
            if (!pLevel.isClientSide) {
                //be.minRenderDistance += 1;
                //System.out.println("BAZINGA");
                //pLevel.sendBlockUpdated(be.getBlockPos(), pState, pState, 2);
            }
        }
        return InteractionResult.SUCCESS;
    }
}
