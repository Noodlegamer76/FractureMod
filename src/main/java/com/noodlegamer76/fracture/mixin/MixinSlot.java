package com.noodlegamer76.fracture.mixin;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Accessor;
import net.minecraft.world.inventory.Slot;

@Mixin(Slot.class)
public interface MixinSlot {

    // Method to set the x position
    @Accessor("x")
    void setX(int x);

    // Method to set the y position
    @Accessor("y")
    void setY(int y);
}