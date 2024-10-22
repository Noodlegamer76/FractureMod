package com.noodlegamer76.fracture.client.renderers.entity;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public abstract class MultiAttackMonster extends Monster {
    public static final EntityDataAccessor<Integer> DATA_ATTACK = SynchedEntityData.defineId(MultiAttackMonster.class, EntityDataSerializers.INT);

    public int attackNumber = 0;
    public int defaultAttacks = 0;
    public int ticksSinceLastHit = 0;
    public boolean doMinionAttack = true;

    protected MultiAttackMonster(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public void tick() {
        ticksSinceLastHit++;
        if (getLastHurtMobTimestamp() == 0) {
            ticksSinceLastHit = 0;
        }
        setAttackNumber();
        getEntityData().set(DATA_ATTACK, attackNumber);
        if (!level().isClientSide) {
           // System.out.println(defaultAttacks);
        }
        super.tick();
    }

    public void setAttackNumber() {
        if (attackNumber == 0) {
            attackNumber = 1;
        }
        if (defaultAttacks % 7 == 0 && defaultAttacks != 0) {
            attackNumber = 2;
            defaultAttacks++;
        }
        if (getHealth() <= getMaxHealth() / 2 && doMinionAttack) {
            attackNumber = 3;
            defaultAttacks++;
            doMinionAttack = false;
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ATTACK, -1);
    }
}
