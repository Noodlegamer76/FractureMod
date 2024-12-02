package com.noodlegamer76.fracture.client.renderers.entity;

import com.noodlegamer76.fracture.spellcrafting.wand.Wand;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public abstract class MultiAttackMonster extends Monster {
    public static final EntityDataAccessor<Integer> DATA_ATTACK = SynchedEntityData.defineId(MultiAttackMonster.class, EntityDataSerializers.INT);

    public int attackTimeout = 0;
    public int attacks = 0;
    public boolean resettingSpells = false;

    public int attackNumber = 0;
    public int defaultAttacks = 0;
    public int ticksSinceLastHit = 0;
    public boolean doMinionAttack = true;

    protected MultiAttackMonster(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


    @Override
    public void tick() {
        isResettingSpells();
        ticksSinceLastHit++;
        if (getLastHurtMobTimestamp() == 0) {
            ticksSinceLastHit = 0;
        }
        attackTimeout--;
        setAttackNumber();
        getEntityData().set(DATA_ATTACK, attackNumber);
        if (!level().isClientSide) {
           //System.out.println(attackTimeout);
        }

        super.tick();

    }

    private void isResettingSpells() {
        if (getMainHandItem().getItem() instanceof Wand) {
            if (getMainHandItem().getTag().getInt("slot") == -1) {
                resettingSpells = true;
            }
        }
    }

    public abstract void setAttackNumber();

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ATTACK, -1);
    }
}
