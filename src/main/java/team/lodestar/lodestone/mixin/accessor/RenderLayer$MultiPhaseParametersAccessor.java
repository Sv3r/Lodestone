package team.lodestar.lodestone.mixin.accessor;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RenderLayer.MultiPhaseParameters.class)
public interface RenderLayer$MultiPhaseParametersAccessor {
    @Accessor
    RenderPhase.Transparency getTransparency();

    @Accessor
    RenderPhase.ShaderProgram getProgram();

    @Accessor
    RenderPhase.TextureBase getTexture();
}
