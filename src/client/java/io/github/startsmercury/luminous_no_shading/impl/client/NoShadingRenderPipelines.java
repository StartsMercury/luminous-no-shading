package io.github.startsmercury.luminous_no_shading.impl.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.blaze3d.pipeline.RenderPipeline;

import java.util.function.Consumer;

public final class NoShadingRenderPipelines {
    /**
     * In the creation of a custom derived {@link RenderPipeline}, its builder
     * is reused.
     */
    public static final String M_BUILD = """
        Lcom/mojang/blaze3d/pipeline/RenderPipeline$Builder; \
        build (                                              \
        ) Lcom/mojang/blaze3d/pipeline/RenderPipeline;       \
    """;

    /**
     * A custom {@link RenderPipeline} is registered after its original.
     */
    public static final String M_REGISTER = """
        Lnet/minecraft/client/renderer/RenderPipelines;  \
        register (                                       \
            Lcom/mojang/blaze3d/pipeline/RenderPipeline; \
        ) Lcom/mojang/blaze3d/pipeline/RenderPipeline;   \
    """;

    public static RenderPipeline ENTITY_SOLID;
    public static RenderPipeline ENTITY_CUTOUT;
    public static RenderPipeline ITEM_TRANSLUCENT;
    public static RenderPipeline ITEM_CUTOUT;

    public static RenderPipeline createNoShading(
        final RenderPipeline.Builder builder,
        final Operation<RenderPipeline> original,
        final Consumer<? super RenderPipeline> setter
    ) {
        final var pipeline = original.call(builder);

        final var customLocation = pipeline
            .getLocation()
            .withPath(path -> path + LuminousNoShadingImpl.LUMINOUS_SUFFIX);
        final var customVertexShader = pipeline
            .getVertexShader()
            .withPath(path -> path + LuminousNoShadingImpl.NO_SHADING_SUFFIX);
        final var customPipeline = builder
            .withLocation(customLocation)
            .withVertexShader(customVertexShader)
            .build();
        setter.accept(customPipeline);

        return pipeline;
    }

    public static RenderPipeline createLuminous(
        final RenderPipeline.Builder builder,
        final Operation<RenderPipeline> original,
        final Consumer<? super RenderPipeline> setter
    ) {
        final var pipeline = original.call(builder);

        final var customLocation = pipeline
            .getLocation()
            .withPath(path -> path + LuminousNoShadingImpl.LUMINOUS_SUFFIX);
        final var customPipeline = builder
            .withLocation(customLocation)
            .withoutShaderDefine("PER_FACE_LIGHTING")
            .withShaderDefine("NO_CARDINAL_LIGHTING")
            .build();
        setter.accept(customPipeline);

        return pipeline;
    }

    public static void es(final RenderPipeline entitySolid) {
        ENTITY_SOLID = entitySolid;
    }

    public static void ec(final RenderPipeline entityCutout) {
        ENTITY_CUTOUT = entityCutout;
    }

    public static void it(final RenderPipeline itemTranslucent) {
        ITEM_TRANSLUCENT = itemTranslucent;
    }

    public static void ic(final RenderPipeline itemCutout) {
        ITEM_CUTOUT = itemCutout;
    }
}
