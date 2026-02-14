package com.noodlegamer76.fracture.entity.ai;

import com.noodlegamer76.fracture.entity.monster.PlayerMimic;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;

public class WalkRunAndJumpOverGapsToWalkTarget<E extends PlayerMimic> extends MoveToWalkTarget<E> {

    @Override
    protected void tick(E entity) {
        super.tick(entity);

        entity.setSprinting(entity.isShouldJumpOverGap());

        if (!entity.onGround() && entity.isShouldJumpOverGap() && entity.isWasOnGround()) {
            entity.coyoteTime();
        }
    }
}
