package team.lodestar.lodestone.mixin;

import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.lodestar.lodestone.handlers.screenparticle.ScreenParticleHandler;

@Mixin(Screen.class)
public abstract class ScreenMixin {
    @Inject(
            method = "renderInGameBackground",
            at = @At("HEAD")
    )
    private void beforeBackgroundParticle(CallbackInfo ci) {
        ScreenParticleHandler.renderEarliestParticles();
    }
}
