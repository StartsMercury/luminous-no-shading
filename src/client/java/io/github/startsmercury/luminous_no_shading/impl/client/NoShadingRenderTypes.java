package io.github.startsmercury.luminous_no_shading.impl.client;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public final class NoShadingRenderTypes {
    public static RenderType entityCutoutNoCull(final ResourceLocation resourceLocation) {
        return RenderType.entityCutoutNoCull(LuminousNoShadingImpl.mangle(resourceLocation));
    }

    public static RenderType entitySolid(final ResourceLocation resourceLocation) {
        return RenderType.entitySolid(LuminousNoShadingImpl.mangle(resourceLocation));
    }
}
