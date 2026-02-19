package io.github.startsmercury.luminous_no_shading.impl.client;

import net.minecraft.client.model.animal.squid.SquidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.rendertype.RenderTypes;

public class GlowSquidModel extends SquidModel {
    public GlowSquidModel(final ModelPart modelPart) {
        super(modelPart);
        this.renderType = identifier -> {
            if (!LuminousNoShadingImpl.isOnGui() && LuminousNoShadingImpl.isGuiOnly()) {
                return RenderTypes.entityCutout(identifier);
            } else {
                return NoShadingRenderTypes.entityCutout(identifier);
            }
        };
    }
}
