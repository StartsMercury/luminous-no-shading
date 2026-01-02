package io.github.startsmercury.luminous_no_shading.mixin.client.item.minecraft;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.startsmercury.luminous_no_shading.impl.client.LuminousNoShadingImpl;
import net.minecraft.client.renderer.item.BlockModelWrapper;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockModelWrapper.class)
public class BlockModelWrapperMixin {
    @ModifyReturnValue(method = "method_76559", at = @At("RETURN"))
    private static RenderType replaceItemRenderType(
        final RenderType original,
        final @Local(ordinal = 0, argsOnly = true) ItemStack itemStack
    ) {
        if (!LuminousNoShadingImpl.isOnGui() && LuminousNoShadingImpl.isGuiOnly()) {
            return original;
        } else {
            return LuminousNoShadingImpl.modifyItemRenderType(original, itemStack);
        }
    }

    @ModifyReturnValue(method = "method_76557", at = @At("RETURN"))
    private static RenderType replaceBlockRenderType(
        final RenderType original,
        final @Local(ordinal = 0, argsOnly = true) ItemStack itemStack
    ) {
        if (!LuminousNoShadingImpl.isOnGui() && LuminousNoShadingImpl.isGuiOnly()) {
            return original;
        } else {
            return LuminousNoShadingImpl.modifyBlockRenderType(original, itemStack);
        }
    }
}
