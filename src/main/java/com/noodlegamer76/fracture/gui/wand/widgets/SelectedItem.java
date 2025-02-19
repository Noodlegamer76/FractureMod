package com.noodlegamer76.fracture.gui.wand.widgets;

import com.noodlegamer76.fracture.gui.wand.ListIndex;
import net.minecraft.world.item.ItemStack;

public class SelectedItem {
    public ListIndex listIndex;
    public int selectItemIndex;
    public ItemStack selectedItem;

    public SelectedItem(ListIndex listIndex, int selectItemIndex, ItemStack selectedItem) {
        this.listIndex = listIndex;
        this.selectItemIndex = selectItemIndex;
        this.selectedItem = selectedItem;
    }
}
