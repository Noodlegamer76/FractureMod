package com.noodlegamer76.fracture.gui.skyboxgenerator;

import com.noodlegamer76.fracture.entity.block.SkyboxGeneratorEntity;
import com.noodlegamer76.fracture.network.PacketHandler;
import com.noodlegamer76.fracture.network.SSkyboxGeneratorPacket;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;

public class SkyboxGeneratorScreen extends AbstractContainerScreen<SkyboxGeneratorMenu> {
    private ContainerData data;
    private SkyboxGeneratorEntity entity;
    private int[] previous;

    EditBox rotationSpeed;
    EditBox transparency;
    EditBox minimumRenderDistance;
    EditBox maxRenderDistance;
    EditBox renderpriority;


    public SkyboxGeneratorScreen(SkyboxGeneratorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        data = pMenu.containerData;
        entity = pMenu.getBlockEntity();

        if (data == null) {
            System.out.println("data null");
        }
        else {
            System.out.println("Data not null");
        }
        if (entity == null) {
            System.out.println("entity null");
        }
        else {
            System.out.println("entity not null");
        }
        previous = new int[]{
                menu.containerData.get(0),
                menu.containerData.get(1),
                menu.containerData.get(2),
                menu.containerData.get(3),
                menu.containerData.get(4),
                menu.containerData.get(5),
                menu.containerData.get(6),
        };

        //addRenderableWidget(new StringWidget(Component.literal(String.valueOf(data.get(5))), this.font));
    }

    @Override
    protected void rebuildWidgets() {
        super.rebuildWidgets();
    }

    @Override
    protected void init() {
        super.init();
        rotationSpeed = new EditBox(this.font, this.leftPos + 30, this.topPos + 5, 118, 18, Component.translatable("gui.fracture.skyboxgenerator.RotationSpeed"));
        transparency = new EditBox(this.font, this.leftPos + 30, this.topPos + 27, 118, 18, Component.translatable("gui.fracture.skyboxgenerator.Transparency"));
        renderpriority = new EditBox(this.font, this.leftPos + 30, this.topPos + 49, 118, 18, Component.translatable("gui.fracture.skyboxgenerator.RenderPriority"));
        minimumRenderDistance = new EditBox(this.font, this.leftPos + 30, this.topPos + 71, 118, 18, Component.translatable("gui.fracture.skyboxgenerator.RotationSpeed"));
        maxRenderDistance = new EditBox(this.font, this.leftPos + 30, this.topPos + 93, 118, 18, Component.translatable("gui.fracture.skyboxgenerator.RotationSpeed"));

        rotationSpeed.setValue(String.valueOf(data.get(2)));
        transparency.setValue(String.valueOf(data.get(3)));
        renderpriority.setValue(String.valueOf(data.get(4)));
        minimumRenderDistance.setValue(String.valueOf(data.get(5)));
        maxRenderDistance.setValue(String.valueOf(data.get(6)));

        addRenderableWidget(rotationSpeed);
        addRenderableWidget(transparency);
        addRenderableWidget(renderpriority);
        addRenderableWidget(minimumRenderDistance);
        addRenderableWidget(maxRenderDistance);
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        try {
            previous[2] = Integer.parseInt(rotationSpeed.getValue());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + e.getMessage());
        }
        try {
            previous[3] = Integer.parseInt(transparency.getValue());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + e.getMessage());
        }
        try {
            previous[4] = Integer.parseInt(renderpriority.getValue());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + e.getMessage());
        }
        try {
            previous[5] = Integer.parseInt(minimumRenderDistance.getValue());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + e.getMessage());
        }
        try {
            previous[6] = Integer.parseInt(maxRenderDistance.getValue());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + e.getMessage());
        }

    }

    @Override
    public void renderBackground(GuiGraphics pGuiGraphics) {
        super.renderBackground(pGuiGraphics);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        pGuiGraphics.drawString(font, String.valueOf(data.get(5)), imageWidth / 2, imageHeight / 2, 0);
    }

    @Override
    public void onClose() {
        previous[5] += 1;
        super.onClose();

        PacketHandler.sendToServer(new SSkyboxGeneratorPacket(new int[] {
                previous[0],
                previous[1],
                previous[2],
                previous[3],
                previous[4],
                previous[5],
                previous[6],
        }, entity.getBlockPos()));
    }
}
