package com.noodlegamer76.fracture.mixin;

import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(GameRenderer.class)
public interface GameRendererMixinAccessor {

    @Accessor("shaders")
    Map<String, ShaderInstance> getShaders();
}
