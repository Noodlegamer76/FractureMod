package com.noodlegamer76.fracture.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

public class ParkourEvaluator extends WalkNodeEvaluator {
    private static final double MAX_JUMP_DIST_SQ = 16.0;
    private static final int HORIZONTAL_RANGE = 5;
    private static final int VERTICAL_UP = 1;
    private static final int VERTICAL_DOWN = -3;

    @Override
    public int getNeighbors(Node[] pNeighbors, Node pNode) {
        int count = super.getNeighbors(pNeighbors, pNode);
        BlockPos startPos = pNode.asBlockPos();

        if (!level.getBlockState(startPos.above(2)).isAir()) {
            return count;
        }

        for (int dx = -HORIZONTAL_RANGE; dx <= HORIZONTAL_RANGE; dx++) {
            for (int dz = -HORIZONTAL_RANGE; dz <= HORIZONTAL_RANGE; dz++) {
                if (Math.abs(dx) <= 1 && Math.abs(dz) <= 1) continue;

                double distSq = (dx * (double)dx) + (dz * (double)dz);
                if (distSq > MAX_JUMP_DIST_SQ) continue;

                count = addVerticalJumpNodes(pNeighbors, count, startPos, dx, dz);
            }
        }
        return count;
    }

    private int addVerticalJumpNodes(Node[] pNeighbors, int count, BlockPos startPos, int dx, int dz) {
        for (int dy = VERTICAL_UP; dy >= VERTICAL_DOWN; dy--) {
            if (count >= pNeighbors.length) return count;

            BlockPos targetPos = startPos.offset(dx, dy, dz);

            if (isValidJumpLanding(targetPos)) {
                if (isPathClear(startPos, targetPos)) {
                    Node jumpNode = this.getNode(targetPos.getX(), targetPos.getY(), targetPos.getZ());
                    if (!jumpNode.closed) {
                        jumpNode.type = ModBlockPathTypes.JUMP_OVER_IT;
                        pNeighbors[count++] = jumpNode;
                        break;
                    }
                }
            }
        }
        return count;
    }


    private boolean isPathClear(BlockPos start, BlockPos end) {
        int dx = end.getX() - start.getX();
        int dz = end.getZ() - start.getZ();
        int steps = Math.max(Math.abs(dx), Math.abs(dz));

        for (int i = 1; i < steps; i++) {
            double pct = (double) i / steps;
            int x = (int) Math.round(start.getX() + (dx * pct));
            int z = (int) Math.round(start.getZ() + (dz * pct));

            int checkY = Math.max(start.getY(), end.getY()) + 1;
            BlockPos checkPos = new BlockPos(x, checkY, z);

            if (!level.getBlockState(checkPos).isAir() || !level.getBlockState(checkPos.above()).isAir()) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidJumpLanding(BlockPos pos) {
        return level.getBlockState(pos).isSolid() &&
                level.getBlockState(pos.above(1)).isAir() &&
                level.getBlockState(pos.above(2)).isAir();
    }
}