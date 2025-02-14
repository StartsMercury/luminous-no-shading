package io.github.startsmercury.luminous_no_shading.mixin.client.shader.minecraft;

import com.google.common.collect.ImmutableMap;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.shaders.CompiledShader;
import io.github.startsmercury.luminous_no_shading.impl.client.LuminousNoShadingImpl;
import io.github.startsmercury.luminous_no_shading.impl.client.NoShadingGlslPreprocessor;
import net.minecraft.FileUtil;
import net.minecraft.client.renderer.ShaderManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(ShaderManager.class)
public abstract class ShaderManagerMixin {
    @Inject(
        method = """
            loadShader (                                         \
                Lnet/minecraft/resources/ResourceLocation;       \
                Lnet/minecraft/server/packs/resources/Resource;  \
                Lcom/mojang/blaze3d/shaders/CompiledShader$Type; \
                Ljava/util/Map;                                  \
                Lcom/google/common/collect/ImmutableMap$Builder; \
            ) V                                                  \
        """,
        at = @At(value = "INVOKE", shift = At.Shift.AFTER, remap = false, target = """
            Lcom/google/common/collect/ImmutableMap$Builder;   \
            put (                                              \
                Ljava/lang/Object;                             \
                Ljava/lang/Object;                             \
            ) Lcom/google/common/collect/ImmutableMap$Builder; \
        """)
    )
    private static void loadCustomShader(
        final CallbackInfo callback,
        final @Local(ordinal = 0, argsOnly = true) ResourceLocation resourceLocation,
        final @Local(ordinal = 0, argsOnly = true) CompiledShader.Type type,
        final @Local(ordinal = 0, argsOnly = true) Map<ResourceLocation, Resource> map,
        final @Local(ordinal = 0, argsOnly = true) ImmutableMap.Builder<
            ShaderManager.ShaderSourceKey,
            String
        > builder,
        final @Local(ordinal = 1) ResourceLocation resourceLocation2,
        final @Local(ordinal = 0) String string
    ) {
        switch (resourceLocation.getPath()) {
            case "shaders/core/rendertype_item_entity_translucent_cull.vsh",
                 "shaders/core/terrain.vsh":
                break;
            default:
                return;
        }

        final var glslPreprocessor = new NoShadingGlslPreprocessor(
            resourceLocation.withPath(FileUtil::getFullResourcePath),
            map
        );

        builder.put(
            new ShaderManager.ShaderSourceKey(
                resourceLocation2.withPath(
                    path -> path + LuminousNoShadingImpl.NO_SHADING_SUFFIX
                ),
                type
            ),
            String.join("", glslPreprocessor.process(string))
        );
    }

    @WrapOperation(
        method = "loadPostChain",
        at = @At(
            value = "INVOKE",
            target = """
                Lcom/google/common/collect/ImmutableMap$Builder;   \
                put (                                              \
                    Ljava/lang/Object;                             \
                    Ljava/lang/Object;                             \
                ) Lcom/google/common/collect/ImmutableMap$Builder; \
            """,
            remap = false
        )
    )
    private static <K, V> ImmutableMap.Builder<K, V> loadCustomProgram(
        ImmutableMap.Builder instance,
        final K key,
        final V value,
        final Operation<ImmutableMap.Builder<K, V>> original,
        final @Local(ordinal = 0, argsOnly = true) ResourceLocation resourceLocation,
        final @Local(ordinal = 1) ResourceLocation resourceLocation2
    ) {
        instance = original.call(instance, key, value);

        switch (resourceLocation.getPath()) {
            case "post_effect/entity_cutout.json",
                 "post_effect/entity_cutout_no_cull.json",
                 "post_effect/entity_solid.json":
                break;
            default:
                return null;
        }

        final var resourceLocation3 = resourceLocation2.withPath(
            path -> path + LuminousNoShadingImpl.LUMINOUS_SUFFIX
        );

        return instance.put(resourceLocation3, value);
    }
}
