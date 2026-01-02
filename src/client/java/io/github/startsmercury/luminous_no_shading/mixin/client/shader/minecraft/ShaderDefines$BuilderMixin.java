package io.github.startsmercury.luminous_no_shading.mixin.client.shader.minecraft;

import com.google.common.collect.ImmutableSet;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.startsmercury.luminous_no_shading.impl.client.ShaderDefines.BuilderExtension;
import net.minecraft.client.renderer.ShaderDefines;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ShaderDefines.Builder.class)
public class ShaderDefines$BuilderMixin implements BuilderExtension {
    @Unique
    private final ImmutableSet.Builder<String> flagsToUndef = new ImmutableSet.Builder<>();

    @Override
    public ShaderDefines.Builder undefine(final String flag) {
        this.flagsToUndef.add(flag);
        return (ShaderDefines.Builder) (BuilderExtension) this;
    }

    @ModifyExpressionValue(
        method = "build",
        at = @At(
            value = "INVOKE",
            target = "Lcom/google/common/collect/ImmutableSet$Builder;build(" +
            ")Lcom/google/common/collect/ImmutableSet;"
        )
    )
    private ImmutableSet<String> applyUndefs(final ImmutableSet<String> original) {
        final var flagsToUndef = this.flagsToUndef.build();
        final var builder = ImmutableSet.<String>builder();
        for (final var flag : original) {
            if (!flagsToUndef.contains(flag)) {
                builder.add(flag);
            }
        }
        return builder.build();
    }
}
