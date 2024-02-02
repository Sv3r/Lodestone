package team.lodestar.lodestone.mixin;

import net.fabricmc.fabric.impl.client.rendering.FabricShaderProgram;
import net.minecraft.client.gl.ShaderProgram;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import team.lodestar.lodestone.systems.rendering.ExtendedShaderProgram;

@Mixin(ShaderProgram.class)
abstract class ShaderProgramMixin {
    @Shadow
    @Final
    private String name;

    // Allow loading our ShaderPrograms from arbitrary namespaces.
    @ModifyArg(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/Identifier;<init>(Ljava/lang/String;)V"
            )
    )
    private String modifyProgramId(String id) {
        if ((Object) this instanceof ExtendedShaderProgram) {
            return FabricShaderProgram.rewriteAsId(id, name);
        }

        return id;
    }
}
