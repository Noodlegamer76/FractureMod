package com.noodlegamer76.fracture.worldgen.megastructure.rules;

import com.noodlegamer76.fracture.worldgen.megastructure.Node;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.StructureInstance;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.access.WorldAccess;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec2;

public class RandomPosInNode implements StructureRule {
    @Override
    public void run(WorldAccess access, Node n, RandomSource random, StructureInstance instance) {
        int x = n.getX();
        int z = n.getZ();
        int randomX = random.nextInt(x, x + n.getSize());
        int randomZ = random.nextInt(z, z + n.getSize());

        instance.setGenVar(
                "pos", new Vec2(randomX, randomZ)
        );
    }

    @Override
    public String getDescription() {
        return "Returns a random position in the node.";
    }
}
