package com.noodlegamer76.fracture.entity.ai.behavior;

import com.mojang.datafixers.util.Pair;
import com.noodlegamer76.fracture.entity.misc.ObeliskLaser;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;
import java.util.function.Predicate;

public class SpawnObeliskLasersAroundTargetOnGround<E extends LivingEntity> extends ExtendedBehaviour<E> {
    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS =
            ObjectArrayList.of(
                    Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT),
                    Pair.of(MemoryModuleType.ATTACK_COOLING_DOWN, MemoryStatus.VALUE_ABSENT)
            );

    private final int chargeTime;
    private final int fireTime;
    private final int cooldownTime;
    private final int laserFreezeTime;
    private final int laserCount;
    private final int spawnRadius;
    private final int attackCooldownTime;
    private final int spawnDelay;
    private final Predicate<E> setCooldown;

    private LivingEntity target;
    private int currentTick;

    public SpawnObeliskLasersAroundTargetOnGround(int laserFreezeTime, int chargeTime, int fireTime, int cooldownTime, int laserCount, int spawnRadius, int attackCooldownTime, int spawnDelay, Predicate<E> setCooldown) {
        this.chargeTime = chargeTime;
        this.fireTime = fireTime;
        this.cooldownTime = cooldownTime;
        this.laserFreezeTime = laserFreezeTime;
        this.laserCount = laserCount;
        this.spawnRadius = spawnRadius;
        this.attackCooldownTime = attackCooldownTime;
        this.spawnDelay = spawnDelay;
        this.setCooldown = setCooldown;
    }

    @Override
    protected void start(E entity) {
        currentTick = 0;
        this.target = entity.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);

        if (target == null || !target.isAlive())
            return;

        int totalDuration = laserFreezeTime + chargeTime + fireTime + cooldownTime;
        if (setCooldown.test(entity)) {
            BrainUtils.setForgettableMemory(entity, MemoryModuleType.ATTACK_COOLING_DOWN, true, totalDuration);
        }
    }

    @Override
    protected boolean shouldKeepRunning(E entity) {
        return currentTick < (spawnDelay * laserCount) + attackCooldownTime;
    }

    @Override
    protected void tick(E entity) {
        currentTick++;

        if (target == null || !target.isAlive()) {
            return;
        }

        if (currentTick % spawnDelay != 0 || currentTick >= spawnDelay * laserCount) {
            return;
        }

        Vec3 position = getNextSpawnPosition(entity);

        ObeliskLaser laser = new ObeliskLaser(
                position,
                entity.level(),
                chargeTime,
                fireTime,
                cooldownTime,
                laserFreezeTime,
                null
        );
        int currentLaser = currentTick / spawnDelay - 1;

        laser.setPlaySounds(currentLaser % 5 == 0);
        laser.setLaserDirection(new Vec3(0, 1, 0));
        entity.level().addFreshEntity(laser);
    }

    private Vec3 getNextSpawnPosition(E entity) {
        Vec3 center = target.position();
        RandomSource random = entity.getRandom();
        Level level = entity.level();

        double angle = random.nextDouble() * Math.PI * 2;
        double dist = random.nextDouble() * spawnRadius;

        double x = center.x + Math.cos(angle) * dist;
        double z = center.z + Math.sin(angle) * dist;

        BlockPos pos = BlockPos.containing(x, center.y, z);
        while (pos.getY() > level.getMinBuildHeight() && !level.getBlockState(pos.below()).isSolid()) {
            pos = pos.below();
        }

        return new Vec3(x, pos.getY(), z);
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }
}
