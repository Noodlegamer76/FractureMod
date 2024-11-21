package com.noodlegamer76.fracture.item.modifiable;

import com.noodlegamer76.fracture.gui.modificationstation.ModificationStationMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class ModifierItemSlot extends SlotItemHandler {
    private ModificationStationMenu menu;
    public ModifierItemSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, ModificationStationMenu menu) {
        super(itemHandler, index, xPosition, yPosition);
        this.menu = menu;
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        if (stack.isEmpty())
            return false;
        if (menu.getSlot(0).hasItem()) {
            return false;
        }
        if (stack.getItem() instanceof ModifiableItem) {
            return true;
        }
        return false;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public void onTake(Player pPlayer, ItemStack pStack) {
        super.onTake(pPlayer, pStack);
        menu.insertDataToItem(pStack);
    }
}
