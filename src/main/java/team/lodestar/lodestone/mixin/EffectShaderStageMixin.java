package team.lodestar.lodestone.mixin;


import net.minecraft.client.gl.EffectShaderStage;
import net.minecraft.client.gl.GlImportProcessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import team.lodestar.lodestone.systems.postprocess.LodestoneGlslPreprocessor;

@Mixin(EffectShaderStage.class)
public abstract class EffectShaderStageMixin {
    @ModifyArg(
            method = "createFromResource",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gl/EffectShaderStage;load(Lnet/minecraft/client/gl/ShaderStage$Type;Ljava/lang/String;Ljava/io/InputStream;Ljava/lang/String;Lnet/minecraft/client/gl/GlImportProcessor;)I"
            ),
            index = 4
    )
    private static GlImportProcessor useCustomPreprocessor(GlImportProcessor org) {
        return new LodestoneGlslPreprocessor();
    }
}
