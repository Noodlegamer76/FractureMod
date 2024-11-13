package com.noodlegamer76.fracture.client.util;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import org.lwjgl.opengl.GL44;

public class RenderFog {
    public static void renderFog(PoseStack poseStack, BlockPos pos) {

        RenderSystem.setShader(() -> ModRenderTypes.fog);
        GL44.glUseProgram(ModRenderTypes.fog.getId());

        RenderSystem.activeTexture(GL44.GL_TEXTURE0);
        RenderSystem.bindTexture(Minecraft.getInstance().getMainRenderTarget().getDepthTextureId());
        RenderSystem.glUniform1i(GlStateManager._glGetUniformLocation(ModRenderTypes.fog.getId(),  "Depth"), 0);

        Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();

        RenderCubeAroundPlayer.render(poseStack, null);



    }
}
