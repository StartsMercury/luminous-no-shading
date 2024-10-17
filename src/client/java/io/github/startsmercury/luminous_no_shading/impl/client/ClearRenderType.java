package io.github.startsmercury.luminous_no_shading.impl.client;

public interface ClearRenderType {
    static void clear(final Object self) {
        ((ClearRenderType) self).clear();
    }

    void clear();
}
