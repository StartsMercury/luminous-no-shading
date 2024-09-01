package io.github.startsmercury.luminous_no_shading.mixin.client.entity.minecraft;

import io.github.startsmercury.luminous_no_shading.impl.client.GlowSquidModel;
import net.minecraft.client.model.SquidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.GlowSquid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityRenderers.class)
public class EntityRenderersMixin {
    @Redirect(method = "method_33430", at = @At(value = "NEW", target = "(Lnet/minecraft/client/model/geom/ModelPart;)Lnet/minecraft/client/model/SquidModel;"))
    private static SquidModel<GlowSquid> replaceGlowSquidModel(final ModelPart modelPart) {
        return new GlowSquidModel(modelPart);
    }
}
