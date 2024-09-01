package io.github.startsmercury.luminous_no_shading.mixin.client.block.minecraft;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ModelBlockRenderer.class)
public class ModelBlockRendererMixin {
    @ModifyExpressionValue(
        method = { "renderModelFaceAO", "renderModelFaceFlat" },
        at = @At(value = "INVOKE", target = """
            Lnet/minecraft/client/renderer/block/model/BakedQuad;isShade()Z\
        """)
    )
    private boolean modifyShade(
        final boolean original,
        final @Local(ordinal = 0, argsOnly = true) BlockState state
    ) {
        return original && state.getLightEmission() <= 0;
    }
}
