package io.github.startsmercury.luminous_no_shading.impl.client;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;

public final class NoShadingSheets {
    public static void init() {}

    @SuppressWarnings("deprecation")
    private static final ResourceLocation MANGLED_LOCATION_BLOCKS =
        LuminousNoShadingImpl.mangle(TextureAtlas.LOCATION_BLOCKS);

    private static final RenderType CUTOUT_BLOCK_SHEET = RenderType.entityCutout(MANGLED_LOCATION_BLOCKS);
    private static final RenderType TRANSLUCENT_ITEM_CULL_BLOCK_SHEET = RenderType.itemEntityTranslucentCull(MANGLED_LOCATION_BLOCKS);
    private static final RenderType TRANSLUCENT_CULL_BLOCK_SHEET = RenderType.entityTranslucentCull(MANGLED_LOCATION_BLOCKS);

    public static RenderType cutoutBlockSheet() {
        return CUTOUT_BLOCK_SHEET;
    }

    public static RenderType translucentItemSheet() {
        return TRANSLUCENT_ITEM_CULL_BLOCK_SHEET;
    }

    public static RenderType translucentCullBlockSheet() {
        return TRANSLUCENT_CULL_BLOCK_SHEET;
    }
}
