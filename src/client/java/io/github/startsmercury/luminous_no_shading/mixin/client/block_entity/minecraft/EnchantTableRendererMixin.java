package io.github.startsmercury.luminous_no_shading.mixin.client.block_entity.minecraft;

import io.github.startsmercury.luminous_no_shading.impl.client.NoShadingRenderTypes;
import net.minecraft.client.renderer.blockentity.EnchantTableRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EnchantTableRenderer.class)
public class EnchantTableRendererMixin {
    static {
        EnchantTableRenderer.BOOK_LOCATION.renderType(NoShadingRenderTypes::entitySolid);
    }
}
