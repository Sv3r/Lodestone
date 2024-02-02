package team.lodestar.lodestone.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.lodestar.lodestone.handlers.screenparticle.ScreenParticleHandler;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(
            method = "renderHotbar",
            at = @At("HEAD")
    )
    private void renderHotbarStart(CallbackInfo ci) {
        ScreenParticleHandler.renderingHotbar = true;
        ScreenParticleHandler.x = 0;
        ScreenParticleHandler.y = 0;
    }

    @Inject(
            method = "renderHotbar",
            at = @At("RETURN")
    )
    private void renderHotbarEnd(CallbackInfo ci) {
        ScreenParticleHandler.renderingHotbar = false;
    }
}
