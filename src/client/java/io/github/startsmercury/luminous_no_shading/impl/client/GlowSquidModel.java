package io.github.startsmercury.luminous_no_shading.impl.client;

import net.minecraft.client.model.SquidModel;
import net.minecraft.client.model.geom.ModelPart;

public class GlowSquidModel extends SquidModel {
    public GlowSquidModel(final ModelPart modelPart) {
        super(modelPart);
        this.renderType = NoShadingRenderTypes::entityCutoutNoCull;
    }
}
