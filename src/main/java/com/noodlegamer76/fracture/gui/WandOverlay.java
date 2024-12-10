package com.noodlegamer76.fracture.gui;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.spellcrafting.wand.Wand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class WandOverlay {
    public static final ResourceLocation MANA_EMPTY = new ResourceLocation(FractureMod.MODID, "textures/hud/mana_empty.png");
    public static final ResourceLocation MANA_FULL = new ResourceLocation(FractureMod.MODID, "textures/hud/mana_full.png");

    public static final ResourceLocation RECHARGE_EMPTY = new ResourceLocation(FractureMod.MODID, "textures/hud/recharge_empty.png");
    public static final ResourceLocation RECHARGE_FULL = new ResourceLocation(FractureMod.MODID, "textures/hud/recharge_full.png");

    public static final IGuiOverlay HUD_MANA = ((gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        int x = screenWidth;
        int y = screenHeight;

        LocalPlayer player = Minecraft.getInstance().player;
        assert player != null;

        ItemStack wand = null;
        if (player.getOffhandItem().getItem() instanceof Wand) {
            wand = player.getOffhandItem();
        }
        else if (player.getMainHandItem().getItem() instanceof Wand) {
            wand = player.getMainHandItem();
        }
        if (wand == null || !wand.getOrCreateTag().getBoolean("isCreated")) {
            return;
        }

        guiGraphics.blit(MANA_EMPTY, x - 148, 8, 0, 0, 140, 16, 140, 16);

        float maxMana = wand.getTag().getFloat("maxMana");
        float currentMana = wand.getTag().getFloat("currentMana");
        float totalMana = currentMana / maxMana * 100;
        guiGraphics.blit(MANA_FULL, x - 137, 8, 0, 0, (int) (totalMana * 1.18), 16, 118, 16);


        guiGraphics.blit(RECHARGE_EMPTY, x - 148, 32, 0, 0, 140, 16, 140, 16);

        float rechargeTime = wand.getTag().getFloat("currentCastDelay");
        float lastRechargeTime = wand.getTag().getFloat("lastRechargeTime");
        float totalRecharge = rechargeTime / lastRechargeTime * 100;
        guiGraphics.blit(RECHARGE_FULL, x - 143, 32, 0, 0, (int) (totalRecharge * 1.18), 16, 118, 16);
    });
}
