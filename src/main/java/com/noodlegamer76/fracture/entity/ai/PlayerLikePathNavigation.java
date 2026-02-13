package com.noodlegamer76.fracture.entity.ai;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.tslat.smartbrainlib.api.core.navigation.SmoothGroundNavigation;

public class PlayerLikePathNavigation extends SmoothGroundNavigation {
    public PlayerLikePathNavigation(Mob mob, Level level) {
        super(mob, level);
    }
}
