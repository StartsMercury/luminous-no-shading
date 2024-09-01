package io.github.startsmercury.luminous_no_shading.mixin.client.shader.minecraft;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import io.github.startsmercury.luminous_no_shading.impl.client.NoShadingRenderStateShards;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderType.class)
public abstract class RenderTypeMixin {
    @Inject(
        method = { "method_34832", "method_34826", "method_34825", "method_34824", "method_34823" },
        at = @At("HEAD")
    )
    private static void detectCustom(
        final CallbackInfoReturnable<RenderType> callback,
        final @Local(ordinal = 0, argsOnly = true) LocalRef<ResourceLocation> resourceLocationRef,
        final @Share("custom") LocalBooleanRef custom
    ) {
        final var resourceLocation = resourceLocationRef.get();
        if ("luminous-no-shading".equals(resourceLocation.getNamespace())) {
            resourceLocationRef.set(new ResourceLocation(resourceLocation.getPath()));
            custom.set(true);
        }
    }

    @ModifyExpressionValue(
        method = "method_34832",
        at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/RenderType;RENDERTYPE_ENTITY_CUTOUT_NO_CULL_SHADER:Lnet/minecraft/client/renderer/RenderStateShard$ShaderStateShard;")
    )
    private static RenderStateShard.ShaderStateShard createCustomEntityCutoutNoCullProvider(
        final RenderStateShard.ShaderStateShard original,
        final @Share("custom") LocalBooleanRef custom
    ) {
        if (custom.get()) {
            return NoShadingRenderStateShards.RENDERTYPE_ENTITY_CUTOUT_NO_CULL_SHADER;
        } else {
            return original;
        }
    }

    @ModifyExpressionValue(
        method = "method_34826",
        at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/RenderType;RENDERTYPE_ENTITY_SOLID_SHADER:Lnet/minecraft/client/renderer/RenderStateShard$ShaderStateShard;")
    )
    private static RenderStateShard.ShaderStateShard createCustomEntitySolidProvider(
        final RenderStateShard.ShaderStateShard original,
        final @Share("custom") LocalBooleanRef custom
    ) {
        if (custom.get()) {
            return NoShadingRenderStateShards.RENDERTYPE_ENTITY_SOLID_SHADER;
        } else {
            return original;
        }
    }

    @ModifyExpressionValue(
        method = "method_34825",
        at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/RenderType;RENDERTYPE_ENTITY_CUTOUT_SHADER:Lnet/minecraft/client/renderer/RenderStateShard$ShaderStateShard;")
    )
    private static RenderStateShard.ShaderStateShard createCustomEntityCutoutProvider(
        final RenderStateShard.ShaderStateShard original,
        final @Share("custom") LocalBooleanRef custom
    ) {
        if (custom.get()) {
            return NoShadingRenderStateShards.RENDERTYPE_ENTITY_CUTOUT_SHADER;
        } else {
            return original;
        }
    }

    @ModifyExpressionValue(
        method = "method_34824",
        at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/RenderType;RENDERTYPE_ITEM_ENTITY_TRANSLUCENT_CULL_SHADER:Lnet/minecraft/client/renderer/RenderStateShard$ShaderStateShard;")
    )
    private static RenderStateShard.ShaderStateShard createCustomItemEntityTranslucentCullProvider(
        final RenderStateShard.ShaderStateShard original,
        final @Share("custom") LocalBooleanRef custom
    ) {
        if (custom.get()) {
            return NoShadingRenderStateShards.RENDERTYPE_ITEM_ENTITY_TRANSLUCENT_CULL_SHADER;
        } else {
            return original;
        }
    }

    @ModifyExpressionValue(
        method = "method_34823",
        at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/RenderType;RENDERTYPE_ENTITY_TRANSLUCENT_CULL_SHADER:Lnet/minecraft/client/renderer/RenderStateShard$ShaderStateShard;")
    )
    private static RenderStateShard.ShaderStateShard createCustomEntityTranslucentCullProvider(
            final RenderStateShard.ShaderStateShard original,
            final @Share("custom") LocalBooleanRef custom
    ) {
        if (custom.get()) {
            return NoShadingRenderStateShards.RENDERTYPE_ENTITY_TRANSLUCENT_CULL_SHADER;
        } else {
            return original;
        }
    }
}
