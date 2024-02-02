package team.lodestar.lodestone.mixin.accessor;

import net.minecraft.client.render.RenderLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RenderLayer.MultiPhase.class)
public interface RenderLayer$MultiPhaseAccessor {
    @Accessor
    RenderLayer.MultiPhaseParameters getPhases();
}
