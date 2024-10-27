package com.noodlegamer76.fracture.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class CustomPrimedTnt extends PrimedTnt {
    public CustomPrimedTnt(EntityType<? extends PrimedTnt> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


}
