package io.github.startsmercury.luminous_no_shading.impl.client;

import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.Identifier;

public final class NoShadingSheets {
    public static void init() {}

    @SuppressWarnings("deprecation")
    private static final Identifier MANGLED_LOCATION_BLOCKS =
        LuminousNoShadingImpl.mangle(TextureAtlas.LOCATION_BLOCKS);

    @SuppressWarnings("deprecation")
    private static final Identifier MANGLED_LOCATION_ITEMS =
        LuminousNoShadingImpl.mangle(TextureAtlas.LOCATION_ITEMS);

    private static final RenderType CUTOUT_BLOCK_ITEM_SHEET =
        RenderTypes.itemCutout(MANGLED_LOCATION_BLOCKS);

    private static final RenderType TRANSLUCENT_BLOCK_ITEM_SHEET =
        RenderTypes.itemTranslucent(MANGLED_LOCATION_BLOCKS);

    private static final RenderType TRANSLUCENT_ITEM_SHEET =
        RenderTypes.itemTranslucent(MANGLED_LOCATION_ITEMS);

    public static RenderType cutoutBlockItemSheet() {
        return CUTOUT_BLOCK_ITEM_SHEET;
    }

    public static RenderType translucentItemSheet() {
        return TRANSLUCENT_ITEM_SHEET;
    }

    public static RenderType translucentBlockItemSheet() {
        return TRANSLUCENT_BLOCK_ITEM_SHEET;
    }
}
