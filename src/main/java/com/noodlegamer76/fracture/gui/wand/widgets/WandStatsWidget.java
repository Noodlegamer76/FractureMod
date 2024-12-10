package com.noodlegamer76.fracture.gui.wand.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import com.noodlegamer76.fracture.gui.AbstractGuiPanel;
import com.noodlegamer76.fracture.gui.wand.WandMenu;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.awt.*;

public class WandStatsWidget extends AbstractGuiPanel {
    ItemStack wand;
    Component mana;
    Component manaRechargeSpeed;
    Component rechargeSpeed;
    Component castDelay;
    Component capacity;
    Component casts;
    public WandStatsWidget(int pX, int pY, int pWidth, int pHeight, Component pMessage, ResourceLocation texture, Font font, WandMenu menu) {
        super(pX, pY, pWidth, pHeight, pMessage, texture, font, menu);
        wand = getWand();
        mana = Component.literal("Mana: " + wand.getTag().getFloat("currentMana") + "/" + wand.getTag().getInt("maxMana"));
        manaRechargeSpeed = Component.literal("ManaRechargeSpeed: " + wand.getTag().getFloat("manaRechargeSpeed"));
        rechargeSpeed = Component.literal("RechargeSpeed: " + wand.getTag().getFloat("rechargeTime"));
        capacity = Component.literal("Capacity: " + wand.getTag().getInt("capacity"));
        casts = Component.literal("Casts: " + wand.getTag().getInt("casts"));
    }

    public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick, float currentMana) {
        super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
        if (wand == null) {
            return;
        }

        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        float scale = (float) width / 175;

        poseStack.translate(x + (int) (width * 0.025f), y + (int) (height * 0.025f), 0);
        poseStack.scale(scale, scale, scale);
        poseStack.translate(-(x + (int) (width * 0.025f)), -(y + (int) (height * 0.025f)), 0);
        guiGraphics.drawString(font, Component.literal("Mana: " + currentMana + "/" + wand.getTag().getInt("maxMana")), x + (int) (width * 0.025f), y + (int) (height * 0.025f), Color.WHITE.getRGB());
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(x + (int) (width * 0.025f), y + (int) (height * 0.175f), 0);
        poseStack.scale(scale, scale, scale);
        poseStack.translate(-(x + (int) (width * 0.025f)), -(y + (int) (height * 0.175f)), 0);
        guiGraphics.drawString(font, manaRechargeSpeed, x + (int) (width * 0.025f), y + (int) (height * 0.175f), Color.WHITE.getRGB());
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(x + (int) (width * 0.025f), y + (int) (height * 0.325f), 0);
        poseStack.scale(scale, scale, scale);
        poseStack.translate(-(x + (int) (width * 0.025f)), -(y + (int) (height * 0.325f)), 0);
        guiGraphics.drawString(font, rechargeSpeed, x + (int) (width * 0.025f), y + (int) (height * 0.325f), Color.WHITE.getRGB());
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(x + (int) (width * 0.025f), y + (int) (height * 0.475f), 0);
        poseStack.scale(scale, scale, scale);
        poseStack.translate(-(x + (int) (width * 0.025f)), -(y + (int) (height * 0.475f)), 0);
        guiGraphics.drawString(font, capacity, x + (int) (width * 0.025f), y + (int) (height * 0.475f), Color.WHITE.getRGB());
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(x + (int) (width * 0.025f), y + (int) (height * 0.625f), 0);
        poseStack.scale(scale, scale, scale);
        poseStack.translate(-(x + (int) (width * 0.025f)), -(y + (int) (height * 0.625f)), 0);
        guiGraphics.drawString(font, casts, x + (int) (width * 0.025f), y + (int) (height * 0.625f), Color.WHITE.getRGB());
        poseStack.popPose();
    }
}
