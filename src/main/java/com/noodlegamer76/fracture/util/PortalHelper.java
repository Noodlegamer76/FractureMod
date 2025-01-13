package com.noodlegamer76.fracture.util;

import com.noodlegamer76.fracture.block.BoreasPortal;
import com.noodlegamer76.fracture.block.BoreasPortalFrame;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
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
     * Creates a portal at the specified location with a customizable shape and orientation.
     *
     * @param level        The level to create the portal in.
     * @param center       The center position of the portal.
     * @param frameBlock   The block type for the portal frame.
     * @param portalBlock  The block type for the portal itself.
     * @param shapePattern A two-dimensional array where '1' places the portal block and '0' places the frame block.
     * @param horizontal    If true, creates a horizontal portal; if false, creates a vertical portal.
     */
    public static void createPortal(ServerLevel level, BlockPos center, Block frameBlock, Block portalBlock, int[][] shapePattern, boolean horizontal) {
        int height = shapePattern.length;
        int width = shapePattern[0].length;

        if (horizontal) {
            for (int x = 0; x < width; x++) {
                for (int z = 0; z < height; z++) {
                    BlockPos pos = center.offset(x, 0, z);
                    if (shapePattern[x][z] == 1) {
                        level.setBlock(pos, portalBlock.defaultBlockState(), 2);
                    } else {
                        level.setBlock(pos, frameBlock.defaultBlockState(), 2);
                    }
                }
            }
        } else {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    BlockPos pos = center.offset(x, y, 0);
                    if (shapePattern[y][x] == 1) {
                        level.setBlock(pos, portalBlock.defaultBlockState(), 2);
                    } else {
                        level.setBlock(pos, frameBlock.defaultBlockState(), 2);
                    }
                }
            }
        }
    }

    public static void createBoreasPortal(ServerLevel level, BlockPos center, Block frameBlock, Block portalBlock, int[][] shapePattern) {
        int height = shapePattern.length;
        int width = shapePattern[0].length;

        for (int x = 0; x < width; x++) {
            for (int z = 0; z < height; z++) {
                BlockPos pos = center.offset(x, 0, z);
                if (shapePattern[x][z] == 1) {
                    level.setBlock(pos, portalBlock.defaultBlockState(), 2);
                }
                else if (shapePattern[x][z] == 2 && frameBlock instanceof BoreasPortalFrame portal) {
                    BlockState state = portal.defaultBlockState().rotate(level, pos, Rotation.CLOCKWISE_90);
                    level.setBlock(pos, state, 2);
                }
                else if (shapePattern[x][z] == 3) {
                    level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
                }
                else {
                    level.setBlock(pos, frameBlock.defaultBlockState(), 2);
                }
            }
        }
    }
}