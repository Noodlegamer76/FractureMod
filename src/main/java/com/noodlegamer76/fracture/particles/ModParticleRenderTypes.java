package com.noodlegamer76.fracture.particles;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.noodlegamer76.fracture.client.util.ModRenderTypes;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraftforge.client.event.RegisterShadersEvent;
import org.lwjgl.opengl.GL44;

public class ModParticleRenderTypes {
    public static ParticleRenderType VOID_RENDER_TYPE = new ParticleRenderType() {
        public void begin(BufferBuilder bufferBuilder, TextureManager p_107442_) {
            RenderSystem.setShader(() -> ModRenderTypes.skybox);
            RenderSystem.depthMask(true);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
        }

        public void end(Tesselator bufferBuilder) {
            bufferBuilder.end();
        }

        public String toString() {
            return "VOID";
        }
    };
}
