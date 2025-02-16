package com.noodlegamer76.fracture.gui.wand.widgets;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.gui.wand.WandScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractScrollWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;

public class ScrollableSpellList extends AbstractScrollWidget {
    private final ArrayList<ItemStack> itemList;
    private final WandScreen screen;
    private ItemStack selectedItem;
    private ItemStack hoveredItem;
    private int hoveredItemIndex = -1;
    private int selectedItemIndex = -1;
    private int itemXAmount = 12;

    private static final ResourceLocation SLOT = new ResourceLocation(FractureMod.MODID, "textures/screens/slot.png");


    public ScrollableSpellList(int pX, int pY, int pWidth, int pHeight, Component pMessage, ArrayList<ItemStack> itemList, WandScreen screen) {
        super(pX, pY, pWidth, pHeight, pMessage);
        this.itemList = itemList;
        this.screen = screen;
    }

    @Override
    protected int getInnerHeight() {
        return 0;
    }

    @Override
    protected double scrollRate() {
        return 0;
    }

    @Override
    protected void renderContents(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {

    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {

    }
}
