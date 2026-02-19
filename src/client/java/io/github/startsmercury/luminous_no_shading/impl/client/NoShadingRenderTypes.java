package io.github.startsmercury.luminous_no_shading.impl.client;

import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

public final class NoShadingRenderTypes {
    public static RenderType entityCutout(final Identifier identifier) {
        return RenderTypes.entityCutout(LuminousNoShadingImpl.mangle(identifier));
    }

    public static RenderType entitySolid(final Identifier identifier) {
        return RenderTypes.entitySolid(LuminousNoShadingImpl.mangle(identifier));
    }
}
