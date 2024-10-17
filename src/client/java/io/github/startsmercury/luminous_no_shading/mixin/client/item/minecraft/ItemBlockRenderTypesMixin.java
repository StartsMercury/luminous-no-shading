package io.github.startsmercury.luminous_no_shading.mixin.client.item.minecraft;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.startsmercury.luminous_no_shading.impl.client.LuminousNoShadingImpl;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemBlockRenderTypes.class)
public class ItemBlockRenderTypesMixin {
	@ModifyReturnValue(at = @At("RETURN"), method = "getRenderType(Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/client/renderer/RenderType;")
	private static RenderType modifyBlockRenderType(
		final RenderType original,
		final @Local(ordinal = 0, argsOnly = true) BlockState state
    ) {
		if (!LuminousNoShadingImpl.isOnGui() && LuminousNoShadingImpl.isGuiOnly()) {
			return original;
		} else {
			return LuminousNoShadingImpl.modifyBlockRenderType(original, state);
		}
	}
}
