package com.noodlegamer76.fracture.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import com.noodlegamer76.fracture.gui.imgui.imgui.ImGuiRenderer;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.flag.ImGuiConfigFlags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderSystem.class)
public abstract class RenderSystemMixin {

    @Inject( at = @At( value = "TAIL" ), method = "initRenderer(IZ)V" )
    private static void initRenderer(int flags, boolean enable, CallbackInfo cbi) {
        RenderSystem.assertOnRenderThread();
        LogUtils.getLogger().info("Initializing ImGui");
        ImGuiRenderer.getInstance().init(RenderSystemMixin::noodle_Engine$loadSettings);
    }

    @Unique
    private static void noodle_Engine$loadSettings() {
        ImGuiIO io = ImGui.getIO();
        // ImGui.getIO().addConfigFlags(ImGuiConfigFlags.ViewportsEnable);
        io.addConfigFlags(ImGuiConfigFlags.DockingEnable);
    }



    @Inject( at = @At( value = "HEAD"), method = "flipFrame(J)V" )
    private static void flipFrame(long pWindowId, CallbackInfo ci) {
        RenderSystem.recordRenderCall(() -> ImGuiRenderer.getInstance().render());
    }
}
