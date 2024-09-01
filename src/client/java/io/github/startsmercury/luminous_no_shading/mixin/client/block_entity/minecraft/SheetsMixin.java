package io.github.startsmercury.luminous_no_shading.mixin.client.block_entity.minecraft;

import io.github.startsmercury.luminous_no_shading.impl.client.LuminousNoShadingImpl;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Sheets.class)
public class SheetsMixin {
    static {
        Sheets.ENDER_CHEST_LOCATION.renderType(atlasLocations -> {
            return RenderType.entityCutout(
                LuminousNoShadingImpl.mangle(atlasLocations)
            );
        });
    }
}
