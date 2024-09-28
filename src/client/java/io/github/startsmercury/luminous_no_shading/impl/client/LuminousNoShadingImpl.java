package io.github.startsmercury.luminous_no_shading.impl.client;

import net.minecraft.resources.ResourceLocation;

public class LuminousNoShadingImpl {
	public static final String CUSTOM_SHADER_SUFFIX = "_no_shading";

    public static ResourceLocation mangle(final ResourceLocation resourceLocation) {
		return ResourceLocation.fromNamespaceAndPath("luminous-no-shading", resourceLocation.getPath());
	}
}
