package io.github.startsmercury.luminous_no_shading.mixin.client.shader.minecraft;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import io.github.startsmercury.luminous_no_shading.impl.client.NoShadingRenderPipelines;
import net.minecraft.client.renderer.RenderPipelines;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static io.github.startsmercury.luminous_no_shading.impl.client.NoShadingRenderPipelines.*;

@Mixin(RenderPipelines.class)
public abstract class RenderPipelinesMixin {
    @WrapOperation(
        method = "<clinit>",
        at = @At(value = "INVOKE", target = M_BUILD, ordinal = 0),
        slice = @Slice(from = @At(
            value = "CONSTANT",
            args = "stringValue=pipeline/entity_solid"
        ))
    )
    private static RenderPipeline createCustomEntitySolid(
        final RenderPipeline.Builder builder,
        final Operation<RenderPipeline> original
    ) {
        return createLuminous(builder, original, NoShadingRenderPipelines::es);
    }

    @WrapOperation(
        method = "<clinit>",
        at = @At(value = "INVOKE", target = M_BUILD, ordinal = 0),
        slice = @Slice(from = @At(
            value = "CONSTANT",
            args = "stringValue=pipeline/entity_cutout"
        ))
    )
    private static RenderPipeline createCustomEntityCutout(
        final RenderPipeline.Builder builder,
        final Operation<RenderPipeline> original
    ) {
        return createLuminous(builder, original, NoShadingRenderPipelines::ec);
    }

    @WrapOperation(
        method = "<clinit>",
        at = @At(value = "INVOKE", target = M_BUILD, ordinal = 0),
        slice = @Slice(from = @At(
            value = "CONSTANT",
            args = "stringValue=pipeline/item_translucent"
        ))
    )
    private static RenderPipeline createCustomItemTranslucent(
        final RenderPipeline.Builder builder,
        final Operation<RenderPipeline> original
    ) {
        return createNoShading(builder, original, NoShadingRenderPipelines::it);
    }

    @WrapOperation(
        method = "<clinit>",
        at = @At(value = "INVOKE", target = M_BUILD, ordinal = 0),
        slice = @Slice(from = @At(
            value = "CONSTANT",
            args = "stringValue=pipeline/item_cutout"
        ))
    )
    private static RenderPipeline createCustomItemCutout(
        final RenderPipeline.Builder builder,
        final Operation<RenderPipeline> original
    ) {
        return createNoShading(builder, original, NoShadingRenderPipelines::ic);
    }

    @Shadow
    private static RenderPipeline register(RenderPipeline renderPipeline) {
        throw new AssertionError();
    }

    @Inject(
        method = "<clinit>",
        at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = M_REGISTER, ordinal = 0),
        slice = @Slice(from = @At(
            value = "CONSTANT",
            args = "stringValue=pipeline/entity_solid"
        ))
    )
    private static void registerCustomEntitySolid(final CallbackInfo callback) {
        NoShadingRenderPipelines.ENTITY_SOLID =
            register(NoShadingRenderPipelines.ENTITY_SOLID);
    }

    @Inject(
        method = "<clinit>",
        at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = M_REGISTER, ordinal = 0),
        slice = @Slice(from = @At(
            value = "CONSTANT",
            args = "stringValue=pipeline/entity_cutout"
        ))
    )
    private static void registerCustomEntityCutout(final CallbackInfo callback) {
        NoShadingRenderPipelines.ENTITY_CUTOUT =
            register(NoShadingRenderPipelines.ENTITY_CUTOUT);
    }

    @Inject(
        method = "<clinit>",
        at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = M_REGISTER, ordinal = 0),
        slice = @Slice(from = @At(
            value = "CONSTANT",
            args = "stringValue=pipeline/item_translucent"
        ))
    )
    private static void registerCustomItemTranslucent(final CallbackInfo callback) {
        NoShadingRenderPipelines.ITEM_TRANSLUCENT =
            register(NoShadingRenderPipelines.ITEM_TRANSLUCENT);
    }

    @Inject(
        method = "<clinit>",
        at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = M_REGISTER, ordinal = 0),
        slice = @Slice(from = @At(
            value = "CONSTANT",
            args = "stringValue=pipeline/item_cutout"
        ))
    )
    private static void registerCustomItemCutout(final CallbackInfo callback) {
        NoShadingRenderPipelines.ITEM_CUTOUT =
            register(NoShadingRenderPipelines.ITEM_CUTOUT);
    }
}
