package com.noodlegamer76.fracture.gui.imgui.imgui;

import imgui.ImGui;
import imgui.ImGuiIO;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public abstract class ImGuiScreen extends Screen {

    // Modifier key states
    private boolean isCtrlPressed = false;
    private boolean isShiftPressed = false;
    private boolean isAltPressed = false;

    public ImGuiScreen() {
        super(Component.literal("ImGui"));
    }

    public void init() {
    }

    @Override
    public abstract void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick);

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (pKeyCode == 256) {
            onClose();
            return true;
        }
        ImGuiIO io = ImGui.getIO();
        updateModifiers(pModifiers);
        io.setKeysDown(pKeyCode, true);

        return true;
    }

    @Override
    public boolean keyReleased(int pKeyCode, int pScanCode, int pModifiers) {
        ImGuiIO io = ImGui.getIO();
        updateModifiers(pModifiers);
        io.setKeysDown(pKeyCode, false);

        return true;
    }

    @Override
    public boolean charTyped(char pCodePoint, int pModifiers) {
        ImGuiIO io = ImGui.getIO();
        io.addInputCharacter(pCodePoint);
        return true;
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        ImGuiIO io = ImGui.getIO();
        io.setMouseWheel((float) pDelta);
        return true;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        //mouse dragging and clicking already working
        return true;
    }

    private void updateModifiers(int pModifiers) {
        isCtrlPressed = (pModifiers & 0x0002) != 0;
        isShiftPressed = (pModifiers & 0x0001) != 0;
        isAltPressed = (pModifiers & 0x0004) != 0;

        ImGuiIO io = ImGui.getIO();
        io.setKeyCtrl(isCtrlPressed);
        io.setKeyShift(isShiftPressed);
        io.setKeyAlt(isAltPressed);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
