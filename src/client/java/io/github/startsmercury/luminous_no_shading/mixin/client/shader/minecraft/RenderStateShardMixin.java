package io.github.startsmercury.luminous_no_shading.mixin.client.shader.minecraft;

import io.github.startsmercury.luminous_no_shading.impl.client.NoShadingRenderStateShards;
import net.minecraft.client.renderer.RenderStateShard;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(RenderStateShard.class)
public class RenderStateShardMixin {
    static {
        NoShadingRenderStateShards.init();
    }
}
