package com.noodlegamer76.fracture.entity.monster.multiattack;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public abstract class MultiAttackMonster extends Monster {
    private static final EntityDataAccessor<Integer> DATA_ATTACKING = SynchedEntityData.defineId(MultiAttackMonster.class, EntityDataSerializers.INT);

    protected MultiAttackMonster(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ATTACKING, 0);
    }

    public void setCurrentAttack(AttackType attack) {
        this.entityData.set(DATA_ATTACKING, attack.ordinal());
    }

    public AttackType getCurrentAttack() {
        return AttackType.values()[this.entityData.get(DATA_ATTACKING)];
    }
}
