package com.noodlegamer76.fracture.item.modifiable;

import com.noodlegamer76.fracture.gui.modificationstation.ModificationStationMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class BroomPartSlot extends SlotItemHandler {
    ModificationStationMenu menu;
    public BroomPartSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, ModificationStationMenu menu) {
        super(itemHandler, index, xPosition, yPosition);
        this.menu = menu;
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        if (menu.getSlot(0).getItem().getItem() instanceof ModifiableItem &&
                stack.getItem() instanceof ModItemPart) {
            return true;
        }
        return false;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}
