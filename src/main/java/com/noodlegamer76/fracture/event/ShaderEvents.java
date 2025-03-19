package com.noodlegamer76.fracture.event;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.client.util.ExtendedShaderInstance;
import com.noodlegamer76.fracture.client.util.ModRenderTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;

@Mod.EventBusSubscriber(modid = FractureMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ShaderEvents {



    @SubscribeEvent
    public static void registerShaders(net.minecraftforge.client.event.RegisterShadersEvent event) throws IOException {
        event.registerShader(new ExtendedShaderInstance(event.getResourceProvider(),
                        new ResourceLocation(FractureMod.MODID, "fog"),
                        DefaultVertexFormat.POSITION),
                (e) -> ModRenderTypes.fog = (ExtendedShaderInstance) e);

        event.registerShader(new ExtendedShaderInstance(event.getResourceProvider(),
                        new ResourceLocation(FractureMod.MODID, "skybox"),
                        DefaultVertexFormat.POSITION),
                (e) -> ModRenderTypes.skybox = (ExtendedShaderInstance) e);

        event.registerShader(new ExtendedShaderInstance(event.getResourceProvider(),
                        new ResourceLocation(FractureMod.MODID, "normal"),
                        DefaultVertexFormat.POSITION),
                (e) -> ModRenderTypes.normal = (ExtendedShaderInstance) e);

        event.registerShader(new ExtendedShaderInstance(event.getResourceProvider(),
                        new ResourceLocation(FractureMod.MODID, "frosted_glass"),
                        DefaultVertexFormat.POSITION),
                (e) -> ModRenderTypes.frostedGlass = (ExtendedShaderInstance) e);
    }

    @SubscribeEvent
    public static void dimensionSpecialEffect(RegisterDimensionSpecialEffectsEvent event) {

    }
}