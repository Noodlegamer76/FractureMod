package com.noodlegamer76.fracture.spellcrafting.nodes;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.event.FractureRegistries;
import com.noodlegamer76.fracture.spellcrafting.nodes.logic.AddNode;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class InitNodes {
    public static final DeferredRegister<NodeType<?>> NODE_TYPES = DeferredRegister.create(FractureRegistries.NODE_TYPE, FractureMod.MODID);

    public static final RegistryObject<NodeType<AddNode>> ADD_NODE = NODE_TYPES.register("add_node",
            () -> new NodeType<>(graph -> new AddNode(graph)));

}
