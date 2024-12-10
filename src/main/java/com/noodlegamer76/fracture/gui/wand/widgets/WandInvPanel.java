package com.noodlegamer76.fracture.gui.wand.widgets;

import com.noodlegamer76.fracture.gui.AbstractGuiPanel;
import com.noodlegamer76.fracture.gui.wand.WandMenu;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;

import java.lang.reflect.Field;

public class WandInvPanel extends AbstractGuiPanel {
    public WandInvPanel(int pX, int pY, int pWidth, int pHeight, Component pMessage, ResourceLocation texture, Font font, WandMenu menu) {
        super(pX, pY, pWidth, pHeight, pMessage, texture, font, menu);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    public void setSlotPositions(int x, int y, int width, int height) {
        int slotWidth = 18;
        int slotHeight = 18;

        int slotsX = width / slotWidth;
        int slotsY = height / slotHeight;
        int widthOffset = width % 18 / 2;
        int heightOffset = height % 18 / 2;

        try {
            Field xField = Slot.class.getDeclaredField("x");
            Field yField = Slot.class.getDeclaredField("y");
            xField.setAccessible(true);
            yField.setAccessible(true);

            int slotsSet = 36;
            for (int row = 0; row < slotsY; row++) {
                for (int col = 0; col < slotsX; col++) {
                    int xPos = x + col * slotWidth + widthOffset;
                    int yPos = y + row * slotHeight + heightOffset;

                    if (slotsSet < this.menu.slots.size()) {
                        Slot slot = this.menu.getSlot(slotsSet);
                        xField.setInt(slot, xPos);
                        yField.setInt(slot, yPos);
                    }

                    slotsSet++;

                    if (slotsSet >= 36 + this.menu.customSlots.size())
                        return;
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
