package io.github.startsmercury.luminous_no_shading.mixin.client.item.minecraft;

import io.github.startsmercury.luminous_no_shading.impl.client.LuminousNoShadingImpl;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;render(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/DeltaTracker;)V"))
    private void applyRenderTypes(final CallbackInfo callback) {
        LuminousNoShadingImpl.setOnGui(true);
        if (LuminousNoShadingImpl.isGuiOnly()) {
            LuminousNoShadingImpl.resetMinimalRenderTypes();
            LuminousNoShadingImpl.applyMinimalRenderTypes();
        }
    }

    @Inject(method = "render", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/client/gui/render/GuiRenderer;render(Lcom/mojang/blaze3d/buffers/GpuBufferSlice;)V", ordinal = 0))
    private void resetRenderTypes(final CallbackInfo callback) {
        if (LuminousNoShadingImpl.isGuiOnly()) {
            LuminousNoShadingImpl.resetMinimalRenderTypes();
        }
        LuminousNoShadingImpl.setOnGui(false);
    }
}
