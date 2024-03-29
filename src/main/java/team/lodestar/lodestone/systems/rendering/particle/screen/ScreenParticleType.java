package team.lodestar.lodestone.systems.rendering.particle.screen;

import net.minecraft.world.World;
import team.lodestar.lodestone.systems.rendering.particle.screen.base.ScreenParticle;

public class ScreenParticleType<T extends ScreenParticleEffect> {

    public Factory<T> factory;

    public ScreenParticleType() {
    }

    public interface Factory<T extends ScreenParticleEffect> {
        ScreenParticle createParticle(World clientWorld, T options, double pX, double pY, double pXSpeed, double pYSpeed);
    }
}
