package io.github.startsmercury.luminous_no_shading.mixin.client.block_entity.minecraft;

import io.github.startsmercury.luminous_no_shading.impl.client.NoShadingRenderTypes;
import net.minecraft.client.renderer.blockentity.ConduitRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ConduitRenderer.class)
public class ConduitRendererMixin {
    static {
        ConduitRenderer.SHELL_TEXTURE.renderType(NoShadingRenderTypes::entitySolid);
        ConduitRenderer.ACTIVE_SHELL_TEXTURE.renderType(NoShadingRenderTypes::entityCutoutNoCull);
        ConduitRenderer.WIND_TEXTURE.renderType(NoShadingRenderTypes::entityCutoutNoCull);
        ConduitRenderer.VERTICAL_WIND_TEXTURE.renderType(NoShadingRenderTypes::entityCutoutNoCull);
        ConduitRenderer.OPEN_EYE_TEXTURE.renderType(NoShadingRenderTypes::entityCutoutNoCull);
        ConduitRenderer.CLOSED_EYE_TEXTURE.renderType(NoShadingRenderTypes::entityCutoutNoCull);
    }
}
