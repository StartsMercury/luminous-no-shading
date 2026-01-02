package io.github.startsmercury.luminous_no_shading.mixin.client.shader.minecraft;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import io.github.startsmercury.luminous_no_shading.impl.client.RenderPipeline.BuilderExtension;
import net.minecraft.client.renderer.ShaderDefines;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;

@Mixin(RenderPipeline.Builder.class)
public class RenderPipeline$BuilderMixin implements BuilderExtension {
    @Shadow
    private Optional<ShaderDefines.Builder> definesBuilder;

    @Override
    public RenderPipeline.Builder withoutShaderDefine(final String flag) {
        this.definesBuilder.ifPresent(builder -> builder.undefine(flag));
        return (RenderPipeline.Builder) (BuilderExtension) this;
    }
}
