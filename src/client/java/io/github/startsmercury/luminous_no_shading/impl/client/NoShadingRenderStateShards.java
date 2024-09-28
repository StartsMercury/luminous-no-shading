package io.github.startsmercury.luminous_no_shading.impl.client;

import net.minecraft.client.renderer.RenderStateShard;

public final class NoShadingRenderStateShards {
    public static void init() {}

    public static final RenderStateShard.ShaderStateShard RENDERTYPE_TRANSLUCENT_SHADER =
        new RenderStateShard.ShaderStateShard(NoShadingCoreShaders.RENDERTYPE_TRANSLUCENT);

    public static final RenderStateShard.ShaderStateShard RENDERTYPE_ENTITY_SOLID_SHADER =
        new RenderStateShard.ShaderStateShard(NoShadingCoreShaders.RENDERTYPE_ENTITY_SOLID);

    public static final RenderStateShard.ShaderStateShard RENDERTYPE_ENTITY_CUTOUT_SHADER =
        new RenderStateShard.ShaderStateShard(NoShadingCoreShaders.RENDERTYPE_ENTITY_CUTOUT);

    public static final RenderStateShard.ShaderStateShard RENDERTYPE_ITEM_ENTITY_TRANSLUCENT_CULL_SHADER =
        new RenderStateShard.ShaderStateShard(
            NoShadingCoreShaders.RENDERTYPE_ITEM_ENTITY_TRANSLUCENT_CULL
        );
}
