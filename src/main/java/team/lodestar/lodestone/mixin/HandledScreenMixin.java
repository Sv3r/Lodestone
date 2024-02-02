package team.lodestar.lodestone.mixin;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.lodestar.lodestone.handlers.screenparticle.ScreenParticleHandler;

@Mixin(HandledScreen.class)
public abstract class HandledScreenMixin {
    @Shadow
    protected int x;

    @Shadow
    protected int y;

    @Inject(
            method = "render",
            at = @At("RETURN")
    )
    private void beforeTooltipParticle(CallbackInfo ci) {
        ScreenParticleHandler.renderEarlyParticles();
    }

    @Inject(
            method = "render",
            at = @At("HEAD")
    )
    private void renderHotbarStart(CallbackInfo ci) {
        ScreenParticleHandler.renderingHotbar = true;
        ScreenParticleHandler.x = this.x;
        ScreenParticleHandler.y = this.y;
    }
}
