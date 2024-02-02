package team.lodestar.lodestone.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.lodestar.lodestone.LodestoneLib;
import team.lodestar.lodestone.handlers.ScreenshakeHandler;
import team.lodestar.lodestone.handlers.screenparticle.ScreenParticleHandler;
import team.lodestar.lodestone.setup.LodestoneParticles;
import team.lodestar.lodestone.setup.LodestoneScreenParticles;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Inject(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/resource/ReloadableResourceManagerImpl;registerReloader(Lnet/minecraft/resource/ResourceReloader;)V",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "FIELD",
                            opcode = Opcodes.GETFIELD,
                            target = "Lnet/minecraft/client/MinecraftClient;particleManager:Lnet/minecraft/client/particle/ParticleManager;"
                    )
            )
    )
    private void registerParticleFactories(RunArgs runArgs, CallbackInfo ci) {
        LodestoneParticles.registerFactories();
        LodestoneScreenParticles.registerParticleFactories();
    }

    @Inject(
            method = "tick",
            at = @At("TAIL")
    )
    private void clientTick(CallbackInfo ci) {
        ScreenParticleHandler.tickParticles();
        ScreenshakeHandler.clientTick(MinecraftClient.getInstance().gameRenderer.getCamera(), LodestoneLib.RANDOM);
    }

    @Inject(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/profiler/Profiler;pop()V",
                    shift = At.Shift.AFTER,
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/client/render/GameRenderer;render(FJZ)V"
                    )
            )
    )
    private void renderTickThingamajig(boolean tick, CallbackInfo ci) {
        ScreenParticleHandler.renderParticles();
    }
}
