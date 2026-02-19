package io.github.startsmercury.luminous_no_shading.mixin.client.entity.minecraft;

import io.github.startsmercury.luminous_no_shading.impl.client.LuminousNoShadingImpl;
import io.github.startsmercury.luminous_no_shading.impl.client.NoShadingRenderTypes;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.monster.blaze.BlazeModel;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlazeModel.class)
public abstract class BlazeModelMixin extends EntityModel<LivingEntityRenderState> {
    private BlazeModelMixin(final ModelPart modelPart) {
        super(modelPart);
    }

    @Inject(method = "<init>", at = @At("CTOR_HEAD"))
    private void makeItGlow(final CallbackInfo callback) {
        this.renderType = resourceLocation -> {
            if (!LuminousNoShadingImpl.isOnGui() && LuminousNoShadingImpl.isGuiOnly()) {
                return RenderTypes.entityCutout(resourceLocation);
            } else {
                return NoShadingRenderTypes.entityCutout(resourceLocation);
            }
        };
    }
}
