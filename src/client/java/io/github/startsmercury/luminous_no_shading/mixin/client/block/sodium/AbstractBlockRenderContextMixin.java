package io.github.startsmercury.luminous_no_shading.mixin.client.block.sodium;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.caffeinemc.mods.sodium.client.render.frapi.render.AbstractBlockRenderContext;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractBlockRenderContext.class)
public class AbstractBlockRenderContextMixin {
    @Shadow
    protected BlockState state;

    @ModifyExpressionValue(
        method = "shadeQuad",
        at = @At(value = "INVOKE", target = "Lnet/caffeinemc/mods/sodium/client/render/frapi/mesh/MutableQuadViewImpl;hasShade()Z"),
        remap = false
    )
    private boolean modifyShade(final boolean original) {
        return original && this.state.getLightEmission() == 0;
    }
}
