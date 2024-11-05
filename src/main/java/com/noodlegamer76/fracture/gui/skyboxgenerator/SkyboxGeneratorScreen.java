package com.noodlegamer76.fracture.gui.skyboxgenerator;

import com.noodlegamer76.fracture.FractureMod;
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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;

public class SkyboxGeneratorScreen extends AbstractContainerScreen<SkyboxGeneratorMenu> {
    private ContainerData data;
    private SkyboxGeneratorEntity entity;
    private int[] previous;

    private static final ResourceLocation TEXTURE = new ResourceLocation(FractureMod.MODID, "textures/screens/skybox_generator.png");

    EditBox rotationSpeed;
    EditBox transparency;
    EditBox skybox;
    EditBox maxRenderDistance;
    EditBox renderpriority;


    public SkyboxGeneratorScreen(SkyboxGeneratorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        data = pMenu.containerData;
        entity = pMenu.getBlockEntity();


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
    protected void renderLabels(GuiGraphics guiGraphics, int pMouseX, int pMouseY) {
        guiGraphics.drawString(this.font, Component.translatable("gui.fracture.skybox_generator.render_priority"), 29, 136, -12829636, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.fracture.skybox_generator.max_render_distance"), 29, 107, -12829636, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.fracture.skybox_generator.skybox"), 29, 78, -12829636, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.fracture.skybox_generator.transparency"), 29, 49, -12829636, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.fracture.skybox_generator.rotation_speed"), 29, 20, -12829636, false);

    }

    @Override
    protected void rebuildWidgets() {
        super.rebuildWidgets();
    }

    @Override
    protected void init() {
        super.init();
        rotationSpeed = new EditBox(this.font, this.leftPos + 30, this.topPos + 29, 118, 18, Component.translatable("gui.fracture.skybox_generator.rotation_speed"));
        transparency = new EditBox(this.font, this.leftPos + 30, this.topPos + 58, 118, 18, Component.translatable("gui.fracture.skybox_generator.transparency"));
        renderpriority = new EditBox(this.font, this.leftPos + 30, this.topPos + 144, 118, 18, Component.translatable("gui.fracture.skybox_generator.render_priority"));
        skybox = new EditBox(this.font, this.leftPos + 30, this.topPos + 87, 118, 18, Component.translatable("gui.fracture.skybox_generator.skybox"));
        maxRenderDistance = new EditBox(this.font, this.leftPos + 30, this.topPos + 116, 118, 18, Component.translatable("gui.fracture.skybox_generator.max_render_distance"));

        rotationSpeed.setValue(String.valueOf(data.get(2)));
        transparency.setValue(String.valueOf(data.get(3)));
        renderpriority.setValue(String.valueOf(data.get(4)));
        skybox.setValue(String.valueOf(data.get(0)));
        maxRenderDistance.setValue(String.valueOf(data.get(6)));

        addRenderableWidget(rotationSpeed);
        addRenderableWidget(transparency);
        addRenderableWidget(renderpriority);
        addRenderableWidget(skybox);
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
            previous[0] = Integer.parseInt(skybox.getValue());
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
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        guiGraphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

    }

    @Override
    public void onClose() {
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
