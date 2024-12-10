package com.noodlegamer76.fracture.gui.wand;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.items.IItemHandler;

public class MovableSlot extends Slot {
    public int x, y; // Custom x and y

    public MovableSlot(Inventory itemHandler, int index, int x, int y) {
        super(itemHandler, index, x, y); // Use default x and y in the parent class
        this.x = x; // Now assign custom x and y
        this.y = y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}