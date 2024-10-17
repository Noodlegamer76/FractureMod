package com.noodlegamer76.fracture.client.renderers.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class MultiAttackMonster extends Monster {
    public int attackNumber = 2;

    protected MultiAttackMonster(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

}
