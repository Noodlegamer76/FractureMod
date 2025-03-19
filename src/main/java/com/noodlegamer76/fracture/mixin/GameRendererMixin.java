package com.noodlegamer76.fracture.mixin;

import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.server.packs.resources.ResourceProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(value = GameRenderer.class)
public class GameRendererMixin {

    @Final
    @Shadow()
    private Map<String, ShaderInstance> shaders;

    @Inject(method = "reloadShaders(Lnet/ minecraft/ server/ packs/ resources/ ResourceProvider;) V", at = @At("TAIL"))
    private void injectShaderPack(ResourceProvider provider, CallbackInfo ci) {
        Map<String, ShaderInstance> shaders = this.shaders;

    }
}
