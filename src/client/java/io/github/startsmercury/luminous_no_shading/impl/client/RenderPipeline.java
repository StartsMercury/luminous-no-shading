package io.github.startsmercury.luminous_no_shading.impl.client;

public final class RenderPipeline {
    public interface BuilderExtension {
        default com.mojang.blaze3d.pipeline.RenderPipeline.Builder withoutShaderDefine(final String flag) {
            return null;
        }
    }

    private RenderPipeline() {
    }
}
