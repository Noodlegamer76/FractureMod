package com.noodlegamer76.fracture.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.item.InitItems;
import com.noodlegamer76.fracture.spellcrafting.wand.Wand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class WandOverlay {
    public static final ResourceLocation MANA_EMPTY = new ResourceLocation(FractureMod.MODID, "textures/hud/mana_empty.png");
    public static final ResourceLocation MANA_FULL = new ResourceLocation(FractureMod.MODID, "textures/hud/mana_full");

    public static final IGuiOverlay HUD_MANA = ((gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        int x = screenWidth;
        int y = screenHeight;

        LocalPlayer player = Minecraft.getInstance().player;
        assert player != null;
        if (player.getMainHandItem().getItem() instanceof Wand mainhand ||
        player.getOffhandItem().getItem() instanceof Wand offhand) {

            guiGraphics.blit(MANA_EMPTY, x - 120, 0, 0, 0, 120, 12, 120, 12);


           ItemStack mainWand = player.getMainHandItem();
            if (mainWand.getItem() instanceof Wand) {
                if (mainWand.getOrCreateTag().getBoolean("isCreated")) {
                    float maxMana = mainWand.getTag().getFloat("maxMana");
                    for (int i = 0; i < 10; i++) {
                        guiGraphics.blit(MANA_FULL, (x + i * 12) - 12 * 10, 0, 0, 0, 12, 12);
                    }
                    return;
                }
            }

            ItemStack offWand = player.getOffhandItem();
            if (offWand.getItem() instanceof Wand) {
                if (offWand.getOrCreateTag().getBoolean("isCreated")) {
                    float maxMana = offWand.getTag().getFloat("maxMana");
                    for (int i = 0; i < 10; i++) {
                        guiGraphics.blit(MANA_FULL, (x + i * 12) - 12 * 10, 0, 0, 0, 12, 12);
                    }
                    return;
                }

            }
        }
    });
}
