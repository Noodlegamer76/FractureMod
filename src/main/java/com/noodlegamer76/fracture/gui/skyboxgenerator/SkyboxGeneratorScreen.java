package com.noodlegamer76.fracture.gui.skyboxgenerator;

import com.noodlegamer76.fracture.entity.block.SkyboxGeneratorEntity;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
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

    public SkyboxGeneratorScreen(SkyboxGeneratorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
      //  data = pMenu.getContainerData()
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

        //addRenderableWidget(new StringWidget(Component.literal(String.valueOf(data.get(5))), this.font));
    }

    @Override
    protected void rebuildWidgets() {
        super.rebuildWidgets();
    }

    @Override
    public void renderBackground(GuiGraphics pGuiGraphics) {
        super.renderBackground(pGuiGraphics);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        pGuiGraphics.drawString(font, String.valueOf(menu.getNumber()), imageWidth / 2, imageHeight / 2, 0);
    }
}
