package com.noodlegamer76.fracture.gui.wand.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.gui.wand.ListIndex;
import com.noodlegamer76.fracture.gui.wand.WandScreen;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractScrollWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;

public class ScrollableSpellList extends AbstractScrollWidget {
    private final ArrayList<ItemStack> spellsList;
    private final ArrayList<Integer> amountList;
    private final WandScreen screen;
    private ItemStack hoveredItem;
    private int hoveredItemIndex = -1;
    private final int itemXAmount = 8;

    private static final ResourceLocation SQUARE_PANEL = new ResourceLocation(FractureMod.MODID, "textures/screens/basic_background.png");
    private static final ResourceLocation SLOT = new ResourceLocation(FractureMod.MODID, "textures/screens/slot.png");


    public ScrollableSpellList(int pX, int pY, int pWidth, int pHeight, Component pMessage, ArrayList<ItemStack> spellsList, ArrayList<Integer> amountList, WandScreen screen) {
        super(pX, pY, pWidth, pHeight, pMessage);
        this.spellsList = spellsList;
        this.amountList = amountList;
        this.screen = screen;
    }

    @Override
    protected int getInnerHeight() {
        //yeah... I don't know either, I just copied this from a-n older project with a similar item list I made
        return ((spellsList.size() / itemXAmount) * (width / itemXAmount)) + (width / itemXAmount);
    }

    @Override
    protected double scrollRate() {
        //don't cast to double to match with the slot sizes
        return width / itemXAmount;
    }

    @Override
    protected void renderContents(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        int currentXAmount = 0;
        int currentYAmount = 0;
        int itemSize = width / itemXAmount;
        float itemScale = (float) itemSize / 18;

        int totalSlotWidth = itemSize * itemXAmount;
        int xOffset = (width - totalSlotWidth) / 2;

        boolean isHovering = false;
        for (int i = 0; i < spellsList.size(); i++) {

            int xPos = getX() + xOffset + (itemSize * currentXAmount);
            int yPos = getY() + (itemSize * currentYAmount);

            guiGraphics.blit(SLOT, xPos, yPos, 0, 0, itemSize, itemSize, itemSize, itemSize);

            int amount = Math.max(1, amountList.get(i));

            ItemStack stack = spellsList.get(i);
            stack.setCount(amount);
            PoseStack poseStack = guiGraphics.pose();

            poseStack.pushPose();
            poseStack.translate(xPos + itemScale, yPos + itemScale, 0);
            poseStack.scale(itemScale, itemScale, itemScale);
            guiGraphics.renderFakeItem(stack, 0, 0);
            guiGraphics.renderItemDecorations(screen.getMinecraft().font, stack, 0, 0);
            poseStack.popPose();

            if ((mouseX >= xPos) && (mouseX < xPos + itemSize) && (mouseY + scrollAmount() >= yPos) && (mouseY + scrollAmount() < yPos + itemSize)) {
                guiGraphics.fillGradient(RenderType.guiOverlay(), xPos, yPos, xPos + itemSize, yPos + itemSize, -2130706433, -2130706433, 0);
                hoveredItem = spellsList.get(i);
                hoveredItemIndex = i;

                isHovering = true;
            }

            currentXAmount++;
            if (currentXAmount >= itemXAmount) {
                currentXAmount = 0;
                currentYAmount++;
            }
        }
        if (!isHovering) {
            hoveredItem = null;
            hoveredItemIndex = -1;
        }
    }


    @Override
    protected void renderBackground(GuiGraphics pGuiGraphics) {
        super.renderBackground(pGuiGraphics);
    }

    @Override
    protected void renderBorder(GuiGraphics pGuiGraphics, int pX, int pY, int pWidth, int pHeight) {
        super.renderBorder(pGuiGraphics, pX, pY, pWidth, pHeight);
    }

    @Override
    protected void renderDecorations(GuiGraphics pGuiGraphics) {
        super.renderDecorations(pGuiGraphics);
    }

    @Override
    protected void renderScrollingString(GuiGraphics pGuiGraphics, Font pFont, int pWidth, int pColor) {
        super.renderScrollingString(pGuiGraphics, pFont, pWidth, pColor);
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (hoveredItem != null) {
            screen.selectedItem = new SelectedItem(ListIndex.SPELLS_INV, hoveredItemIndex, hoveredItem);
        }

        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {

    }
}
