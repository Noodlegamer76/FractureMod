package com.noodlegamer76.fracture.worldgen.megastructure.rules.pos;

import com.noodlegamer76.fracture.worldgen.megastructure.Node;
import com.noodlegamer76.fracture.worldgen.megastructure.rules.StructureRule;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.StructureInstance;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.access.WorldAccess;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec2;

public class NodePos implements StructureRule {
    @Override
    public void run(WorldAccess access, Node n, RandomSource random, StructureInstance instance) {
        int x = n.getX();
        int z = n.getZ();

        instance.setGenVar(
                n, "pos", new Vec2(x, z)
        );
    }

    @Override
    public String getDescription() {
        return "Returns the position of the node.";
    }
}
