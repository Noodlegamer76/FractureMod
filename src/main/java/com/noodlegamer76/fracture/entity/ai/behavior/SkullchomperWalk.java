package com.noodlegamer76.fracture.entity.ai.behavior;

import com.noodlegamer76.fracture.entity.monster.boss.SkullChomper;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.WalkOrRunToWalkTarget;

public class SkullchomperWalk<T extends SkullChomper> extends WalkOrRunToWalkTarget<T> {
    private final float skipRange;

    public SkullchomperWalk(float pSkipRange) {
        super();
        skipRange = pSkipRange;
    }

    @Override
    protected void tick(T entity) {
        super.tick(entity);
        if (path == null) return;
        if (path.getNextNode().distanceToSqr(entity.getOnPos() ) < skipRange * skipRange) {
            path.advance();
        }
    }

    @Override
    protected boolean shouldKeepRunning(T entity) {
        return super.shouldKeepRunning(entity);
    }
}
