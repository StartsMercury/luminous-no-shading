package io.github.startsmercury.luminous_no_shading.mixin.client.shader.minecraft;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import io.github.startsmercury.luminous_no_shading.impl.client.NoShadingRenderPipelines;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderType.class)
public abstract class RenderTypeMixin {
    @Inject(
        method = { "method_34832", "method_34826", "method_62288", "method_34824" },
        at = @At("HEAD")
    )
    private static void detectCustom(
        final CallbackInfoReturnable<RenderType> callback,
        final @Local(ordinal = 0, argsOnly = true) LocalRef<ResourceLocation> resourceLocationRef,
        final @Share("custom") LocalBooleanRef custom
    ) {
        final var resourceLocation = resourceLocationRef.get();
        if ("luminous-no-shading".equals(resourceLocation.getNamespace())) {
            resourceLocationRef.set(
                ResourceLocation.withDefaultNamespace(resourceLocation.getPath())
            );
            custom.set(true);
        }
    }

    @ModifyExpressionValue(
        method = "method_34832",
        at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/RenderPipelines;ENTITY_CUTOUT_NO_CULL:Lcom/mojang/blaze3d/pipeline/RenderPipeline;")
    )
    private static RenderPipeline createCustomEntityCutoutNoCullProvider(
        final RenderPipeline original,
        final @Share("custom") LocalBooleanRef custom
    ) {
        if (custom.get()) {
            return NoShadingRenderPipelines.ENTITY_CUTOUT_NO_CULL;
        } else {
            return original;
        }
    }

    @ModifyExpressionValue(
        method = "method_34826",
        at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/RenderPipelines;ENTITY_SOLID:Lcom/mojang/blaze3d/pipeline/RenderPipeline;")
    )
    private static RenderPipeline createCustomEntitySolidProvider(
        final RenderPipeline original,
        final @Share("custom") LocalBooleanRef custom
    ) {
        if (custom.get()) {
            return NoShadingRenderPipelines.ENTITY_SOLID;
        } else {
            return original;
        }
    }

    @ModifyExpressionValue(
        method = "method_62288",
        at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/RenderPipelines;ENTITY_CUTOUT:Lcom/mojang/blaze3d/pipeline/RenderPipeline;")
    )
    private static RenderPipeline createCustomEntityCutoutProvider(
        final RenderPipeline original,
        final @Share("custom") LocalBooleanRef custom
    ) {
        if (custom.get()) {
            return NoShadingRenderPipelines.ENTITY_CUTOUT;
        } else {
            return original;
        }
    }

    @ModifyExpressionValue(
        method = "method_34824",
        at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/RenderPipelines;ITEM_ENTITY_TRANSLUCENT_CULL:Lcom/mojang/blaze3d/pipeline/RenderPipeline;")
    )
    private static RenderPipeline createCustomItemEntityTranslucentCullProvider(
        final RenderPipeline original,
        final @Share("custom") LocalBooleanRef custom
    ) {
        if (custom.get()) {
            return NoShadingRenderPipelines.ITEM_ENTITY_TRANSLUCENT_CULL;
        } else {
            return original;
        }
    }
}
