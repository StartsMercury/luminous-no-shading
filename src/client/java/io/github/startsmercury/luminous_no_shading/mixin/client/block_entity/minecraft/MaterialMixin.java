package io.github.startsmercury.luminous_no_shading.mixin.client.block_entity.minecraft;

import io.github.startsmercury.luminous_no_shading.impl.client.ClearRenderType;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.Material;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Material.class)
public class MaterialMixin implements ClearRenderType {
    @Shadow
    private @Nullable RenderType renderType;

    @Override
    @Unique
    public void clear() {
        this.renderType = null;
    }
}
