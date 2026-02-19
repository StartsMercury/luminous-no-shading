package io.github.startsmercury.luminous_no_shading.mixin.client.shader.minecraft;

import com.google.common.collect.ImmutableMap;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.shaders.ShaderType;
import io.github.startsmercury.luminous_no_shading.impl.client.LuminousNoShadingImpl;
import io.github.startsmercury.luminous_no_shading.impl.client.NoShadingGlslPreprocessor;
import net.minecraft.client.renderer.ShaderManager;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.util.FileUtil;
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
                Lnet/minecraft/resources/Identifier;       \
                Lnet/minecraft/server/packs/resources/Resource;  \
                Lcom/mojang/blaze3d/shaders/ShaderType;          \
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
        final @Local(ordinal = 0, argsOnly = true) Identifier identifier,
        final @Local(ordinal = 0, argsOnly = true) ShaderType type,
        final @Local(ordinal = 0, argsOnly = true) Map<Identifier, Resource> map,
        final @Local(ordinal = 0, argsOnly = true) ImmutableMap.Builder<
            ShaderManager.ShaderSourceKey,
            String
        > builder,
        final @Local(ordinal = 1) Identifier identifier2,
        final @Local(ordinal = 0) String string
    ) {
        switch (identifier.getPath()) {
            case "shaders/core/item.vsh",
                 "shaders/core/terrain.vsh":
                break;
            default:
                return;
        }

        final var glslPreprocessor = new NoShadingGlslPreprocessor(
            identifier.withPath(FileUtil::getFullResourcePath),
            map
        );

        builder.put(
            new ShaderManager.ShaderSourceKey(
                identifier2.withPath(
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
        final @Local(ordinal = 0, argsOnly = true) Identifier identifier,
        final @Local(ordinal = 1) Identifier identifier2
    ) {
        instance = original.call(instance, key, value);

        switch (identifier.getPath()) {
            case "post_effect/entity_cutout.json",
                 "post_effect/entity_solid.json":
                break;
            default:
                return instance;
        }

        final var identifier3 = identifier2.withPath(
            path -> path + LuminousNoShadingImpl.LUMINOUS_SUFFIX
        );

        return instance.put(identifier3, value);
    }
}
