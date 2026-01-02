package io.github.startsmercury.luminous_no_shading.mixin.client.entity.minecraft;

import io.github.startsmercury.luminous_no_shading.impl.client.LuminousNoShadingImpl;
import io.github.startsmercury.luminous_no_shading.impl.client.NoShadingRenderTypes;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.monster.slime.MagmaCubeModel;
import net.minecraft.client.renderer.entity.state.SlimeRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MagmaCubeModel.class)
public abstract class MagmaCubeModelMixin extends EntityModel<SlimeRenderState> {
    private MagmaCubeModelMixin(final ModelPart modelPart) {
        super(modelPart);
    }

    @Inject(method = "<init>", at = @At("CTOR_HEAD"))
    private void makeItGlow(final CallbackInfo callback) {
        this.renderType = resourceLocation -> {
            if (!LuminousNoShadingImpl.isOnGui() && LuminousNoShadingImpl.isGuiOnly()) {
                return RenderTypes.entityCutoutNoCull(resourceLocation);
            } else {
                return NoShadingRenderTypes.entityCutoutNoCull(resourceLocation);
            }
        };
    }
}
