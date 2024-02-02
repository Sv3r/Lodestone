package team.lodestar.lodestone.mixin;

import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.lodestar.lodestone.LodestoneLib;
import team.lodestar.lodestone.config.ClientConfig;
import team.lodestar.lodestone.handlers.ScreenshakeHandler;


@Mixin(Camera.class)
public abstract class CameraMixin {
    @Inject(
            method = "update",
            at = @At("RETURN")
    )
    private void lodestoneScreenshake(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci) {
        if (ClientConfig.SCREENSHAKE_INTENSITY > 0) {
            ScreenshakeHandler.cameraTick((Camera) (Object) this, LodestoneLib.RANDOM);
        }
    }
}
