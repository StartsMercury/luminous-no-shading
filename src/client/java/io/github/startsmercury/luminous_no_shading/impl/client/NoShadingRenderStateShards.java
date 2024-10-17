package io.github.startsmercury.luminous_no_shading.impl.client;

import net.minecraft.client.renderer.RenderStateShard;

public final class NoShadingRenderStateShards {
    public static void init() {}

    public static final RenderStateShard.ShaderStateShard RENDERTYPE_ENTITY_CUTOUT_NO_CULL_SHADER =
        new RenderStateShard.ShaderStateShard(
            LuminousNoShadingImpl::getRendertypeEntityCutoutNoCullShader
        );

    public static final RenderStateShard.ShaderStateShard RENDERTYPE_ENTITY_SOLID_SHADER =
        new RenderStateShard.ShaderStateShard(
            LuminousNoShadingImpl::getRendertypeEntitySolidShader
        );

    public static final RenderStateShard.ShaderStateShard RENDERTYPE_ENTITY_CUTOUT_SHADER =
        new RenderStateShard.ShaderStateShard(
            LuminousNoShadingImpl::getRendertypeEntityCutoutShader
        );

    public static final RenderStateShard.ShaderStateShard RENDERTYPE_ITEM_ENTITY_TRANSLUCENT_CULL_SHADER =
        new RenderStateShard.ShaderStateShard(
            LuminousNoShadingImpl::getRendertypeItemEntityTranslucentCullShader
        );

    public static final RenderStateShard.ShaderStateShard RENDERTYPE_ENTITY_TRANSLUCENT_CULL_SHADER =
        new RenderStateShard.ShaderStateShard(
            LuminousNoShadingImpl::getRendertypeEntityTranslucentCullShader
        );
}
