package io.github.startsmercury.luminous_no_shading.impl.client;

import net.minecraft.client.model.SquidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;

public class GlowSquidModel extends SquidModel {
    public GlowSquidModel(final ModelPart modelPart) {
        super(modelPart);
        this.renderType = resourceLocation -> {
            if (!LuminousNoShadingImpl.isOnGui() && LuminousNoShadingImpl.isGuiOnly()) {
                return RenderType.entityCutoutNoCull(resourceLocation);
            } else {
                return NoShadingRenderTypes.entityCutoutNoCull(resourceLocation);
            }
        };
    }
}
