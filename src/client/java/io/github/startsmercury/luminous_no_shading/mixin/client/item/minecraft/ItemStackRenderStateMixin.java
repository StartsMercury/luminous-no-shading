package io.github.startsmercury.luminous_no_shading.mixin.client.item.minecraft;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import io.github.startsmercury.luminous_no_shading.impl.client.ItemStackRenderStateExtension;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.world.item.ItemDisplayContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStackRenderState.class)
public class ItemStackRenderStateMixin implements ItemStackRenderStateExtension {
    @Shadow
    ItemDisplayContext displayContext;

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
        // NOTE At 26.1-snapshot-1, the ones on GUIs started turning invisible, skip to fix
        if (this.displayContext == ItemDisplayContext.GUI) {
            return;
        }

        final var original = lightCoords.get();
        final var block = LightCoordsUtil.block(original);
        final var sky = LightCoordsUtil.sky(original);
        lightCoords.set(LightCoordsUtil.pack(
            Math.max(block, this.luminous_no_shading$getBlockLight()),
            sky
        ));
    }
}
