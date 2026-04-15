package com.noodlegamer76.fracture.entity.ai.behavior;

import com.mojang.datafixers.util.Pair;
import com.noodlegamer76.fracture.entity.projectile.ObeliskLaser;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ShootLaserBeamAtAttackTarget<E extends Mob> extends ExtendedBehaviour<E> {
    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS =
            ObjectArrayList.of(
                    Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT),
                    Pair.of(MemoryModuleType.ATTACK_COOLING_DOWN, MemoryStatus.VALUE_ABSENT)
            );
    private final Consumer<ObeliskLaser> positionCallback;

    private final int chargeTime;
    private final int fireTime;
    private final int cooldownTime;
    private final int laserFreezeTime;
    private final Predicate<E> setCooldown;

    private LivingEntity target;
    private int currentTick;

    private ObeliskLaser laser;

    public ShootLaserBeamAtAttackTarget(int chargeTime, int fireTime, int cooldownTime, int laserFreezeTime, Consumer<ObeliskLaser> positionCallback, Predicate<E> setCooldown) {
        this.chargeTime = chargeTime;
        this.fireTime = fireTime;
        this.cooldownTime = cooldownTime;
        this.laserFreezeTime = laserFreezeTime;
        this.positionCallback = positionCallback;
        this.setCooldown = setCooldown;
    }

    public ShootLaserBeamAtAttackTarget(int chargeTime, int fireTime, int cooldownTime, int laserFreezeTime, Predicate<E> setCooldown) {
        this(chargeTime, fireTime, cooldownTime, laserFreezeTime, null, setCooldown);
    }

    @Override
    protected void start(E entity) {
        currentTick = 0;
        this.target = entity.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);

        if (target == null || !target.isAlive())
            return;

        int totalDuration = laserFreezeTime + chargeTime + fireTime + cooldownTime;
        if (setCooldown != null && setCooldown.test(entity)) {
            BrainUtils.setForgettableMemory(entity, MemoryModuleType.ATTACK_COOLING_DOWN, true, totalDuration);
        }

        laser = new ObeliskLaser(
                entity.getEyePosition().add(0, 2.5, 0),
                entity.level(),
                chargeTime,
                fireTime,
                cooldownTime,
                laserFreezeTime,
                positionCallback
                );
        entity.level().addFreshEntity(laser);

        super.start(entity);
    }

    @Override
    protected void tick(E entity) {
        currentTick++;

        int freezeEnd = laserFreezeTime;
        int chargeEnd = freezeEnd + chargeTime;
        int fireEnd = chargeEnd + fireTime;
        int totalEnd = fireEnd + cooldownTime;

        if (target == null || !target.isAlive())
            return;

        Vec3 start = entity.getEyePosition().add(0, 2.5, 0);
        Vec3 targetDir = target.getEyePosition().subtract(start).normalize();

        if (currentTick > 0 && currentTick <= freezeEnd) {
            Vec3 look = start.add(targetDir.scale(10));
            //entity.getLookControl().setLookAt(look.x, look.y, look.z, 30f, 30f);
        }

        laser.setLaserDirection(targetDir);
    }

    @Override
    protected boolean shouldKeepRunning(E entity) {
        int totalDuration = laserFreezeTime + chargeTime + fireTime + cooldownTime;
        return currentTick < totalDuration && target != null;
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }

    public int getChargeTime() {
        return chargeTime;
    }

    public int getCurrentTick() {
        return currentTick;
    }

    public int getLaserFreezeTime() {
        return laserFreezeTime;
    }

    public LivingEntity getTarget() {
        return target;
    }
}
