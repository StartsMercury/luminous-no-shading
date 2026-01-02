package io.github.startsmercury.luminous_no_shading.impl.client;

public interface ItemStackRenderStateExtension {
    default int luminous_no_shading$getBlockLight() {
        return 0;
    }

    default void luminous_no_shading$setBlockLight(final int blockLight) {
    }
}
