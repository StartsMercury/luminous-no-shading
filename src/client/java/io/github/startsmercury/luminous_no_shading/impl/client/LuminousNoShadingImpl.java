package io.github.startsmercury.luminous_no_shading.impl.client;

import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.ConduitRenderer;
import net.minecraft.client.renderer.blockentity.EnchantTableRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.LoggerFactory;

public class LuminousNoShadingImpl {
	public static void resetMinimalRenderTypes() {
		ClearRenderType.clear(ConduitRenderer.SHELL_TEXTURE);
		ClearRenderType.clear(Sheets.ENDER_CHEST_LOCATION);
	}

	public static void resetRenderTypes() {
		LuminousNoShadingImpl.resetMinimalRenderTypes();

		ClearRenderType.clear(ConduitRenderer.ACTIVE_SHELL_TEXTURE);
		ClearRenderType.clear(ConduitRenderer.WIND_TEXTURE);
		ClearRenderType.clear(ConduitRenderer.VERTICAL_WIND_TEXTURE);
		ClearRenderType.clear(ConduitRenderer.OPEN_EYE_TEXTURE);
		ClearRenderType.clear(ConduitRenderer.CLOSED_EYE_TEXTURE);
		ClearRenderType.clear(EnchantTableRenderer.BOOK_LOCATION);
	}

	public static void applyMinimalRenderTypes() {
		ConduitRenderer.SHELL_TEXTURE.renderType(NoShadingRenderTypes::entitySolid);
		Sheets.ENDER_CHEST_LOCATION.renderType(atlasLocations -> RenderType.entityCutout(
				LuminousNoShadingImpl.mangle(atlasLocations)
		));
	}

	public static void applyRenderTypes() {
		LuminousNoShadingImpl.applyMinimalRenderTypes();

		ConduitRenderer.ACTIVE_SHELL_TEXTURE.renderType(NoShadingRenderTypes::entityCutoutNoCull);
		ConduitRenderer.WIND_TEXTURE.renderType(NoShadingRenderTypes::entityCutoutNoCull);
		ConduitRenderer.VERTICAL_WIND_TEXTURE.renderType(NoShadingRenderTypes::entityCutoutNoCull);
		ConduitRenderer.OPEN_EYE_TEXTURE.renderType(NoShadingRenderTypes::entityCutoutNoCull);
		ConduitRenderer.CLOSED_EYE_TEXTURE.renderType(NoShadingRenderTypes::entityCutoutNoCull);
		EnchantTableRenderer.BOOK_LOCATION.renderType(NoShadingRenderTypes::entitySolid);
	}

	private static boolean guiOnly;

	public static boolean isGuiOnly() {
		return LuminousNoShadingImpl.guiOnly;
	}

	public static void setGuiOnly(final boolean guiOnly) {
		final var wasGuiOnly = LuminousNoShadingImpl.guiOnly;
        if (wasGuiOnly == guiOnly) {
            return;
        }

        LuminousNoShadingImpl.guiOnly = guiOnly;
        LuminousNoShadingImpl.resetRenderTypes();
        if (!guiOnly) {
            LuminousNoShadingImpl.applyRenderTypes();
        }
    }

	private static boolean onGui;

	public static boolean isOnGui() {
		return LuminousNoShadingImpl.onGui;
	}

	public static void setOnGui(final boolean onGui) {
		LuminousNoShadingImpl.onGui = onGui;
	}

	public static ResourceLocation mangle(final ResourceLocation resourceLocation) {
		return new ResourceLocation("luminous-no-shading", resourceLocation.getPath());
	}

	public static RenderType modifyBlockRenderType(final RenderType original, final BlockState state) {
		if (state.getLightEmission() <= 0) {
			return original;
		} else if (original == Sheets.translucentCullBlockSheet()) {
			return NoShadingSheets.translucentCullBlockSheet();
		} else if (original == Sheets.translucentItemSheet()) {
			return NoShadingSheets.translucentItemSheet();
		} else if (original == Sheets.cutoutBlockSheet()) {
			return NoShadingSheets.cutoutBlockSheet();
		} else {
			handleUnexpectedRenderType(original);
			return original;
		}
	}

	private static ReferenceSet<RenderType> unknowns;

	private static void handleUnexpectedRenderType(final RenderType renderType) {
		var unknowns = LuminousNoShadingImpl.unknowns;

		if (unknowns == null) {
			synchronized (LuminousNoShadingImpl.class) {
				unknowns = LuminousNoShadingImpl.unknowns;

				if (unknowns == null) {
					LuminousNoShadingImpl.unknowns = unknowns = new ReferenceOpenHashSet<>();

					final var player = Minecraft.getInstance().player;

					if (player != null) {
						final var message =
							"[Luminous No Shading]: Detected unexpected render type (see logs)";
						player.sendSystemMessage(
							Component.literal(message)
								.withStyle(style -> style.withColor(ChatFormatting.RED))
						);
					}
				}
			}
		}

		if (unknowns.add(renderType)) {
			final var logger = LoggerFactory.getLogger("Luminous No Shading");

			if (logger.isWarnEnabled()) {
				logger
					.atWarn()
					.setCause(new AssertionError("Unexpected render type"))
					.setMessage("[Luminous No Shading] " + renderType)
					.log();
			}
		}
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
