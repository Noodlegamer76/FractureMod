package com.noodlegamer76.fracture.gui.wand;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.gui.wand.widgets.WandInvPanel;
import com.noodlegamer76.fracture.gui.wand.widgets.WandStatsWidget;
import com.noodlegamer76.fracture.mixin.MixinSlot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.level.Level;

import java.lang.reflect.Field;
import java.util.HashMap;

public class WandScreen extends AbstractContainerScreen<WandMenu> {
    private final static HashMap<String, Object> guistate = WandMenu.guistate;
    private final Level level;
    private final int x, y, z;
    private final Player entity;
    public WandStatsWidget wandStatsWidget;
    public WandInvPanel wandInvPanel;

    private int scaledWidth;
    private int scaledHeight;
    private double guiScale;

    private static final ResourceLocation SQUARE_PANEL = new ResourceLocation(FractureMod.MODID, "textures/screens/basic_background.png");
    private static final ResourceLocation TEXTURE = new ResourceLocation(FractureMod.MODID, "textures/screens/slot.png");

    public WandScreen(WandMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.level = container.level;
        this.x = container.x;
        this.y = container.y;
        this.z = container.z;
        this.entity = container.entity;

        // Get initial screen dimensions
        int screenWidth = Minecraft.getInstance().getWindow().getScreenWidth();
        int screenHeight = Minecraft.getInstance().getWindow().getScreenHeight();

        int[] clampedDimensions = clampTo16x9(screenWidth, screenHeight);
        scaledWidth = clampedDimensions[0];
        scaledHeight = clampedDimensions[1];

        this.guiScale = Minecraft.getInstance().getWindow().getGuiScale();
        this.imageWidth = (int) Math.round(scaledWidth / guiScale);
        this.imageHeight = (int) Math.round(scaledHeight / guiScale);

        this.leftPos = (screenWidth - imageWidth) / 2;
        this.topPos = (screenHeight - imageHeight) / 2;

        //System.out.println("Initial Screen dimensions: " + screenWidth + "x" + screenHeight);
        //System.out.println("Initial GUI Scale: " + guiScale);
        //System.out.println("Clamped dimensions: " + imageWidth + "x" + imageHeight);
        //System.out.println("Initial Position: " + leftPos + ", " + topPos);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }



    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        int smallGapTop = (int) Math.round(imageHeight * 0.025);
        int smallGapLeft = (int) Math.round(imageWidth * 0.025);
        int widthThird = (imageWidth / 3) - (smallGapLeft * 3);
        int availableHeight = imageHeight - (int) (imageHeight * 0.1);
        int imageSizeSquare = availableHeight / 3;

        wandStatsWidget.render(guiGraphics, mouseX, mouseY, partialTicks, menu.getWand().getTag().getFloat("currentMana"));

        wandInvPanel.render(guiGraphics, mouseX, mouseY, partialTicks);


        guiGraphics.blit(SQUARE_PANEL, leftPos + smallGapLeft, topPos + imageSizeSquare + smallGapTop * 2, 0, 0, widthThird, imageSizeSquare, widthThird, imageSizeSquare);
        guiGraphics.blit(SQUARE_PANEL, leftPos + smallGapLeft, topPos + (imageSizeSquare * 2) + smallGapTop * 3, 0, 0, widthThird, imageSizeSquare, widthThird, imageSizeSquare);

        guiGraphics.blit(SQUARE_PANEL, leftPos + widthThird + smallGapLeft * 2, topPos + smallGapTop * 5, 0, 0,
                widthThird + smallGapLeft * 5,
                imageHeight - smallGapTop * 6,
                widthThird + smallGapLeft * 5,
                imageHeight - smallGapTop * 6);

        guiGraphics.blit(SQUARE_PANEL, leftPos + widthThird * 2 + smallGapLeft * 8, topPos + smallGapTop, 0, 0,
                widthThird,
                (int) ((double) imageHeight / 2 - smallGapTop * 1.5),
                widthThird,
                (int) ((double) imageHeight / 2 - smallGapTop * 1.5));

        guiGraphics.blit(SQUARE_PANEL, leftPos + widthThird * 2 + smallGapLeft * 8, topPos + (imageHeight / 2) + (smallGapTop / 2), 0, 0,
                widthThird,
                (int) ((double) imageHeight / 2 - smallGapTop * 1.5),
                widthThird,
                (int) ((double) imageHeight / 2 - smallGapTop * 1.5));

        for (int i = 0; i < 36 + menu.customSlots.size(); i++) {
            Slot slot = this.menu.getSlot(i);
            final int x = leftPos + slot.x - 1;
            final int y = topPos + slot.y - 1;
            guiGraphics.blit(TEXTURE, x, y, 0, 0, 18, 18, 18, 18);
        }
    }

    @Override
    public void init() {
        super.init();
        setupWidgets();
        setInventorySlots();
    }

    public void setInventorySlots() {
        float smallGapTop = (int) Math.round(imageHeight * 0.025);
        float smallGapLeft = (int) Math.round(imageWidth * 0.025);
        float widthThird = (imageWidth / 3) - (smallGapLeft * 3);
        float heightThird = (imageHeight / 3) - (smallGapTop * 3);


        setSlotPositions(
                (int) (imageWidth - widthThird - smallGapLeft),
                (int) (imageHeight - imageHeight / 2 + smallGapTop / 2),
                (int) widthThird,
                (int) ((double) imageHeight / 2 - smallGapTop * 1.5));


        wandInvPanel.setSlotPositions(
                (int) (imageWidth - widthThird - smallGapLeft * 2 - (smallGapLeft * 5) - widthThird),
                (int) (imageHeight - (smallGapTop * 4) - (heightThird * 3) + smallGapTop * 0.25),
                (int) (widthThird + smallGapLeft * 5),
                (int) (imageHeight - smallGapTop * 6));


    }

    public void setSlotPositions(int startX, int startY, int slotAreaWidth, int slotAreaHeight) {
        int slotWidth = 18;
        int slotHeight = 18;

        int slotsX = slotAreaWidth / slotWidth;
        int slotsY = slotAreaHeight / slotHeight;
        int widthOffset = slotAreaWidth % 18 / 2;
        int heightOffset = slotAreaHeight % 18 / 2;

        try {
            Field xField = Slot.class.getDeclaredField("x");
            Field yField = Slot.class.getDeclaredField("y");
            xField.setAccessible(true);
            yField.setAccessible(true);

            int slotsSet = 0;
            for (int row = 0; row < slotsY; row++) {
                for (int col = 0; col < slotsX; col++) {
                    int xPos = startX + col * slotWidth + widthOffset;
                    int yPos = startY + row * slotHeight + heightOffset;

                    if (slotsSet < this.menu.slots.size()) {
                        Slot slot = this.menu.getSlot(slotsSet);
                        xField.setInt(slot, xPos);
                        yField.setInt(slot, yPos);
                    }

                    slotsSet++;

                    if (slotsSet >= 36)
                        return;
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
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
    public void resize(Minecraft pMinecraft, int pWidth, int pHeight) {
        super.resize(pMinecraft, pWidth, pHeight);
        //setInventorySlots();

        int screenWidth = pMinecraft.getWindow().getWidth();
        int screenHeight = pMinecraft.getWindow().getHeight();

        int[] clampedDimensions = clampTo16x9(screenWidth, screenHeight);
        scaledWidth = clampedDimensions[0];
        scaledHeight = clampedDimensions[1];

        guiScale = pMinecraft.getWindow().getGuiScale();
        imageWidth = (int) Math.round(scaledWidth / guiScale);
        imageHeight = (int) Math.round(scaledHeight / guiScale);

        leftPos = (width - imageWidth) / 2;
        topPos = (height - imageHeight) / 2;


        clearWidgets();
        setupWidgets();

        setInventorySlots();
    }

    private void setupWidgets() {
        int availableHeight = imageHeight - (int) (imageHeight * 0.1);
        int smallGapTop = (int) Math.round(imageHeight * 0.025);
        int smallGapLeft = (int) Math.round(imageWidth * 0.025);
        int imageSizeSquare = availableHeight / 3;
        int widthThird = (imageWidth / 3) - (smallGapLeft * 3);

        wandStatsWidget = new WandStatsWidget(
                leftPos + smallGapLeft,
                topPos + smallGapTop,
                widthThird,
                imageSizeSquare,
                Component.literal(""),
                SQUARE_PANEL,
                font,
                menu
        );

        wandInvPanel = new WandInvPanel(
                leftPos + widthThird + smallGapLeft * 2,
                topPos + smallGapTop * 5,
                widthThird + smallGapLeft * 5,
                imageHeight - smallGapTop * 6,
                Component.literal(""),
                SQUARE_PANEL,
                font,
                menu
        );

    }

    public static int[] clampTo16x9(int screenWidth, int screenHeight) {
        double targetAspectRatio = 16.0 / 9.0;

        // Calculate the aspect ratio of the current screen dimensions
        double currentAspectRatio = (double) screenWidth / screenHeight;

        if (currentAspectRatio > targetAspectRatio) {
            // Screen is wider than 16:9, clamp width based on height
            screenWidth = (int) (screenHeight * targetAspectRatio);
        } else if (currentAspectRatio < targetAspectRatio) {
            // Screen is taller than 16:9, clamp height based on width
            screenHeight = (int) (screenWidth / targetAspectRatio);
        }

        return new int[]{screenWidth, screenHeight};
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
    }
}
