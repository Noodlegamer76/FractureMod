package com.noodlegamer76.fracture.gui.wand.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.gui.wand.ListIndex;
import com.noodlegamer76.fracture.gui.wand.WandScreen;
import com.noodlegamer76.fracture.spellcrafting.spells.item.SpellItem;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractScrollWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.awt.*;
import java.util.ArrayList;

public class ScrollableSpellList extends AbstractScrollWidget {
    private final ArrayList<ItemStack> spellsList;
    private final ArrayList<Integer> amountList;
    private final WandScreen screen;
    private ItemStack hoveredItem;
    private int hoveredItemIndex = -1;
    private int hoveredItemAmount = -1;
    private final int itemXAmount = 6;

    private static final ResourceLocation SQUARE_PANEL = new ResourceLocation(FractureMod.MODID, "textures/screens/basic_background.png");
    private static final ResourceLocation SLOT = new ResourceLocation(FractureMod.MODID, "textures/screens/spell_slot.png");
    private static final ResourceLocation SPRUCE_PLANKS = new ResourceLocation("textures/block/spruce_planks.png");
    private static final int color = new Color(188, 188, 188).getRGB();


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
        for (int i = 0; i < Math.min(spellsList.size(), amountList.size()); i++) {

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

            if (amountList.get(i) == -1) {
                poseStack.pushPose();
                poseStack.translate(0, 0, 500);
                guiGraphics.blit(new ResourceLocation(FractureMod.MODID, "textures/screens/locked_spell.png"), xPos, yPos,
                        0, 0, 0,
                        itemSize, itemSize,
                        itemSize, itemSize);
                poseStack.popPose();
            }
            else if (amountList.get(i) == 0) {
                poseStack.pushPose();
                poseStack.translate(0, 0, 500);
                guiGraphics.blit(new ResourceLocation(FractureMod.MODID, "textures/screens/out_of_spells.png"), xPos, yPos,
                        0, 0, 0,
                        itemSize, itemSize,
                        itemSize, itemSize);
                poseStack.popPose();
            }

            if ((mouseX >= xPos) && (mouseX < xPos + itemSize) && (mouseY + scrollAmount() >= yPos) && (mouseY + scrollAmount() < yPos + itemSize)) {
                guiGraphics.fillGradient(RenderType.guiOverlay(), xPos, yPos, xPos + itemSize, yPos + itemSize, -2130706433, -2130706433, 0);
                hoveredItem = spellsList.get(i);
                hoveredItemIndex = i;
                hoveredItemAmount = amountList.get(i);

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
    protected void renderBackground(GuiGraphics guiGraphics) {
        int itemSize = width / itemXAmount;
        float itemScale = (float) itemSize / 18;

        int i = this.isFocused() ? -1 : color;
        guiGraphics.fill(getX(), getY(), getX() + width, getY() + height, i);
        guiGraphics.blit(SPRUCE_PLANKS, getX() + 1, getY() + 1, 0, 0, 0, width - 2, height - 2, itemSize, itemSize);
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
        if (hoveredItem != null && hoveredItemAmount != -1 && hoveredItemAmount != 0 && !Screen.hasShiftDown() && pButton == 0) {
            if (screen.selectedItem != null && screen.selectedItem.selectedItem.getItem() instanceof SpellItem) {
                int index = getIndexFromSelected();
                amountList.set(index, amountList.get(index) + 1);
            }
            amountList.set(hoveredItemIndex, amountList.get(hoveredItemIndex) - 1);
            screen.selectedItem = new SelectedItem(ListIndex.SPELLS_INV, hoveredItemIndex, hoveredItem);
        }
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    public int getIndexFromSelected() {
        SpellItem spell = (SpellItem) screen.selectedItem.selectedItem.getItem();

        for (int i = 0; i < spellsList.size(); i++) {
            SpellItem check = (SpellItem) spellsList.get(i).getItem();

            if (spell.equals(check)) {
                return i;
            }
        }
        return 0;
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {

    }
}
