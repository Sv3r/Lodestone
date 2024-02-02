package team.lodestar.lodestone.mixin;

import com.llamalad7.mixinextras.injector.ModifyReceiver;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.resource.ResourceFactory;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.lodestar.lodestone.handlers.PostProcessHandler;
import team.lodestar.lodestone.setup.LodestoneShaders;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Inject(
            method = "renderWorld",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/WorldRenderer;render(FJZLnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/GameRenderer;Lnet/minecraft/client/render/LightmapTextureManager;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;)V",
                    shift = At.Shift.AFTER
            )
    )
    private void renderWorldLast(CallbackInfo ci, @Local MatrixStack matricies) {
        Vec3d cameraPos = MinecraftClient.getInstance().gameRenderer.getCamera().getPos();
        matricies.push();
        matricies.translate(-cameraPos.getX(), -cameraPos.getY(), -cameraPos.getZ());
        PostProcessHandler.renderLast(matricies);
        matricies.pop();
    }

    @ModifyReceiver(
            method = "loadPrograms",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    remap = false,
                    ordinal = 0
            )
    )
    private List<Pair<ShaderProgram, Consumer<ShaderProgram>>> registerShaders(List<Pair<ShaderProgram, Consumer<ShaderProgram>>> instance, Object e, ResourceFactory factory) throws IOException {
        LodestoneShaders.init(factory);
        instance.addAll(LodestoneShaders.shaderList);
        return instance;
    }

    @Inject(
            method = "onResized",
            at = @At("HEAD")
    )
    public void injectionResizeListener(int width, int height, CallbackInfo ci) {
        PostProcessHandler.resize(width, height);
    }
}
