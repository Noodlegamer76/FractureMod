package com.noodlegamer76.fracture.client.renderers.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.entity.misc.ObeliskLaser;
import com.noodlegamer76.fracture.entity.monster.boss.FleshObelisk;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ObeliskLaserRenderer extends GeoEntityRenderer<ObeliskLaser> {
    public static final ResourceLocation SMALL_OBELISK_BEAM = ResourceLocation.fromNamespaceAndPath(FractureMod.MODID, "textures/entity/flesh_obelisk/small_obelisk_beam.png");
    public static final ResourceLocation LARGE_OBELISK_BEAM = ResourceLocation.fromNamespaceAndPath(FractureMod.MODID, "textures/entity/flesh_obelisk/large_obelisk_beam.png");
    public static final ResourceLocation MAGIC_CIRCLE = ResourceLocation.fromNamespaceAndPath(FractureMod.MODID, "textures/entity/flesh_obelisk/magic_circle.png");

    public ObeliskLaserRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DefaultedEntityGeoModel<>(ResourceLocation.fromNamespaceAndPath(FractureMod.MODID, "flesh_obelisk")));
    }

    @Override
    public void actuallyRender(PoseStack poseStack, ObeliskLaser animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        laserAttack(poseStack, bufferSource, animatable.tickCount, partialTick);
    }

    private void laserAttack(PoseStack poseStack, MultiBufferSource bufferSource, int clientTick, float partialTick) {
        int chargeUpEnd = FleshObelisk.LASER_BEAM_FREEZE_TIME + FleshObelisk.LASER_BEAM_CHARGE_UP;
        int fireEnd = chargeUpEnd + FleshObelisk.LASER_BEAM_FIRE_TIME;
        int cooldownEnd = fireEnd + FleshObelisk.LASER_BEAM_COOLDOWN;
        poseStack.pushPose();

        if (clientTick >= 0 && clientTick < fireEnd) {
            Vector3f randomOffset = new Vector3f((float) Math.random() - 0.5f, (float) Math.random() - 0.5f, (float) Math.random() - 0.5f);
            if (clientTick < chargeUpEnd) {
                randomOffset.mul(0.05f);
            }
            else {
                randomOffset.mul(0.15f);
            }
            poseStack.translate(randomOffset.x, randomOffset.y, randomOffset.z);
        }

        if (clientTick < 0 || clientTick >= cooldownEnd) {
            poseStack.popPose();
            return;
        }

        Vector3f entityPos = animatable.getPosition(partialTick).toVector3f();
        Vector3f worldStart = animatable.getEyePosition(partialTick).toVector3f().sub(entityPos);

        Vector3f worldEnd = animatable.getInterpolatedLaserPosition(partialTick);
        worldEnd.sub(entityPos);

        if (clientTick < chargeUpEnd) {
            float t = (float)clientTick / chargeUpEnd;
            int r = lerpColor(120, 255, t);
            int g = lerpColor(0, 0, t);
            int b = lerpColor(200, 0, t);
            VertexConsumer smallBeam = bufferSource.getBuffer(RenderType.entityTranslucentEmissive(SMALL_OBELISK_BEAM));
            renderStackedCrossBeam(poseStack, smallBeam, worldStart, worldEnd, 0.2f, 0.6f, r, g, b, partialTick);
            VertexConsumer circle = bufferSource.getBuffer(RenderType.entityTranslucentEmissive(MAGIC_CIRCLE));
            renderMagicCirclePlane(poseStack, circle, worldStart, worldEnd, 0.5f, r, g, b, partialTick);
        } else if (clientTick < fireEnd) {
            spawnBeamParticles(animatable, worldStart, worldEnd, entityPos, 1, partialTick, 255, 50, 255);
            VertexConsumer largeBeam = bufferSource.getBuffer(RenderType.entityTranslucentEmissive(LARGE_OBELISK_BEAM));
            renderStackedCrossBeam(poseStack, largeBeam, worldStart, worldEnd, 1.0f, 3.0f, 255, 255, 255, partialTick);
            VertexConsumer circle = bufferSource.getBuffer(RenderType.entityTranslucentEmissive(MAGIC_CIRCLE));
            renderMagicCirclePlane(poseStack, circle, worldStart, worldEnd, 1.25f, 180, 0, 180, partialTick);
        }

        poseStack.popPose();
    }

    private static int lerpColor(int a, int b, float t) {
        return (int)(a + (b - a) * t);
    }

    @Override
    public RenderType getRenderType(ObeliskLaser animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(texture);
    }

    private static void renderStackedCrossBeam(
            PoseStack poseStack,
            VertexConsumer consumer,
            Vector3f startWorld,
            Vector3f endWorld,
            float width,
            float segmentLength,
            int r, int g, int b,
            float partialTick
    ) {
        Vector3f diff = new Vector3f(endWorld).sub(startWorld);
        float totalLength = diff.length();
        if (totalLength <= 1e-6f) return;

        Vector3f dir = new Vector3f(diff).normalize();

        float yaw = (float) Math.atan2(dir.z, dir.x);
        float pitch = (float) Math.asin(dir.y);

        poseStack.pushPose();

        poseStack.translate(startWorld.x, startWorld.y, startWorld.z);
        poseStack.mulPose(Axis.YP.rotation(-yaw));
        poseStack.mulPose(Axis.ZP.rotation(pitch));

        float half = width * 0.8f;

        int segments = Mth.ceil(totalLength / segmentLength);
        segments = Mth.clamp(segments, 1, 200);

        for (int i = 0; i < segments; i++) {
            float startX = i * segmentLength;
            float endX = Math.min((i + 1) * segmentLength, totalLength);
            drawCrossSection(poseStack, consumer, startX, endX, half, r, g, b, partialTick);
        }

        poseStack.popPose();
    }

    private static void renderMagicCirclePlane(
            PoseStack poseStack,
            VertexConsumer consumer,
            Vector3f startWorld,
            Vector3f endWorld,
            float size,
            int r, int g, int b,
            float partialTick
    ) {
        Vector3f diff = new Vector3f(endWorld).sub(startWorld);
        float totalLength = diff.length();
        if (totalLength <= 1e-6f) return;

        Vector3f dir = new Vector3f(diff).normalize();

        float yaw = (float) Math.atan2(dir.z, dir.x);
        float pitch = (float) Math.asin(dir.y);

        poseStack.pushPose();

        poseStack.translate(startWorld.x, startWorld.y, startWorld.z);
        poseStack.mulPose(Axis.YP.rotation(-yaw));
        poseStack.mulPose(Axis.ZP.rotation(pitch));
        float rotation = Minecraft.getInstance().levelRenderer.getTicks() + partialTick * (size / 2.0f);
        poseStack.mulPose(Axis.XP.rotation(rotation));

        Matrix4f pose = poseStack.last().pose();
        Matrix3f normal = poseStack.last().normal();
        int light = LightTexture.pack(15, 15);

        vertex(pose, normal, consumer, 0, -size, -size, 0, 0, r, g, b, light);
        vertex(pose, normal, consumer, 0, -size,  size, 1, 0, r, g, b, light);
        vertex(pose, normal, consumer, 0,  size,  size, 1, 1, r, g, b, light);
        vertex(pose, normal, consumer, 0,  size, -size, 0, 1, r, g, b, light);

        vertex(pose, normal, consumer, 0,  size, -size, 0, 1, r, g, b, light);
        vertex(pose, normal, consumer, 0,  size,  size, 1, 1, r, g, b, light);
        vertex(pose, normal, consumer, 0, -size,  size, 1, 0, r, g, b, light);
        vertex(pose, normal, consumer, 0, -size, -size, 0, 0, r, g, b, light);

        poseStack.popPose();
    }

    private static void drawCrossSection(
            PoseStack poseStack,
            VertexConsumer consumer,
            float start,
            float end,
            float halfWidth,
            int r, int g, int b,
            float partialTick
    ) {
        Matrix4f pose = poseStack.last().pose();
        Matrix3f normal = poseStack.last().normal();
        int light = LightTexture.pack(15, 15);
        float scroll = -partialTick;
        float u1 = start + scroll;
        float u2 = end + scroll;

        vertex(pose, normal, consumer, start, -halfWidth, -halfWidth, u1, 0, r, g, b, light);
        vertex(pose, normal, consumer, end, -halfWidth, -halfWidth, u2, 0, r, g, b, light);
        vertex(pose, normal, consumer, end,  halfWidth,  halfWidth, u2, 1, r, g, b, light);
        vertex(pose, normal, consumer, start,  halfWidth,  halfWidth, u1, 1, r, g, b, light);

        poseStack.pushPose();
        poseStack.mulPose(Axis.XP.rotationDegrees(90));

        pose = poseStack.last().pose();
        normal = poseStack.last().normal();

        vertex(pose, normal, consumer, start, -halfWidth, -halfWidth, u1, 0, r, g, b, light);
        vertex(pose, normal, consumer, end, -halfWidth, -halfWidth, u2, 0, r, g, b, light);
        vertex(pose, normal, consumer, end,  halfWidth,  halfWidth, u2, 1, r, g, b, light);
        vertex(pose, normal, consumer, start,  halfWidth,  halfWidth, u1, 1, r, g, b, light);

        poseStack.popPose();
    }

    private static void spawnBeamParticles(
            ObeliskLaser entity,
            Vector3f startLocal,
            Vector3f endLocal,
            Vector3f entityPos,
            int count,
            float partialTick,
            int r, int g, int b
    ) {
        var level = Minecraft.getInstance().level;
        if (level == null) return;

        Vector3f diff = new Vector3f(endLocal).sub(startLocal);
        float totalLength = diff.length();
        if (totalLength <= 1e-6f) return;

        Vector3f dir = new Vector3f(diff).normalize();

        if (Math.random() * 12 > partialTick) {
            return;
        }

        for (int i = 0; i < count; i++) {
            float t = (float) Math.random();
            float px = entityPos.x + startLocal.x + dir.x * t * totalLength;
            float py = entityPos.y + startLocal.y + dir.y * t * totalLength;
            float pz = entityPos.z + startLocal.z + dir.z * t * totalLength;

            float spread = 0.15f;
            float ox = (float)(Math.random() - 0.5) * spread;
            float oy = (float)(Math.random() - 0.5) * spread;
            float oz = (float)(Math.random() - 0.5) * spread;

            level.addParticle(
                    new DustParticleOptions(new Vector3f(0.75f, 0f, 0.75f), 2f),
                    px + ox, py + oy, pz + oz,
                    0, 0, 0
            );
        }
    }

    private static void vertex(
            Matrix4f pose,
            Matrix3f normal,
            VertexConsumer consumer,
            float x, float y, float z,
            float u, float v,
            int r, int g, int b,
            int light
    ) {
        consumer.vertex(pose, x, y, z)
                .color(r, g, b, 255)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(light)
                .normal(normal, 0,1,0)
                .endVertex();
    }
}
