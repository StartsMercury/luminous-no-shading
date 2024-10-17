package io.github.startsmercury.luminous_no_shading.mixin.client.item.minecraft;

import io.github.startsmercury.luminous_no_shading.impl.client.LuminousNoShadingImpl;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(method = "render", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/client/gui/GuiGraphics;<init>(Lnet/minecraft/client/Minecraft;Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;)V"))
    private void applyRenderTypes(final CallbackInfo callback) {
        LuminousNoShadingImpl.setOnGui(true);
        if (LuminousNoShadingImpl.isGuiOnly()) {
            LuminousNoShadingImpl.resetMinimalRenderTypes();
            LuminousNoShadingImpl.applyMinimalRenderTypes();
        }
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;flush()V"))
    private void resetRenderTypes(final CallbackInfo callback) {
        if (LuminousNoShadingImpl.isGuiOnly()) {
            LuminousNoShadingImpl.resetMinimalRenderTypes();
        }
        LuminousNoShadingImpl.setOnGui(false);
    }
}
