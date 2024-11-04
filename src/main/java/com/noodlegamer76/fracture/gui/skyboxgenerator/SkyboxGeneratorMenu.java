package com.noodlegamer76.fracture.gui.skyboxgenerator;

import com.noodlegamer76.fracture.block.InitBlocks;
import com.noodlegamer76.fracture.entity.block.SkyboxGeneratorEntity;
import com.noodlegamer76.fracture.gui.InitMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

public class SkyboxGeneratorMenu extends AbstractContainerMenu {
    private final SkyboxGeneratorEntity blockEntity;
    private final ContainerLevelAccess containerLevelAccess;
    public final ContainerData containerData;

    public SkyboxGeneratorMenu(int pContainerId, Inventory playerInventory, FriendlyByteBuf additionalData) {
        this(pContainerId, playerInventory, playerInventory.player.level().getBlockEntity(additionalData.readBlockPos()), new SimpleContainerData(7));
    }

    public SkyboxGeneratorMenu(int pContainerId, Inventory playerInventory, BlockEntity entity, ContainerData data) {
        super(InitMenus.SKYBOX_GENERATOR.get(), pContainerId);
        if (entity instanceof SkyboxGeneratorEntity generatorEntity) {
            this.blockEntity = generatorEntity;
            data = blockEntity.dataAccess;
        } else {
            throw new IllegalStateException("Incorrect block entity class (%s) passed into SkyboxGeneratorMenu!!"
                    .formatted(entity.getClass().getCanonicalName()));
        }

        this.containerLevelAccess = ContainerLevelAccess.create(entity.getLevel(), entity.getBlockPos());
        this.addDataSlots(data);

        this.containerData = data;
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return null;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(this.containerLevelAccess, pPlayer, InitBlocks.SKYBOX_GENERATOR.get());
    }

    public SkyboxGeneratorEntity getBlockEntity() {
        return blockEntity;
    }
}
