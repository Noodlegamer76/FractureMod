package com.noodlegamer76.fracture.util;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraftforge.common.util.ITeleporter;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class BoreasTeleporter implements ITeleporter {
    private final BlockPos initialPosition;
    private final Block frameBlock;
    private final Block portalBlock;

    public BoreasTeleporter(BlockPos initialPosition, Block frameBlock, Block portalBlock) {
        this.initialPosition = initialPosition;
        this.frameBlock = frameBlock;
        this.portalBlock = portalBlock;
    }

    @Override
    public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
        BlockPos portalPos = PortalHelper.findExistingPortal(destWorld, initialPosition, 16, portalBlock);

        if (portalPos == null) {
            portalPos = initialPosition;
            PortalHelper.createPortal(destWorld, portalPos, frameBlock, portalBlock);
        }

        // Move the entity to the portal position
        repositionEntity.apply(true).moveTo(portalPos.getX() + 0.5, portalPos.getY() + 1, portalPos.getZ() + 0.5, yaw, entity.getXRot());
        return repositionEntity.apply(true);
    }

    @Override
    public @Nullable PortalInfo getPortalInfo(Entity entity, ServerLevel destWorld, Function<ServerLevel, PortalInfo> defaultPortalInfo) {
        return new PortalInfo(entity.position(), entity.getDeltaMovement(), entity.getYRot(), entity.getXRot());
    }
}