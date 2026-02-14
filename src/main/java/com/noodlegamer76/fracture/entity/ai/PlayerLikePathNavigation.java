package com.noodlegamer76.fracture.entity.ai;

import com.noodlegamer76.fracture.entity.monster.PlayerMimic;
import com.noodlegamer76.fracture.mixin.accessor.PathAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.navigation.ExtendedNavigator;
import net.tslat.smartbrainlib.api.core.navigation.SmoothGroundNavigation;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class PlayerLikePathNavigation extends GroundPathNavigation implements ExtendedNavigator {
    public PlayerLikePathNavigation(Mob mob, Level level) {
        super(mob, level);
    }

    @Override
    public Mob getMob() {
        return this.mob;
    }

    @Nullable
    @Override
    public Path getPath() {
        return super.getPath();
    }

    @Override
    protected PathFinder createPathFinder(int maxVisitedNodes) {
        this.nodeEvaluator = new ParkourEvaluator();
        this.nodeEvaluator.setCanPassDoors(true);

        return new PathFinder(this.nodeEvaluator, maxVisitedNodes) {
            @Nullable
            @Override
            public Path findPath(PathNavigationRegion navigationRegion, Mob mob, Set<BlockPos> targetPositions, float maxRange, int accuracy, float searchDepthMultiplier) {
                final Path path = super.findPath(navigationRegion, mob, targetPositions, maxRange, accuracy, searchDepthMultiplier);

                return path == null ? null : new Path(((PathAccessor) path).fracture$getNodes(), path.getTarget(), path.canReach()) {
                    @Override
                    public Vec3 getEntityPosAtNode(Entity entity, int nodeIndex) {
                        return PlayerLikePathNavigation.this.getEntityPosAtNode(nodeIndex);
                    }
                };
            }
        };
    }

    @Override
    protected void followThePath() {
        if (this.path == null || this.path.isDone()) return;

        if (this.mob instanceof PlayerMimic mimic && mimic.isShouldJumpOverGap()) {
            Vec3 target = this.path.getNextEntityPos(this.mob);
            mob.getMoveControl().setWantedPosition(target.x, target.y, target.z, this.speedModifier);

            double arrivalDist = 1.25D;
            if (this.mob.distanceToSqr(target) < arrivalDist * arrivalDist) {
                this.path.advance();
            }
        } else {
            super.followThePath();
        }
    }

    protected int getClosestVerticalTraversal(int safeSurfaceHeight) {
        final int nodesLength = this.path.getNodeCount();

        for (int nodeIndex = this.path.getNextNodeIndex(); nodeIndex < nodesLength; nodeIndex++) {
            if (this.path.getNode(nodeIndex).y != safeSurfaceHeight)
                return nodeIndex;
        }

        return nodesLength;
    }
}
