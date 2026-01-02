package io.github.startsmercury.luminous_no_shading.mixin.client.item.minecraft;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import io.github.startsmercury.luminous_no_shading.impl.client.ItemStackRenderStateExtension;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStackRenderState.class)
public class ItemStackRenderStateMixin implements ItemStackRenderStateExtension {
    @Unique
    private int blockLight;

    @Override
    public int luminous_no_shading$getBlockLight() {
        return this.blockLight;
    }

    @Override
    public void luminous_no_shading$setBlockLight(final int blockLight) {
        this.blockLight = blockLight;
    }

    @Inject(method = "submit", at = @At("HEAD"))
    private void modifySubmittedLightCoords(
        final CallbackInfo callback,
        final @Local(ordinal = 0, argsOnly = true) LocalIntRef lightCoords
    ) {
        final var original = lightCoords.get();
        final var block = LightTexture.block(original);
        final var sky = LightTexture.sky(original);
        lightCoords.set(LightTexture.pack(
            Math.max(block, this.luminous_no_shading$getBlockLight()),
            sky
        ));
    }
}
