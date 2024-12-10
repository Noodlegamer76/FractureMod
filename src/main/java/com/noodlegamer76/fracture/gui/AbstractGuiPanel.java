package com.noodlegamer76.fracture.gui;

import com.noodlegamer76.fracture.gui.wand.WandMenu;
import com.noodlegamer76.fracture.spellcrafting.wand.Wand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;

public abstract class AbstractGuiPanel {
    public double guiScale = Minecraft.getInstance().getWindow().getGuiScale();
    public int x, y;
    private static ResourceLocation panelTexture;
    public Font font;
    public int width, height;
    public WandMenu menu;
    public AbstractGuiPanel(int pX, int pY, int pWidth, int pHeight, Component pMessage, ResourceLocation texture, Font font, WandMenu menu) {
        this.menu = menu;
        this.x = pX;
        this.y = pY;
        this.width = pWidth;
        this.height = pHeight;
        this.font = font;
        panelTexture = texture;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }


    public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        guiGraphics.blit(panelTexture, x, y, 0, 0, width, height, width, height);
    }

    public ItemStack getWand() {
        ItemStack item = null;
        if (Minecraft.getInstance().player.getOffhandItem().getItem() instanceof Wand) {
            item = Minecraft.getInstance().player.getOffhandItem();
        }
        else if (Minecraft.getInstance().player.getMainHandItem().getItem() instanceof Wand) {
            item = Minecraft.getInstance().player.getMainHandItem();
        }
        return item;
    }
}
