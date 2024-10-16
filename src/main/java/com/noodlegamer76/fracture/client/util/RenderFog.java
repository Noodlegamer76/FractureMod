package com.noodlegamer76.fracture.client.util;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.noodlegamer76.fracture.event.ShaderEvents;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import org.lwjgl.opengl.GL44;

public class RenderFog {
    public static void renderFog(PoseStack poseStack, BlockPos pos) {

        RenderSystem.setShader(() -> ShaderEvents.fog);
        GL44.glUseProgram(ShaderEvents.fog.getId());

        RenderSystem.activeTexture(GL44.GL_TEXTURE0);
        RenderSystem.bindTexture(Minecraft.getInstance().getMainRenderTarget().getDepthTextureId());
        RenderSystem.glUniform1i(GlStateManager._glGetUniformLocation(ShaderEvents.fog.getId(),  "Depth"), 0);

        Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();

        RenderCubeAroundPlayer.render(poseStack, null);



    }
}
