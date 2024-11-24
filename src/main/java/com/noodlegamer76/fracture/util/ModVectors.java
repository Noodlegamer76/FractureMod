package com.noodlegamer76.fracture.util;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class ModVectors {

    public static @NotNull Vec3 getForwardVector(@NotNull Entity entity) {
        float yaw = entity.getYRot();
        float pitch = entity.getXRot();

        float yawRad = (float) Math.toRadians(yaw);
        float pitchRad = (float) Math.toRadians(pitch);

        float x = (float) (-Math.sin(yawRad) * Math.cos(pitchRad));
        float y = (float) -Math.sin(pitchRad);
        float z = (float) (Math.cos(yawRad) * Math.cos(pitchRad));

        return new Vec3(x, y, z);
    }
}
