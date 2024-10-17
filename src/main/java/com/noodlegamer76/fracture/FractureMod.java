package com.noodlegamer76.fracture;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.logging.LogUtils;
import com.noodlegamer76.fracture.block.InitBlocks;
import com.noodlegamer76.fracture.client.renderers.entity.*;
import com.noodlegamer76.fracture.client.renderers.entity.block.FogEmitterRenderer;
import com.noodlegamer76.fracture.creativetabs.FractureTab;
import com.noodlegamer76.fracture.creativetabs.InitCreativeTabs;
import com.noodlegamer76.fracture.entity.BloodSlimeEntity;
import com.noodlegamer76.fracture.entity.FleshWalkerEntity;
import com.noodlegamer76.fracture.entity.InitEntities;
import com.noodlegamer76.fracture.entity.block.InitBlockEntities;
import com.noodlegamer76.fracture.event.RenderLevelEvent;
import com.noodlegamer76.fracture.event.ShaderEvents;
import com.noodlegamer76.fracture.fluid.InitFluidTypes;
import com.noodlegamer76.fracture.fluid.InitFluids;
import com.noodlegamer76.fracture.item.InitItems;
import com.noodlegamer76.fracture.particles.BloodParticle;
import com.noodlegamer76.fracture.particles.InitParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(FractureMod.MODID)
public class FractureMod
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "fracture";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public FractureMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        GeckoLib.initialize();

        //registers DeferredRegisters
        InitBlocks.BLOCKS.register(modEventBus);
        InitItems.ITEMS.register(modEventBus);
        InitParticles.PARTICLE_TYPES.register(modEventBus);
        InitFluids.FLUIDS.register(modEventBus);
        InitFluidTypes.FLUID_TYPES.register(modEventBus);
        InitBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        InitEntities.ENTITIES.register(modEventBus);

        InitCreativeTabs.CREATIVE_TABS.register(modEventBus);
        modEventBus.register(new FractureTab());
        modEventBus.register(new RenderLevelEvent());
        modEventBus.register(new ShaderEvents());

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);


        if(Dist.CLIENT.isClient()) {

        }

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ItemBlockRenderTypes.setRenderLayer(InitFluids.SOURCE_BLOOD.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(InitFluids.FLOWING_BLOOD.get(), RenderType.translucent());
        }

        @SubscribeEvent
        public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            //this is where you register renderers for both Entities and BlockEntities
            event.registerEntityRenderer(InitEntities.FLESH_RATTLER.get(), FleshRattlerRenderer::new);
            event.registerEntityRenderer(InitEntities.ANKLE_BITER.get(), AnkleBiterRenderer::new);
            event.registerEntityRenderer(InitEntities.FLESH_WALKER.get(), FleshWalkerRenderer::new);
            event.registerEntityRenderer(InitEntities.FLESH_SLIME.get(), FleshSlimeRenderer::new);
            event.registerEntityRenderer(InitEntities.BLOOD_SLIME.get(), BloodSlimeRenderer::new);

            event.registerBlockEntityRenderer(InitBlockEntities.FOG_EMITTER.get(), FogEmitterRenderer::new);
        }

        @SubscribeEvent
        public static void RegisterParticleProviders(RegisterParticleProvidersEvent event) {
            Minecraft.getInstance().particleEngine.register(InitParticles.BLOOD_PARTICLES.get(),
                    BloodParticle.Provider::new);
        }

    }
}
