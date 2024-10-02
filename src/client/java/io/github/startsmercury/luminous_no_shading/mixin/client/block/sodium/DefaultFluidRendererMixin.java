package io.github.startsmercury.luminous_no_shading.mixin.client.block.sodium;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.caffeinemc.mods.sodium.client.render.chunk.compile.pipeline.DefaultFluidRenderer;
import net.caffeinemc.mods.sodium.client.world.LevelSlice;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DefaultFluidRenderer.class)
public class DefaultFluidRendererMixin {
    @Inject(
        method = "render",
        at = @At(value = "FIELD", shift = At.Shift.AFTER, ordinal = 0, target = "Lnet/caffeinemc/mods/sodium/client/util/DirectionUtil;HORIZONTAL_DIRECTIONS:[Lnet/minecraft/core/Direction;")
    )
    private void detectLuminosity(
        final CallbackInfo callback,
        final @Local(ordinal = 0, argsOnly = true) LevelSlice level,
        final @Local(ordinal = 0, argsOnly = true) BlockPos blockpos,
        final @Share("luminous") LocalBooleanRef luminousRef
    ) {
        luminousRef.set(level.getBlockState(blockpos).getLightEmission() != 0);
    }

    @ModifyExpressionValue(
        method = "render",
        at = {
            @At(value = "CONSTANT", args = "floatValue=0.8", ordinal = 0),
            @At(value = "CONSTANT", args = "floatValue=0.6", ordinal = 0),
        },
        remap = false
    )
    private float modifyShade(
        final float br,
        final @Share("luminous") LocalBooleanRef luminousRef
    ) {
        return luminousRef.get() ? 1.0F : br;
    }
}
