package com.noodlegamer76.fracture.gui.guibuilder;

import com.noodlegamer76.fracture.FractureMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.HashMap;

public class GuiBuilderScreen extends AbstractContainerScreen<GuiBuilderMenu> {
    private final static HashMap<String, Object> guistate = GuiBuilderMenu.guistate;
    private final Level level;
    private final int x, y, z;
    private final Player entity;
    private int guiScale = Minecraft.getInstance().options.guiScale().get();

    public GuiBuilderScreen(GuiBuilderMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.level = container.level;
        this.x = container.x;
        this.y = container.y;
        this.z = container.z;
        this.entity = container.entity;

        int width = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        int height = Minecraft.getInstance().getWindow().getGuiScaledHeight();

        int[] clampedDimensions = clampTo16x9(width, height);
        System.out.println("Clamped Width: " + clampedDimensions[0]);
        System.out.println("Clamped Height: " + clampedDimensions[1]);


        imageWidth = clampedDimensions[0] - 20;
        imageHeight = clampedDimensions[1] - 20;
    }

    private static final ResourceLocation SQUARE_PANEL = new ResourceLocation(FractureMod.MODID, "textures/screens/wand/square_panel.png");

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
    }

    @Override
    public boolean keyPressed(int key, int b, int c) {
        if (key == 256) {
            this.minecraft.player.closeContainer();
            return true;
        }
        return super.keyPressed(key, b, c);
    }

    @Override
    public void containerTick() {
        super.containerTick();
    }



    @Override
    public void onClose() {
        super.onClose();
        //Minecraft.getInstance().keyboardHandler.setSendRepeatsToGui(false);
    }

    @Override
    public void init() {
        super.init();
        //this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
    }

    public static int[] clampTo16x9(int screenWidth, int screenHeight) {
        int width = screenWidth;
        int height = (int) (width * 9.0 / 16.0); // Calculate height based on 16:9 ratio

        // If height exceeds screen bounds, adjust width instead
        if (height > screenHeight) {
            height = screenHeight;
            width = (int) (height * 16.0 / 9.0); // Recalculate width based on height
        }

        return new int[]{width, height};
    }
}
