package com.noodlegamer76.fracture.worldgen.megastructure.rules.layout.castle;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.worldgen.megastructure.Node;
import com.noodlegamer76.fracture.worldgen.megastructure.rules.StructureRule;
import com.noodlegamer76.fracture.worldgen.megastructure.rules.layout.paths.AnchorGraphRule;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.StructureInstance;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.access.WorldAccess;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.StructureUtils;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.geometry.Polygons;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.graph.StructureGraph;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVar;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.operation.buffer.BufferOp;
import org.locationtech.jts.operation.buffer.BufferParameters;

import java.util.ArrayList;
import java.util.List;

public class ShrinkStructureGraphPolygonsRule implements StructureRule {
    public static final String BUILDING_POLYGONS_KEY = "building_polygons";
    private final float shrinkAmount;

    public ShrinkStructureGraphPolygonsRule(float shrinkAmount) {
        this.shrinkAmount = shrinkAmount;
    }

    @Override
    public boolean shouldRun(WorldAccess access, Node n, RandomSource random, StructureInstance instance) {
        return !instance.hasGenVar(BUILDING_POLYGONS_KEY);
    }

    @Override
    public void run(WorldAccess access, Node n, RandomSource random, @NotNull StructureInstance instance) {
        GenVar<StructureGraph> structureGraphVar =
                instance.getGenVar(
                        n,
                        AnchorGraphRule.PATH_GRAPH_KEY,
                        StructureGraph.class
                );

        if (structureGraphVar == null) {
            FractureMod.LOGGER.error(
                    this.getClass().getSimpleName()
                            + " ran before variable ["
                            + AnchorGraphRule.PATH_GRAPH_KEY
                            + "] was set"
            );
            return;
        }

        StructureGraph structureGraph = structureGraphVar.getValue();

        List<Polygon> shrunken = StructureUtils.shrinkPolygons(structureGraph.triangles(), shrinkAmount);

        instance.setGenVar(n, new GenVar<>(BUILDING_POLYGONS_KEY, new Polygons(shrunken), true));
    }



    @Override
    public String getDescription() {
        return "Shrink the triangles in the structure graph.";
    }

    public float getShrinkAmount() {
        return shrinkAmount;
    }
}
