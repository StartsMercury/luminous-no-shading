package io.github.startsmercury.luminous_no_shading.mixin.client.block.sodium;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.BlockRenderContext;
import me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.BlockRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockRenderer.class)
public class BlockRendererMixin {
    @ModifyExpressionValue(
        method = "getVertexLight",
        at = @At(value = "INVOKE", target = """
            Lme/jellysquid/mods/sodium/client/model/quad/BakedQuadView;hasShade()Z\
        """),
        remap = false
    )
    private boolean modifyShade(
        final boolean original,
        final @Local(ordinal = 0, argsOnly = true) BlockRenderContext ctx
    ) {
        return original && ctx.state().getLightEmission() == 0;
    }
}
