package com.noodlegamer76.fracture.client.util;

import java.util.ArrayList;
import java.util.List;

public class ExtendedShaders {
    private static final List<ExtendedShaderInstance> extendedShaders = new ArrayList<>();

    public static void addExtendedShader(ExtendedShaderInstance extendedShaderInstance) {
        extendedShaders.add(extendedShaderInstance);
    }

    public static List<ExtendedShaderInstance> getExtendedShaders() {
        return extendedShaders;
    }
}
