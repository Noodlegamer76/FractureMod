package com.noodlegamer76.fracture.client.util;

import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceProvider;

import java.io.IOException;

public class ExtendedShaderInstance extends ShaderInstance {

    public ExtendedShaderInstance(ResourceProvider pResourceProvider, ResourceLocation shaderLocation, VertexFormat pVertexFormat) throws IOException {
        super(pResourceProvider, shaderLocation, pVertexFormat);
    }

    @Override
    public void apply() {
        Uniform inverseProjectionMat = getUniform("inverseProjectionMat");
        if (inverseProjectionMat != null) {
            inverseProjectionMat.set(RenderSystem.getProjectionMatrix().invert());
        }

        Uniform inverseModelViewMat = getUniform("inverseModelViewMat");
        if (inverseModelViewMat != null) {
            inverseModelViewMat.set(RenderSystem.getModelViewMatrix().invert());
        }
        super.apply();
    }
}
