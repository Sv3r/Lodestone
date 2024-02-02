package team.lodestar.lodestone.systems.rendering.particle.type;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.impl.client.particle.FabricSpriteProviderImpl;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.particle.ParticleType;
import team.lodestar.lodestone.systems.rendering.particle.world.GenericParticle;
import team.lodestar.lodestone.systems.rendering.particle.world.WorldParticleEffect;

public class LodestoneParticleType extends ParticleType<WorldParticleEffect> {
    public LodestoneParticleType() {
        super(false, WorldParticleEffect.DESERIALIZER);
    }

    @Override
    public boolean shouldAlwaysSpawn() {
        return true;
    }

    @Override
    public Codec<WorldParticleEffect> getCodec() {
        return WorldParticleEffect.codecFor(this);
    }

    @Override
    public PacketCodec<RegistryByteBuf, WorldParticleEffect> getPacketCodec() {
        return WorldParticleEffect.packetCodecFor(this);
    }

    public record Factory(SpriteProvider sprite) implements ParticleFactory<WorldParticleEffect> {
        @Override
        public Particle createParticle(WorldParticleEffect data, ClientWorld world, double x, double y, double z, double mx, double my, double mz) {
            return new GenericParticle(world, data, (FabricSpriteProviderImpl) sprite, x, y, z, mx, my, mz);
        }
    }
}
