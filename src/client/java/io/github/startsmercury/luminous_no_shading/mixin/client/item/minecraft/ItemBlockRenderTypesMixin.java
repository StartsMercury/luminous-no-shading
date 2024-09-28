package io.github.startsmercury.luminous_no_shading.mixin.client.item.minecraft;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.startsmercury.luminous_no_shading.impl.client.NoShadingRenderTypes;
import io.github.startsmercury.luminous_no_shading.impl.client.NoShadingSheets;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemBlockRenderTypes.class)
public class ItemBlockRenderTypesMixin {
    @Unique
    private static ReferenceSet<RenderType> unknowns;

    @Unique
    private static void handleUnexpectedRenderType(final RenderType renderType) {
        var unknowns = ItemBlockRenderTypesMixin.unknowns;

        if (unknowns == null) {
            synchronized (ItemBlockRenderTypesMixin.class) {
                unknowns = ItemBlockRenderTypesMixin.unknowns;

                if (unknowns == null) {
                    ItemBlockRenderTypesMixin.unknowns = unknowns = new ReferenceOpenHashSet<>();

                    final var player = Minecraft.getInstance().player;

                    if (player != null) {
                        final var message =
                            "[Luminous No Shading]: Detected unexpected render type (see logs)";
                        final var component = Component.literal(message)
                            .withStyle(style -> style.withColor(ChatFormatting.RED));
                        player.displayClientMessage(component, false);
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

    @ModifyReturnValue(
        method = """
            getRenderType (                                        \
                Lnet/minecraft/world/level/block/state/BlockState; \
            ) Lnet/minecraft/client/renderer/RenderType;           \
        """,
        at = @At("RETURN")
    )
    private static RenderType inspect(
            final RenderType original,
            final @Local(ordinal = 0, argsOnly = true) BlockState state
    ) {
        if (state.getLightEmission() <= 0) {
            return original;
        } else if (original == RenderType.translucent()) {
            return NoShadingRenderTypes.translucent();
        } else if (original == Sheets.translucentItemSheet()) {
            return NoShadingSheets.translucentItemSheet();
        } else if (original == Sheets.cutoutBlockSheet()) {
            return NoShadingSheets.cutoutBlockSheet();
        } else {
            handleUnexpectedRenderType(original);
            return original;
        }
    }
}
