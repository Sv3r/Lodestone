package team.lodestar.lodestone.mixin;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.particle.ParticleTextureSheet;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import team.lodestar.lodestone.systems.rendering.particle.LodestoneWorldParticleTextureSheet;
import java.util.List;

@Mixin(ParticleManager.class)
public abstract class ParticleManagerMixin {
    @Mutable
    @Final
    @Shadow
    private static List<ParticleTextureSheet> PARTICLE_TEXTURE_SHEETS;

    static {
        PARTICLE_TEXTURE_SHEETS = ImmutableList.<ParticleTextureSheet>builder().addAll(PARTICLE_TEXTURE_SHEETS)
                                               .add(
                                                       LodestoneWorldParticleTextureSheet.ADDITIVE,
                                                       LodestoneWorldParticleTextureSheet.TRANSPARENT,
                                                       LodestoneWorldParticleTextureSheet.LUMITRANSPARENT
                                               )
                                               .build();
    }
}
