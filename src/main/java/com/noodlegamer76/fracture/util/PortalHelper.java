package com.noodlegamer76.fracture.util;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class PortalHelper {

    /**
     * Searches for an existing portal within a specified radius.
     *
     * @param level       The level to search in.
     * @param center      The center position to search around.
     * @param radius      The search radius.
     * @param portalBlock The block type representing the portal.
     * @return The position of the existing portal, or null if not found.
     */
    public static BlockPos findExistingPortal(ServerLevel level, BlockPos center, int radius, Block portalBlock) {
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos pos = center.offset(x, y, z);
                    BlockState state = level.getBlockState(pos);
                    if (state.getBlock() == portalBlock) {
                        return pos;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Creates a rectangular portal with a frame at the specified location.
     *
     * @param level       The level to create the portal in.
     * @param center      The center position of the portal.
     * @param frameBlock  The block type for the portal frame.
     * @param portalBlock The block type for the portal itself.
     */
    public static void createPortal(ServerLevel level, BlockPos center, Block frameBlock, Block portalBlock) {
        for (int x = -1; x <= 1; x++) {
            for (int y = 0; y <= 4; y++) {
                BlockPos pos = center.offset(x, y, 0);
                if (x == -1 || x == 1 || y == 0 || y == 4) {
                    level.setBlock(pos, frameBlock.defaultBlockState(), 3);
                } else {
                    level.setBlock(pos, portalBlock.defaultBlockState(), 3);
                }
            }
        }
    }
}