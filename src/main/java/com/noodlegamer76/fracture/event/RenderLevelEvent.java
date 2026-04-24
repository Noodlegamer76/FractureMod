package com.noodlegamer76.fracture.event;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.client.render.world.BorealisRenderer;
import com.noodlegamer76.fracture.client.render.world.FleshBossWaterRenderer;
import com.noodlegamer76.fracture.worldgen.ModDimensionKeys;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FractureMod.MODID, value = Dist.CLIENT)
public class RenderLevelEvent {

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        Minecraft mc = Minecraft.getInstance();
        float partialTicks = event.getPartialTick();
        Level level = Minecraft.getInstance().level;
        PoseStack poseStack = event.getPoseStack();
        Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();

        PoseStack viewStack = new PoseStack();

        viewStack.mulPose(Axis.XP.rotationDegrees(camera.getXRot()));
        viewStack.mulPose(Axis.YP.rotationDegrees(camera.getYRot() + 180.0F));


        Vec3 camPos = camera.getPosition();

        if (level != null && level.dimension().equals(ModDimensionKeys.BOREAS)) {
            BorealisRenderer.render(poseStack, event.getPartialTick(), event.getRenderTick());
        }

        if (level != null && level.dimension().equals(ModDimensionKeys.FLESH)) {
            if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_BLOCK_ENTITIES) {
                viewStack.pushPose();

                viewStack.translate(0, 64.995 - camPos.y, 0);
                FleshBossWaterRenderer.renderQuad(viewStack);

                viewStack.popPose();
            }
        }

        if (level != null && event.getStage() == RenderLevelStageEvent.Stage.AFTER_LEVEL) {
           //WorldRenderContext ctx = new WorldRenderContext(
           //        level,
           //        poseStack,
           //        partialTicks,
           //        mc.gameRenderer.getMainCamera().getPosition().x,
           //        mc.gameRenderer.getMainCamera().getPosition().y,
           //        mc.gameRenderer.getMainCamera().getPosition().z
           //);

           //StructureDebugRenderer.renderWorld(ctx);
        }
    }
}
