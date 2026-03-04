package com.noodlegamer76.fracture.worldgen.megastructure.structure.structures.bridge;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.worldgen.megastructure.Node;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.Structure;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.StructureInstance;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.placers.StructurePlacer;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.AnchorPoint;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVar;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.List;

public class BridgeStructure extends Structure {
    public BridgeStructure(int priority) {
        super(priority);
    }

    @Override
    public int getMaxSize() {
        return 63;
    }

    @Override
    public void generate(FeaturePlaceContext<NoneFeatureConfiguration> ctx, Node n, RandomSource random, StructureInstance instance) {
        BlockPos origin = ctx.origin();
        BlockPos pos = origin.east();

        MinecraftServer server = ctx.level().getServer();

        if (server == null) return;

        ResourceLocation bridgeSupportLoc = ResourceLocation.fromNamespaceAndPath(FractureMod.MODID, "castle/supports/bridge_support_1");
        ResourceLocation bridgeSupportTopLoc = ResourceLocation.fromNamespaceAndPath(FractureMod.MODID, "castle/supports/bridge_support_1_top");
        ResourceLocation bridgeLoc = ResourceLocation.fromNamespaceAndPath(FractureMod.MODID, "castle/bridge/top_1");

        StructureTemplate bridgeSupport = server
                .getStructureManager()
                .get(bridgeSupportLoc)
                .orElse(null);

        StructureTemplate bridgeSupportTop = server
                .getStructureManager()
                .get(bridgeSupportTopLoc)
                .orElse(null);

        StructureTemplate bridge = server
                .getStructureManager()
                .get(bridgeLoc)
                .orElse(null);

        int bridgeHeight = 200;

        StructurePlaceSettings settings = new StructurePlaceSettings();

        for (int i = pos.getY(); i < bridgeHeight;) {
            pos = new BlockPos(n.getX(), i, n.getZ());
            if (pos.getY() + 5 < bridgeHeight) {
                StructurePlacer placer = new StructurePlacer(pos, bridgeSupport, settings, AnchorPoint.CORNER);
                instance.addPlacer(placer);
            }
            else {
                StructurePlacer placer = new StructurePlacer(pos, bridgeSupportTop, settings, AnchorPoint.CORNER);
                instance.addPlacer(placer);
            }
            i += 5;
        }

        for (int i = n.getZ(); i < n.getZ() + getMaxSize() + 15;) {
            pos = new BlockPos(n.getX(), bridgeHeight, i);
            StructurePlacer placer = new StructurePlacer(pos, bridge, settings, AnchorPoint.CORNER);
            instance.addPlacer(placer);
            i += 13;
        }
    }

    @Override
    public List<GenVar<?>> getGenVariables() {
        return List.of();
    }
}
