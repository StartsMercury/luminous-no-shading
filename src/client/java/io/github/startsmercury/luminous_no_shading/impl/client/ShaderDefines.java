package io.github.startsmercury.luminous_no_shading.impl.client;

public final class ShaderDefines {
    public interface BuilderExtension {
        default net.minecraft.client.renderer.ShaderDefines.Builder undefine(final String flag) {
            return null;
        }
    }

    private ShaderDefines() {}
}
