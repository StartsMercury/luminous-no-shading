package io.github.startsmercury.luminous_no_shading.impl.client;

import net.minecraft.client.model.SquidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.GlowSquid;

public class GlowSquidModel extends SquidModel<GlowSquid> {
    public GlowSquidModel(final ModelPart modelPart) {
        super(modelPart);
        this.renderType = NoShadingRenderTypes::entityCutoutNoCull;
    }
}
