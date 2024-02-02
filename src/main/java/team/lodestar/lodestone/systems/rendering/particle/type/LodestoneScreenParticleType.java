package team.lodestar.lodestone.systems.rendering.particle.type;

import net.fabricmc.fabric.impl.client.particle.FabricSpriteProviderImpl;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.world.World;
import team.lodestar.lodestone.systems.rendering.particle.screen.GenericScreenParticle;
import team.lodestar.lodestone.systems.rendering.particle.screen.ScreenParticleEffect;
import team.lodestar.lodestone.systems.rendering.particle.screen.ScreenParticleType;
import team.lodestar.lodestone.systems.rendering.particle.screen.base.ScreenParticle;

public class LodestoneScreenParticleType extends ScreenParticleType<ScreenParticleEffect> {

    public LodestoneScreenParticleType() {
        super();
    }

    public record Factory(SpriteProvider sprite) implements ScreenParticleType.Factory<ScreenParticleEffect> {

        @Override
        public ScreenParticle createParticle(World clientWorld, ScreenParticleEffect options, double pX, double pY, double pXSpeed, double pYSpeed) {
            return new GenericScreenParticle(
                    (ClientWorld) clientWorld,
                    options,
                    (FabricSpriteProviderImpl) sprite,
                    pX,
                    pY,
                    pXSpeed,
                    pYSpeed
            );
        }
    }
}
