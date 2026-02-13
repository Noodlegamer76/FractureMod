package com.noodlegamer76.fracture.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import com.noodlegamer76.fracture.entity.ai.memory.InitMemoryModuleTypes;
import com.noodlegamer76.fracture.entity.monster.multiattack.AttackType;
import com.noodlegamer76.fracture.entity.monster.multiattack.MultiAttackMonster;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ProjectileWeaponItem;

public class MeleeAttackWithDelay extends Behavior<Mob> {
    private final int cooldownTicks;
    private final int windupTicks;
    private final AttackType attackType;
    private boolean isHit;

    public MeleeAttackWithDelay(int cooldownTicks, int windupTicks) {
        this(cooldownTicks, windupTicks, null);
    }

    public MeleeAttackWithDelay(int cooldownTicks, int windupTicks, AttackType attackType) {
        super(
                ImmutableMap.of(
                        MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT,
                        MemoryModuleType.ATTACK_COOLING_DOWN, MemoryStatus.VALUE_ABSENT,
                        MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED,
                        InitMemoryModuleTypes.ATTACK_DELAY.get(), MemoryStatus.REGISTERED,
                        MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryStatus.VALUE_PRESENT
                ),
                windupTicks + cooldownTicks
        );
        this.cooldownTicks = cooldownTicks;
        this.windupTicks = windupTicks;
        this.attackType = attackType;
    }

    @Override
    protected void start(ServerLevel level, Mob mob, long gameTime) {
        isHit = false;
        Brain<?> brain = mob.getBrain();
        brain.setMemoryWithExpiry(MemoryModuleType.ATTACK_COOLING_DOWN, true, windupTicks + cooldownTicks);
        brain.setMemoryWithExpiry(InitMemoryModuleTypes.ATTACK_DELAY.get(), true, windupTicks);

        if (attackType != null && mob instanceof MultiAttackMonster multiAttackMonster) {
            multiAttackMonster.setCurrentAttack(attackType);
        }
    }

    @Override
    protected void tick(ServerLevel level, Mob owner, long gameTime) {
        Brain<?> brain = owner.getBrain();
        LivingEntity target = brain.getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
        NearestVisibleLivingEntities nearestVisibleLivingEntities = brain.getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES).orElse(null);

        if (target == null || nearestVisibleLivingEntities == null) {
            return;
        }

        brain.setMemory(MemoryModuleType.LOOK_TARGET, new EntityTracker(target, true));

        if (
                !brain.hasMemoryValue(InitMemoryModuleTypes.ATTACK_DELAY.get()) &&
                        !isHit &&
                        owner.isWithinMeleeAttackRange(target) &&
                        nearestVisibleLivingEntities.contains(target)) {
            owner.swing(InteractionHand.MAIN_HAND);
            owner.doHurtTarget(target);
            isHit = true;
        }
    }

    @Override
    protected void stop(ServerLevel level, Mob mob, long gameTime) {
        if (attackType != null && mob instanceof MultiAttackMonster multiAttackMonster) {
            multiAttackMonster.setCurrentAttack(AttackType.NONE);
        }
    }

    @Override
    protected boolean canStillUse(ServerLevel pLevel, Mob pEntity, long pGameTime) {
        return true;
    }

    private static boolean isHoldingUsableProjectileWeapon(Mob mob) {
        return mob.isHolding((itemStack) -> {
            Item item = itemStack.getItem();
            return item instanceof ProjectileWeaponItem && mob.canFireProjectileWeapon((ProjectileWeaponItem)item);
        });
    }
}