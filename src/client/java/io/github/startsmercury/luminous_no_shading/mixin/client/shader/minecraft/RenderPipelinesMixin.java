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
            args = "stringValue=pipeline/translucent_terrain"
        ))
    )
    private static RenderPipeline createCustomTranslucent(
        final RenderPipeline.Builder builder,
        final Operation<RenderPipeline> original
    ) {
        return createNoShading(builder, original, NoShadingRenderPipelines::tt);
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
            args = "stringValue=pipeline/item_entity_translucent_cull"
        ))
    )
    private static RenderPipeline createCustomItemEntityTranslucentCull(
        final RenderPipeline.Builder builder,
        final Operation<RenderPipeline> original
    ) {
        return createNoShading(builder, original, NoShadingRenderPipelines::ietc);
    }

    @WrapOperation(
        method = "<clinit>",
        at = @At(value = "INVOKE", target = M_BUILD, ordinal = 0),
        slice = @Slice(from = @At(
            value = "CONSTANT",
            args = "stringValue=pipeline/entity_cutout_no_cull"
        ))
    )
    private static RenderPipeline createCustomEntityCutoutNoCull(
        final RenderPipeline.Builder builder,
        final Operation<RenderPipeline> original
    ) {
        return createLuminous(builder, original, NoShadingRenderPipelines::ecnc);
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
            args = "stringValue=pipeline/translucent_terrain"
        ))
    )
    private static void registerCustomTranslucent(final CallbackInfo callback) {
        NoShadingRenderPipelines.TRANSLUCENT_TERRAIN =
            register(NoShadingRenderPipelines.TRANSLUCENT_TERRAIN);
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
            args = "stringValue=pipeline/item_entity_translucent_cull"
        ))
    )
    private static void registerCustomItemEntityTranslucentCull(final CallbackInfo callback) {
        NoShadingRenderPipelines.ITEM_ENTITY_TRANSLUCENT_CULL =
            register(NoShadingRenderPipelines.ITEM_ENTITY_TRANSLUCENT_CULL);
    }
}
