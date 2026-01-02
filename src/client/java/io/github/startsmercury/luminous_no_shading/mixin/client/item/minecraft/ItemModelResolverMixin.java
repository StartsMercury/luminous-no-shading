package io.github.startsmercury.luminous_no_shading.mixin.client.item.minecraft;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemModelResolver.class)
public class ItemModelResolverMixin {
    @Inject(
        method = "updateForTopItem",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/item/ItemModelResolver;appendItemLayers(" +
                "Lnet/minecraft/client/renderer/item/ItemStackRenderState;" +
                "Lnet/minecraft/world/item/ItemStack;" +
                "Lnet/minecraft/world/item/ItemDisplayContext;" +
                "Lnet/minecraft/world/level/Level;" +
                "Lnet/minecraft/world/entity/ItemOwner;" +
                "I" +
            ")V"
        )
    )
    private void setBlockLight(
        final CallbackInfo callback,
        final @Local(ordinal = 0, argsOnly = true) ItemStackRenderState renderState,
        final @Local(ordinal = 0, argsOnly = true) ItemStack itemStack
    ) {
        if (itemStack.getItem() instanceof final BlockItem item) {
            renderState.luminous_no_shading$setBlockLight(
                item.getBlock().defaultBlockState().getLightEmission()
            );
        }
    }
}
