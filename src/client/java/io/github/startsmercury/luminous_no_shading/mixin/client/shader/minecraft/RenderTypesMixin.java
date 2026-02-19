package io.github.startsmercury.luminous_no_shading.mixin.client.shader.minecraft;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import io.github.startsmercury.luminous_no_shading.impl.client.NoShadingRenderPipelines;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderTypes.class)
public abstract class RenderTypesMixin {
    @Inject(
        method = { "lambda$static$4", "lambda$static$7", "lambda$static$11", "lambda$static$12" },
        at = @At("HEAD")
    )
    private static void detectCustom(
        final CallbackInfoReturnable<RenderType> callback,
        final @Local(ordinal = 0, argsOnly = true) LocalRef<Identifier> resourceLocationRef,
        final @Share("custom") LocalBooleanRef custom
    ) {
        final var resourceLocation = resourceLocationRef.get();
        if ("luminous-no-shading".equals(resourceLocation.getNamespace())) {
            resourceLocationRef.set(
                Identifier.withDefaultNamespace(resourceLocation.getPath())
            );
            custom.set(true);
        }
    }

    @ModifyExpressionValue(
        method = "lambda$static$4",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/client/renderer/RenderPipelines;ENTITY_SOLID:Lcom/mojang/blaze3d/pipeline/RenderPipeline;",
            opcode = Opcodes.GETSTATIC
        )
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
        method = "lambda$static$7",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/client/renderer/RenderPipelines;ENTITY_CUTOUT:Lcom/mojang/blaze3d/pipeline/RenderPipeline;",
            opcode = Opcodes.GETSTATIC
        )
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
        method = "lambda$static$11",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/client/renderer/RenderPipelines;ITEM_CUTOUT:Lcom/mojang/blaze3d/pipeline/RenderPipeline;",
            opcode = Opcodes.GETSTATIC
        )
    )
    private static RenderPipeline createCustomItemCutoutProvider(
        final RenderPipeline original,
        final @Share("custom") LocalBooleanRef custom
    ) {
        if (custom.get()) {
            return NoShadingRenderPipelines.ITEM_CUTOUT;
        } else {
            return original;
        }
    }

    @ModifyExpressionValue(
        method = "lambda$static$12",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/client/renderer/RenderPipelines;ITEM_TRANSLUCENT:Lcom/mojang/blaze3d/pipeline/RenderPipeline;",
            opcode = Opcodes.GETSTATIC
        )
    )
    private static RenderPipeline createCustomItemTranslucentProvider(
        final RenderPipeline original,
        final @Share("custom") LocalBooleanRef custom
    ) {
        if (custom.get()) {
            return NoShadingRenderPipelines.ITEM_TRANSLUCENT;
        } else {
            return original;
        }
    }
}
