package com.noodlegamer76.fracture.gui.wand;

import com.mojang.blaze3d.vertex.PoseStack;
import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.gui.wand.widgets.ScrollableSpellList;
import com.noodlegamer76.fracture.gui.wand.widgets.SelectedItem;
import com.noodlegamer76.fracture.gui.wand.widgets.WandInvPanel;
import com.noodlegamer76.fracture.gui.wand.widgets.WandStatsWidget;
import com.noodlegamer76.fracture.network.PacketHandler;
import com.noodlegamer76.fracture.network.SpellInvPacket;
import com.noodlegamer76.fracture.spellcrafting.data.SpellHelper;
import com.noodlegamer76.fracture.spellcrafting.spells.item.SpellItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.HashMap;

import static com.noodlegamer76.fracture.spellcrafting.data.SpellHelper.getSpellItemFromName;

public class WandScreen extends AbstractContainerScreen<WandMenu> {
    private final static HashMap<String, Object> guistate = WandMenu.guistate;
    public ArrayList<ItemStack> spellsList = new ArrayList<>();
    public ArrayList<Integer> amountList = new ArrayList<>();

    public ArrayList<ItemStack> wandSpellsList = new ArrayList<>();
    private final Level level;
    private final int x, y, z;
    private final Player entity;
    public WandStatsWidget wandStatsWidget;
    public WandInvPanel wandInvPanel;

    private int scaledWidth;
    private int scaledHeight;
    private double guiScale;

    public SelectedItem selectedItem;

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

        addSpellsToList();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);

        if (selectedItem != null) {
            PoseStack poseStack = guiGraphics.pose();
            float itemScale = 1.5f;

            poseStack.pushPose();

            poseStack.translate(mouseX - (8 * itemScale), mouseY - (8 * itemScale), 100);
            poseStack.scale(itemScale, itemScale, itemScale);

            guiGraphics.renderFakeItem(selectedItem.selectedItem, 0, 0);

            poseStack.popPose();
        }
    }

    public void addSpellsToList() {
        for (int i = 0; i < SpellItem.spellItems.size(); i++) {
            spellsList.add(SpellItem.spellItems.get(i).getDefaultInstance());
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        int smallGapTop = (int) Math.round(imageHeight * 0.025);
        int smallGapLeft = (int) Math.round(imageWidth * 0.025);
        int widthThird = (imageWidth / 3) - (smallGapLeft * 3);
        int availableHeight = imageHeight - (int) (imageHeight * 0.1);
        int imageSizeSquare = availableHeight / 3;
        if (menu.getWand() == null) {
            return;
        }

        wandStatsWidget.render(guiGraphics, mouseX, mouseY, partialTicks, menu.getWand().getTag().getFloat("currentMana"));


        guiGraphics.blit(SQUARE_PANEL, leftPos + smallGapLeft, topPos + imageSizeSquare + smallGapTop * 2, 0, 0, widthThird, imageSizeSquare, widthThird, imageSizeSquare);
        guiGraphics.blit(SQUARE_PANEL, leftPos + smallGapLeft, topPos + (imageSizeSquare * 2) + smallGapTop * 3, 0, 0, widthThird, imageSizeSquare, widthThird, imageSizeSquare);

        guiGraphics.blit(SQUARE_PANEL, leftPos + widthThird + smallGapLeft * 2, topPos + smallGapTop * 5, 0, 0,
                widthThird + smallGapLeft * 5,
                imageHeight - smallGapTop * 6,
                widthThird + smallGapLeft * 5,
                imageHeight - smallGapTop * 6);
    }


    @Override
    public void init() {
        super.init();
        setupWidgets();
        amountList.addAll(SpellHelper.amounts);

        if (menu.getWand().getTag() != null) {
            CompoundTag spells = menu.getWand().getTag().getCompound("spells");

            for (int i = 0; i < menu.getWand().getTag().getInt("capacity"); i++) {
                String spellName = spells.getString(String.valueOf(i));
                wandSpellsList.add(getSpellItemFromName(spellName));
            }
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
        PacketHandler.sendToServer(new SpellInvPacket(wandSpellsList, menu.hand == 0 ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND));

        super.onClose();
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    public void resize(Minecraft pMinecraft, int pWidth, int pHeight) {
        super.resize(pMinecraft, pWidth, pHeight);

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

        wandInvPanel = new WandInvPanel(leftPos + widthThird + smallGapLeft * 2,
                topPos + smallGapTop * 5,
                widthThird + smallGapLeft * 5,
                imageHeight - smallGapTop * 6,
                Component.literal("Spell"),
                wandSpellsList,
                this);

        addRenderableWidget(new ScrollableSpellList(
                leftPos + widthThird * 2 + smallGapLeft * 8,
                topPos + smallGapTop,
                widthThird,
                (int) ((double) imageHeight / 2 - smallGapTop * 1.5) * 2,
                Component.literal("Spells"),
                spellsList,
                amountList,
                this));

        addRenderableWidget(wandInvPanel);
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

