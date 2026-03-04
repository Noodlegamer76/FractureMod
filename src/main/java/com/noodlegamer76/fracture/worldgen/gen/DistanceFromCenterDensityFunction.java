package com.noodlegamer76.fracture.worldgen.gen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;

public class DistanceFromCenterDensityFunction implements DensityFunction {
    // Define a codec for serialization/deserialization from JSON.
    public static final Codec<DistanceFromCenterDensityFunction> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.DOUBLE.fieldOf("center_x").forGetter(func -> func.centerX),
                    Codec.DOUBLE.fieldOf("center_y").forGetter(func -> func.centerY),
                    Codec.DOUBLE.fieldOf("center_z").forGetter(func -> func.centerZ),
                    Codec.DOUBLE.fieldOf("scale").forGetter(func -> func.scale)
            ).apply(instance, DistanceFromCenterDensityFunction::new)
    );

    private final double centerX;
    private final double centerY;
    private final double centerZ;
    private final double scale;

    public DistanceFromCenterDensityFunction(double centerX, double centerY, double centerZ, double scale) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.centerZ = centerZ;
        this.scale = scale;
    }

    @Override
    public double compute(FunctionContext context) {
        // Obtain the block coordinates (or noise coordinates) from the context.
        double x = context.blockX(); // Adjust if your context uses a different method
        double y = context.blockY();
        double z = context.blockZ();
        double dx = x - this.centerX;
        double dy = y - this.centerY;
        double dz = z - this.centerZ;
        // Compute the Euclidean distance and apply the scale.
        return Math.sqrt(dx * dx + dy * dy + dz * dz) * this.scale;
    }

    @Override
    public void fillArray(double[] array, ContextProvider contextProvider) {
        for (int i = 0; i < array.length; i++) {
            DensityFunction.FunctionContext ctx = contextProvider.forIndex(i);
            array[i] = compute(ctx);
        }
    }

    @Override
    public DensityFunction mapAll(Visitor visitor) {
        // This example simply applies the visitor to this instance.
        return visitor.apply(this);
    }

    @Override
    public double minValue() {
        return 0.0;
    }

    @Override
    public double maxValue() {
        // Theoretically unbounded; here we return positive infinity.
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public KeyDispatchDataCodec<? extends DensityFunction> codec() {
        // Wrap our codec for use by the key-dispatch system.
        return KeyDispatchDataCodec.of(CODEC);
    }
}
