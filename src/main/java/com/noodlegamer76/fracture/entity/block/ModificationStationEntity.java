package com.noodlegamer76.fracture.entity.block;

import com.noodlegamer76.fracture.gui.modificationstation.ModificationStationMenu;
import com.noodlegamer76.fracture.gui.skyboxgenerator.SkyboxGeneratorMenu;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ModificationStationEntity extends BlockEntity implements MenuProvider {
    private static final Component TITLE = Component.translatable("gui.fracture.modification_station");
    public ModificationStationEntity(BlockPos pPos, BlockState pBlockState) {
        super(InitBlockEntities.MODIFICATION_STATION.get(), pPos, pBlockState);
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        FriendlyByteBuf packetBuffer = new FriendlyByteBuf(Unpooled.buffer());
        packetBuffer.writeBlockPos(getBlockPos());
        return new ModificationStationMenu(pContainerId, pPlayerInventory, packetBuffer);
    }

    @Override
    public Component getDisplayName() {
        return TITLE;
    }
}
