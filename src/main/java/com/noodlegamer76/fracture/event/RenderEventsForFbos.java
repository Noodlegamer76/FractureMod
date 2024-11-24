package com.noodlegamer76.fracture.event;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.client.util.ModRenderTypes;
import com.noodlegamer76.fracture.client.util.RenderCube;
import com.noodlegamer76.fracture.client.util.SkyBoxRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.GL44;

import java.util.ArrayList;


@Mod.EventBusSubscriber(modid = FractureMod.MODID, value = Dist.CLIENT)
public class RenderEventsForFbos {
    public static final ResourceLocation TEXTURE = new ResourceLocation(FractureMod.MODID, "textures/environment/layer1/nebula");
    private static boolean fboSetup = false;
    public static int Fbo;
    public static int postFbo;
    public static int postTexture;
    public static int skyboxTexture;
    public static int stencilBufferTexture;
    public static int width;
    public static int height;

    private static int previousSizeX;
    private static int previousSizeY;
    public static ArrayList<BlockPos> positions = new ArrayList<>();
    @SubscribeEvent
    public static void levelRenderEvent(RenderLevelStageEvent event) {

        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SKY && !fboSetup) {
            int current = GL44.glGetInteger(GL44.GL_FRAMEBUFFER_BINDING);
            previousSizeX = width;
            previousSizeY = height;
            fboSetup = true;
            Fbo = GlStateManager.glGenFramebuffers();
            GlStateManager._glBindFramebuffer(GL44.GL_FRAMEBUFFER, Fbo);

            skyboxTexture = GlStateManager._genTexture();
            RenderSystem.bindTexture(skyboxTexture);

            GlStateManager._texImage2D(GL44.GL_TEXTURE_2D, 0, GL44.GL_RGBA,
                    width, height,
                    0, GL44.GL_RGBA, GL44.GL_UNSIGNED_BYTE, null);
            GlStateManager._texParameter(GL44.GL_TEXTURE_2D, GL44.GL_TEXTURE_MIN_FILTER, GL44.GL_LINEAR);
            GlStateManager._texParameter(GL44.GL_TEXTURE_2D, GL44.GL_TEXTURE_MAG_FILTER, GL44.GL_LINEAR);
            GlStateManager._glFramebufferTexture2D(GL44.GL_FRAMEBUFFER, GL44.GL_COLOR_ATTACHMENT0, GL44.GL_TEXTURE_2D, skyboxTexture, 0);

            stencilBufferTexture = GlStateManager._genTexture();
            GlStateManager._bindTexture(stencilBufferTexture);

            GlStateManager._texImage2D(GL44.GL_TEXTURE_2D, 0, GL44.GL_DEPTH24_STENCIL8,
                    width, height,
                    0, GL44.GL_DEPTH_STENCIL, GL44.GL_UNSIGNED_INT_24_8, null);
            GlStateManager._texParameter(GL44.GL_TEXTURE_2D, GL44.GL_TEXTURE_MIN_FILTER, GL44.GL_NEAREST);
            GlStateManager._texParameter(GL44.GL_TEXTURE_2D, GL44.GL_TEXTURE_MAG_FILTER, GL44.GL_NEAREST);

            GlStateManager._glFramebufferTexture2D(GL44.GL_FRAMEBUFFER, GL44.GL_DEPTH_STENCIL_ATTACHMENT, GL44.GL_TEXTURE_2D, stencilBufferTexture, 0);


            //SETUP FOR NORMALS MAP
            postFbo = GlStateManager.glGenFramebuffers();
            GlStateManager._glBindFramebuffer(GL44.GL_FRAMEBUFFER, postFbo);

            postTexture = GlStateManager._genTexture();
            RenderSystem.bindTexture(postTexture);

            GlStateManager._texImage2D(GL44.GL_TEXTURE_2D, 0, GL44.GL_RGB16F,
                    width, height,
                    0, GL44.GL_RGB, GL44.GL_FLOAT, null);
            GlStateManager._texParameter(GL44.GL_TEXTURE_2D, GL44.GL_TEXTURE_MIN_FILTER, GL44.GL_NEAREST);
            GlStateManager._texParameter(GL44.GL_TEXTURE_2D, GL44.GL_TEXTURE_MAG_FILTER, GL44.GL_NEAREST);
            GlStateManager._glFramebufferTexture2D(GL44.GL_FRAMEBUFFER, GL44.GL_COLOR_ATTACHMENT0, GL44.GL_TEXTURE_2D, postTexture, 0);

            GlStateManager._glBindFramebuffer(GL44.GL_FRAMEBUFFER, current);
        }

        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SKY) {
            width = Minecraft.getInstance().getWindow().getWidth();
            height = Minecraft.getInstance().getWindow().getHeight();
            changeTextureSize();

            int current = GL44.glGetInteger(GL44.GL_FRAMEBUFFER_BINDING);
            GlStateManager._glBindFramebuffer(GL44.GL_FRAMEBUFFER, Fbo);

            RenderSystem.bindTexture(skyboxTexture);



            RenderSystem.clear(GL44.GL_DEPTH_BUFFER_BIT | GL44.GL_STENCIL_BUFFER_BIT | GL44.GL_COLOR_BUFFER_BIT, true);
            SkyBoxRenderer.renderBlockSkybox(event.getPoseStack(), TEXTURE);
            GlStateManager._glBindFramebuffer(GL44.GL_FRAMEBUFFER, current);
            ModRenderTypes.skybox.setSampler("Skybox", skyboxTexture);
            ModRenderTypes.skybox.setSampler("SkyboxDepth", stencilBufferTexture);
            ModRenderTypes.skybox.setSampler("MainDepth", Minecraft.getInstance().getMainRenderTarget().getDepthTextureId());
        }

        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_BLOCK_ENTITIES) {


            RenderCube.createCubeWithShader(event.getPoseStack(), positions, event.getPartialTick());

        }

        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS) {
            //SkyBoxRenderer.renderSimple4(event.getPoseStack(), TEXTURE);
        }

        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_WEATHER) {
            previousSizeY = height;
            previousSizeX = width;
        }
    }


    public static void changeTextureSize() {
        if (previousSizeX != width || previousSizeY != height) {
            int current = GL44.glGetInteger(GL44.GL_FRAMEBUFFER_BINDING);

            GlStateManager._glDeleteFramebuffers(Fbo);
            Fbo = GlStateManager.glGenFramebuffers();


            GlStateManager._glBindFramebuffer(GL44.GL_FRAMEBUFFER, Fbo);

            GlStateManager._deleteTexture(skyboxTexture);

            skyboxTexture = GlStateManager._genTexture();
            RenderSystem.bindTexture(skyboxTexture);

            GlStateManager._texImage2D(GL44.GL_TEXTURE_2D, 0, GL44.GL_RGBA,
                    width, height,
                    0, GL44.GL_RGBA, GL44.GL_UNSIGNED_BYTE, null);
            GlStateManager._texParameter(GL44.GL_TEXTURE_2D, GL44.GL_TEXTURE_MIN_FILTER, GL44.GL_LINEAR);
            GlStateManager._texParameter(GL44.GL_TEXTURE_2D, GL44.GL_TEXTURE_MAG_FILTER, GL44.GL_LINEAR);
            GlStateManager._glFramebufferTexture2D(GL44.GL_FRAMEBUFFER, GL44.GL_COLOR_ATTACHMENT0, GL44.GL_TEXTURE_2D, skyboxTexture, 0);

            GlStateManager._deleteTexture(stencilBufferTexture);

            stencilBufferTexture = GlStateManager._genTexture();
            RenderSystem.bindTexture(stencilBufferTexture);


            GlStateManager._texImage2D(GL44.GL_TEXTURE_2D, 0, GL44.GL_DEPTH24_STENCIL8,
                    width, height,
                    0, GL44.GL_DEPTH_STENCIL, GL44.GL_UNSIGNED_INT_24_8, null);
            GlStateManager._texParameter(GL44.GL_TEXTURE_2D, GL44.GL_TEXTURE_MIN_FILTER, GL44.GL_NEAREST);
            GlStateManager._texParameter(GL44.GL_TEXTURE_2D, GL44.GL_TEXTURE_MAG_FILTER, GL44.GL_NEAREST);

            GlStateManager._glFramebufferTexture2D(GL44.GL_FRAMEBUFFER, GL44.GL_DEPTH_STENCIL_ATTACHMENT, GL44.GL_TEXTURE_2D, stencilBufferTexture, 0);

            //RESIZE FOR NORMALS

            GlStateManager._glDeleteFramebuffers(postFbo);
            postFbo = GlStateManager.glGenFramebuffers();


            GlStateManager._glBindFramebuffer(GL44.GL_FRAMEBUFFER, postFbo);

            GlStateManager._deleteTexture(postTexture);

            postTexture = GlStateManager._genTexture();
            RenderSystem.bindTexture(postTexture);

            GlStateManager._texImage2D(GL44.GL_TEXTURE_2D, 0, GL44.GL_RGB16F,
                    width, height,
                    0, GL44.GL_RGB, GL44.GL_FLOAT, null);
            GlStateManager._texParameter(GL44.GL_TEXTURE_2D, GL44.GL_TEXTURE_MIN_FILTER, GL44.GL_NEAREST);
            GlStateManager._texParameter(GL44.GL_TEXTURE_2D, GL44.GL_TEXTURE_MAG_FILTER, GL44.GL_NEAREST);
            GlStateManager._glFramebufferTexture2D(GL44.GL_FRAMEBUFFER, GL44.GL_COLOR_ATTACHMENT0, GL44.GL_TEXTURE_2D, postTexture, 0);

            GlStateManager._glBindFramebuffer(GL44.GL_FRAMEBUFFER, current);
        }
    }
}
