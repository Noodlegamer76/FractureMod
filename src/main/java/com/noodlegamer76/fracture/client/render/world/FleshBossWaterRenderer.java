package com.noodlegamer76.fracture.client.render.world;

import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.client.util.ModRenderTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public class FleshBossWaterRenderer {
    public static void renderQuad(PoseStack poseStack) {
        Matrix4f matrix = poseStack.last().pose();

        float size = 512.0f;

        float x0 = -size;
        float x1 = size;
        float z0 = -size;
        float z1 = size;

        Vec3 worldPos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();

        float wx0 = (float) (worldPos.x + x0);
        float wx1 = (float) (worldPos.x + x1);
        float wz0 = (float) (worldPos.z + z0);
        float wz1 = (float) (worldPos.z + z1);

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        RenderSystem.enableDepthTest();

        ShaderInstance shader = ModRenderTypes.fluidFlow;
        RenderSystem.setShader(() -> shader);

        Uniform noiseValues = shader.getUniform("NoiseValues");
        if (noiseValues != null) {
            noiseValues.set(1.0f, 1.0f);
        }

        RenderSystem.setShaderTexture(0, ResourceLocation.withDefaultNamespace("textures/block/water_still.png"));
        RenderSystem.setShaderTexture(1, ResourceLocation.fromNamespaceAndPath(FractureMod.MODID, "textures/environment/fog_noise.png"));

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder buffer = tesselator.getBuilder();

        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

        buffer.vertex(matrix, x0, 0, z0).uv(wx0, wz0).endVertex();
        buffer.vertex(matrix, x1, 0, z0).uv(wx1, wz0).endVertex();
        buffer.vertex(matrix, x1, 0, z1).uv(wx1, wz1).endVertex();
        buffer.vertex(matrix, x0, 0, z1).uv(wx0, wz1).endVertex();

        BufferUploader.drawWithShader(buffer.end());

        RenderSystem.disableDepthTest();
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
    }
}
