package io.github.startsmercury.luminous_no_shading.mixin.client.shader.minecraft;

import io.github.startsmercury.luminous_no_shading.impl.client.NoShadingSheets;
import net.minecraft.client.renderer.Sheets;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Sheets.class)
public class SheetsMixin {
    static {
        NoShadingSheets.init();
    }
}
