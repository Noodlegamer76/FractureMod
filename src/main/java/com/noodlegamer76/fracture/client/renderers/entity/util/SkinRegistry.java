package com.noodlegamer76.fracture.client.renderers.entity.util;

import com.noodlegamer76.fracture.FractureMod;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class SkinRegistry {
    private static final Map<String, ResourceLocation> skins = new HashMap<>();
    private static final Map<String, ResourceLocation> uniqueSkins = new HashMap<>();

    public static ResourceLocation getSkin(String name) {
        return skins.getOrDefault(name, new ResourceLocation("textures/entity/steve.png"));
    }

    public static ResourceLocation getUniqueSkin(String name) {
        return uniqueSkins.getOrDefault(name, new ResourceLocation("textures/entity/steve.png"));
    }

    public static void registerSkin(String name, ResourceLocation location) {
        skins.put(name, location);
    }

    public static void registerUniqueSkin(String name, ResourceLocation location) {
        uniqueSkins.put(name, location);
    }

    public static Map.Entry<String, ResourceLocation> getRandomSkin() {
        int size = skins.keySet().size();
        int random = (int) (Math.random() * size);
        return skins.entrySet().stream().skip(random).findFirst().orElse(null);
    }

    static {
        registerSkin("Steve", new ResourceLocation("textures/entity/player/wide/steve.png"));
        registerSkin("Alex", new ResourceLocation("textures/entity/player/wide/alex.png"));
        registerSkin("Ari", new ResourceLocation("textures/entity/player/wide/ari.png"));
        registerSkin("Efe", new ResourceLocation("textures/entity/player/wide/efe.png"));
        registerSkin("Kai", new ResourceLocation("textures/entity/player/wide/kai.png"));
        registerSkin("Makena", new ResourceLocation("textures/entity/player/wide/makena.png"));
        registerSkin("Noor", new ResourceLocation("textures/entity/player/wide/noor.png"));
        registerSkin("Sunny", new ResourceLocation("textures/entity/player/wide/sunny.png"));
        registerSkin("Zuri", new ResourceLocation("textures/entity/player/wide/zuri.png"));
        registerSkin("noodlegamer76", new ResourceLocation(FractureMod.MODID, "textures/entity/player/wide/noodlegamer76.png"));
        registerSkin("siggystar", new ResourceLocation(FractureMod.MODID, "textures/entity/player/wide/siggystar.png"));
        registerSkin("mistermeltdown", new ResourceLocation(FractureMod.MODID, "textures/entity/player/wide/mistermeltdown.png"));
        registerSkin("Pixelatednerd", new ResourceLocation(FractureMod.MODID, "textures/entity/player/wide/pixelatednerd.png"));
        registerSkin("mistermeltdown", new ResourceLocation(FractureMod.MODID, "textures/entity/player/wide/mistermeltdown.png"));
        registerSkin("matthewkaelyn", new ResourceLocation(FractureMod.MODID, "textures/entity/player/wide/matthewkaelyn.png"));

    }
}
