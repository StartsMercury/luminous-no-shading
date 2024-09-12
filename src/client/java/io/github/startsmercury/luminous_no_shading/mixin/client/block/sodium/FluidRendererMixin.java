package io.github.startsmercury.luminous_no_shading.mixin.client.block.sodium;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.FluidRenderer;
import me.jellysquid.mods.sodium.client.world.WorldSlice;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FluidRenderer.class)
public class FluidRendererMixin {
    @Inject(
        method = "render",
        at = @At(value = "FIELD", shift = At.Shift.AFTER, ordinal = 0, target = """
            Lme/jellysquid/mods/sodium/client/util/DirectionUtil;HORIZONTAL_DIRECTIONS:[Lnet/minecraft/core/Direction;\
        """),
        remap = false
    )
    private void detectLuminosity(
        final CallbackInfo callback,
        final @Local(ordinal = 0, argsOnly = true) WorldSlice world,
        final @Local(ordinal = 0, argsOnly = true) BlockPos blockpos,
        final @Share("luminous") LocalBooleanRef luminousRef
    ) {
        luminousRef.set(world.getBlockState(blockpos).getLightEmission() != 0);
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
