package com.noodlegamer76.fracture.client.util;

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
public class ModRenderTypes {
    public static ShaderInstance fog;
    public static ShaderInstance skybox;


    public static final RenderType FOG_RENDERTYPE = RenderType.create(
            "fog",
            DefaultVertexFormat.POSITION,
            VertexFormat.Mode.QUADS,
            100000,
            true,
            true,
            RenderType.CompositeState.builder()
                    .setShaderState(new RenderStateShard.ShaderStateShard(() -> fog))
                    .setWriteMaskState(new RenderStateShard.WriteMaskStateShard(true, false))
                    .createCompositeState(true)
    );

    public static final RenderType SKYBOX = RenderType.create(
            "test",
            DefaultVertexFormat.POSITION,
            VertexFormat.Mode.QUADS,
            100000,
            true,
            true,
            RenderType.CompositeState.builder()
                    .setShaderState(new RenderStateShard.ShaderStateShard(() -> skybox))
                    .createCompositeState(true)
    );
}
