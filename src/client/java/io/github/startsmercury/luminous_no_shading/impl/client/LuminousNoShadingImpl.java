package io.github.startsmercury.luminous_no_shading.impl.client;

import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;

public class LuminousNoShadingImpl {
	public static ResourceLocation mangle(final ResourceLocation resourceLocation) {
		return ResourceLocation.fromNamespaceAndPath("luminous-no-shading", resourceLocation.getPath());
	}

	public static ShaderInstance rendertypeEntityCutoutNoCullShader;

	public static ShaderInstance rendertypeEntitySolidShader;

	public static ShaderInstance rendertypeEntityCutoutShader;

	public static ShaderInstance rendertypeItemEntityTranslucentCullShader;

	public static ShaderInstance rendertypeEntityTranslucentCullShader;

	public static ShaderInstance getRendertypeEntityCutoutNoCullShader() {
		return rendertypeEntityCutoutNoCullShader;
	}

	public static ShaderInstance getRendertypeEntitySolidShader() {
		return rendertypeEntitySolidShader;
	}

	public static ShaderInstance getRendertypeEntityCutoutShader() {
		return rendertypeEntityCutoutShader;
	}

	public static ShaderInstance getRendertypeItemEntityTranslucentCullShader() {
		return rendertypeItemEntityTranslucentCullShader;
	}

	public static ShaderInstance getRendertypeEntityTranslucentCullShader() {
		return rendertypeEntityTranslucentCullShader;
	}
}
