package io.github.startsmercury.luminous_no_shading.mixin.client.block.minecraft;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.block.LiquidBlockRenderer;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(LiquidBlockRenderer.class)
public class LiquidBlockRendererMixin {
    @ModifyArg(
        method = "tesselate",
        at = @At(value = "INVOKE", target = """
            Lnet/minecraft/world/level/BlockAndTintGetter; \
            getShade (                                     \
                Lnet/minecraft/core/Direction;             \
                Z                                          \
            ) F                                            \
        """),
        index = 1
    )
    private boolean modifyShade(
        final boolean bl,
        @Local(ordinal = 0, argsOnly = true) BlockState state
    ) {
        return bl && state.getLightEmission() <= 0;
    }
}
