package team.lodestar.lodestone.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.lodestar.lodestone.handlers.PostProcessHandler;
import team.lodestar.lodestone.handlers.RenderHandler;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {
    @Shadow
    @Nullable
    private PostEffectProcessor transparencyPostProcessor;

    @Shadow
    @Nullable
    public abstract Framebuffer getCloudsFramebuffer();

    @Inject(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gl/PostEffectProcessor;render(F)V"
            ),
            slice = @Slice(
                    from = @At(
                            value = "FIELD",
                            opcode = Opcodes.GETFIELD,
                            target = "Lnet/minecraft/client/render/WorldRenderer;transparencyPostProcessor:Lnet/minecraft/client/gl/PostEffectProcessor;",
                            ordinal = 0
                    )
            )
    )
    public void injectionBeforeTransparencyChainProcess(CallbackInfo ci) {
        PostProcessHandler.copyDepthBuffer();
    }

    @Inject(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/particle/ParticleManager;renderParticles(Lnet/minecraft/client/render/LightmapTextureManager;Lnet/minecraft/client/render/Camera;F)V",
                    shift = At.Shift.AFTER
            )
    )
    public void postRenderParticles(CallbackInfo ci) {
        RenderHandler.MATRIX4F = new Matrix4f(RenderSystem.getModelViewMatrix());
    }

    @Inject(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/WorldRenderer;renderWeather(Lnet/minecraft/client/render/LightmapTextureManager;FDDD)V",
                    shift = At.Shift.AFTER
            )
    )
    public void postRenderWeather(float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, Matrix4f matrix4f2, CallbackInfo ci, @Local MatrixStack matrices) {
        Matrix4f last = new Matrix4f(RenderSystem.getModelViewMatrix());
        matrices.push();
        Vec3d cameraPos = camera.getPos();
        matrices.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        if (transparencyPostProcessor != null) {
            MinecraftClient.getInstance().getFramebuffer().beginWrite(false);
        }
        RenderHandler.beginBufferedRendering(matrices);
        RenderHandler.renderBufferedParticles(true);
        if (RenderHandler.MATRIX4F != null) {
            RenderSystem.getModelViewMatrix().set(RenderHandler.MATRIX4F);
        }
        RenderHandler.renderBufferedBatches(true);
        RenderHandler.renderBufferedBatches(false);
        RenderSystem.getModelViewMatrix().set(last);
        RenderHandler.renderBufferedParticles(false);

        RenderHandler.endBufferedRendering(matrices);
        if (transparencyPostProcessor != null) {
            getCloudsFramebuffer().beginWrite(false);
        }
        matrices.pop();
    }

}
