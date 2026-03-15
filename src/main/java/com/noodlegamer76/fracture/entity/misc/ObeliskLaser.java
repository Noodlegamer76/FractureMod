package com.noodlegamer76.fracture.entity.misc;

import com.noodlegamer76.fracture.entity.InitEntities;
import com.noodlegamer76.fracture.entity.monster.boss.FleshObelisk;
import com.noodlegamer76.fracture.sound.InitSoundEvents;
import com.noodlegamer76.fracture.util.RaycastUtils;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class ObeliskLaser extends Entity implements GeoEntity {
    private static final EntityDataAccessor<Vector3f> DATA_ID_ATTACK_POSITION =
            SynchedEntityData.defineId(ObeliskLaser.class, EntityDataSerializers.VECTOR3);
    private static final EntityDataAccessor<Integer> DATA_ID_CHARGE_TIME =
            SynchedEntityData.defineId(ObeliskLaser.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_ID_FIRE_TIME =
            SynchedEntityData.defineId(ObeliskLaser.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_ID_COOLDOWN_TIME =
            SynchedEntityData.defineId(ObeliskLaser.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_ID_LASER_FREEZE_TIME =
            SynchedEntityData.defineId(ObeliskLaser.class, EntityDataSerializers.INT);
    AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private Vec3 lockedDirection;
    private Vec3 laserDirection;
    private Vector3f lockedEnd = new Vector3f();
    private AABB laserBox;

    private Vector3f clientLastLaserPosition = new Vector3f();
    private final Vector3f clientPrevSyncedLaserPosition = new Vector3f();
    private Consumer<ObeliskLaser> positionCallback;
    private boolean playSounds = true;

    public ObeliskLaser(Level level) {
        this(Vec3.ZERO, level, 7, 75, 20, 30, null);
    }

    public ObeliskLaser(Vec3 pos, Level level, int chargeTime, int fireTime, int cooldownTime, int laserFreezeTime, Consumer<ObeliskLaser> positionCallback) {
        super(InitEntities.OBELISK_LASER.get(), level);
        entityData.set(DATA_ID_CHARGE_TIME, laserFreezeTime + chargeTime);
        entityData.set(DATA_ID_FIRE_TIME, laserFreezeTime + chargeTime + fireTime);
        entityData.set(DATA_ID_COOLDOWN_TIME, laserFreezeTime + chargeTime + fireTime + cooldownTime);
        entityData.set(DATA_ID_LASER_FREEZE_TIME, laserFreezeTime);
        setPos(pos);
        this.positionCallback = Objects.requireNonNullElseGet(positionCallback, () -> (attack) -> {
            Vector3f newPos = attack.getLockedEnd();
            Vector3f old = getClientLastLaserPosition();

            if (old.distanceSquared(newPos) > 0.001f) {
                setClientLastLaserPosition(newPos);
            }
        });
    }

    @Override
    public void tick() {
        super.tick();

        if (!level().isClientSide) {
            int freezeEnd = entityData.get(DATA_ID_LASER_FREEZE_TIME);
            int chargeEnd = entityData.get(DATA_ID_CHARGE_TIME);
            int fireEnd = entityData.get(DATA_ID_FIRE_TIME);
            int cooldownEnd = entityData.get(DATA_ID_COOLDOWN_TIME);

            Vec3 start = getEyePosition();

            if (laserDirection == null || laserDirection.equals(Vec3.ZERO)) {
                laserDirection = getLookAngle();
            }

            if (lockedEnd != null) {
                setClientSizeLaserPosition(lockedEnd);
            }

            if (tickCount <= freezeEnd + 1) {
                lockedDirection = laserDirection;
                lockedEnd = start.add(lockedDirection.scale(50)).toVector3f();
                setClientSizeLaserPosition(lockedEnd);
            }

            if (tickCount > freezeEnd && tickCount <= chargeEnd) {
                laserDirection = lockedDirection;
            }

            if (tickCount > chargeEnd && tickCount <= fireEnd) {
                if (lockedDirection != null) fireLaser();
            }

            if (positionCallback != null) positionCallback.accept(this);

            if (tickCount > cooldownEnd) {
                remove(RemovalReason.DISCARDED);
            }

            if (shouldPlaySounds()) {
                if (tickCount == chargeEnd) {
                    playSound(InitSoundEvents.FLESH_OBELISK_LASER_MIDDLE.get());
                }
                if (tickCount == 1) {
                    playSound(InitSoundEvents.FLESH_OBELISK_LASER_START.get());
                }
            }
        }

        if (level().isClientSide) {
            Vector3f synced = entityData.get(DATA_ID_ATTACK_POSITION);

            if (clientLastLaserPosition.lengthSquared() < 1e-6f && synced.lengthSquared() > 1e-6f) {
                clientLastLaserPosition.set(synced);
            } else {
                clientLastLaserPosition.set(clientPrevSyncedLaserPosition);
            }

            clientPrevSyncedLaserPosition.set(synced);

            Vector3f end = synced;
            laserBox = new AABB(getEyePosition(), new Vec3(end.x(), end.y(), end.z())).inflate(0.1f);
        }
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(DATA_ID_ATTACK_POSITION, new Vector3f());
        entityData.define(DATA_ID_CHARGE_TIME, 0);
        entityData.define(DATA_ID_FIRE_TIME, 0);
        entityData.define(DATA_ID_COOLDOWN_TIME, 0);
        entityData.define(DATA_ID_LASER_FREEZE_TIME, 0);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        CompoundTag tag2 = tag.getCompound("attackPosition");
        entityData.set(DATA_ID_ATTACK_POSITION, new Vector3f(tag2.getFloat("x"), tag2.getFloat("y"), tag2.getFloat("z")));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        CompoundTag tag2 = new CompoundTag();
        tag2.putFloat("x", entityData.get(DATA_ID_ATTACK_POSITION).x());
        tag2.putFloat("y", entityData.get(DATA_ID_ATTACK_POSITION).y());
        tag2.putFloat("z", entityData.get(DATA_ID_ATTACK_POSITION).z());
        tag.put("attackPosition", tag2);
    }

    private void fireLaser() {
        var level = level();

        Vec3 start = getEyePosition();
        Vec3 end = start.add(lockedDirection.scale(50));

        Vec3 beam = end.subtract(start);

        double length = beam.length();
        Vec3 dir = beam.normalize();

        double radius = 1.2;

        AABB box = new AABB(start, end).inflate(radius);

        List<LivingEntity> entities = level.getEntitiesOfClass(
                LivingEntity.class,
                box
        );

        doLaserParticles(start, dir, length);

        for (LivingEntity hit : entities) {
            if (hit instanceof FleshObelisk) {
                continue;
            }
            double distance = distanceToLine(hit.position(), start, dir, length);
            distance = Math.min(distance, distanceToLine(hit.getEyePosition(), start, dir, length));

            if (distance <= radius) {
                hit.hurt(damageSources().magic(), 5f);
            }
        }
    }

    private void doLaserParticles(Vec3 start, Vec3 dir, double length) {
        List<RaycastUtils.RayHit> hits = RaycastUtils.raycast(level(), start, dir, length);

        if (!(level() instanceof ServerLevel serverLevel)) {
            return;
        }

        float radius = 0.3f;
        for (RaycastUtils.RayHit hit : hits) {
            Vec3 pos = hit.position();
            Vec3 normal = hit.normal();

            Vec3 up = Math.abs(normal.y) < 0.999 ? new Vec3(0, 1, 0) : new Vec3(1, 0, 0);
            Vec3 tangent = normal.cross(up).normalize();
            Vec3 bitangent = normal.cross(tangent).normalize();

            double r1 = (Math.random() - 0.5) * 2 * radius;
            double r2 = (Math.random() - 0.5) * 2 * radius;

            double offsetX = tangent.x * r1 + bitangent.x * r2;
            double offsetY = tangent.y * r1 + bitangent.y * r2;
            double offsetZ = tangent.z * r1 + bitangent.z * r2;

            serverLevel.sendParticles(
                    new DustParticleOptions(new Vector3f(0.75f, 0f, 0.75f), 3f),
                    pos.x, pos.y, pos.z,
                    3,
                    offsetX, offsetY, offsetZ,
                    0
            );
        }
    }

    @Override
    public AABB getBoundingBoxForCulling() {
        return laserBox == null ? super.getBoundingBoxForCulling() : laserBox;
    }

    private double distanceToLine(Vec3 point, Vec3 start, Vec3 dir, double length) {
        Vec3 toPoint = point.subtract(start);

        double projection = toPoint.dot(dir);

        if (projection < 0 || projection > length)
            return Double.MAX_VALUE;

        Vec3 closest = start.add(dir.scale(projection));

        return point.distanceTo(closest);
    }

    public void setClientSizeLaserPosition(Vector3f clientSizeLaserPosition) {
        entityData.set(DATA_ID_ATTACK_POSITION, clientSizeLaserPosition);
    }

    public Vector3f getClientSideLaserPosition() {
        return entityData.get(DATA_ID_ATTACK_POSITION);
    }

    public Vector3f getClientLastLaserPosition() {
        return clientLastLaserPosition;
    }

    public void setClientLastLaserPosition(Vector3f clientLastLaserPosition) {
        this.clientLastLaserPosition = clientLastLaserPosition;
    }

    public Vector3f getInterpolatedLaserPosition(float partialTick) {
        return new Vector3f(clientLastLaserPosition).lerp(clientPrevSyncedLaserPosition, partialTick);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    public int getChargeTime() {
        return entityData.get(DATA_ID_CHARGE_TIME);
    }

    public int getFireTime() {
        return entityData.get(DATA_ID_FIRE_TIME);
    }

    public int getCooldownTime() {
        return entityData.get(DATA_ID_COOLDOWN_TIME);
    }

    public int getLaserFreezeTime() {
        return entityData.get(DATA_ID_LASER_FREEZE_TIME);
    }

    public Vec3 getLaserDirection() {
        return laserDirection;
    }

    public void setLaserDirection(Vec3 laserDirection) {
        this.laserDirection = laserDirection;
    }

    public Vector3f getLockedEnd() {
        return lockedEnd;
    }

    public boolean shouldPlaySounds() {
        return playSounds;
    }

    public void setPlaySounds(boolean playSounds) {
        this.playSounds = playSounds;
    }
}
