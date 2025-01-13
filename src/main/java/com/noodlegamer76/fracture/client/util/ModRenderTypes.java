package com.noodlegamer76.fracture.client.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.noodlegamer76.fracture.FractureMod;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.io.IOException;

@OnlyIn(Dist.CLIENT)
public class ModRenderTypes extends RenderStateShard{
    public static ShaderInstance fog;
    public static ShaderInstance frostedGlass;
    public static ShaderInstance skybox;
    public static ShaderInstance normal;


    public static final RenderType FOG_RENDERTYPE = RenderType.create(
            "fog",
            DefaultVertexFormat.POSITION,
            VertexFormat.Mode.QUADS,
            256,
            true,
            true,
            RenderType.CompositeState.builder()
                    .setShaderState(new RenderStateShard.ShaderStateShard(() -> fog))
                    .setTextureState(NO_TEXTURE) // No texture required
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY) // Use translucent
                    .setCullState(NO_CULL) // Disable culling for visibility
                    .setDepthTestState(NO_DEPTH_TEST) // No depth test
                    .setWriteMaskState(COLOR_WRITE)
                    .createCompositeState(false)
    );

    public static final RenderType SKYBOX = RenderType.create(
            "skybox",
            DefaultVertexFormat.POSITION,
            VertexFormat.Mode.QUADS,
            100000,
            true,
            true,
            RenderType.CompositeState.builder()
                    .setShaderState(new RenderStateShard.ShaderStateShard(() -> skybox))
                    .createCompositeState(true)
    );

    public static final RenderType FROSTED_GLASS = RenderType.create(
            "frosted_glass",
            DefaultVertexFormat.POSITION,
            VertexFormat.Mode.QUADS,
            100000,
            true,
            true,
            RenderType.CompositeState.builder()
                    .setShaderState(new ShaderStateShard(() -> frostedGlass))
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .setCullState(NO_CULL).setLightmapState(LIGHTMAP)
                    .setOverlayState(OVERLAY)
                    .createCompositeState(true)
    );

    public ModRenderTypes(String pName, Runnable pSetupState, Runnable pClearState) {
        super(pName, pSetupState, pClearState);
    }
}
