package com.noodlegamer76.fracture.entity.ai.behavior;

import com.noodlegamer76.fracture.entity.monster.PlayerMimic;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.player.Player;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.util.BrainUtils;

public class AnimatableMeleeAttackOrCrit<E extends PlayerMimic> extends AnimatableMeleeAttack<E> {
    public AnimatableMeleeAttackOrCrit(int delayTicks) {
        super(delayTicks);
    }

    @Override
    protected void doDelayedAction(E entity) {
        BrainUtils.setForgettableMemory(entity, MemoryModuleType.ATTACK_COOLING_DOWN, true, this.attackIntervalSupplier.apply(entity));

        if (target == null)
            return;

        if (!entity.getSensing().hasLineOfSight(target) || !entity.isWithinMeleeAttackRange(target)) {
            return;
        }

        entity.attack(target);
    }
}
