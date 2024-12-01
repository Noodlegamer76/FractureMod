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

        guiGraphics.blit(MANA_EMPTY, x - 120, 0, 0, 0, 120, 12, 120, 12);

        float maxMana = wand.getTag().getFloat("maxMana");
        float currentMana = wand.getTag().getFloat("currentMana");
        float totalMana = currentMana / maxMana * 100;
        guiGraphics.blit(MANA_FULL, x - 120, 0, 0, 0, (int) (totalMana * 1.2), 12, 120, 12);

        guiGraphics.blit(RECHARGE_EMPTY, x - 120, 15, 0, 0, 120, 12, 120, 12);

        float rechargeTime = wand.getTag().getFloat("currentCastDelay");
        float lastRechargeTime = wand.getTag().getFloat("lastRechargeTime");
        float totalRecharge = rechargeTime / lastRechargeTime * 100;
        guiGraphics.blit(RECHARGE_FULL, x - 120, 15, 0, 0, (int) (totalRecharge * 1.2), 12, 120, 12);


    });
}
