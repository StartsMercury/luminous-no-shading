package io.github.startsmercury.luminous_no_shading.mixin.client.item.minecraft;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.startsmercury.luminous_no_shading.impl.client.ClearRenderType;
import io.github.startsmercury.luminous_no_shading.impl.client.LuminousNoShadingImpl;
import io.github.startsmercury.luminous_no_shading.impl.client.NoShadingRenderTypes;
import net.minecraft.client.renderer.special.ChestSpecialRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.item.ItemDisplayContext;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChestSpecialRenderer.class)
public class ChestSpecialRendererMixin {
    @Final
    @Shadow
    private Material material;

    @Unique
    private boolean ender;

    @Unique
    private boolean wasGui;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void initEnder(final CallbackInfo callback) {
        this.ender = this.material.texture().getPath().contains("ender");
    }

    @Inject(
        method = "submit",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/resources/model/Material;renderType(" +
                "Ljava/util/function/Function;" +
            ")Lnet/minecraft/client/renderer/rendertype/RenderType;"
        )
    )
    private void freeRenderType(
        final CallbackInfo callback,
        final @Local(argsOnly = true, ordinal = 0) ItemDisplayContext itemDisplayContext
    ) {
        if (!this.ender) {
            return;
        }

        if (this.wasGui) {
            if (itemDisplayContext != ItemDisplayContext.GUI && LuminousNoShadingImpl.isGuiOnly()) {
                ClearRenderType.clear(this.material);
                this.wasGui = false;
            }
        } else {
            if (itemDisplayContext == ItemDisplayContext.GUI) {
                ClearRenderType.clear(this.material);
                this.material.renderType(NoShadingRenderTypes::entitySolid);
                this.wasGui = true;
            }
        }
    }
}
