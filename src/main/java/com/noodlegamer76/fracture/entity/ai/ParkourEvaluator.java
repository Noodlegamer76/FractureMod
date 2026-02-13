package com.noodlegamer76.fracture.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

public class ParkourEvaluator extends WalkNodeEvaluator {

    @Override
    public int getNeighbors(Node[] pNeighbors, Node pNode) {
        int count = super.getNeighbors(pNeighbors, pNode);
        BlockPos startPos = pNode.asBlockPos();

        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                if (dx == 0 && dz == 0) continue;
                count = addJumpNodesForDirection(pNeighbors, count, startPos, dx, dz);
            }
        }
        return count;
    }

    private int addJumpNodesForDirection(Node[] pNeighbors, int count, BlockPos startPos, int dx, int dz) {
        if (count > pNeighbors.length - 2) return count;

        for (int distance = 2; distance <= 4; distance++) {
            BlockPos targetPos = startPos.offset(dx * distance, 0, dz * distance);

            if (!isValidJump(targetPos) && !isValidJump(targetPos.below()) && !isValidJump(targetPos.below(2))) {
                continue;
            }

            if (!isPathClear(startPos, targetPos, dx, dz)) {
                break;
            }

            BlockPos landingPos = isValidJump(targetPos) ? targetPos :
                    (isValidJump(targetPos.below()) ? targetPos.below() : targetPos.below(2));

            Node jumpNode = this.getNode(landingPos.getX(), landingPos.getY(), landingPos.getZ());
            if (!jumpNode.closed) {
                jumpNode.type = ModBlockPathTypes.JUMP_OVER_IT;
                pNeighbors[count++] = jumpNode;
                break;
            }
        }
        return count;
    }

    private boolean isPathClear(BlockPos start, BlockPos end, int dx, int dz) {
        int dist = Math.max(Math.abs(end.getX() - start.getX()), Math.abs(end.getZ() - start.getZ()));

        for (int i = 1; i <= dist; i++) {
            int x = start.getX() + (i * (end.getX() - start.getX()) / dist);
            int z = start.getZ() + (i * (end.getZ() - start.getZ()) / dist);
            BlockPos center = new BlockPos(x, start.getY(), z);

            if (isBlocked(center)) return false;

            if (dx != 0 && dz != 0) {
                if (isBlocked(new BlockPos(x - dx, start.getY(), z)) ||
                        isBlocked(new BlockPos(x, start.getY(), z - dz))) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isBlocked(BlockPos pos) {
        return level.getBlockState(pos).isSolid() ||
                !level.getBlockState(pos.above(1)).isAir() ||
                !level.getBlockState(pos.above(2)).isAir();
    }

    private boolean isValidJump(BlockPos pos) {
        return level.getBlockState(pos).isSolid() &&
                level.getBlockState(pos.above(1)).isAir() &&
                level.getBlockState(pos.above(2)).isAir();
    }
}