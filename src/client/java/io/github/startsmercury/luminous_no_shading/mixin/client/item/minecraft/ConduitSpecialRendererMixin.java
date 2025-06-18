package io.github.startsmercury.luminous_no_shading.mixin.client.item.minecraft;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.startsmercury.luminous_no_shading.impl.client.ClearRenderType;
import io.github.startsmercury.luminous_no_shading.impl.client.LuminousNoShadingImpl;
import io.github.startsmercury.luminous_no_shading.impl.client.NoShadingRenderTypes;
import net.minecraft.client.renderer.blockentity.ConduitRenderer;
import net.minecraft.client.renderer.special.ConduitSpecialRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ConduitSpecialRenderer.class)
public class ConduitSpecialRendererMixin {
    @Unique
    private boolean wasGui;

    @Inject(
        method = "render",
        at = @At(
            value = "INVOKE",
            target = """
                Lnet/minecraft/client/resources/model/Material;       \
                buffer(                                               \
                    Lnet/minecraft/client/renderer/MultiBufferSource; \
                    Ljava/util/function/Function;                     \
                ) Lcom/mojang/blaze3d/vertex/VertexConsumer;          \
            """
        )
    )
    private void freeRenderType(
        final CallbackInfo callback,
        final @Local(argsOnly = true, ordinal = 0) ItemDisplayContext itemDisplayContext
    ) {
        if (this.wasGui) {
            if (itemDisplayContext != ItemDisplayContext.GUI && LuminousNoShadingImpl.isGuiOnly()) {
                ClearRenderType.clear(ConduitRenderer.SHELL_TEXTURE);
                this.wasGui = false;
            }
        } else {
            if (itemDisplayContext == ItemDisplayContext.GUI) {
                ClearRenderType.clear(ConduitRenderer.SHELL_TEXTURE);
                ConduitRenderer.SHELL_TEXTURE.renderType(NoShadingRenderTypes::entitySolid);
                this.wasGui = true;
            }
        }
    }
}
